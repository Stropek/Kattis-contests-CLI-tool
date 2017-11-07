package kattis

import KattisCliArgs
import interfaces.IFileReader
import kattis.models.Team

class Command(args: KattisCliArgs, private val reader: IFileReader) {
    var credentials: Credentials
    var teams: List<Team>

    init {
        credentials = getCredentials(args)
        teams = getTeamsFromFile(args.teams)

        // TODO: read number of problems / difficult level from args or use default values
        // TODO: read contest name from args or use some default value
        // TODO: read contest start date from args or use the closest saturday midnight as a default value
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
            !path.isNullOrBlank() -> {
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

