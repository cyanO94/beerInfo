package com.example.beerInfo.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "beer_table")
data class Beer(@PrimaryKey val id: Int,
                val name: String,
                val tagline: String,
                val image_url: String,
                val description: String,
                var isBookmarked: Boolean = false
) : Parcelable
