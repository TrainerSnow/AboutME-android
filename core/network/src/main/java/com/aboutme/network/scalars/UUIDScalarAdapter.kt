package com.aboutme.network.scalars

import com.apollographql.apollo3.api.Adapter
import com.apollographql.apollo3.api.CustomScalarAdapters
import com.apollographql.apollo3.api.json.JsonReader
import com.apollographql.apollo3.api.json.JsonWriter
import java.util.UUID

internal object UUIDScalarAdapter : Adapter<UUID> {

    override fun fromJson(reader: JsonReader, customScalarAdapters: CustomScalarAdapters): UUID =
        UUID.fromString(reader.nextString())

    override fun toJson(
        writer: JsonWriter,
        customScalarAdapters: CustomScalarAdapters,
        value: UUID
    ) {
        writer.value(value.toString())
    }

}