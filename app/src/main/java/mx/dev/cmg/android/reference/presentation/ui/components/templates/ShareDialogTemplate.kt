package mx.dev.cmg.android.reference.presentation.ui.components.templates

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.dev.cmg.android.reference.presentation.ui.components.atoms.ContentText
import mx.dev.cmg.android.reference.presentation.ui.components.atoms.PrimaryButton
import mx.dev.cmg.android.reference.presentation.ui.components.atoms.TitleText
import mx.dev.cmg.android.reference.ui.theme.ReferenceTheme

/**
 * Share Dialog Template Component - Referral Edition 💀⚡
 * 
 * Follows Figma design specifications with top alignment:
 * - Surface background color (#d3d3d3)
 * - 16dp padding on all sides
 * - Elements aligned to top instead of center
 * - Proper spacing between title, description and button
 * - Button centered horizontally with specific width
 * - Dark magic infusion for referral invitation system 🌟
 */
@Composable
fun ShareDialogTemplate(
    title: @Composable () -> Unit,
    description: @Composable () -> Unit,
    actionButton: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart), // Align to top instead of center
            horizontalAlignment = Alignment.Start, // Align content to start for title/description
            verticalArrangement = Arrangement.Top
        ) {
            // Title slot - aligned to start (left)
            title()
            
            // Spacing between title and description (y=63 - y=16 - height=31 = 16dp approx)
            Spacer(modifier = Modifier.height(16.dp))
            
            // Description slot - aligned to start (left)
            description()
            
            // Spacing between description and button (y=157 - y=63 - height=30 = 64dp approx)
            Spacer(modifier = Modifier.height(64.dp))
            
            // Button slot - centered horizontally with specific width
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier.width(237.dp)) {
                    actionButton()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShareDialogTemplatePreview() {
    ReferenceTheme {
        ShareDialogTemplate(
            title = {
                TitleText(text = "Referir un Amigo")
            },
            description = {
                ContentText(
                    text = "¡Invita a tus amigos y obtén beneficios exclusivos! Comparte tu código de referido único."
                )
            },
            actionButton = {
                PrimaryButton(
                    text = "Enviar invitación",
                    onClick = { }
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShareDialogTemplateEmptyPreview() {
    ReferenceTheme {
        ShareDialogTemplate(
            title = { },
            description = { },
            actionButton = { }
        )
    }
}