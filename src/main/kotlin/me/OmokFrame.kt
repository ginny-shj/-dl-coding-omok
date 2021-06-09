package me

import java.awt.Color
import java.awt.Graphics
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import kotlin.math.round

//TODO : 1) MVC or MVVM 만들기, 2) 심판, AI2 서버 만들어서 승률 UI

class OmokFrame : JFrame() {

    companion object {
        private const val STONE_SIZE: Int = 30
        private const val BOARD_SIZE: Int = 19
        private const val BLACK: Int = 1
        private const val WHITE: Int = 2
    }

    private val operation = Operation(BOARD_SIZE)
    private val matrix = operation.matrix
    private val players: List<Player> = listOf(Player(BLACK), Player(WHITE))
    private val popup = Popup()

    inner class OmokPanel : JPanel() {
        private var xClick: Int = -1
        private var yClick: Int = -1

        init {
            background = Color(248, 190, 100)
            addMouseListener(object : MouseAdapter() { //클래스 따로 빼기
                override fun mouseClicked(e: MouseEvent) {
                    if (validLocation(e.x, e.y)) {
                        val y = (round(e.x / STONE_SIZE.toDouble()) - 1).toInt()
                        val x = (round(e.y / STONE_SIZE.toDouble()) - 1).toInt()

                        if (matrix[x][y] == BLACK || matrix[x][y] == WHITE) {
                            popup.popupWarning()
                        } else {
                            if (operation.order) {
                                matrix[x][y] = BLACK
                            } else {
                                matrix[x][y] = WHITE
                            }
                            operation.changeOrder()
                            xClick = y
                            yClick = x
                        }
                    }
                    repaint()
                    for (i in players.indices) {
                        if (operation.checkWinnerDirection(matrix, BOARD_SIZE, players[i])) {
                            popup.popupWinner(players[i])
                        }
                    }
                }

                fun validLocation(x: Int, y: Int): Boolean {
                    return (STONE_SIZE * 1.5 <= x && x <= STONE_SIZE * (BOARD_SIZE - 0.5)
                            && STONE_SIZE * 1.5 <= y && y <= STONE_SIZE * (BOARD_SIZE - 0.5))
                }

            }
            )
        }

        override fun paintComponent(g: Graphics) {
            super.paintComponent(g)

            for (i in 0..BOARD_SIZE) {
                g.color = Color.BLACK
                g.drawLine(STONE_SIZE, STONE_SIZE * i, STONE_SIZE * BOARD_SIZE, STONE_SIZE * i) //x: 행, y: 열
                g.drawLine(STONE_SIZE * i, STONE_SIZE, STONE_SIZE * i, STONE_SIZE * BOARD_SIZE) //x: 행, y: 열

                for (x in 0 until BOARD_SIZE) {
                    for (y in 0 until BOARD_SIZE) {
                        when (matrix[y][x].toString()) {
                            1.toString() -> {
                                g.color = Color.BLACK
                                g.fillOval(
                                    x * STONE_SIZE + STONE_SIZE / 2,
                                    y * STONE_SIZE + STONE_SIZE / 2,
                                    STONE_SIZE - 1,
                                    STONE_SIZE - 1
                                )
                            }
                            2.toString() -> {
                                g.color = Color.WHITE
                                g.fillOval(
                                    x * STONE_SIZE + STONE_SIZE / 2,
                                    y * STONE_SIZE + STONE_SIZE / 2,
                                    STONE_SIZE - 1,
                                    STONE_SIZE - 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    init {
        this.title = "오목 게임"
        this.defaultCloseOperation = EXIT_ON_CLOSE
        val omokPanel = OmokPanel()
        contentPane = omokPanel
        this.setSize(650, 650)
        this.isVisible = true
    }
}