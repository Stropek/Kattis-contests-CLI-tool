package kattis

import interfaces.IFileReader

// TODO: move settings code to command
class Command(val args: Array<String>, val reader: IFileReader) {
//    var credentials: Credentials
//    var teams: MutableList<Team>

    init {
        // TODO: introduce arguments parser?

        // TODO: either read credentials from args or from a file
        // TODO: read number of problems / difficult level from args or use default values
        // TODO: read contest name from args or used some default value
        // TODO: read contest start date from args or use the closest saturday midnight as a default value
        // TODO: either read teams from a file that's passed as an argument or from configuration/.teams
    }
}

data class Credentials(val user: String, val token: String)