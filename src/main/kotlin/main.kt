import configuration.*
import kattis.Contest
import kattis.KattisApi
import kattis.KattisRepository
import org.jsoup.Jsoup
import java.io.File

fun main(args: Array<String>) {
    //TODO: add ability to run from a command line

    val repo = KattisRepository()
    val kattisApi = KattisApi(repo)

    kattisApi.login(Settings.user, Settings.token)

    val newContest = kattisApi.createBlankContest()
    println(newContest.csrfToken)

    //TODO: given csrf_token -> create a new contest using PUT http method

//    File("responses/login.html").bufferedWriter().use {
//        out -> out.write(loginResponse.text)
//    }

//    File("responses/newContest.html").bufferedWriter().use {
//        out -> out.write(newContest.text)
//    }
}