package me

data class Player (val color: Int) {

    fun getColor(integer: Int): String? {
        return when (integer) {
            1 -> "검은돌"
            2 -> "흰돌"
            else -> null
        }
    }
}