package com.aboutme.core.sync.test;

import com.aboutme.core.sync.adapter.SyncAdapter
import com.aboutme.core.sync.test.mock.MockDatabaseAccessor
import com.aboutme.core.sync.test.mock.MockDtoAccessor
import com.aboutme.core.sync.test.mock.TestDto
import com.aboutme.core.sync.test.mock.TestEntity
import com.aboutme.core.sync.test.mock.UpdateTestDto
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.time.Instant
import java.time.temporal.ChronoUnit

class SyncAdapterTest {

    private val dbAccessor = MockDatabaseAccessor()

    private val dtoAccessor = MockDtoAccessor()

    private val syncAdapter = object :
        SyncAdapter<TestEntity, TestDto, UpdateTestDto, Long>(
            dbAccessor,
            dtoAccessor,
            { it("[placeholder_token]") },
            true
        ) {

        override fun convertEntityToDto(entity: TestEntity) =
            TestDto(entity.id, entity.content, entity.createdAt, entity.updatedAt)

        override fun convertEntityToUpdateDto(entity: TestEntity) =
            UpdateTestDto(entity.content, entity.updatedAt)

        override fun convertDtoToEntity(dto: TestDto) =
            TestEntity(dto.id, dto.content, dto.createdAt, null, dto.updatedAt)


    }

    @Test
    fun `SyncAdapter doesn't do anything with two null values`() {
        val entity: TestEntity? = null
        val dto: TestDto? = null

        runBlocking {
            syncAdapter.sync(entity, dto, -1)
        }

        Assert.assertEquals(0, dbAccessor.getAll().size)
        Assert.assertEquals(0, dtoAccessor.getAll().size)

        dbAccessor.reset()
        dtoAccessor.reset()
    }

    @Test
    fun `SyncAdapter will take the more recently updated value`() = runBlocking {
        val entity = TestEntity(
            id = 1,
            content = "entity.1",
            createdAt = Instant.now().minus(1, ChronoUnit.DAYS),
            deletedAt = null,
            updatedAt = Instant.now()
        )
        dbAccessor.insert(entity)

        val dto = TestDto(
            id = 1,
            content = "dto.1",
            createdAt = Instant.now().minus(2, ChronoUnit.DAYS),
            updatedAt = Instant.now().minus(1, ChronoUnit.DAYS)
        )
        dtoAccessor.insert(dto.id, dto, "")

        syncAdapter.sync(entity, dto, entity.id)

        Assert.assertTrue(dbAccessor.getAll().first().content == "entity.1")
        Assert.assertTrue(dtoAccessor.getAll().first().content == "entity.1")

        dtoAccessor.reset()
        dbAccessor.reset()
    }

    @Test
    fun `SyncAdapter can sync multiple values`() = runBlocking {
        val entities = (1..5).map { index ->
            TestEntity(
                id = index.toLong(),
                content = "entity.$index",
                createdAt = Instant.now().minus(10, ChronoUnit.DAYS),
                deletedAt = null,
                //First 1 day ago, second 2 days ago, third 3 days ago, fourth 4 days ago, fifth 5 days ago
                updatedAt = Instant.now().minus(index.toLong(), ChronoUnit.DAYS)
            )
        }
        entities.forEach {
            dbAccessor.insert(it)
        }

        val dtos = (1..5).map { index ->
            TestDto(
                id = index.toLong(),
                content = "dto.$index",
                createdAt = Instant.now().minus(10, ChronoUnit.DAYS),
                //First 4 days ago, second 3 days ago, third 2 days ago, fourth 1 day ago, fifth 0 days ago
                updatedAt = Instant.now().minus(5, ChronoUnit.DAYS)
                    .plus(index.toLong(), ChronoUnit.DAYS)
            )
        }
        dtos.forEach {
            dtoAccessor.insert(it.id, it, "")
        }

        entities.forEach { entity ->
            val dto = dtos.find { it.id == entity.id }
            syncAdapter.sync(entity, dto, entity.id)
        }

        Assert.assertTrue(dbAccessor.getAll().find { it.id == 1L }?.content == "entity.1")
        Assert.assertTrue(dtoAccessor.getAll().find { it.id == 1L }?.content == "entity.1")

        Assert.assertTrue(dbAccessor.getAll().find { it.id == 2L }?.content == "entity.2")
        Assert.assertTrue(dtoAccessor.getAll().find { it.id == 2L }?.content == "entity.2")

        Assert.assertTrue(dbAccessor.getAll().find { it.id == 3L }?.content == "dto.3")
        Assert.assertTrue(dtoAccessor.getAll().find { it.id == 3L }?.content == "dto.3")

        Assert.assertTrue(dbAccessor.getAll().find { it.id == 4L }?.content == "dto.4")
        Assert.assertTrue(dtoAccessor.getAll().find { it.id == 4L }?.content == "dto.4")

        Assert.assertTrue(dbAccessor.getAll().find { it.id == 5L }?.content == "dto.5")
        Assert.assertTrue(dtoAccessor.getAll().find { it.id == 5L }?.content == "dto.5")

        dbAccessor.reset()
        dtoAccessor.reset()
    }

    @Test
    fun `SyncAdapter takes the ratherTakeLocal setting into account`() = runBlocking {
        val entity = TestEntity(
            id = 1,
            content = "entity.1",
            createdAt = Instant.now().minus(1, ChronoUnit.DAYS),
            updatedAt = Instant.parse("2007-12-03T10:15:30.00Z"),
            deletedAt = null
        )
        dbAccessor.insert(entity)

        val dto = TestDto(
            id = 1,
            content = "dto.1",
            createdAt = Instant.now().minus(1, ChronoUnit.DAYS),
            updatedAt = Instant.parse("2007-12-03T10:15:30.00Z")
        )
        dtoAccessor.insert(dto.id, dto, "")

        syncAdapter.sync(entity, dto, entity.id)

        Assert.assertEquals("entity.1", dbAccessor.first().content)
        Assert.assertEquals("entity.1", dtoAccessor.first().content)

        dbAccessor.reset()
        dtoAccessor.reset()
    }

    @Test
    fun `SyncAdapter properly checks deleted timestamp`() = runBlocking {
        val entity1 = TestEntity(
            id = 1,
            content = "entity.1",
            createdAt = Instant.now().minus(1, ChronoUnit.DAYS),
            updatedAt = Instant.now().minus(12, ChronoUnit.HOURS),
            deletedAt = Instant.now().minus(12, ChronoUnit.HOURS)
        )
        val entity2 = TestEntity(
            id = 2,
            content = "entity.2",
            createdAt = Instant.now().minus(1, ChronoUnit.DAYS),
            updatedAt = Instant.now().minus(1, ChronoUnit.DAYS),
            deletedAt = Instant.now()
        )
        dbAccessor.insert(entity1)
        dbAccessor.insert(entity2)

        val dto1 = TestDto(
            id = 1,
            content = "dto.1",
            createdAt = Instant.now().minus(1, ChronoUnit.DAYS),
            //Updated after entity1 was deleted and updated
            updatedAt = Instant.now()
        )
        val dto2 = TestDto(
            id = 2,
            content = "dto.2",
            createdAt = Instant.now().minus(1, ChronoUnit.DAYS),
            //Updated before entity2 was deleted and updated
            updatedAt = Instant.now().minus(2, ChronoUnit.DAYS)
        )
        dtoAccessor.insert(dto1.id, dto1, "")
        dtoAccessor.insert(dto2.id, dto2, "")

        syncAdapter.sync(entity1, dto1, entity1.id)
        syncAdapter.sync(entity2, dto2, entity2.id)

        Assert.assertEquals("dto.1", dbAccessor.getAll().find { it.id == 1L }?.content)
        Assert.assertEquals("dto.1", dtoAccessor.getAll().find { it.id == 1L }?.content)

        Assert.assertNull(dbAccessor.getAll().find { it.id == 2L })
        Assert.assertNull(dtoAccessor.getAll().find { it.id == 2L })

        dbAccessor.reset()
        dtoAccessor.reset()
    }


}