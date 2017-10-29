import configuration.*

import khttp.get
import khttp.post

import java.io.File

fun main(args: Array<String>) {
    val Headers = mutableMapOf("User-Agent" to "kattis-cli-submit")

    val user = Settings.user
    val token = Settings.token

    val loginArgs = mapOf("user" to user, "token" to token, "script" to "true")
    val loginResponse = post("http://open.kattis.com/login", data = loginArgs, headers = Headers)
    println("logged in")

    var newContest = get("http://open.kattis.com/new-contest", cookies = loginResponse.cookies, headers = Headers)

    File("responses/login.html").bufferedWriter().use {
        out -> out.write(loginResponse.text)
    }

    File("responses/newContest.html").bufferedWriter().use {
        out -> out.write(newContest.text)
    }
}