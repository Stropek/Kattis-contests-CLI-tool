package interfaces

import kattis.models.Contest
import kattis.models.Problem
import kattis.models.Team
import khttp.responses.Response
import org.json.JSONObject
import org.jsoup.nodes.Document

interface IKattisRepository {
    fun addTeamToContest(contest: Contest, team: Team)

    fun addProblemToContest(contest: Contest, problem: Problem)

    fun getProblemsPage(page: Int = 0, order: String = "problem_difficulty",
                        showUntried: Boolean = true, showTried: Boolean = false,
                        showSolved: Boolean = false): Document

    fun createNewContest(contest: Contest): JSONObject

    fun getNewContestPage(): Document

    fun login(credentials: Map<String, String>): Response
}