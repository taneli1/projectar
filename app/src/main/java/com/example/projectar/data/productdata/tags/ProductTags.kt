package com.example.projectar.data.productdata.tags

private const val TAG_ID_BED = 0L
private const val TAG_ID_CHAIR = 1L
private const val TAG_ID_TABLE = 2L
private const val TAG_ID_LAMP = 3L
private const val TAG_ID_SOFA = 4L
private const val TAG_ID_SURFACE = 5L

/**
 * All tags for products of the application
 */
enum class ProductTags {

    BED {
        override fun id(): Long = TAG_ID_BED
        override fun resourceString(): Int = 0
    },
    CHAIR {
        override fun id(): Long = TAG_ID_CHAIR
        override fun resourceString(): Int = 0
    },
    TABLE {
        override fun id(): Long = TAG_ID_TABLE
        override fun resourceString(): Int = 0
    },
    LAMP {
        override fun id(): Long = TAG_ID_LAMP
        override fun resourceString(): Int = 0
    },
    SOFA {
        override fun id(): Long = TAG_ID_SOFA
        override fun resourceString(): Int = 0
    },
    SURFACE {
        override fun id(): Long = TAG_ID_SURFACE
        override fun resourceString(): Int = 0
    };

    abstract fun id(): Long
    abstract fun resourceString(): Int
}

