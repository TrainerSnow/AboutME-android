package com.aboutme.feature.preferences.screen.main;

import androidx.lifecycle.viewModelScope
import com.aboutme.core.data.repository.PreferencesRepository
import com.aboutme.core.domain.viewmodel.AboutMeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PreferencesViewModel @Inject constructor(
    val preferencesRepos: PreferencesRepository
) : AboutMeViewModel<PreferencesEvent, PreferencesUiEvent, Unit>() {

    override val initialState = Unit

    val preferences = preferencesRepos
        .preferences
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    override fun handleEvent(event: PreferencesEvent) {
        when (event) {
            is PreferencesEvent.ChangeColorMode -> viewModelScope.launch {
                preferencesRepos.updateColorMode(event.colorMode)
            }

            is PreferencesEvent.ChangeColorTheme -> viewModelScope.launch {
                preferencesRepos.updateColorTheme(event.colorTheme)
            }

            PreferencesEvent.Return -> {
                triggerUiEvent(PreferencesUiEvent.Return)
            }

            PreferencesEvent.OpenSyncPreferences -> {
                triggerUiEvent(PreferencesUiEvent.GoToSyncPreferences)
            }
        }
    }

}