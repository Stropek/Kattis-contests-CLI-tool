import kattis.Command

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.ShowHelpException
import interfaces.IFileReader

import mu.KotlinLogging

import java.io.StringWriter

import kattis.KattisApi
import kattis.KattisCliArgs
import kattis.KattisRepository

private const val appName = "Kattis Competition CLI"
private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    main(args, FileReader())
}

fun main(args: Array<String>, reader: IFileReader) {
    try
    {
        logger.info { "Running $appName." }
        val argsParser = ArgParser(args)
        val kattisArgs = KattisCliArgs(argsParser)
        argsParser.force()

        logger.info { "Executing command..." }
        run(kattisArgs, reader)
        logger.info { "Finished. Exiting $appName." }
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

private fun run(args: KattisCliArgs, reader: IFileReader) {
    val command = Command(args, reader)

    val repo = KattisRepository()
    val api = KattisApi(repo)

    api.login(command.credentials.user, command.credentials.token)
    val contest = api.createContest(command)
    val problems = api.getRandomProblems(command.numberOfProblems, command.minDifficulty)

    api.addProblemsToContest(contest, problems)
    api.addTeamsToContest(contest, command.teams)
}