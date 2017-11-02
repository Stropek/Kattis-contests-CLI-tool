import configuration.*
import kattis.KattisApi
import kattis.KattisRepository
import java.time.LocalDateTime

fun main(args: Array<String>) {
    //TODO: add ability to run from a command line

    val repo = KattisRepository()
    val kattisApi = KattisApi(repo)

    kattisApi.login(Settings.user, Settings.token)

    val newContest = kattisApi.createBlankContest()

    newContest.name = "New Kattis Contest"
    newContest.startTime = LocalDateTime.now().plusDays(10).toString()
    newContest.duration = 100

    kattisApi.createContest(newContest)

    println(newContest.shortName)

    //TODO: get randomly some number of problems with various difficulty
    // https://open.kattis.com/problems?show_solved=off&show_tried=off&show_untried=on

//    File("responses/login.html").bufferedWriter().use {
//        out -> out.write(loginResponse.text)
//    }

//    File("responses/newContest.html").bufferedWriter().use {
//        out -> out.write(newContest.text)
//    }
}