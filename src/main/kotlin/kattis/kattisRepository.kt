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
import mu.KotlinLogging
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

private val logger = KotlinLogging.logger {}

class KattisRepository : IKattisRepository {
    private val baseUrl = Config.KattisBaseUrl
    private val headers = mapOf("User-Agent" to "kattis-cli-submit", "Content-Type" to "application/json")

    private lateinit var authCookies: CookieJar

    override fun addTeamToContest(contest: Contest, team: Team) {
        val teamJson = contest.toData() + team.toData()

        logger.debug { "Adding team '${team.name}' to '${contest.name}' contest" }
        val teamResponse = post("$baseUrl/ajax/session/team",
                cookies = authCookies,
                headers = headers,
                json = teamJson)
        logger.debug { "Status code: ${teamResponse.statusCode}" }

        team.id = teamResponse.jsonObject
                .getJSONObject("response")
                .getJSONObject("team")
                .getString("team_id")

        for (member in team.members) {
            val memberJson = contest.toData() + team.toData() + mapOf("username" to member.trim(), "accepted" to false)

            logger.debug { "Adding $member to ${team.name} team" }
            val memberResponse = post("$baseUrl/ajax/session/team/member",
                    cookies = authCookies,
                    headers = headers,
                    json = memberJson)
            logger.debug { "Status code: ${memberResponse.statusCode}" }
        }
    }

    override fun addProblemToContest(contest: Contest, problem: Problem) {
        val json = contest.toData() + problem.toData()

        logger.debug { "Adding problem '${problem.name}' to '${contest.name}' contest" }
        val problemResponse = post("$baseUrl/ajax/session/problem",
                cookies = authCookies,
                headers = headers,
                json = json)
        logger.debug { "Status code: ${problemResponse.statusCode}" }
    }

    override fun getProblemsPage(page: Int, order: String, showUntried: Boolean, showTried: Boolean, showSolved: Boolean): Document {
        logger.debug { "Getting problems page #$page" }
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
        logger.debug { "Creating contest '${contest.name}'" }
        val response = put("$baseUrl/ajax/session",
                cookies = authCookies,
                headers = headers,
                json = contest.toData())
        logger.debug { "Status code: ${response.statusCode}" }

        return response.jsonObject
    }

    override fun getNewContestPage(): Document {
        logger.debug { "Getting new contest page" }
        return Jsoup.connect("$baseUrl/new-contest")
                .cookies(authCookies)
                .headers(headers)
                .get()
    }

    override fun login(credentials: Map<String, String>): Response {
        logger.debug { "Logging in ${credentials["user"]}" }
        val response = get("$baseUrl/login",
                data = credentials,
                headers = headers)
        logger.debug { "Status code: ${response.statusCode}" }

        authCookies = response.cookies
        return response
    }
}