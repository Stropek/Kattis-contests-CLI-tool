package kattis

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock

import khttp.responses.Response
import org.jsoup.Jsoup

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

internal class KattisApiTests {
    @Test fun `login() given valid response from kattis repository should return auth cookie`() {
        // given
        val mockResponse = mock<Response> {
            on { text } doReturn "Login successful!"
        }
        val mockRepository = mock<IKattisRepository> {
            on { login(any()) } doReturn mockResponse
        }
        val api = KattisApi(mockRepository)

        // when
        val result = api.login("user", "token")

        // then
        assertEquals("Login successful!", result)
    }
    @Test fun `createBlankContest() given valid document from kattis repository should return new contest object`() {
        // given
        val html = """
            |<html>
            |  <head></head>
            |  <body>
            |    <form>
            |    </form>
            |    <script type="text/javascript">
            |       jQuery.extend(jQuery.ns('Kattis.views.contest.edit.data'), {
            |           is_privileged: false,
            |           existing: false,
            |           short_name: "",
            |           csrf_token: "1234567890",
            |           judges: [],
            |           teams: []
            |       });
            |    </script>
            |  </body>
            |</html>
        """.trimMargin()
        val mockDocument = Jsoup.parse(html)
        val mockRepository = mock<IKattisRepository> {
            on { getNewContestPage() } doReturn mockDocument
        }
        val api = KattisApi(mockRepository)

        // when
        val result = api.createBlankContest()

        // then
        assertEquals("1234567890", result.csrfToken)
    }
    @Test fun `getRandomProblems(1) given valid document from kattis repository should return a list with a single problem`() {
        // given
        val html = """<tr>
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
        |<tr>
        |   <td><a href="/problems/problem_2">Other problem</a></td>
        |   <td />
        |   <td />
        |   <td />
        |   <td />
        |   <td />
        |   <td />
        |   <td />
	    |   <td>1.5</td>
        |   <td />
        |   <td />
        |</tr>
        |<tr>
        |   <td><a href="/problems/problem_3">Another problem</a></td>
        |   <td />
        |   <td />
        |   <td />
        |   <td />
        |   <td />
        |   <td />
        |   <td />
	    |   <td>2.0</td>
        |   <td />
        |   <td />
        |</tr>""".trimMargin()
        val mockDocument = Jsoup.parse(html)
        val mockRepository = mock<IKattisRepository> {
            on { getProblemsPage() } doReturn mockDocument
        }
        val api = KattisApi(mockRepository)

        // when
        val result = api.getRandomProblems(1)

        // then
        assertEquals(1, result.count())
    }

    //TODO: add a lot more test cases
}