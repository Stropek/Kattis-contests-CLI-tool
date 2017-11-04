import kattis.Command
import kattis.KattisApi
import kattis.KattisRepository

import settings.Settings

fun main(args: Array<String>) {
    // TODO: add ability to run from a command line
    // TODO: move settings functionality to command
    val reader = FileReader()
    // TODO: if command line arguments for user and token are passed - use the other constructor for Settings
    val settings = Settings(reader)
    val command = Command(args, reader)

    val repo = KattisRepository()
    val api = KattisApi(repo)


    api.login(settings.user, settings.token)

//    val problems = api.getRandomProblems(10, 3.0)
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