package pt.isel.tds.ttt.ui

import pt.isel.tds.ttt.model.BOARD_SIZE
import pt.isel.tds.ttt.model.Game
import pt.isel.tds.ttt.model.Position

// Separator line precomputed for efficiency
private val separator = "---+".repeat(BOARD_SIZE-1)+"---"

/**
 * Shows the board of the game and the current turn.
 * The board is shown as a grid in format:
 *  X | O | X
 * ---+---+---
 *    | X | O
 * ---+---+---
 *  X | O |
 */
fun Game.show() {
    Position.values.forEach { pos ->
        print(" ${board.moves[pos] ?: ' '} ")
        if (pos.col == BOARD_SIZE - 1) {
            println()
            if (pos.row < BOARD_SIZE-1) println(separator)
        } else
            print("|")
    }
    println("turn: ${board.turn}")
}