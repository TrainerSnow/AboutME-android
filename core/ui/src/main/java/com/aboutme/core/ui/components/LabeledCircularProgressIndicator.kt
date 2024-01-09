package com.aboutme.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.aboutme.core.ui.R

@Composable
fun LabeledCircularProgressIndicator(
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        ProvideTextStyle(MaterialTheme.typography.labelLarge, label)
    }
}

@Composable
fun LabeledCircularProgressIndicator(
    modifier: Modifier = Modifier,
    label: String = stringResource(R.string.loading)
) = LabeledCircularProgressIndicator(
    modifier = modifier,
    label = { Text(label) }
)