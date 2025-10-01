package mx.dev.cmg.android.reference.presentation.ui.components.atoms

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import mx.dev.cmg.android.reference.ui.theme.ReferenceTheme

/**
 * Title Text Atom Component
 * 
 * Follows Figma design specifications:
 * - Kode Mono Bold 24sp font
 * - OnSurface color (#2b2b2b)
 * - Used for main titles
 */
@Composable
fun TitleText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign? = null
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = color,
        modifier = modifier,
        textAlign = textAlign
    )
}

/**
 * Content Text Atom Component
 * 
 * Follows Figma design specifications:
 * - Kode Mono Regular 12sp font
 * - OnSurface color (#2b2b2b)
 * - Used for descriptions and body content
 * - Supports multiline text with proper overflow handling
 */
@Composable
fun ContentText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = color,
        modifier = modifier,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = androidx.compose.ui.text.style.TextOverflow.Clip
    )
}

/**
 * Label Text Atom Component
 * 
 * Follows Figma design specifications:
 * - Kode Mono Bold 16sp font
 * - OnPrimary color (#d3d3d3) - used for button labels
 * - Used for labels and emphasized text
 */
@Composable
fun LabelText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onPrimary,
    textAlign: TextAlign? = null
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = color,
        modifier = modifier,
        textAlign = textAlign
    )
}

// PREVIEWS

@Preview(showBackground = true)
@Composable
fun TitleTextPreview() {
    ReferenceTheme {
        TitleText(text = "Reference App")
    }
}

@Preview(showBackground = true)
@Composable
fun ContentTextPreview() {
    ReferenceTheme {
        ContentText(text = "This is a demo app, to share to friends and family as a reference to the actual user account.")
    }
}

@Preview(showBackground = true)
@Composable
fun LabelTextPreview() {
    ReferenceTheme {
        LabelText(text = "Share with someone")
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF504AFF)
@Composable
fun LabelTextOnPrimaryPreview() {
    ReferenceTheme {
        LabelText(
            text = "Share with someone",
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}