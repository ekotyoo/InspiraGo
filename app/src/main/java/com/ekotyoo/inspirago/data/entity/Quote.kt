package com.ekotyoo.inspirago.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quote(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "bg_image_url") val bgImageUrl: String = "https://picsum.photos/300/500",
    @ColumnInfo(name = "is_bookmarked") val isBookmarked: Boolean = false
)
