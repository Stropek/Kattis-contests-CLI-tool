package kattis

import java.util.*

//ajax_session_edit: "/ajax/session",
//ajax_session_banner: "/ajax/session/banner",
//ajax_session_judge: "/ajax/session/judge",
//ajax_session_problem: "/ajax/session/problem",
//ajax_session_team: "/ajax/session/team",
//ajax_session_team_member: "/ajax/session/team/member",
//ajax_session_short_name_available: "/ajax/session/short-name-available",
//ajax_session_publish: "/ajax/session/publish",
//ajax_author: "/ajax/author",
//ajax_course_offering_teacher: "/ajax/course_offering/teacher",
//ajax_course_offering_short_name_available: "/ajax/course_offering/short-name-available",
//user_view: "/users",
//problemgroup_edit: "/problemgroups/{0}/edit"

interface IKattisApi {
    fun getRandomProblems(numberOfProblems: Int, minDifficulty: Double = 0.0) : List<Problem>

    fun createContest(contest: Contest)

    fun createBlankContest() : Contest

    fun login(user: String, toke: String) : String
}

class KattisApi(private val kattisRepository: IKattisRepository) : IKattisApi {
    override fun getRandomProblems(numberOfProblems: Int, minDifficulty: Double): List<Problem> {
        var selectedNumbers = setOf<Int>()
        var selectedProblems = mutableListOf<Problem>()
        var random = Random()
        var pageNo = -1

        do {
            pageNo++
            val document = kattisRepository.getProblemsPage(pageNo)
            // println("getProblems($pageNo)")
            val rows = document.select("table.problem_list tbody tr")
            val firstProblem = Problem.parseRow(rows[0])
            if (firstProblem.difficulty < minDifficulty) {
                println("Problems on page #$pageNo are too easy - moving to the next one...")
                continue
            }

            val selectedNumber = random.nextInt(rows.size)
            selectedNumbers = selectedNumbers.plus(selectedNumber)

            val selectedProblem = Problem.parseRow(rows[selectedNumber])
            selectedProblems.add(selectedProblem)

            println("Selected problem #$selectedNumber -> ${selectedProblem.name}")
        } while (rows.size > 0 && selectedNumbers.size < numberOfProblems)

        return selectedProblems
    }

    override fun createContest(contest: Contest) {
        val json = kattisRepository.createNewContest(contest)

        contest.shortName = json.getJSONObject("response")
                .getString("redirect")
                .let { it.split("/")[2] }
    }

    override fun createBlankContest() : Contest {
        val document = kattisRepository.getNewContestPage()

        var contestData = document.select("script")
                .single { it.toString().contains("Kattis.views.contest.edit.data") }

        return Contest.parse(contestData.toString())
    }

    override fun login(user: String, token: String) : String {
        val loginArgs = mapOf("user" to user, "token" to token, "script" to "true")

        var response = kattisRepository.login(loginArgs)
        return response.text
    }
}