package mx.dev.cmg.android.reference.domain.usecase

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import mx.dev.cmg.android.reference.domain.model.AppCategory
import mx.dev.cmg.android.reference.domain.model.InstalledApp
import mx.dev.cmg.android.reference.domain.repository.AppsRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Unit Tests: Get Installed Apps Use Case
 * 
 * Tests the business logic for retrieving installed apps.
 * Uses MockK for mocking dependencies and Coroutine Test for async testing.
 */
class GetInstalledAppsUseCaseTest {

    @Mock
    private lateinit var appsRepository: AppsRepository

    private lateinit var getInstalledAppsUseCase: GetInstalledAppsUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getInstalledAppsUseCase = GetInstalledAppsUseCase(appsRepository)
    }

    @Test
    fun `invoke should return list of installed apps from repository`() = runTest {
        // Given
        val expectedApps = listOf(
            InstalledApp(
                packageName = "com.whatsapp",
                appName = "WhatsApp",
                category = AppCategory.SOCIAL
            ),
            InstalledApp(
                packageName = "com.google.android.gm",
                appName = "Gmail",
                category = AppCategory.EMAIL
            )
        )
        `when`(appsRepository.getInstalledApps()).thenReturn(flowOf(expectedApps))

        // When
        val result = getInstalledAppsUseCase().first()

        // Then
        assertEquals(expectedApps, result)
        verify(appsRepository).getInstalledApps()
    }

    @Test
    fun `invoke should return empty list when repository returns empty`() = runTest {
        // Given
        val expectedApps = emptyList<InstalledApp>()
        `when`(appsRepository.getInstalledApps()).thenReturn(flowOf(expectedApps))

        // When
        val result = getInstalledAppsUseCase().first()

        // Then
        assertEquals(expectedApps, result)
        verify(appsRepository).getInstalledApps()
    }
}