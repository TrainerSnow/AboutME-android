package com.aboutme.network.scalars

import com.apollographql.apollo3.api.Adapter
import com.apollographql.apollo3.api.CustomScalarAdapters
import com.apollographql.apollo3.api.json.JsonReader
import com.apollographql.apollo3.api.json.JsonWriter
import java.time.Instant

internal object InstantScalarAdapter : Adapter<Instant> {
    override fun fromJson(reader: JsonReader, customScalarAdapters: CustomScalarAdapters) =
        Instant.parse(reader.nextString())

    override fun toJson(
        writer: JsonWriter,
        customScalarAdapters: CustomScalarAdapters,
        value: Instant
    ) {
        writer.value(value.toString())
    }
}