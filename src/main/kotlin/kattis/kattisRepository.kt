package kattis

import khttp.get
import khttp.put
import khttp.responses.Response
import khttp.structures.cookie.CookieJar

import org.json.JSONObject

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

interface IKattisRepository {
    fun createNewContest(contest: Contest) : JSONObject

    fun getNewContestDocument() : Document

    fun login(user: Map<String, String>) : Response
}

class KattisRepository : IKattisRepository {
    private val baseUrl = "http://open.kattis.com"
    private val headers = mapOf("User-Agent" to "kattis-cli-submit", "Content-Type" to "application/json")

    lateinit var authCookies: CookieJar

    override fun createNewContest(contest: Contest): JSONObject {
        val response = put("$baseUrl/ajax/session", cookies = authCookies, headers = headers, json = contest.toData())
        return response.jsonObject
    }

    override fun getNewContestDocument(): Document {
        return Jsoup.connect("$baseUrl/new-contest")
                .cookies(authCookies)
                .headers(headers)
                .get()
    }

    override fun login(loginArgs: Map<String, String>): Response {
        val response = get("$baseUrl/login", data = loginArgs, headers = headers)
        authCookies = response.cookies
        return response
    }
}