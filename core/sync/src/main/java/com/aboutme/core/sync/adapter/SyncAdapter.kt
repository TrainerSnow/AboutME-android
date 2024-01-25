package com.aboutme.core.sync.adapter;

import com.aboutme.core.cache.dao.base.SyncableEntityAccessor
import com.aboutme.core.cache.entity.base.SyncableEntity
import com.aboutme.core.common.Response
import com.aboutme.network.dto.base.SyncableDto
import com.aboutme.network.dto.base.UpdateDto
import com.aboutme.network.source.base.SyncableDtoAccessor

/**
 * Base class for a Sync Adapter which takes care of the logic to synchronize singular entities from the network to the local database
 *
 * @param DbEntity The type of the Entity stored in the local database
 * @param NetworkDto The type of the DTO used to send and receive from/to the server
 * @param NetworkUpdateDto The type of the DTO used to update data on the server
 * @param Identifier The type of the identifier used to identify an entity/dto
 */
internal abstract class SyncAdapter<
        DbEntity : SyncableEntity,
        NetworkDto : SyncableDto,
        NetworkUpdateDto : UpdateDto,
        Identifier
        >(

    /**
     * The interface to the db
     */
    private val dbAccessor: SyncableEntityAccessor<DbEntity>,

    /**
     * The interface to the network
     */
    private val networkAccessor: SyncableDtoAccessor<NetworkDto, NetworkUpdateDto, Identifier>,

    /**
     * When two entities have been updated at the same timestamp, the adapter will use the local entity
     */
    private val ratherTakeLocal: Boolean = true
) {

    /**
     * Converts a db entity to the network representation
     * @return
     */
    abstract fun convertEntityToDto(entity: DbEntity): NetworkDto

    /**
     * Converts a db entity to the network representation
     */
    abstract fun convertEntityToUpdateDto(entity: DbEntity): NetworkUpdateDto

    /**
     * Converts a network dto to the database representation
     */
    abstract fun convertDtoToEntity(dto: NetworkDto, localId: Long?): DbEntity

    abstract suspend fun <T> networkCall(call: suspend (String) -> T): T

    suspend fun sync(entity: DbEntity?, dto: NetworkDto?, id: Identifier): AdapterResult {
        return if (entity == null && dto == null) {
            AdapterResult.None
        } else if (entity != null && dto == null) { // Only exists on client
            if (entity.deletedAt != null) { // Was deleted on client before syncing to server
                deleteDb(entity)
                AdapterResult.DeletedLocal
            } else { // Must be given to server
                //Entity is uploaded to the server. There, it receives a 'remoteId' (UUID).
                // To have this take effect on the client, we need to update the db with the result of the server upload
                val response = insertNetwork(entity, id)
                val createdDto = (response as? Response.Success<NetworkDto>)?.data
                    ?: return AdapterResult.AddedServer

                updateDb(createdDto, entity.localId!!)
                AdapterResult.AddedServer
            }
        } else if (dto != null && entity == null) { // Only exists on server
            insertDb(dto)
            AdapterResult.AddedLocal
        } else { // Exists on both
            entity!!
            dto!!

            if (entity.deletedAt != null) { // Was deleted on client
                if (entity.deletedAt!! > dto.updatedAt) { // Was deleted after last change was made on server
                    deleteDb(entity)
                    deleteNetwork(id)
                    AdapterResult.DeletedServer
                } else { // Was deleted before last change made on server
                    updateDb(dto, entity.localId)
                    AdapterResult.UpdatedLocal
                }
            } else { // Was not deleted on client
                if (entity.updatedAt > dto.updatedAt) { // Client was more recently updated
                    updateNetwork(entity, id)
                    AdapterResult.UpdatedServer
                } else if (entity.updatedAt < dto.updatedAt) { // Server was more recently updates
                    updateDb(dto, entity.localId)
                    AdapterResult.UpdatedLocal
                } else {
                    if (ratherTakeLocal) {
                        updateNetwork(entity, id)
                        AdapterResult.UpdatedServer
                    } else {
                        updateDb(dto, entity.localId)
                        AdapterResult.UpdatedLocal
                    }
                }
            }
        }
    }

    private suspend fun insertDb(dto: NetworkDto) {
        dbAccessor.insert(convertDtoToEntity(dto, null))
    }

    private suspend fun updateDb(dto: NetworkDto, localId: Long?) {
        dbAccessor.update(convertDtoToEntity(dto, localId))
    }

    private suspend fun insertNetwork(entity: DbEntity, id: Identifier) = networkCall {
        networkAccessor.insert(id, convertEntityToDto(entity), it)
    }

    private suspend fun updateNetwork(entity: DbEntity, id: Identifier) {
        networkCall {
            networkAccessor.update(id, convertEntityToUpdateDto(entity), it)
        }
    }

    private suspend fun deleteDb(entity: DbEntity) {
        dbAccessor.delete(entity)
    }

    private suspend fun deleteNetwork(id: Identifier) {
        networkCall {
            networkAccessor.delete(id, it)
        }
    }

}