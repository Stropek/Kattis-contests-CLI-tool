import configuration.*
import kattis.KattisApi
import kattis.KattisRepository

fun main(args: Array<String>) {
    //TODO: add ability to run from a command line

    val repo = KattisRepository()
    val api = KattisApi(repo)

    api.login(Settings.user, Settings.token)

//    val newContest = kattisApi.createBlankContest()
//
//    newContest.name = "New Kattis Contest"
//    newContest.startTime = LocalDateTime.now().plusDays(10).toString()
//    newContest.duration = 100
//
//    kattisApi.createContest(newContest)
//    println(newContest.shortName)

    val problems = api.getRandomProblems(10, 3.0)

//    File("responses/problems.html").bufferedWriter().use {
//        out -> out.write(problems.text)
//    }
}