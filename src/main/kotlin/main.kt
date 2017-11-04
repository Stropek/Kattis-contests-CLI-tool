import configuration.*
import kattis.KattisApi
import kattis.KattisRepository

fun main(args: Array<String>) {
    //TODO: add ability to run from a command line

    val repo = KattisRepository()
    val api = KattisApi(repo)

    val reader = FileReader()
    val settings = Settings(reader)

    api.login(settings.user, settings.token)

//    val teams = // TODO: read teams from a file and add them to contest

//    val problems = api.getRandomProblems(10, 3.0)
//
//    val newContest = api.createBlankContest()
//
//    newContest.name = "Kattis with problems"
//    newContest.startTime = LocalDateTime.now().plusDays(10).toString()
//    newContest.duration = 100
//
//    api.createContest(newContest)
//    println(newContest.shortName)
//
//    api.addProblemsToContest(newContest, problems)


//    File("responses/problems.html").bufferedWriter().use {
//        out -> out.write(problems.text)
//    }
}