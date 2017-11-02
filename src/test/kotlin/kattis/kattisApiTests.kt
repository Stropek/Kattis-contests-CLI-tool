package kattis

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock

import khttp.responses.Response
import org.jsoup.Jsoup

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

import org.jsoup.nodes.Document

internal class KattisApiTests {
    companion object {
        @BeforeAll fun setUp() {
        }

        @AfterAll fun tearDown() {
        }
    }

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
            on { getNewContestDocument() } doReturn mockDocument
        }
        val api = KattisApi(mockRepository)

        // when
        val result = api.createBlankContest()

        // then
        assertEquals("\"1234567890\"", result.csrfToken)
    }
}