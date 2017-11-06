import kattis.Command

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.ShowHelpException

import java.io.StringWriter

import java.lang.System.err

val APP_NAME = "Kattis Competition CLI"

fun main(args: Array<String>) {
    try {
        run(KattisCliArgs(ArgParser(args)))
    } catch (ex: ShowHelpException) {
        StringWriter()
                .apply { ex.printUserMessage(this, APP_NAME, 80) }
                .apply { err.println(this) }
    } catch (ex: Throwable) {
        err.println(ex.message)
    }
}

fun run(args: KattisCliArgs) {
    val reader = FileReader()
    val command = Command(args, reader)

//    val repo = KattisRepository()
//    val api = KattisApi(repo)

//    api.login(settings.user, settings.token)

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