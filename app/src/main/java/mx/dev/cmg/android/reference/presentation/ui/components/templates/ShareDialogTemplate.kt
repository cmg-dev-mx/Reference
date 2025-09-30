package mx.dev.cmg.android.reference.presentation.ui.components.templates

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
 * Share Dialog Template Component
 * 
 * Follows Figma design specifications:
 * - Surface background color (#d3d3d3)
 * - 16dp padding on all sides
 * - Centered column layout with 16dp gaps
 * - 32dp spacer before button (from Figma design)
 * - Composable slots for flexible content
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
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title slot
            title()
            
            // Description slot
            description()
            
            // 32dp spacer as shown in Figma design
            Spacer(modifier = Modifier.height(32.dp))
            
            // Action button slot
            actionButton()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShareDialogTemplatePreview() {
    ReferenceTheme {
        ShareDialogTemplate(
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