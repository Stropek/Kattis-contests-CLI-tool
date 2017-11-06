import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock

import interfaces.IFileReader
import settings.Settings

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SettingsTests {
    @Test fun `constructor(name, token) - sets values of name and token`() {
        // when
        val settings = Settings("max", "1234567890")

        // then
        assertEquals("max", settings.user)
        assertEquals("1234567890", settings.token)
    }
    @Test fun `constructor(fileReader) - reader returns valid configuration - sets values of name and token`() {
        // given
        val content = mutableListOf<String>()
        content.add("# sample comment in a file")
        content.add("username: max")
        content.add("token: 1234567890")
        val mockReader = mock<IFileReader> {
            on { readKattisConfiguration() } doReturn content
        }

        // when
        val settings = Settings(mockReader)

        // then
        assertEquals("max", settings.user)
        assertEquals("1234567890", settings.token)
    }
}
