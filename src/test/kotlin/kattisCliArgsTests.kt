import com.xenomachina.argparser.ArgParser

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KattisCliArgsTests {
    @Test fun `constructor - no args - sets default values`() {
        // given
        val args = arrayOf<String>()

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("", result.user)
        assertEquals("", result.token)
        assertEquals("", result.settings)
        assertEquals("configuration/teams.kattis", result.teams)
    }
    @Test fun `constructor - args with '-u' parameter - sets user value`() {
        // given
        val args = arrayOf("-u", "Jon")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("Jon", result.user)
    }
    @Test fun `constructor - args with '--user' parameter - sets user value`() {
        // given
        val args = arrayOf("--user", "Jon")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("Jon", result.user)
    }
    @Test fun `constructor - args with '-t' parameter - sets token value`() {
        // given
        val args = arrayOf("-t", "12345")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("12345", result.token)
    }
    @Test fun `constructor - args with '--token' parameter - sets token value`() {
        // given
        val args = arrayOf("--token", "12345")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("12345", result.token)
    }
    @Test fun `constructor - args with '-s' parameter - sets settings value`() {
        // given
        val args = arrayOf("-s", "settings.txt")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("settings.txt", result.settings)
    }
    @Test fun `constructor - args with '--settings' parameter - sets settings value`() {
        // given
        val args = arrayOf("--settings", "settings.txt")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("settings.txt", result.settings)
    }
    @Test fun `constructor - args with '-t' parameter - sets teams value`() {
        // given
        val args = arrayOf("-e", "teams.txt")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("teams.txt", result.teams)
    }
    @Test fun `constructor - args with '--teams' parameter - sets teams value`() {
        // given
        val args = arrayOf("--teams", "teams.txt")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("teams.txt", result.teams)
    }
}