package com.aboutme.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aboutme.profile.R

@Composable
internal fun ProfilePicture(
    modifier: Modifier = Modifier,
    painter: Painter,
    onClick: () -> Unit
) {
    Image(
        modifier = modifier
            .clip(CircleShape)
            .clickable(
                onClick = onClick
            )
            .border(
                width = 3.dp,
                color = MaterialTheme.colorScheme.onSurface,
                shape = CircleShape
            ),
        painter = painter,
        contentDescription = stringResource(R.string.profile_picture)
    )
}