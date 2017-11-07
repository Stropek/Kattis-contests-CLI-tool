package kattis

import kattis.models.Contest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertThrows

internal class KattisRepositoryTests {
    @Test fun `createNewContest() given user's not authenticated should throw UninitializedPropertyAccessException`() {
        assertThrows(UninitializedPropertyAccessException::class.java, {
            // given
            val repository = KattisRepository()
            var contest = Contest()

            // when
            repository.createNewContest(contest)
        })
    }
    @Test fun `getProblemsPage() given user's not authenticated should throw UninitializedPropertyAccessException`() {
        assertThrows(UninitializedPropertyAccessException::class.java, {
            // given
            val repository = KattisRepository()

            // when
            repository.getProblemsPage()
        })
    }
    @Test fun `getNewContestPage() given user's not authenticated should throw UninitializedPropertyAccessException`() {
        assertThrows(UninitializedPropertyAccessException::class.java, {
            // given
            val repository = KattisRepository()

            // when
            repository.getNewContestPage()
        })
    }
}