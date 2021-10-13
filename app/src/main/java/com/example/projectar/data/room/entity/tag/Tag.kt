package com.example.projectar.data.room.entity.tag

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a tag which another entity can have.
 */
@Entity
data class Tag(
    @PrimaryKey val tagId: Long,
)
