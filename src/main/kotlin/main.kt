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

    val problems = api.getRandomProblems(1)

    for (problem in problems) {
        println("${problem.name}: ${problem.difficulty}")
    }

    //TODO: get randomly some number of problems with various difficulty
    // https://open.kattis.com/problems?show_solved=off&show_tried=off&show_untried=on

//    File("responses/problems.html").bufferedWriter().use {
//        out -> out.write(problems.text)
//    }
}