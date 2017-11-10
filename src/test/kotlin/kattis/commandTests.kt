package kattis

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

import com.xenomachina.argparser.ArgParser
import com.nhaarman.mockito_kotlin.mock

import com.nhaarman.mockito_kotlin.doReturn
import interfaces.IFileReader
import org.junit.jupiter.api.Assertions.assertTrue
import java.time.DayOfWeek
import java.time.LocalDate

class CommandTests {
    @Test fun `constructor() - credentials - args with user and token - sets credentials`() {
        // given
        val args = arrayOf("-u", "jon", "-t", "snow")
        val kattisArgs = KattisCliArgs(ArgParser(args))
        val mockReader = mock<IFileReader>()

        // when
        val result = Command(kattisArgs, mockReader)

        // then
        assertEquals("jon", result.credentials.user)
        assertEquals("snow", result.credentials.token)
    }
    @Test fun `constructor() - credentials - args with settings - sets credentials to given settings file`() {
        // given
        val args = arrayOf("-s", "settings.txt")
        val kattisArgs = KattisCliArgs(ArgParser(args))

        val content = mutableListOf("# sample comment in a file", "username: max", "token: payne")
        val mockReader = mock<IFileReader> {
            on { read("settings.txt") } doReturn content
        }

        // when
        val result = Command(kattisArgs, mockReader)

        // then
        assertEquals("max", result.credentials.user)
        assertEquals("payne", result.credentials.token)
    }
    @Test fun `constructor() - credentials - args with user, token and settings - sets credentials`() {
        // given
        val args = arrayOf("-u", "jon", "-t", "snow", "-s", "settings.txt")
        val kattisArgs = KattisCliArgs(ArgParser(args))
        val mockReader = mock<IFileReader>()

        // when
        val result = Command(kattisArgs, mockReader)

        // then
        assertEquals("jon", result.credentials.user)
        assertEquals("snow", result.credentials.token)
    }
    @Test fun `constructor() - credentials - args without either user-token or settings - sets credentials to settings file from home path`() {
        // given
        val args = arrayOf<String>()
        val kattisArgs = KattisCliArgs(ArgParser(args))

        val content = mutableListOf<String>()
        content.add("# sample comment in a file")
        content.add("username: jane")
        content.add("token: doe")
        val mockReader = mock<IFileReader> {
            on { read("${System.getenv("HOME")}/.kattis") } doReturn content
        }

        // when
        val result = Command(kattisArgs, mockReader)

        // then
        assertEquals("jane", result.credentials.user)
        assertEquals("doe", result.credentials.token)
    }
    @Test fun `constructor() - teams - args with teams - sets teams to given teams file`() {
        // given
        val args = arrayOf("-e", "teams.txt")
        val kattisArgs = KattisCliArgs(ArgParser(args))

        val content = mutableListOf("# sample comment in a file", "team_1: sample@mail.pl, user-name", "team_2: other user")
        val mockReader = mock<IFileReader> {
            on { read("teams.txt") } doReturn content
        }

        // when
        val result = Command(kattisArgs, mockReader)

        // then
        assertEquals(2, result.teams.size)
        assertEquals("team_1", result.teams[0].name)
        assertEquals(2, result.teams[0].members.size)
        assertEquals("team_2", result.teams[1].name)
        assertEquals(1, result.teams[1].members.size)
    }
    @Test fun `constructor() - teams - args without teams - sets teams to file from default path`() {
        // given
        val args = arrayOf<String>()
        val kattisArgs = KattisCliArgs(ArgParser(args))

        val content = mutableListOf("# sample comment in a file", "team_1: sample@mail.pl, user-name", "team_2: other user")
        val mockReader = mock<IFileReader> {
            on { read("configuration/teams.kattis") } doReturn content
        }

        // when
        val result = Command(kattisArgs, mockReader)

        // then
        assertEquals(2, result.teams.size)
        assertEquals("team_1", result.teams[0].name)
        assertEquals(2, result.teams[0].members.size)
        assertEquals("team_2", result.teams[1].name)
        assertEquals(1, result.teams[1].members.size)
    }
    @Test fun `constructor() - numberOfProblems - args with numberOfProblems - sets numberOfProblems to value from args`() {
        // given
        val args = arrayOf("-p", "10")
        val kattisArgs = KattisCliArgs(ArgParser(args))
        val mockReader = mock<IFileReader>()

        // when
        val result = Command(kattisArgs, mockReader)

        // then
        assertEquals(10, result.numberOfProblems)
    }
    @Test fun `constructor() - minDifficulty - args with minDifficulty - sets minDifficulty to value from args`() {
        // given
        val args = arrayOf("-m", "3.5")
        val kattisArgs = KattisCliArgs(ArgParser(args))
        val mockReader = mock<IFileReader>()

        // when
        val result = Command(kattisArgs, mockReader)

        // then
        assertEquals(3.5, result.minDifficulty)
    }
    @Test fun `constructor() - name - args with name - sets name to value from args`() {
        // given
        val args = arrayOf("-n", "AvaSE-Mar")
        val kattisArgs = KattisCliArgs(ArgParser(args))
        val mockReader = mock<IFileReader>()

        // when
        val result = Command(kattisArgs, mockReader)

        // then
        assertEquals("AvaSE-Mar", result.name)
    }
    @Test fun `constructor() - name - args without name - sets name to default value`() {
        // given
        val args = arrayOf<String>()
        val kattisArgs = KattisCliArgs(ArgParser(args))
        val mockReader = mock<IFileReader>()

        // when
        val result = Command(kattisArgs, mockReader)

        // then
        assertTrue(result.name.startsWith("AvaSE"))
    }
    @Test fun `constructor() - startDate - args with startDate - sets startDate to value from args`() {
        // given
        val args = arrayOf("-d", "2017-01-02 08:00:00")
        val kattisArgs = KattisCliArgs(ArgParser(args))
        val mockReader = mock<IFileReader>()

        // when
        val result = Command(kattisArgs, mockReader)

        // then
        assertEquals(LocalDate.parse("2017-01-02"), result.startDate.toLocalDate())
    }
    @Test fun `constructor() - startDate - args without startDate - sets startDate to default value`() {
        // given
        val args = arrayOf<String>()
        val kattisArgs = KattisCliArgs(ArgParser(args))
        val mockReader = mock<IFileReader>()

        // when
        val result = Command(kattisArgs, mockReader)

        // then
        assertEquals(DayOfWeek.SATURDAY, result.startDate.dayOfWeek)
    }
}