import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default

class KattisCliArgs(parser: ArgParser) {
    // TODO: add argument for verbose logging: https://github.com/MicroUtils/kotlin-logging
    val user by parser.storing("-u", "--user", help = "Open Kattis user name")
            .default("")
    val token by parser.storing("-t", "--token", help = "Open Kattis token")
            .default("")
    val settings by parser.storing("-s", "--settings", help = "File with Open Kattis user name and token")
            .default("")
}