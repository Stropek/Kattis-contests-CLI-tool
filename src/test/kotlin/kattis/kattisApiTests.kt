package kattis

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.xenomachina.argparser.ArgParser
import interfaces.IFileReader
import interfaces.IKattisRepository
import kattis.models.Contest
import kattis.models.Problem
import kattis.models.Team
import khttp.responses.Response
import org.json.JSONObject
import org.jsoup.Jsoup
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito

internal class KattisApiTests {
    @Test fun `addTeamsToContest - list of teams - calls addTeamToContest for each team`() {
        // given
        val contest = Contest("contest_1")
        val teams = listOf(Team("t1", listOf()), Team("t2", listOf()))
        val mockRepository = mock<IKattisRepository>()
        val api = KattisApi(mockRepository)

        // when
        api.addTeamsToContest(contest, teams)

        // then
        for (team in teams) {
            Mockito.verify(mockRepository).addTeamToContest(contest, team)
        }
    }
    @Test fun `addProblemsToContest - list of problems - calls addProblemToContest for each problem`() {
        // given
        val contest = Contest("contest_1")
        val problems = listOf(Problem("p1", 1.0), Problem("p2", 2.5))
        val mockRepository = mock<IKattisRepository>()
        val api = KattisApi(mockRepository)

        // when
        api.addProblemsToContest(contest, problems)

        // then
        for (problem in problems) {
            Mockito.verify(mockRepository).addProblemToContest(contest, problem)
        }
    }
    @Test fun `getRandomProblems(1) - html with problems - returns a list with 1 problem`() {
        // given
        val mockHtml = ProblemsListHtml.Builder().withProblems(5).build()
        val mockRepository = mock<IKattisRepository> {
            on { getProblemsPage(0) } doReturn Jsoup.parse(mockHtml)
        }
        val api = KattisApi(mockRepository)

        // when
        val result = api.getRandomProblems(1)

        // then
        assertEquals(1, result.size)
    }
    @Test fun `getRandomProblems(2) - 2 html pages with problems - returns a list with 2 problems`() {
        // given
        var mockHtml_1 = ProblemsListHtml.Builder().withProblems(5, 2.0).build()
        var mockHtml_2 = ProblemsListHtml.Builder().withProblems(5, 3.0).build()
        val mockRepository = mock<IKattisRepository> {
            on { getProblemsPage(0) } doReturn Jsoup.parse(mockHtml_1)
            on { getProblemsPage(1) } doReturn Jsoup.parse(mockHtml_2)
        }
        val api = KattisApi(mockRepository)

        // when
        val result = api.getRandomProblems(2)

        // then
        assertEquals(2, result.size)
    }
    @Test fun `getRandomProblems(1, 3d) - 3 html pages - 3rd one has minimum difficulty level - returns a list with 1 problem from 3rd page`() {
        // given
        var mockHtml_1 = ProblemsListHtml.Builder().withProblems(5, 1.0).build()
        var mockHtml_2 = ProblemsListHtml.Builder().withProblems(5, 2.0).build()
        var mockHtml_3 = ProblemsListHtml.Builder().withProblems(5, 3.0).build()
        val mockRepository = mock<IKattisRepository> {
            on { getProblemsPage(0) } doReturn Jsoup.parse(mockHtml_1)
            on { getProblemsPage(1) } doReturn Jsoup.parse(mockHtml_2)
            on { getProblemsPage(2) } doReturn Jsoup.parse(mockHtml_3)
        }
        val api = KattisApi(mockRepository)

        // when
        val result = api.getRandomProblems(1, 3.0)

        // then
        assertEquals(1, result.size)
        assertEquals(3.0, result.first().difficulty)
    }
    @Test fun `createContest() - html with contest data - sets csrf token and short name`() {
        // given
        val args = arrayOf<String>()
        val reader = mock<IFileReader>()
        val command = Command(KattisCliArgs(ArgParser(args)), reader)
        val mockHtml = """
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
        val mockJson = """{
            |   "success": true,
            |   "response": {
            |       "redirect": "/problems/short_name"
            |   }
            |}""".trimMargin()
        val mockRepository = mock<IKattisRepository> {
            on { getNewContestPage() } doReturn Jsoup.parse(mockHtml)
            on { createNewContest(any()) } doReturn JSONObject(mockJson)
        }
        val api = KattisApi(mockRepository)

        // when
        val result = api.createContest(command)

        // then
        assertEquals("1234567890", result.csrfToken)
        assertEquals("short_name", result.shortName)
    }
    @Test fun `login() - valid response from repo - returns successful login message`() {
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
}

class ProblemsListHtml {
    class Builder {
        private var problems: MutableList<String> = mutableListOf()

        fun build(): String {
            return """<html>
                |   <table class="problem_list">
                |       <tbody>
                |           ${problems.joinToString()}
                |       </tbody>
                |   </table>
                |</html>""".trimMargin()
        }

        fun withProblems(number: Int, difficulty: Double? = null): ProblemsListHtml.Builder {
            for (it in 0..number) {
                problems.add(getProblemRow("Problem_$it", difficulty ?: it.toDouble()))
            }
            return this
        }

        private fun getProblemRow(name: String, difficulty: Double? = 2.5): String {
            return """<tr>
                |   <td><a href="/problems/$name">display name</a></td>
                |   <td />
                |   <td />
                |   <td />
                |   <td />
                |   <td />
                |   <td />
                |   <td />
                |   <td>$difficulty</td>
                |   <td />
                |   <td />
                </tr>""".trimMargin()
        }
    }
}

