import kattis.Command

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.ShowHelpException

import mu.KotlinLogging

import java.io.StringWriter

import kattis.KattisApi
import kattis.KattisCliArgs
import kattis.KattisRepository

private val appName = "Kattis Competition CLI"
private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    try
    {
        var args = ArgParser(args)
        val kattisArgs = KattisCliArgs(args)
        args.force()

        logger.info { "Running $appName." }
        run(kattisArgs)
        logger.info { "Done. Exiting $appName." }
    }
    catch (ex: ShowHelpException)
    {
        StringWriter()
                .apply { ex.printUserMessage(this, appName, 80) }
                .apply { logger.info { this } }
    }
    catch (ex: Throwable)
    {
        logger.error { ex.message }
    }
}

fun run(args: KattisCliArgs) {
    // gTODO: add verbose logging
    val reader = FileReader()
    val command = Command(args, reader)

//    val repo = KattisRepository()
//    val api = KattisApi(repo)
//
//    api.login(command.credentials.user, command.credentials.token)
//    var contest = api.createContest(command)
//
//    var problems = api.getRandomProblems(command.numberOfProblems, command.minDifficulty)
//    api.addProblemsToContest(contest, problems)
//    api.addTeamsToContest(contest, command.teams)
}