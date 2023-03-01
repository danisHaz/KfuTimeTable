package com.kpfu.kfutimetable.utils.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ImageTable")
class ProfileImageEntity(
    @PrimaryKey
    val imageId: Int,
    @ColumnInfo(name="image", typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray
)