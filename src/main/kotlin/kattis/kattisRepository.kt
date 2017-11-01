package kattis

import khttp.get
import khttp.responses.Response
import khttp.structures.cookie.CookieJar
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import javax.naming.AuthenticationException

interface IKattisRepository {
    fun getNewContestDocument() : Document

    fun login(user: Map<String, String>) : Response
}

class KattisRepository : IKattisRepository {
    private val baseUrl = "http://open.kattis.com"
    private val headers = mapOf("User-Agent" to "kattis-cli-submit")

    lateinit var authCookies: CookieJar

    override fun getNewContestDocument(): Document {
        ensureAuthentication()

        return Jsoup.connect("$baseUrl/new-contest")
                .cookies(authCookies)
                .headers(headers)
                .get()
    }

    override fun login(loginArgs: Map<String, String>): Response {
        var response = get("$baseUrl/login", data = loginArgs, headers = headers)
        authCookies = response.cookies
        return response
    }

    private fun ensureAuthentication() {
        if (authCookies == null)
            throw AuthenticationException("You need to log in first!")
    }
}