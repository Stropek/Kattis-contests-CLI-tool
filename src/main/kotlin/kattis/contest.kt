package kattis

class Contest(var name: String = "") {
    var isPrivileged: Boolean = false
    var existing: Boolean = false
    var shortName: String = ""
    var csrfToken: String = ""
    var judges: MutableList<String> = mutableListOf()
    var teams: MutableList<String> = mutableListOf()

    var startTime: String = ""
    var duration: Int = 0
    var isOpen: Boolean = false

    fun toData(): Map<String, Any> {
        return mapOf("session_name" to this.name,
                "short_name" to this.shortName,
                "start_time" to this.startTime,
                "duration" to "${this.duration}:00",
                "is_open" to false,
                "csrf_token" to this.csrfToken.trim('\''))
    }

    companion object {
        fun parse(data: String): Contest {
            val dictionary = hashMapOf<String, String>()
            val lines = data.split("\n")
            val startIndex = lines.indexOfFirst { it.contains("Kattis.views.contest.edit.data") }
            val endIndex = lines.indexOfFirst { it.contains("});") }

            lines.subList(startIndex, endIndex)
                    .filter { it.contains(":") }
                    .map { it.split(":").let {
                        dictionary.put(it[0].trim(), it[1].trim().trim(','))
                    }}

            return getContestFromDictionary(dictionary)
        }

        private fun getContestFromDictionary(dictionary: HashMap<String, String>): Contest {
            var contest = Contest()

            dictionary.forEach {
                when (it.key) {
                    "is_privileged" -> contest.isPrivileged = it.value.toBoolean()
                    "existing" -> contest.existing = it.value.toBoolean()
                    "short_name" -> contest.name = it.value
                    "csrf_token" -> contest.csrfToken = it.value.trim('"')
                    // TODO:
                    // "teams" ->
                    // "judges" ->
                }
            }

            return contest
        }
    }
}