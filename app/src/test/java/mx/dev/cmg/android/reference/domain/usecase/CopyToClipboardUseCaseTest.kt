package mx.dev.cmg.android.reference.domain.usecase

import kotlinx.coroutines.test.runTest
import mx.dev.cmg.android.reference.domain.model.ShareContent
import mx.dev.cmg.android.reference.domain.repository.ShareRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Test: Copy To Clipboard Use Case
 * 
 * Tests the CopyToClipboardUseCase to ensure it correctly
 * delegates to the repository and handles the result.
 */
class CopyToClipboardUseCaseTest {

    @Mock
    private lateinit var shareRepository: ShareRepository

    private lateinit var copyToClipboardUseCase: CopyToClipboardUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        copyToClipboardUseCase = CopyToClipboardUseCase(shareRepository)
    }

    @Test
    fun `invoke should return true when clipboard copy succeeds`() = runTest {
        // Given
        val shareContent = ShareContent(
            title = "Test Title",
            text = "Test Text",
            url = "https://test.com"
        )
        `when`(shareRepository.copyToClipboard(shareContent)).thenReturn(true)

        // When
        val result = copyToClipboardUseCase(shareContent)

        // Then
        assertTrue(result)
        verify(shareRepository).copyToClipboard(shareContent)
    }

    @Test
    fun `invoke should return false when clipboard copy fails`() = runTest {
        // Given
        val shareContent = ShareContent(
            title = "Test Title",
            text = "Test Text",
            url = "https://test.com"
        )
        `when`(shareRepository.copyToClipboard(shareContent)).thenReturn(false)

        // When
        val result = copyToClipboardUseCase(shareContent)

        // Then
        assertFalse(result)
        verify(shareRepository).copyToClipboard(shareContent)
    }

    @Test
    fun `invoke should call repository with correct content`() = runTest {
        // Given
        val shareContent = ShareContent(
            title = "Referir un Amigo",
            text = "Invita a tus amigos",
            url = "https://app.reference.com/join?ref=ABC123"
        )
        `when`(shareRepository.copyToClipboard(shareContent)).thenReturn(true)

        // When
        copyToClipboardUseCase(shareContent)

        // Then
        verify(shareRepository).copyToClipboard(shareContent)
        verifyNoMoreInteractions(shareRepository)
    }
}
