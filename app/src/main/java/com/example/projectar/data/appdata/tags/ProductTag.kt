package com.example.projectar.data.appdata.tags

import androidx.annotation.StringRes
import com.example.projectar.R

private const val TAG_ID_BED = 0L
private const val TAG_ID_CHAIR = 1L
private const val TAG_ID_TABLE = 2L
private const val TAG_ID_LAMP = 3L
private const val TAG_ID_SOFA = 4L
private const val TAG_ID_SURFACE = 5L

/**
 * All tags which can be used for Products of the application.
 * Saved into the database with the ids defined here.
 *
 * All tags need to define a resource id, which returns the appropriate string for the tag.
 */
enum class ProductTag {

    BED {
        override fun id(): Long = TAG_ID_BED

        @StringRes
        override fun resourceStringId(): Int = R.string.tag_bed
    },
    CHAIR {
        override fun id(): Long = TAG_ID_CHAIR

        @StringRes
        override fun resourceStringId(): Int = R.string.tag_chair
    },
    TABLE {
        override fun id(): Long = TAG_ID_TABLE

        @StringRes
        override fun resourceStringId(): Int = R.string.tag_table
    },
    LAMP {
        override fun id(): Long = TAG_ID_LAMP

        @StringRes
        override fun resourceStringId(): Int = R.string.tag_lamp
    },
    SOFA {
        override fun id(): Long = TAG_ID_SOFA

        @StringRes
        override fun resourceStringId(): Int = R.string.tag_sofa
    },
    SURFACE {
        override fun id(): Long = TAG_ID_SURFACE

        @StringRes
        override fun resourceStringId(): Int = R.string.tag_surface
    };

    abstract fun id(): Long
    abstract fun resourceStringId(): Int
}

