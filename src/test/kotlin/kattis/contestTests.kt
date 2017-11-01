package kattis

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ContestTests {
    @Test fun `primary constructor should set default values`() {
        // given
        val result = Contest()

        // then
        assertEquals(false, result.isPrivileged)
        assertEquals(false, result.existing)
        assertEquals("", result.shortName)
        assertEquals("", result.csrf_token)
        assertEquals(mutableListOf<String>(), result.judges)
        assertEquals(mutableListOf<String>(), result.teams)
    }
    @Test fun `parse when input contains valid is_privileged should return contest with given is_privileged value`() {
        // given
        val input = """jQuery.extend(jQuery.ns('Kattis.views.contest.edit.data'), {
            |is_privileged: true,
            |other: ""
            |});
        """.trimMargin()

        // when
        var result = Contest.parse(input)

        // then
        assertEquals(true, result.isPrivileged)
    }
    @Test fun `parse when input contains invalid is_privileged should return contest with default is_privileged value`() {
        // given
        val input = """jQuery.extend(jQuery.ns('Kattis.views.contest.edit.data'), {
            |is_privileged: true,
            |other: ""
            |});
        """.trimMargin()

        // when
        var result = Contest.parse(input)

        // then
        assertEquals(true, result.isPrivileged)
    }

}