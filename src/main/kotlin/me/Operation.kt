package me


class Operation(private val boardSize: Int){

    var order: Boolean = true
    var matrix = Matrix(boardSize).makeSquaredMatrix()

    fun changeOrder(){
        order = !order
    }

    private fun checkFiveStone(
        matrix: List<MutableList<Int>>,
        dRow: Int,
        dCol: Int,
        row: Int,
        col: Int,
        player: Player
    ): Boolean {
        for (oneCell in 1..4) {
            if (matrix[row + (dRow * oneCell)][col + (dCol * oneCell)] != player.color)  return false
        }
        return true //누군가가 이겼다.
    }
    fun checkWinnerDirection(
        matrix: List<MutableList<Int>>, //리스트 <Int> 를 size 바꿀 수 있는 방법?-> class
        boardSize: Int,
        player: Player
    ): Boolean {
        for (row in matrix.indices) {
            for (col in matrix.indices) {
                if (matrix[row][col] != player.color) continue
                if (col + 4 < boardSize && checkFiveStone(matrix, 0, 1, row, col, player)) return true
                if (row + 4 < boardSize && checkFiveStone(matrix, 1, 0, row, col, player)) return true
                if (col - 4 >= 0 && row - 4 >= 0 && checkFiveStone(matrix, -1, -1, row, col, player)) return true
                if (col + 4 <= boardSize && row - 4 >= 0 && checkFiveStone(matrix, -1, 1, row, col, player)) return true
            }
        }
        return false
    }
}