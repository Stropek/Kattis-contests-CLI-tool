package kattis.models

import org.jsoup.nodes.Element

data class Problem(val name: String, val difficulty: Double) {
    companion object {
        fun parseRow(row: Element) : Problem {
            val cols = row.select("td")

            val name = cols[0].select("a").attr("href").split('/')[2]
            val difficulty = cols[8].text().toDouble()

            return Problem(name, difficulty)
        }
    }

    fun toData(): Map<String, Any> {
        return mapOf("problem_name" to this.name)
    }
}