package com.aboutme.feature.auth.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aboutme.core.ui.components.ErrorCard
import com.aboutme.core.ui.components.LabeledCircularProgressIndicator

internal sealed class AuthState {

    data object Loading : AuthState()

    data object None : AuthState()

    data class Error(val description: String) : AuthState()

}

@Composable
internal fun AuthErrorSection(
    modifier: Modifier = Modifier,
    authState: AuthState
) {
    when (authState) {
        is AuthState.Error -> {
            Box(
                modifier
                    .widthIn(max = 300.dp),
                contentAlignment = Alignment.Center
            ) {
                ErrorCard(description = authState.description)
            }
        }

        AuthState.Loading -> {
            LabeledCircularProgressIndicator(modifier)
        }

        AuthState.None -> {
            Box(modifier)
        }
    }
}