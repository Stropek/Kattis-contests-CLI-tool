package kattis

import interfaces.IKattisRepository
import kattis.models.Contest
import kattis.models.Problem
import kattis.models.Team
import khttp.get
import khttp.post
import khttp.put
import khttp.responses.Response
import khttp.structures.cookie.CookieJar
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class KattisRepository : IKattisRepository {
    private val baseUrl = "http://open.kattis.com"
    private val headers = mapOf("User-Agent" to "kattis-cli-submit", "Content-Type" to "application/json")

    lateinit var authCookies: CookieJar

    override fun addTeamToContest(contest: Contest, team: Team) {
        val teamJson = contest.toData() + team.toData()
        val teamResponse = post("$baseUrl/ajax/session/team",
                cookies = authCookies,
                headers = headers,
                json = teamJson)

        println("Added team '${team.name}' to '${contest.name}' contest - status code: ${teamResponse.statusCode}")

        team.id = teamResponse.jsonObject
                .getJSONObject("response")
                .getJSONObject("team")
                .getString("team_id")

        for (member in team.members) {
            val memberJson = contest.toData() + team.toData() + mapOf("username" to member.trim(), "accepted" to false)
            val memberResponse = post("$baseUrl/ajax/session/team/member",
                    cookies = authCookies,
                    headers = headers,
                    json = memberJson)

            println("Added '$member' to '${team.name}' - status code: ${memberResponse.statusCode}")
        }
    }

    override fun addProblemToContest(contest: Contest, problem: Problem) {
        val json = contest.toData() + problem.toData()

        post("$baseUrl/ajax/session/problem",
                cookies = authCookies,
                headers = headers,
                json = json)
    }

    override fun getProblemsPage(page: Int, order: String, showUntried: Boolean, showTried: Boolean, showSolved: Boolean): Document {
        val params = mapOf("page" to page.toString(), "order" to order,
                "show_untried" to if (showUntried) "on" else "off",
                "show_tried" to if (showTried) "on" else "off",
                "show_solved" to if (showSolved) "on" else "off")

        return Jsoup.connect("$baseUrl/problems")
                .cookies(authCookies)
                .headers(headers)
                .data(params)
                .get()
    }

    override fun createNewContest(contest: Contest): JSONObject {
        val response = put("$baseUrl/ajax/session",
                cookies = authCookies,
                headers = headers,
                json = contest.toData())
        return response.jsonObject
    }

    override fun getNewContestPage(): Document {
        return Jsoup.connect("$baseUrl/new-contest")
                .cookies(authCookies)
                .headers(headers)
                .get()
    }

    override fun login(credentials: Map<String, String>): Response {
        val response = get("$baseUrl/login",
                data = credentials,
                headers = headers)
        authCookies = response.cookies
        return response
    }
}