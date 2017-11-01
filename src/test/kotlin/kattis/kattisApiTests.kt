package kattis

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock

import khttp.responses.Response
import khttp.structures.cookie.CookieJar

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

internal class KattisApiTests {
    companion object {
        @BeforeAll fun setUp() {
        }

        @AfterAll fun tearDown() {
        }
    }

    @Test fun `login with valid response from repository should return auth cookie`() {
        // given
        val responseMock = mock<Response> {
            on { text } doReturn "Login successful!"
        }
        val repoMock = mock<IKattisRepository> {
            on { login(any()) } doReturn responseMock
        }
        val api = KattisApi(repoMock)

        // when
        val result = api.login("user", "token")

        // then
        assertEquals("Login successful!", result)
    }
}