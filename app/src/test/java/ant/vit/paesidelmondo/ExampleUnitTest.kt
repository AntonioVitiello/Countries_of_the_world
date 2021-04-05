package ant.vit.paesidelmondo

import ant.vit.paesidelmondo.tools.Utils
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun isOdd_isCorrect() {
        assertEquals(Utils.isOdd(1), false)
        assertEquals(Utils.isOdd(2), true)
    }
}