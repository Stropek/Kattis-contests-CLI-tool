package kattis

class Contest {
    var isPrivileged: Boolean = false
    var existing: Boolean = false
    var shortName: String = ""
    var csrfToken: String = ""
    var judges: MutableList<String> = mutableListOf()
    var teams: MutableList<String> = mutableListOf()

    companion object {
        fun parse(data: String) : Contest {
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
                    "short_name" -> contest.shortName = it.value
                    "csrf_token" -> contest.csrfToken = it.value
                    // TODO:
                    // "teams" ->
                    // "judges" ->
                }
            }

            return contest
        }
    }
}