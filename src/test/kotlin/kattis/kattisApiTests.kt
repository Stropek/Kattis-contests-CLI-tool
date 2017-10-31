package kattis

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock

import khttp.responses.Response
import khttp.structures.cookie.CookieJar

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

internal class KattisApiTests {
//    private lateinit var repoMock: IKattisRepository
    companion object {
        @BeforeAll fun setUp() {
        }

        @AfterAll fun tearDown() {
        }
    }

    @Test fun login_validRepoResponse_returnsAuthCookie() {
        // given
        val cookieJar = CookieJar(mapOf("cookie_1" to "value_1"))
        val responseMock = mock<Response> {
            on { cookies } doReturn cookieJar
        }
        val repoMock = mock<IKattisRepository> {
            on { login(any()) } doReturn responseMock
        }
        val api = KattisApi(repoMock)

        // when
        val result = api.login("user", "token")

        // then
        assertEquals(result, cookieJar)
    }
}