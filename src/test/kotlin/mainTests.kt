import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import interfaces.IFileReader
import kattis.testData.MockResponses
import org.junit.jupiter.api.Test

internal class MainTests {

    @Test fun `main - invalid parameter - completes successfully`() {
        // given
        val args = arrayOf("-invalid")

        // when
        main(args)
    }
    @Test fun `main - help parameter - completes successfully`() {
        // given
        val args = arrayOf("-h")

        // when
        main(args)
    }
    @Test fun `main - valid parameters - completes successfully`() {
        // given
        val wireMockServer = WireMockServer(MockPorts.MainTests)
        wireMockServer.start()

        Config.Port = "${MockPorts.MainTests}"
        WireMock.configureFor(MockPorts.MainTests)

        stubFor(get(urlPathEqualTo("/new-contest"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200).withBody(MockResponses.NewContestScript)))
        stubFor(post(urlPathEqualTo("/login"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)))
        stubFor(put(urlPathEqualTo("/ajax/session"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200).withBody(MockResponses.Contest)))
        stubFor(get(urlPathEqualTo("/problems"))
                .withQueryParam("page", matching("\\d+"))
                .withQueryParam("order",  equalTo("problem_difficulty"))
                .withQueryParam("show_untried", equalTo("on"))
                .withQueryParam("show_tried", equalTo("off"))
                .withQueryParam("show_solved", equalTo("off"))
                .willReturn(aResponse()
                        .withStatus(200).withBody(MockResponses.ProblemsTable)))
        stubFor(post(urlPathEqualTo("/ajax/session/problem"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)))
        stubFor(post(urlPathEqualTo("/ajax/session/team"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200).withBody(MockResponses.Team)))
        stubFor(post(urlPathEqualTo("/ajax/session/team/member"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)))

        val mockFileReader = mock<IFileReader> {
            on { read("teams") } doReturn mutableListOf("team_1: test-member-1-1, test-member-1-2", "team_2: test-member-2-1")
            on { read("credentials") } doReturn mutableListOf("username: test-user", "token: test-token")
        }

        val args = arrayOf("--teams", "teams", "--settings", "credentials", "-v")

        // when
        main(args, mockFileReader)

        // then
        verify(getRequestedFor(urlPathEqualTo("/new-contest")))
        verify(postRequestedFor(urlPathEqualTo("/login")))
        verify(putRequestedFor(urlPathEqualTo("/ajax/session")))
        verify(getRequestedFor(urlPathEqualTo("/problems")))
        verify(postRequestedFor(urlPathEqualTo("/ajax/session/problem")))
        verify(postRequestedFor(urlPathEqualTo("/ajax/session/team")))
        verify(postRequestedFor(urlPathEqualTo("/ajax/session/team/member")))

        wireMockServer.stop()
    }
}