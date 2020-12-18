package ru.wtw.mediawikibrowser.Model

enum class ViewType(val type: Int) {
    UPDATE_TYPE(0), ARTICLE_TYPE(1);

    companion object {
        fun fromValue(type: Int): ViewType {
            return when (type) {
                0 -> UPDATE_TYPE
                1 -> ARTICLE_TYPE
                else -> throw IllegalArgumentException("$type is not a valid value for ViewType")
            }
        }
    }
}
