import configuration.*
import kattis.KattisApi
import kattis.KattisRepository

import khttp.get
import khttp.post

import java.io.File

fun main(args: Array<String>) {
    val repo = KattisRepository()
    val kattisApi = KattisApi(repo)

    var authCookies = kattisApi.login(Settings.user, Settings.token)
    var newSession = kattisApi.
//    var newContest = get("http://open.kattis.com/new-contest", cookies = authCookies, headers = Headers)

//    File("responses/login.html").bufferedWriter().use {
//        out -> out.write(loginResponse.text)
//    }

//    File("responses/newContest.html").bufferedWriter().use {
//        out -> out.write(newContest.text)
//    }
}