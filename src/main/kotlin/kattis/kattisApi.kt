package kattis

import interfaces.IKattisRepository
import kattis.models.Contest
import kattis.models.Problem
import kattis.models.Team
import java.util.*

class KattisApi(private val kattisRepository: IKattisRepository) {
    fun addTeamsToContest(contest: Contest, teams: List<Team>) {
        teams.forEach {
            kattisRepository.addTeamToContest(contest, it)
        }
    }

    fun addProblemsToContest(contest: Contest, problems: List<Problem>) {
        problems.forEach {
            kattisRepository.addProblemToContest(contest, it)
        }
    }

    fun getRandomProblems(numberOfProblems: Int, minDifficulty: Double = 0.0): List<Problem> {
        var selectedNumbers = setOf<String>()
        var selectedProblems = mutableListOf<Problem>()
        var random = Random()
        var pageNo = -1

        do {
            pageNo++
            println("getProblems($pageNo)")
            val document = kattisRepository.getProblemsPage(pageNo)
            val rows = document.select("table.problem_list tbody tr")
            val firstProblem = Problem.parseRow(rows[0])
            if (firstProblem.difficulty < minDifficulty) {
                println("Problems on page #$pageNo are too easy - moving to the next one...")
                continue
            }

            val selectedNumber = random.nextInt(rows.size)
            selectedNumbers = selectedNumbers.plus("$pageNo $selectedNumber")

            val selectedProblem = Problem.parseRow(rows[selectedNumber])
            selectedProblems.add(selectedProblem)

            println("Selected problem #$selectedNumber -> ${selectedProblem.name}")
        } while (rows.size > 0 && selectedNumbers.size < numberOfProblems)

        return selectedProblems
    }

    fun login(user: String, token: String): String {
        val loginArgs = mapOf("user" to user, "token" to token, "script" to "true")

        var response = kattisRepository.login(loginArgs)
        return response.text
    }

    fun createContest(command: Command): Contest {
        val contest = getBlankContest()

        contest.name = command.name
        contest.startTime = command.startDate.toString()
//      TODO: add cmd line parameters for isOpen and duration
//        contest.duration = command.duration
//        contest.isOpen = command.isOpen

        createContest(contest)

        return contest
    }

    private fun getBlankContest() : Contest {
        val document = kattisRepository.getNewContestPage()

        var contestData = document.select("script")
                .single { it.toString().contains("Kattis.views.contest.edit.data") }

        return Contest.parse(contestData.toString())
    }

    private fun createContest(contest: Contest) {
        val json = kattisRepository.createNewContest(contest)

        contest.shortName = json.getJSONObject("response")
                .getString("redirect")
                .let { it.split("/")[2] }
    }
}