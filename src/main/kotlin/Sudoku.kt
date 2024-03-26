package org.example

import java.util.*
import kotlin.NoSuchElementException
import kotlin.random.Random

class Sudoku {
    val grid : Array<Array<Int>>

    constructor(grid: Array<Array<Int>>){
        require(grid.size == 9 && grid.all { it.size == 9 }) { "Invalid Sudoku grid size" }
        this.grid = grid
    }

    constructor(dif: String){
        val grid = Array(9){Array(9){0} }
        this.grid = createSudoku(grid, dif)
    }

    private fun createSudoku(grid: Array<Array<Int>>, dif:String = "easy"): Array<Array<Int>>{

        val numToRemove = when (dif.lowercase(Locale.getDefault())) {
            "easy" -> 40
            "medium" -> 50
            "hard" -> 55
            "expert" -> 60
            else -> throw IllegalArgumentException("Invalid difficulty level")
        }

        solveWithGrid(grid)
        removeCells(grid,numToRemove)

        return grid

    }


    private fun removeCells(grid: Array<Array<Int>>, numToRemove: Int = 45) {

        repeat(numToRemove) {
            var row = Random.nextInt(0, 9)
            var col = Random.nextInt(0, 9)

            // Ensure the cell is not already empty
            while (grid[row][col] == 0) {
                row = Random.nextInt(0, 9)
                col = Random.nextInt(0, 9)
            }

            grid[row][col] = 0
        }
    }


    operator fun iterator():Iterator<Int>{
        return SudokuIterator(this)
    }


    private fun isValid(r: Int, c: Int, k: Int, grid: Array<Array<Int>>): Boolean{
        // checking row
        if (k in grid[r])
            return false

        // checking column
        for (i in 0 .. 8){
            if (k == grid[i][c])
                return false
        }

        //checking box
        for (i in (r/3 * 3) .. (r/3 * 3) + 2){
            for (j in (c/3 * 3) .. (c/3 * 3) + 2){
                if (k == grid[i][j])
                    return false
            }
        }

        return true
    }

    override fun toString(): String {
        var retVal : String = ""
        for (i in 0..8){
            retVal += "__"
        }
        retVal += "_\n"
        for (i in 0 ..< 9){
            retVal += "|"
            for (j in 0 ..< 8){
                retVal = retVal + grid[i][j] + " "
            }
            retVal = retVal + grid[i][8] + "|\n"
        }
        for (i in 0..8){
            retVal += "__"
        }
        retVal += "_\n"
        return retVal
    }

    fun solve():Boolean{
        return solveWithGrid(grid)
    }

    private fun solveWithGrid(grid: Array<Array<Int>>):Boolean{
        fun solveInternal(r:Int = 0, c: Int = 0): Boolean{
            if (r == 9){
                return true
            }
            else if(c == 9){
                return solveInternal(r+1, 0)
            }
            else if(grid[r][c] != 0){
                return  solveInternal(r, c+1)
            }
            else{
                val randNum = (1..9).shuffled()
                for (k in randNum){
                    if (isValid(r,c,k, grid)){
                        grid[r][c] = k
                        if (solveInternal(r,c+1))
                            return true
                        grid[r][c] = 0
                    }
                }
                return false
            }
        }

        return solveInternal(0,0)
    }

    fun isSolved(): Boolean {
        // Check rows
        for (row in 0 until 9) {
            val rowSet = mutableSetOf<Int>()
            for (col in 0 until 9) {
                if (grid[row][col] !in 1..9 || grid[row][col] in rowSet) {
                    return false
                }
                rowSet.add(grid[row][col])
            }
        }

        // Check columns
        for (col in 0 until 9) {
            val colSet = mutableSetOf<Int>()
            for (row in 0 until 9) {
                if (grid[row][col] !in 1..9 || grid[row][col] in colSet) {
                    return false
                }
                colSet.add(grid[row][col])
            }
        }

        // Check 3x3 subgrids
        for (startRow in 0 until 9 step 3) {
            for (startCol in 0 until 9 step 3) {
                val subgridSet = mutableSetOf<Int>()
                for (row in startRow until startRow + 3) {
                    for (col in startCol until startCol + 3) {
                        if (grid[row][col] !in 1..9 || grid[row][col] in subgridSet) {
                            return false
                        }
                        subgridSet.add(grid[row][col])
                    }
                }
            }
        }

        return true
    }

}

class SudokuIterator(private val sudoku: Sudoku) : Iterator<Int> {
    private var row = 0
    private var col = 0

    override fun hasNext(): Boolean {
        return row < 9 && col < 9
    }

    override fun next(): Int {
        if (!hasNext()) throw NoSuchElementException()
        val value = sudoku.grid[row][col]
        col++
        if (col >= 9) {
            col = 0
            row++
        }
        return value
    }
}