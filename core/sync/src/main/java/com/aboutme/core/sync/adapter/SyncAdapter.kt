package com.aboutme.core.sync.adapter;

import com.aboutme.core.database.dao.base.SyncableEntityAccessor
import com.aboutme.core.database.entity.base.SyncableEntity
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
internal abstract class SyncAdapter<DbEntity : SyncableEntity, NetworkDto : SyncableDto, NetworkUpdateDto : UpdateDto, Identifier>(
    /**
     * The interface to the db
     */
    private val dbAccessor: SyncableEntityAccessor<DbEntity>,

    /**
     * The interface to the network
     */
    private val networkAccessor: SyncableDtoAccessor<NetworkDto, NetworkUpdateDto, Identifier>,

    /**
     * Lambda to execute authenticated network calls
     */
    private val networkCall: suspend (suspend (String) -> Unit) -> Unit
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
    abstract fun convertDtoToEntity(dto: NetworkDto): DbEntity

    suspend fun sync(entity: DbEntity?, dto: NetworkDto?, id: Identifier) {
        if (entity == null && dto == null) return
        if (entity != null && dto == null) { // Only exists on client
            if (entity.deletedAt != null) { // Was deleted on client before syncing to server
                deleteDb(entity)
            } else { // Must be given to server
                insertNetwork(entity, id)
            }
        } else if (dto != null && entity == null) { // Only exists on server
            insertDb(dto)
        } else if (dto != null && entity != null) { // Exists on both
            if (entity.deletedAt != null) { // Was deleted on client
                if (entity.deletedAt!! > dto.updatedAt) { // Was deleted after last change was made on server
                    deleteDb(entity)
                    deleteNetwork(id)
                } else { // Was before last change made on server
                    updateDb(dto)
                }
            } else { // Was not deleted on client
                if (entity.updatedAt > dto.updatedAt) { // Client was more recently updated
                    updateNetwork(entity, id)
                } else { // Server was more recently updates
                    updateDb(dto)
                }
            }
        }
    }

    private suspend fun insertDb(dto: NetworkDto) {
        dbAccessor.insert(convertDtoToEntity(dto))
    }

    private suspend fun updateDb(dto: NetworkDto) {
        dbAccessor.update(convertDtoToEntity(dto))
    }

    private suspend fun insertNetwork(entity: DbEntity, id: Identifier) {
        networkCall {
            networkAccessor.insert(id, convertEntityToDto(entity), it)
        }
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