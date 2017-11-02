package kattis

import org.jsoup.Jsoup

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ProblemTests {
    @Test fun `parseRow() given a row contains valid problem url and difficulty level should return new problem object`() {
        // given
        val html = """<table>
        |<tr>
        |   <td><a href="/problems/problem_1">Some problem</a></td>
        |   <td />
        |   <td />
        |   <td />
        |   <td />
        |   <td />
        |   <td />
        |   <td />
	    |   <td>1.0</td>
        |   <td />
        |   <td />
        |</tr>
        |</table>"""
        val document = Jsoup.parse(html)
        val row = document.select("tr")[0]

        // when
        val result = Problem.parseRow(row)

        // then
        assertEquals("problem_1", result.name)
        assertEquals(1.0, result.difficulty)
    }
}