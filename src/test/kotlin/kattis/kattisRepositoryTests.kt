package kattis

import kattis.models.Contest
import org.junit.Before
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import com.github.tomakehurst.wiremock.*
import com.github.tomakehurst.wiremock.client.*
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.nhaarman.mockito_kotlin.eq
import kattis.models.Problem
import kattis.models.Team
import kattis.testData.MockResponses

internal class KattisRepositoryTests {
    private val _wireMockServer = WireMockServer()

    @BeforeEach fun testSetUp() {
        if (!_wireMockServer.isRunning) {
            _wireMockServer.start()
        }
        _wireMockServer.resetAll()

        stubFor(post(urlEqualTo("/login"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Set-Cookie", "cookie=value")))
    }

    @AfterEach fun testTearDown() {
        _wireMockServer.stop()
    }

    @Test fun `addTeamToContest - valid contest and a team with two members - posts one team and two members`() {
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
        repository.login(mapOf( "user" to "someone"))

        // when
        repository.addTeamToContest(contest, team)

        // then
        verify(postRequestedFor(urlEqualTo("/ajax/session/team")))
        verify(2, postRequestedFor(urlEqualTo("/ajax/session/team/member")))
    }
    @Test fun `addProblemToContest - valid contest and a problem - posts one problem`() {
        // given
        stubFor(post(urlEqualTo("/ajax/session/problem"))
                .willReturn(aResponse()
                        .withStatus(200)))

        val contest = Contest()
        val problem = Problem("problem", 2.0)
        val repository = KattisRepository()
        repository.login(mapOf( "user" to "someone"))

        // when
        repository.addProblemToContest(contest, problem)

        // then
        verify(postRequestedFor(urlEqualTo("/ajax/session/problem")))
    }
    @Test fun `getProblemsPage - valid parameters - gets problems page`() {
        // given
        stubFor(get(urlPathEqualTo("/problems"))
                .withQueryParam("page", matching("\\d+"))
                .withQueryParam("order",  equalTo("problem_difficulty"))
                .withQueryParam("show_untried", equalTo("on"))
                .withQueryParam("show_tried", equalTo("off"))
                .withQueryParam("show_solved", equalTo("off"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("<html><b>Sample problem</b></html>")))

        val repository = KattisRepository()
        repository.login(mapOf( "user" to "someone"))

        // when
        val problemsPage = repository.getProblemsPage(2)

        // then
        verify(getRequestedFor(urlPathEqualTo("/problems")))
        assertEquals("<b>Sample problem</b>", problemsPage.select("b").toString())
    }
    @Test fun `createNewContest - valid contest - returns json object with redirect value`() {
        // given
        stubFor(put(urlEqualTo("/ajax/session"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(MockResponses.Contest)))

        val contest = Contest()
        val repository = KattisRepository()
        repository.login(mapOf( "user" to "someone"))

        // when
        val result = repository.createNewContest(contest)

        // then
        verify(putRequestedFor(urlEqualTo("/ajax/session")))
        assertEquals("redirect_value", result.getJSONObject("response").getString("redirect").split("/")[2])
    }
    @Test fun `getNewContestPage - returns new contest's html page`() {
        // given
        stubFor(get(urlPathEqualTo("/new-contest"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("<html><b>New contest page</b></html>")))

        val repository = KattisRepository()
        repository.login(mapOf( "user" to "someone"))

        // when
        val problemsPage = repository.getNewContestPage()

        // then
        verify(getRequestedFor(urlPathEqualTo("/new-contest")))
        assertEquals("<b>New contest page</b>", problemsPage.select("b").toString())
    }
}