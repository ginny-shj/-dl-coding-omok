package me


data class Matrix(val size: Int){

    fun makeSquaredMatrix(): List<MutableList<Int>> {
        return List(size) { MutableList(size) { 0 } }
    }

}