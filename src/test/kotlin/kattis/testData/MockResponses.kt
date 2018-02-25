package kattis.testData

object MockResponses {
    const val Team =
            "{ " +
            "   \"response\": { " +
            "       \"team\": { " +
            "           \"team_id\": \"1\" " +
            "       } " +
            "   } " +
            "}"

    const val Contest =
            "{ " +
            "   \"response\": { " +
            "       \"redirect\": \"/segment/redirect_value\"" +
            "   } " +
            "}"

    const val NewContestScript =
            "<script> " +
            "   jQuery.extend(jQuery.ns('Kattis.views.contest.edit.data'), {" +
            "       is_privileged: true, " +
            "       other: \"\", " +
            "       short_name: test name, " +
            "       csrf_token: \"1234567890\", " +
            "   }); " +
            "</script>"

    const val ProblemsTable =
            "<table class=\"problem_list\"> " +
            "   <tbody>" +
            "       <tr> " +
            "           <td><a href=\"/segment_1/sample_url\"/></td>" +
            "           <td></td>" +
            "           <td></td>" +
            "           <td></td>" +
            "           <td></td>" +
            "           <td></td>" +
            "           <td></td>" +
            "           <td></td>" +
            "           <td>5.0</td>" +
            "       </tr> " +
            "   </tbody>"
}