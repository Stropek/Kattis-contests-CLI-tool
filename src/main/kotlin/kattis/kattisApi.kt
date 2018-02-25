package kattis

import interfaces.IKattisRepository
import kattis.models.Contest
import kattis.models.Problem
import kattis.models.Team
import mu.KotlinLogging
import java.util.*

private val logger = KotlinLogging.logger {}

class KattisApi(private val kattisRepository: IKattisRepository) {

    fun addTeamsToContest(contest: Contest, teams: List<Team>) {
        logger.info { "Adding ${teams.size} teams to '${contest.name}' contest" }

        teams.forEach {
            kattisRepository.addTeamToContest(contest, it)
        }
    }

    fun addProblemsToContest(contest: Contest, problems: List<Problem>) {
        logger.info { "Adding ${problems.size} problems to '${contest.name}' contest" }

        problems.forEach {
            kattisRepository.addProblemToContest(contest, it)
        }
    }

    fun getRandomProblems(numberOfProblems: Int, minDifficulty: Double = 0.0): List<Problem> {
        logger.info { "Getting $numberOfProblems random problems (minimum difficulty: $minDifficulty)" }

        var selectedNumbers = setOf<String>()
        val selectedProblems = mutableListOf<Problem>()
        val random = Random()
        var pageNo = -1

        do {
            pageNo++
            val document = kattisRepository.getProblemsPage(pageNo)
            val rows = document.select("table.problem_list tbody tr")
            val firstProblem = Problem.parseRow(rows[0])
            if (firstProblem.difficulty < minDifficulty) {
                logger.debug { "Problems on page #$pageNo are too easy - moving to the next one..." }
                continue
            }

            val selectedNumber = random.nextInt(rows.size)
            selectedNumbers = selectedNumbers.plus("$pageNo $selectedNumber")

            val selectedProblem = Problem.parseRow(rows[selectedNumber])
            selectedProblems.add(selectedProblem)

            logger.debug { "Selected problem #$selectedNumber -> ${selectedProblem.name}" }
        } while (selectedNumbers.size < numberOfProblems && rows.size > 0)

        return selectedProblems
    }

    fun login(user: String, token: String): String {
        logger.info { "Logging in user: $user" }

        val loginArgs = mapOf("user" to user, "token" to token, "script" to "true")
        val response = kattisRepository.login(loginArgs)
        return response.text
    }

    fun createContest(command: Command): Contest {
        logger.info { "Creating new contest..." }

        val contest = getBlankContest()

        contest.name = command.name
        contest.startDate = command.startDate.toString()
        contest.duration = command.duration
        contest.isOpen = command.isOpen

        createContest(contest)

        return contest
    }

    private fun getBlankContest() : Contest {
        val document = kattisRepository.getNewContestPage()
        val contestData = document.select("script")
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