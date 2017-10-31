package kattis

import khttp.get
import khttp.responses.Response

interface IKattisRepository {
    fun login(user: Map<String, String>) : Response
}

class KattisRepository : IKattisRepository {
    private val baseUrl = "http://open.kattis.com"
    private val headers = mapOf("User-Agent" to "kattis-cli-submit")

    override fun login(loginArgs: Map<String, String>): Response {
        return  get("$baseUrl/login", data = loginArgs, headers = headers)
    }
}