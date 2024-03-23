import org.example.Sudoku
import org.junit.jupiter.api.Test

class SudokuTest {

    @Test
    fun basicTest(){

        val grid = arrayOf(
            arrayOf(3, 0, 6, 5, 0, 8, 4, 0, 0),
            arrayOf(5, 2, 0, 0, 0, 0, 0, 0, 0),
            arrayOf(0, 8, 7, 0, 0, 0, 0, 3, 1),
            arrayOf(0, 0, 3, 0, 1, 0, 0, 8, 0),
            arrayOf(9, 0, 0, 8, 6, 3, 0, 0, 5),
            arrayOf(0, 5, 0, 0, 9, 0, 6, 0, 0),
            arrayOf(1, 3, 0, 0, 0, 0, 2, 5, 0),
            arrayOf(0, 0, 0, 0, 0, 0, 0, 7, 4),
            arrayOf(0, 0, 5, 2, 0, 6, 3, 0, 0)
        )
        val test = Sudoku(grid)
        println(test)

        test.solve()
        println(test)


    }
}