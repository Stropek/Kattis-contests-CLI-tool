package kattis.models

data class Team(val name: String, var members: List<String>) {
    var id: String = ""

    fun toData(): Map<String, Any> {
        return mapOf("team_name" to name,
                "team_id" to id)
    }
}
