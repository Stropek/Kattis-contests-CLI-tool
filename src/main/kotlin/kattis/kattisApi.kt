package kattis

import khttp.structures.cookie.CookieJar

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
    fun login(user: String, toke: String) : CookieJar
}

class KattisApi(val kattisRepository: IKattisRepository) : IKattisApi {
    override fun login(user: String, token: String) : CookieJar {
        val loginArgs = mapOf("user" to user, "token" to token, "script" to "true")

        var response = kattisRepository.login(loginArgs)
        return response.cookies
    }
}