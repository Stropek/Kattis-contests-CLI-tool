import kattis.Command

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.ShowHelpException

import mu.KotlinLogging

import java.io.StringWriter
import java.lang.System.err

import kattis.KattisApi
import kattis.KattisCliArgs
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
    // TODO: add verbose logging
    val reader = FileReader()
    val command = Command(args, reader)

    val repo = KattisRepository()
    val api = KattisApi(repo)

    api.login(command.credentials.user, command.credentials.token)
    var contest = api.createContest(command)

    var problems = api.getRandomProblems(command.numberOfProblems, command.minDifficulty)
    api.addProblemsToContest(contest, problems)
    api.addTeamsToContest(contest, command.teams)
}