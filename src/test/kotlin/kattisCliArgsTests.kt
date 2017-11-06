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
    }
    @Test fun `constructor - args with '-u' parameter - sets username value`() {
        // given
        val args = arrayOf("-u", "Jon")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("Jon", result.user)
    }
    @Test fun `constructor - args with '--user' parameter - sets username value`() {
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
    @Test fun `constructor - args with '-s' parameter - sets settingsFile value`() {
        // given
        val args = arrayOf("-s", "settings.txt")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("settings.txt", result.settings)
    }
    @Test fun `constructor - args with '--settings' parameter - sets token value`() {
        // given
        val args = arrayOf("--settings", "settings.txt")

        // when
        val result = KattisCliArgs(ArgParser(args))

        // then
        assertEquals("settings.txt", result.settings)
    }
}