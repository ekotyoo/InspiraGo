package com.ekotyoo.inspirago.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quote(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "bg_image_url") val bgImageUrl: String?,
    @ColumnInfo(name = "is_bookmarked") val isBookmarked: Boolean = false
)
