package kattis

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
    fun getRandomProblems(numberOfProblems: Int) : List<Problem>

    fun createContest(contest: Contest) : Boolean

    fun createBlankContest() : Contest

    fun login(user: String, toke: String) : String
}

class KattisApi(private val kattisRepository: IKattisRepository) : IKattisApi {
    override fun getRandomProblems(numberOfProblems: Int): List<Problem> {
        val document = kattisRepository.getProblemsPage()
        val rows = document.select("table.problem_list tbody tr")

        for (row in rows) {
            val cols = row.select("td")
            println(cols[0].select("a").attr("href"))
            println(cols[8].text())
        }

        return listOf()
    }

    //TODO: add unit test
    override fun createContest(contest: Contest): Boolean {
        val json = kattisRepository.createNewContest(contest)

        contest.shortName = json.getJSONObject("response")
                .getString("redirect")
                .let { it.split("/")[2] }

        return !contest.shortName.isNullOrBlank()
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