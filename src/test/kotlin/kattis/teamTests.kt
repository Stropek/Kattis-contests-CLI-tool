package kattis

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TeamTests {
    @Test fun `primary constructor should set default values`() {
        // when
        val result = Team("team_1")

        // then
        assertEquals("team_1", result.name)
        assertEquals(0, result.members.size)
    }
}