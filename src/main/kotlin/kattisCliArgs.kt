import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default

import org.apache.log4j.Level
import org.apache.log4j.LogManager

class KattisCliArgs(parser: ArgParser) {
    val verbose by parser.flagging("-v", "--verbose", help = "Enable verbose logging")
            .apply { LogManager.getRootLogger().level = Level.DEBUG }
    val name by parser.storing("-n", "--name", help = "Contest name")
            .default("")
    val startDate by parser.storing("-d", "--start-date", help = "Contest start date")
            .default("")
    val user by parser.storing("-u", "--user", help = "Open Kattis user name")
            .default("")
    val token by parser.storing("-t", "--token", help = "Open Kattis token")
            .default("")
    val settings by parser.storing("-s", "--settings", help = "File with Open Kattis user name and token")
            .default("")
    val teams by parser.storing("-e", "--teams", help = "File with list of teams for the contest")
            .default("configuration/teams.kattis")
    val numberOfProblems by parser.storing("-p", "--num-of-problems", help = "Number of problems") { toInt() }
            .default(5)
    val minDifficulty by parser.storing("-m", "--min-level", help = "Minimum difficulty level of problems") { toDouble() }
            .default(2.5)
}