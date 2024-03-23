package org.example

import kotlin.random.Random

class Sudoku {
    private val grid : Array<Array<Int>>

    constructor(grid: Array<Array<Int>>){
        this.grid = grid
    }

//    constructor(){
//        this.grid = createSudoku()
//    }

//    private fun createSudoku(): Array<Array<Int>>{
//        val grid = Array(9){Array(9){0} }
//
//        for (i in 0 .. 8){
//            for (j in 0 .. 8){
//                var k = Random.nextInt(1, 10)
//                while (!isValid(i,j,k, grid)){
//                    k = Random.nextInt(1, 10)
//                }
//                grid[i][j] = k
//            }
//        }
//
//        return grid
//    }



    operator fun iterator():Sequence<Int> = sequence {
        for (i in 0 ..< 9){
            for (j in 0 ..< 9){
                yield(grid[i][j])
            }
        }

    }

    fun isValid(r: Int, c: Int, k: Int): Boolean{
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

        for (i in 0 ..< 9){
            retVal += "["
            for (j in 0 ..< 8){
                retVal = retVal + grid[i][j] + " "
            }
            retVal = retVal + grid[i][8] + "]\n"
        }
        return retVal
    }

    fun solve(){
        fun solveInternal(r:Int = 0, c: Int = 0 ): Boolean{
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
                for (k in 1..9){
                    if (isValid(r,c,k)){
                        grid[r][c] = k
                        if (solveInternal(r,c+1))
                            return true
                        grid[r][c] = 0
                    }
                }
                return false
            }
        }

        solveInternal()
    }

}