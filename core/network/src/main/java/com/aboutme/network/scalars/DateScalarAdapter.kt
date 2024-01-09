package com.aboutme.network.scalars

import com.apollographql.apollo3.api.Adapter
import com.apollographql.apollo3.api.CustomScalarAdapters
import com.apollographql.apollo3.api.json.JsonReader
import com.apollographql.apollo3.api.json.JsonWriter
import java.time.LocalDate

internal object DateScalarAdapter: Adapter<LocalDate> {

    override fun fromJson(
        reader: JsonReader,
        customScalarAdapters: CustomScalarAdapters
    ): LocalDate {
        return LocalDate.parse(reader.nextString())
    }

    override fun toJson(
        writer: JsonWriter,
        customScalarAdapters: CustomScalarAdapters,
        value: LocalDate
    ) {
        writer.value(value.toString())
    }

}