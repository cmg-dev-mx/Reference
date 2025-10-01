package mx.dev.cmg.android.reference.presentation.ui.components.atoms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.dev.cmg.android.reference.R
import mx.dev.cmg.android.reference.ui.theme.ReferenceTheme

/**
 * Primary Button Atom Component
 * 
 * Follows Figma design specifications:
 * - Primary color background (#504aff)
 * - 8dp border radius
 * - 16dp horizontal padding, 8dp vertical padding
 * - 8dp gap between text and icon
 * - Kode Mono Bold 16sp font
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    showIcon: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge
            )
            
            if (showIcon) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_share),
                    contentDescription = "Share icon",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonPreview() {
    ReferenceTheme {
        PrimaryButton(
            text = "Share with someone",
            onClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonWithoutIconPreview() {
    ReferenceTheme {
        PrimaryButton(
            text = "Share with someone",
            onClick = { },
            showIcon = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonDisabledPreview() {
    ReferenceTheme {
        PrimaryButton(
            text = "Share with someone",
            onClick = { },
            enabled = false
        )
    }
}