package kattis

import interfaces.IFileReader
import KattisCliArgs

class Command(val args: KattisCliArgs, val reader: IFileReader) {
    var credentials: Credentials
//    var teams: MutableList<Team>

    init {
        credentials = getCredentials(args)

        // TODO: read number of problems / difficult level from args or use default values
        // TODO: read contest name from args or use some default value
        // TODO: read contest start date from args or use the closest saturday midnight as a default value
        // TODO: either read teams from a file that's passed as an argument or from configuration/.teams
    }

    private fun getCredentials(args: KattisCliArgs): Credentials {
        val homePath = System.getenv("HOME")

        return when {
            !(args.user.isBlank() || args.user.isBlank()) -> {
                Credentials(args.user, args.token)
            }
            !args.settings.isBlank() -> {
                getCredentialsFromFile(args.settings)
            }
            !homePath.isNullOrBlank() -> {
                getCredentialsFromFile("$homePath/.kattis")
            }
            else -> {
                throw Exception("Credentials are required.")
            }
        }
    }

    private fun getCredentialsFromFile(path: String): Credentials {
        val dictionary = hashMapOf<String, Any>()
        val lines = reader.read(path)

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

        return Credentials(dictionary["username"].toString(), dictionary["token"].toString())
    }
}

data class Credentials(val user: String, val token: String)