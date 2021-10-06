package com.example.projectar.data.appdata.tags

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
        override fun resourceStringId(): Int = 0
    },
    CHAIR {
        override fun id(): Long = TAG_ID_CHAIR
        override fun resourceStringId(): Int = 0
    },
    TABLE {
        override fun id(): Long = TAG_ID_TABLE
        override fun resourceStringId(): Int = 0
    },
    LAMP {
        override fun id(): Long = TAG_ID_LAMP
        override fun resourceStringId(): Int = 0
    },
    SOFA {
        override fun id(): Long = TAG_ID_SOFA
        override fun resourceStringId(): Int = 0
    },
    SURFACE {
        override fun id(): Long = TAG_ID_SURFACE
        override fun resourceStringId(): Int = 0
    };

    abstract fun id(): Long
    abstract fun resourceStringId(): Int
}

