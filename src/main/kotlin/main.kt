import configuration.*
import kattis.Contest
import kattis.KattisApi
import kattis.KattisRepository
import khttp.get
import org.jsoup.Jsoup
import java.io.File

fun main(args: Array<String>) {
    //TODO: add ability to run from a command line

    val repo = KattisRepository()
    val kattisApi = KattisApi(repo)

    kattisApi.login(Settings.user, Settings.token)



    var file = File("responses/newContest.html")
    Jsoup.parse(file, "UTF-8", "http://open.kattis.com")
            .run {
                var contestData = select("script")
                        .single { it.toString().contains("Kattis.views.contest.edit.data") }

                var contest = Contest.parse(contestData.toString())
                println()
//                        {
//                    index, element ->
//                    println("index: $index, element: $element")
//                }
            }

    println("done")
//    kattisApi.createNewContest("NewContestFromApp")
//    var newContest = get("http://open.kattis.com/new-contest", cookies = repo.authCookies, headers = repo.headers)

//    File("responses/login.html").bufferedWriter().use {
//        out -> out.write(loginResponse.text)
//    }

//    File("responses/newContest.html").bufferedWriter().use {
//        out -> out.write(newContest.text)
//    }
}