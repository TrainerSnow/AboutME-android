package com.aboutme.feature.auth.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextDecoration

@Composable
internal fun AuthBottomSection(
    modifier: Modifier = Modifier,
    text: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    onTextClick: () -> Unit,
    tablet: Boolean
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .clickable(
                    enabled = true,
                    onClickLabel = text,
                    role = Role.Button,
                    onClick = onTextClick
                ),
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            textDecoration = TextDecoration.Underline,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        val buttonMod = if (tablet) Modifier
            .align(Alignment.End)
        else Modifier.fillMaxWidth()

        Button(
            modifier = buttonMod,
            onClick = onButtonClick
        ) {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = buttonText
            )
            Text(
                text = buttonText
            )
        }
    }
}