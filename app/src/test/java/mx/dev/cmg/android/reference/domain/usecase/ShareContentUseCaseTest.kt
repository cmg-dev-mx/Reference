package mx.dev.cmg.android.reference.domain.usecase

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import mx.dev.cmg.android.reference.domain.model.ShareContent
import mx.dev.cmg.android.reference.domain.model.ShareResult
import mx.dev.cmg.android.reference.domain.model.InstalledApp
import mx.dev.cmg.android.reference.domain.repository.ShareRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Unit Tests: Share Content Use Case
 * 
 * Tests the business logic for sharing content with other applications.
 */
class ShareContentUseCaseTest {

    @Mock
    private lateinit var shareRepository: ShareRepository

    private lateinit var shareContentUseCase: ShareContentUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        shareContentUseCase = ShareContentUseCase(shareRepository)
    }

    @Test
    fun `invoke should return success result when sharing succeeds`() = runTest {
        // Given
        val shareContent = ShareContent(
            title = "Test App",
            text = "Check out this app!",
            mimeType = "text/plain"
        )
        val successResult = ShareResult.Success(
            InstalledApp(packageName = "test.app", appName = "Test App")
        )
        `when`(shareRepository.shareContent(shareContent)).thenReturn(flowOf(successResult))

        // When
        val result = shareContentUseCase(shareContent).first()

        // Then
        assertTrue(result is ShareResult.Success)
        verify(shareRepository).shareContent(shareContent)
    }

    @Test
    fun `invoke should return error result when sharing fails`() = runTest {
        // Given
        val shareContent = ShareContent(
            title = "Test App",
            text = "Check out this app!",
            mimeType = "text/plain"
        )
        val errorResult = ShareResult.Error("Sharing failed")
        `when`(shareRepository.shareContent(shareContent)).thenReturn(flowOf(errorResult))

        // When
        val result = shareContentUseCase(shareContent).first()

        // Then
        assertTrue(result is ShareResult.Error)
        assertEquals("Sharing failed", (result as ShareResult.Error).message)
        verify(shareRepository).shareContent(shareContent)
    }

    @Test
    fun `invoke should return no apps available when no sharing apps exist`() = runTest {
        // Given
        val shareContent = ShareContent(
            title = "Test App",
            text = "Check out this app!",
            mimeType = "text/plain"
        )
        val noAppsResult = ShareResult.NoAppsAvailable
        `when`(shareRepository.shareContent(shareContent)).thenReturn(flowOf(noAppsResult))

        // When
        val result = shareContentUseCase(shareContent).first()

        // Then
        assertTrue(result is ShareResult.NoAppsAvailable)
        verify(shareRepository).shareContent(shareContent)
    }
}