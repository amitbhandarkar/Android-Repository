package com.amit.nasaa.pod.ui.main.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apod")
data class APODEntity(
    @ColumnInfo(name = "copyright")
    val copyright: String,
    @PrimaryKey
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "explanation")
    val explanation: String,
    @ColumnInfo(name = "hdurl")
    val hdurl: String?,
    @ColumnInfo(name = "media_type")
    val media_type: String,
    @ColumnInfo(name = "service_version")
    val service_version: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "url")
    val url: String
)