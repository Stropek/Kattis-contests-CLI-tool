package kattis

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ContestTests {
    @Test fun `primary constructor should set default values`() {
        // when
        val result = Contest()

        // then
        assertEquals(false, result.isPrivileged)
        assertEquals(false, result.existing)
        assertEquals(false, result.isOpen)
        assertEquals("", result.name)
        assertEquals("", result.shortName)
        assertEquals("", result.csrfToken)
        assertEquals("", result.startTime)
        assertEquals(0, result.duration)
        assertEquals(mutableListOf<String>(), result.judges)
        assertEquals(mutableListOf<String>(), result.teams)
    }
    @Test fun `parse when input contains valid 'is_privileged' value should return contest with given 'is_privileged' value`() {
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
    @Test fun `parse when input contains invalid 'is_privileged' value should return contest with default 'is_privileged' value`() {
        // given
        val input = """jQuery.extend(jQuery.ns('Kattis.views.contest.edit.data'), {
            |is_privileged: tr ue,
            |other: ""
            |});
        """.trimMargin()

        // when
        var result = Contest.parse(input)

        // then
        assertEquals(false, result.isPrivileged)
    }
    @Test fun `parse when input contains valid 'short_name' value should return contest with given 'short_name' value`() {
        // given
        val input = """jQuery.extend(jQuery.ns('Kattis.views.contest.edit.data'), {
            |other: "",
            |short_name: test name,
            |});
        """.trimMargin()

        // when
        var result = Contest.parse(input)

        // then
        assertEquals("test name", result.name)
    }
    @Test fun `parse when input contains valid 'csrf_token' value should return contest with given 'csrf_token' value`() {
        // given
        val input = """jQuery.extend(jQuery.ns('Kattis.views.contest.edit.data'), {
            |other: "",
            |csrf_token: "1234567890",
            |});
        """.trimMargin()

        // when
        var result = Contest.parse(input)

        // then
        assertEquals("1234567890", result.csrfToken)
    }
}