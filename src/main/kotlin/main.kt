import configuration.*
import kattis.KattisApi
import kattis.KattisRepository

fun main(args: Array<String>) {
    //TODO: add ability to run from a command line

    val repo = KattisRepository()
    val kattisApi = KattisApi(repo)

    kattisApi.login(Settings.user, Settings.token)
    kattisApi.createNewContest("NewContestFromApp")
//    var newContest = get("http://open.kattis.com/new-contest", cookies = authCookies, headers = Headers)

//    File("responses/login.html").bufferedWriter().use {
//        out -> out.write(loginResponse.text)
//    }

//    File("responses/newContest.html").bufferedWriter().use {
//        out -> out.write(newContest.text)
//    }
}