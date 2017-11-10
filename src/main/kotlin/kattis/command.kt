package kattis

import interfaces.IFileReader
import kattis.models.Team
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

class Command(args: KattisCliArgs, private val reader: IFileReader) {
    var name: String
    var startDate: LocalDateTime
    var credentials: Credentials
    var teams: List<Team>
    var numberOfProblems: Int
    var minDifficulty: Double
    var isOpen: Boolean
    var duration: Int

    init {
        name = if (args.name.isBlank()) getContestName() else args.name
        startDate = getStartDate(args.startDate)
        credentials = getCredentials(args)
        teams = getTeamsFromFile(args.teams)
        numberOfProblems = args.numberOfProblems
        minDifficulty = args.minDifficulty
        isOpen = args.isOpen
        duration = args.duration
    }

    private fun getStartDate(startDate: String): LocalDateTime {
        return if (startDate.isBlank())
        {
            LocalDateTime.now().plusDays(1).with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
        } else {
            val dataFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            LocalDateTime.parse(startDate, dataFormatter)
        }
    }

    private fun getContestName(): String {
        val nextSaturday = LocalDateTime.now()
                .plusDays(1)
                .with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
        val month = nextSaturday.month.name.substring(0, 3)
        val year = nextSaturday.year

        return "AvaSE-$month-$year"
    }

    private fun getTeamsFromFile(path: String): List<Team> {
        val dictionary = getEntriesFromFile(path)
        return dictionary.map { Team(it.key, it.value.toString().split(',')) }
    }

    private fun getCredentials(args: KattisCliArgs): Credentials {
        val path = if (!args.settings.isBlank()) args.settings else "${System.getenv("HOME")}/.kattis"

        return when {
            !(args.user.isBlank() || args.user.isBlank()) -> {
                Credentials(args.user, args.token)
            }
            !path.isBlank() -> {
                val dictionary = getEntriesFromFile(path)
                Credentials(dictionary["username"].toString(), dictionary["token"].toString())
            }
            else -> {
                throw Exception("Credentials are required.")
            }
        }
    }

    private fun getEntriesFromFile(path: String): HashMap<String, Any> {
        val lines = reader.read(path)
        val dictionary = hashMapOf<String, Any>()

        lines.map { it.trim() }
                // filter empty lines
                .filterNot { it.isEmpty() }
                // filter comments
                .filterNot { it.startsWith("#") }
                // create list of key-value pairs
                .map {
                    it.split(":").let {
                        dictionary.put(it[0].trim(), it[1].trim())
                    }
                }

        return dictionary
    }
}

