package kattis

import khttp.get
import khttp.responses.Response
import khttp.structures.cookie.CookieJar
import javax.naming.AuthenticationException

interface IKattisRepository {
    fun getNewContestToken() : String

    fun login(user: Map<String, String>) : Response
}

class KattisRepository : IKattisRepository {
    private val baseUrl = "http://open.kattis.com"
    private val headers = mapOf("User-Agent" to "kattis-cli-submit")

    lateinit var authCookies: CookieJar

    override fun getNewContestToken(): String {
        if (authCookies == null)
            throw AuthenticationException("You need to log in first!")

        return "get csrf_token from new-contest page"
    }

    override fun login(loginArgs: Map<String, String>): Response {
        var response = get("$baseUrl/login", data = loginArgs, headers = headers)
        authCookies = response.cookies
        return response
    }
}