package kattis

import kattis.models.Contest
import org.junit.Before
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import com.github.tomakehurst.wiremock.*
import com.github.tomakehurst.wiremock.client.*
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import kattis.models.Team
import kattis.testData.MockResponses

internal class KattisRepositoryTests {

    private val _wireMockServer = WireMockServer()

    @BeforeEach fun testSetUp() {
        _wireMockServer.start()

        stubFor(post(urlEqualTo("/login"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Set-Cookie", "cookie=value")))
    }

    @AfterEach fun testTearDown() {
        _wireMockServer.stop()
    }

    @Test fun `addTeamToContest - a team with two members - posts one team and two members`() {
        // given
        stubFor(post(urlEqualTo("/ajax/session/team"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(MockResponses.Team)))
        stubFor(post(urlEqualTo("/ajax/session/team/member"))
                .willReturn(aResponse()
                        .withStatus(200)))

        val contest = Contest()
        val team = Team("team", listOf("member_1", "member_2"))
        val repository = KattisRepository()

        // when
        repository.login(mapOf( "user" to "someone"))
        repository.addTeamToContest(contest, team)

        // then
        verify(postRequestedFor(urlEqualTo("/ajax/session/team")))
        verify(2, postRequestedFor(urlEqualTo("/ajax/session/team/member")))
    }
    @Test fun `createNewContest - user's not authenticated - throws UninitializedPropertyAccessException`() {
        assertThrows(UninitializedPropertyAccessException::class.java, {
            // given
            val repository = KattisRepository()
            val contest = Contest()

            // when
            repository.createNewContest(contest)
        })
    }
    @Test fun `getProblemsPage - user's not authenticated - throws UninitializedPropertyAccessException`() {
        assertThrows(UninitializedPropertyAccessException::class.java, {
            // given
            val repository = KattisRepository()

            // when
            repository.getProblemsPage()
        })
    }
    @Test fun `getNewContestPage - user's not authenticated - throws UninitializedPropertyAccessException`() {
        assertThrows(UninitializedPropertyAccessException::class.java, {
            // given
            val repository = KattisRepository()

            // when
            repository.getNewContestPage()
        })
    }

}