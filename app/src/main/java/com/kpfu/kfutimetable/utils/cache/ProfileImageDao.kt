package com.kpfu.kfutimetable.utils.cache

import androidx.room.*

@Dao
interface ProfileImageDao {

    @Query("select * from ImageTable")
    suspend fun getProfileImage(): List<ProfileImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProfileImage(image: ProfileImageEntity)

    @Query("delete from ImageTable")
    suspend fun clearProfileImage()
}