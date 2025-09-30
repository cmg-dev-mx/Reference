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
 * Share Dialog Screen Component - Referral Magic Edition 🌟💀
 * 
 * Implements the main referral invitation screen following Figma design specifications.
 * Uses Atomic Design methodology with Templates and Atoms.
 * 
 * This is the main screen that shows:
 * - App title: "Referir un Amigo"
 * - Description text explaining the referral program benefits
 * - Primary action button: "Enviar invitación"
 */
@Composable
fun ShareDialogScreen(
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ShareDialogTemplate(
        modifier = modifier,
        title = {
            TitleText(text = "Referir un Amigo")
        },
        description = {
            ContentText(
                text = "¡Invita a tus amigos y obtén beneficios exclusivos! Comparte tu código de referido único y ayuda a crecer nuestra comunidad."
            )
        },
        actionButton = {
            PrimaryButton(
                text = "Enviar invitación",
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