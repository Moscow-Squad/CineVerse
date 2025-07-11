package com.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromIntList(value: List<Int>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toIntList(value: String?): List<Int> {
        return try {
            gson.fromJson(value, object : TypeToken<List<Int>>() {}.type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}