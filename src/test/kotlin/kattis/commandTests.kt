package kattis

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

import com.xenomachina.argparser.ArgParser
import com.nhaarman.mockito_kotlin.mock

import KattisCliArgs
import com.nhaarman.mockito_kotlin.doReturn
import interfaces.IFileReader

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
    @Test fun `constructor() - credentials - args with settings - sets credentials using given settings file`() {
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
    @Test fun `constructor() - credentials - args without either user-token or settings - sets credentials using settings file from home path`() {
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
    @Test fun `constructor() - teams - args with teams - sets teams using given teams file`() {
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
    @Test fun `constructor() - teams - args without teams - sets teams using file from default path`() {
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
}