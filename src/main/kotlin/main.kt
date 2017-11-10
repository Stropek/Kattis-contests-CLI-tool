import kattis.Command

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.ShowHelpException

import mu.KotlinLogging

import java.io.StringWriter
import java.lang.System.err

import kattis.KattisApi
import kattis.KattisRepository

val APP_NAME = "Kattis Competition CLI"
private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    try
    {
        logger.debug { "Running Kattis competition CLI..." }
        run(KattisCliArgs(ArgParser(args)))
    }
    catch (ex: ShowHelpException)
    {
        StringWriter()
                .apply { ex.printUserMessage(this, APP_NAME, 80) }
                .apply { err.println(this) }
    }
    catch (ex: Throwable)
    {
        err.println(ex.message)
    }
}

fun run(args: KattisCliArgs) {
    val reader = FileReader()
    val command = Command(args, reader)

    val repo = KattisRepository()
    val api = KattisApi(repo)

//    api.login(command.credentials.user, command.credentials.token)

//    val newContest = api.createBlankContest()
//    newContest.name = "Teams from a file 2"
//    newContest.startTime = LocalDateTime.now().plusDays(10).toString()
//    newContest.duration = 100
//    api.createContest(newContest)

    // add teams to contest
//    api.addTeamsToContest(newContest, command.teams)

//    val problems = api.getRandomProblems(10, 3.0)
//    val newContest = api.createBlankContest()
//
//    newContest.name = "Kattis with problems"
//    newContest.startTime = LocalDateTime.now().plusDays(10).toString()
//    newContest.duration = 100
//
//    api.addProblemsToContest(newContest, problems)

    //TODO: api.createContest(command)

//    File("responses/problems.html").bufferedWriter().use {
//        out -> out.write(problems.text)
//    }
}