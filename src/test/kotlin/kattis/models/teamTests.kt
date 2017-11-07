package kattis.models

import kattis.models.Team
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TeamTests {
    @Test fun `primary constructor should set default values`() {
        // when
        val result = Team("team_1", listOf("member_1", "member_2"))

        // then
        assertEquals("team_1", result.name)
        assertEquals(2, result.members.size)
        assertEquals("member_1", result.members[0])
        assertEquals("member_2", result.members[1])
    }
}