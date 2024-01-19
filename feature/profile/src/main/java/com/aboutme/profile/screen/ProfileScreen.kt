package com.aboutme.profile.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aboutme.core.model.data.NameInfo
import com.aboutme.core.model.data.UserData
import com.aboutme.core.ui.components.ErrorCard
import com.aboutme.core.ui.components.LabeledCircularProgressIndicator
import com.aboutme.core.ui.components.NameInfoDialog
import com.aboutme.core.ui.locals.LocalWindowSizeClass
import com.aboutme.core.ui.text.formatText
import com.aboutme.core.ui.util.errorCardColors
import com.aboutme.profile.R
import com.aboutme.profile.components.DeleteProfileDialog
import com.aboutme.profile.components.ProfilePicture
import kotlinx.coroutines.flow.collectLatest
import java.time.Instant
import java.time.LocalDate

@Composable
internal fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),

    onGoToAuth: () -> Unit,
    onGoToChangeEmail: () -> Unit,
    onGoToResetPassword: () -> Unit,
    onReturn: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val profileUserState by viewModel.profileState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.uiEvents.collectLatest {
            when (it) {
                ProfileUiEvent.GoToAuth -> onGoToAuth()
                ProfileUiEvent.GoToChangeEmail -> onGoToChangeEmail()
                ProfileUiEvent.GoToResetPassword -> onGoToResetPassword()
                ProfileUiEvent.Return -> onReturn()
            }
        }
    }

    ProfileScreen(
        userState = profileUserState,
        uiState = state.uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun ProfileScreen(
    userState: ProfileUserState,
    uiState: ProfileUiState,
    onEvent: (ProfileEvent) -> Unit
) {
    if (uiState.showDeleteProfileDialog && userState is ProfileUserState.Success) {
        DeleteProfileDialog(
            expectedEmail = userState.user.email,
            onDismiss = {
                onEvent(ProfileEvent.ToggleDeleteProfileDialog)
            },
            onDelete = {
                onEvent(ProfileEvent.DeleteProfile)
            }
        )
    }
    if (uiState.showNameChangeDialog && userState is ProfileUserState.Success) {
        NameInfoDialog(
            nameInfo = userState.user.nameInfo,
            onNameChange = {
                onEvent(ProfileEvent.ChangeName(it))
            }
        )
    }

    val sizeClass = LocalWindowSizeClass.current
    val widthClass = sizeClass.widthSizeClass
    val wide = widthClass > WindowWidthSizeClass.Compact

    when (userState) {
        is ProfileUserState.Error -> {
            ErrorCard(
                description = stringResource(userState.msg)
            )
        }

        ProfileUserState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LabeledCircularProgressIndicator()
            }
        }

        is ProfileUserState.Success -> {
            if (wide) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    TopSection(
                        modifier = Modifier
                            .weight(1F),
                        user = userState.user,
                        onEvent = onEvent
                    )
                    BottomSection(
                        modifier = Modifier
                            .weight(1F)
                            .padding(16.dp),
                        onEvent = onEvent
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TopSection(
                        user = userState.user,
                        onEvent = onEvent
                    )
                    BottomSection(
                        modifier = Modifier
                            .padding(16.dp),
                        onEvent = onEvent
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomSection(
    modifier: Modifier = Modifier,
    onEvent: (ProfileEvent) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onEvent(ProfileEvent.ToggleNameInfoDialog)
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.change_name),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(R.string.change_name_info),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onEvent(ProfileEvent.GoToChangeEmail)
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.change_email),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(R.string.change_email_info),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onEvent(ProfileEvent.GoToResetPassword)
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.change_password),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(R.string.change_password_info),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onEvent(ProfileEvent.LogOut)
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.log_out),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(R.string.log_out_info),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onEvent(ProfileEvent.LogOutAll)
            },
            colors = CardDefaults.errorCardColors()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.log_out_all),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(R.string.log_out_all_info),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onEvent(ProfileEvent.ToggleDeleteProfileDialog)
            },
            colors = CardDefaults.errorCardColors()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.delete_account),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(R.string.delete_account_info),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Suppress("WrapUnaryOperator")
@Composable
private fun TopSection(
    modifier: Modifier = Modifier,
    user: UserData,
    onEvent: (ProfileEvent) -> Unit
) {
    Box(
        modifier = modifier
            .padding(top = 128.dp),
        contentAlignment = Alignment.Center
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 64.dp + 10.dp,
                        end = 12.dp,
                        bottom = 12.dp,
                        start = 8.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = user.nameInfo.formatText(),
                    style = MaterialTheme.typography.headlineSmall
                        .copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.titleSmall
                )
                TextButton(
                    onClick = {
                        onEvent(ProfileEvent.GoToResetPassword)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.reset_password)
                    )
                }
            }
        }
        ProfilePicture(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .height(128.dp)
                .offset(y = -64.dp),
            painter = painterResource(R.drawable.profile_picture_placeholder),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun TopSectionPreview() {
    TopSection(
        modifier = Modifier
            .width(350.dp),
        user = UserData(
            nameInfo = NameInfo("John", "Peter", "Doe", "Dr."),
            email = "john.peter.doe@gmx.de",
            Instant.now(),
            Instant.now()
        ),
        onEvent = {}
    )
}

@Preview
@Composable
private fun BottomSectionPreview() {
    Surface {
        BottomSection(
            modifier = Modifier
                .width(350.dp)
                .padding(12.dp),
            onEvent = {}
        )
    }
}