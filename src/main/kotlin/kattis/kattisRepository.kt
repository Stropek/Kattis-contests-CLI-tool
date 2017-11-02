package kattis

import khttp.get
import khttp.put
import khttp.responses.Response
import khttp.structures.cookie.CookieJar

import org.json.JSONObject

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

interface IKattisRepository {
    fun getProblemsPage(page: Int = 0, order: String = "problem_difficulty",
                        showUntried: Boolean = true, showTried: Boolean = false,
                        showSolved: Boolean = false): Document

    fun createNewContest(contest: Contest): JSONObject

    fun getNewContestPage(): Document

    fun login(user: Map<String, String>): Response
}

class KattisRepository : IKattisRepository {
    private val baseUrl = "http://open.kattis.com"
    private val headers = mapOf("User-Agent" to "kattis-cli-submit", "Content-Type" to "application/json")

    lateinit var authCookies: CookieJar

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

    override fun login(loginArgs: Map<String, String>): Response {
        val response = get("$baseUrl/login",
                data = loginArgs,
                headers = headers)
        authCookies = response.cookies
        return response
    }
}