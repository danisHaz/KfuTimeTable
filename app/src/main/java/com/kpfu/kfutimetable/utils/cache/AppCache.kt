package com.kpfu.kfutimetable.utils.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProfileImageEntity::class], version = 1, exportSchema = false)
abstract class AppCache : RoomDatabase() {
    abstract fun getProfileImageDao(): ProfileImageDao

    companion object {
        const val NAME = "appCache"
    }
}