package kattis.models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TeamTests {
    @Test fun `primary constructor should set default values`() {
        // when
        val result = Team("team_1", listOf("member_1", "member_2"))

        // then
        assertEquals("team_1", result.name)
        assertEquals(2, result.members.size)
        assertEquals("member_1", result.members[0])
        assertEquals("member_2", result.members[1])
    }
    @Test fun `toData() - returns map of team's properties`() {
        // given
        val team = Team("team_1", listOf("member_1", "member_2"))
        team.id = "t_id"

        // when
        val result = team.toData()

        // then
        assertEquals("team_1", result["team_name"])
        assertEquals("t_id", result["team_id"])
    }
}