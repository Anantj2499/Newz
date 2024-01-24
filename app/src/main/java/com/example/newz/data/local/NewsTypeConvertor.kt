package com.example.newz.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.newz.domain.model.Source

@ProvidedTypeConverter
class NewsTypeConvertor {
    @TypeConverter
    fun fromSource(source: Source): String {
        return "${source.id},${source.name}"
    }
    @TypeConverter
    fun toSource(source: String): Source {
        return source.split(",").let {
            Source(it[0], it[1])
        }
    }
}