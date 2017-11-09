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
        assertEquals(5, result.numberOfProblems)
        assertEquals("", result.name)
    }
    @Test fun `constructor - args with '-u' parameter - sets user`() {
        // given
        val args = arrayOf("-u", "Jon")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("Jon", result.user)
    }
    @Test fun `constructor - args with '--user' parameter - sets user`() {
        // given
        val args = arrayOf("--user", "Jon")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("Jon", result.user)
    }
    @Test fun `constructor - args with '-t' parameter - sets token`() {
        // given
        val args = arrayOf("-t", "12345")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("12345", result.token)
    }
    @Test fun `constructor - args with '--token' parameter - sets token`() {
        // given
        val args = arrayOf("--token", "12345")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("12345", result.token)
    }
    @Test fun `constructor - args with '-s' parameter - sets settings`() {
        // given
        val args = arrayOf("-s", "settings.txt")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("settings.txt", result.settings)
    }
    @Test fun `constructor - args with '--settings' parameter - sets settings`() {
        // given
        val args = arrayOf("--settings", "settings.txt")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("settings.txt", result.settings)
    }
    @Test fun `constructor - args with '-t' parameter - sets teams`() {
        // given
        val args = arrayOf("-e", "teams.txt")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("teams.txt", result.teams)
    }
    @Test fun `constructor - args with '--teams' parameter - sets teams`() {
        // given
        val args = arrayOf("--teams", "teams.txt")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("teams.txt", result.teams)
    }
    @Test fun `constructor - args with '-p' parameter - sets numberOfProblems`() {
        // given
        val args = arrayOf("-p", "10")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals(10, result.numberOfProblems)
    }
    @Test fun `constructor - args with '--num-of-problems' parameter - sets numberOfProblems`() {
        // given
        val args = arrayOf("--num-of-problems", "10")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals(10, result.numberOfProblems)
    }
    @Test fun `constructor - args with '-n' parameter - sets contest name`() {
        // given
        val args = arrayOf("-n", "AvaSE-Jan")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("AvaSE-Jan", result.name)
    }
    @Test fun `constructor - args with '--name' parameter - sets contest name`() {
        // given
        val args = arrayOf("--name", "AvaSE-Feb")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("AvaSE-Feb", result.name)
    }
}