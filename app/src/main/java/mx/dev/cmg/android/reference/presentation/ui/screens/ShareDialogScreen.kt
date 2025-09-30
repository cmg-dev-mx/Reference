package mx.dev.cmg.android.reference.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mx.dev.cmg.android.reference.presentation.ui.components.atoms.ContentText
import mx.dev.cmg.android.reference.presentation.ui.components.atoms.PrimaryButton
import mx.dev.cmg.android.reference.presentation.ui.components.atoms.TitleText
import mx.dev.cmg.android.reference.presentation.ui.components.templates.ShareDialogTemplate
import mx.dev.cmg.android.reference.ui.theme.ReferenceTheme

/**
 * Share Dialog Screen Component
 * 
 * Implements the main share dialog screen following Figma design specifications.
 * Uses Atomic Design methodology with Templates and Atoms.
 * 
 * This is the main screen that shows:
 * - App title: "Reference App"
 * - Description text explaining the demo app purpose
 * - Primary action button: "Share with someone"
 */
@Composable
fun ShareDialogScreen(
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ShareDialogTemplate(
        modifier = modifier,
        title = {
            TitleText(text = "Reference App")
        },
        description = {
            ContentText(
                text = "This is a demo app, to share to friends and family as a reference to the actual user account."
            )
        },
        actionButton = {
            PrimaryButton(
                text = "Share with someone",
                onClick = onShareClick
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ShareDialogScreenPreview() {
    ReferenceTheme {
        ShareDialogScreen(
            onShareClick = { /* Handle share action */ }
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Share Dialog Screen - Full Screen"
)
@Composable
fun ShareDialogScreenFullPreview() {
    ReferenceTheme {
        ShareDialogScreen(
            onShareClick = { /* Handle share action */ }
        )
    }
}