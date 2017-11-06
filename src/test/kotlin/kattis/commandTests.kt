package kattis

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

import com.xenomachina.argparser.ArgParser
import com.nhaarman.mockito_kotlin.mock

import KattisCliArgs
import com.nhaarman.mockito_kotlin.doReturn
import interfaces.IFileReader

class CommandTests {
    @Test fun `constructor() - credentials - args with user and token - sets command credentials`() {
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
    @Test fun `constructor() - credentials - args with settings - sets command credentials using settings file`() {
        // given
        val args = arrayOf("-s", "settings.txt")
        val kattisArgs = KattisCliArgs(ArgParser(args))

        val content = mutableListOf<String>()
        content.add("# sample comment in a file")
        content.add("username: max")
        content.add("token: payne")
        val mockReader = mock<IFileReader> {
            on { read("settings.txt") } doReturn content
        }

        // when
        val result = Command(kattisArgs, mockReader)

        // then
        assertEquals("max", result.credentials.user)
        assertEquals("payne", result.credentials.token)
    }
    @Test fun `constructor() - credentials - args with user, token and settings - sets command credentials`() {
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
    @Test fun `constructor() - credentials - args without either user-token or settings - sets command credentials using settings file from home path`() {
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
}