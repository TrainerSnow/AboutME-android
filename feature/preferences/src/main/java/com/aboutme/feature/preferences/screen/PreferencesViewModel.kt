package com.aboutme.feature.preferences.screen;

import androidx.lifecycle.viewModelScope
import com.aboutme.core.data.repository.PreferencesRepository
import com.aboutme.core.domain.viewmodel.AboutMeViewModel
import com.aboutme.core.model.preferences.SyncOption
import com.aboutme.feature.preferences.model.toDuration
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

            is PreferencesEvent.ChangeSyncOption -> {
                val newSyncPref = preferences.value?.syncPreferences?.copy(
                    syncOption = event.syncOption
                ) ?: return
                viewModelScope.launch {
                    preferencesRepos.updateSyncPreferences(newSyncPref)
                }
            }

            is PreferencesEvent.ChangeSyncPeriod -> {
                val newSyncPref = preferences.value?.syncPreferences?.copy(
                    period = event.syncPeriod.toDuration()
                ) ?: return
                viewModelScope.launch {
                    preferencesRepos.updateSyncPreferences(newSyncPref)
                }
            }

            PreferencesEvent.Return -> {
                triggerUiEvent(PreferencesUiEvent.Return)
            }
            PreferencesEvent.ToggleSyncEnabled -> {
                val newSyncPref = preferences.value?.syncPreferences?.copy(
                    syncOption = if (preferences.value?.syncPreferences?.syncOption != SyncOption.Not) SyncOption.Not
                    else SyncOption.OnEnter
                ) ?: return
                viewModelScope.launch {
                    preferencesRepos.updateSyncPreferences(newSyncPref)
                }
            }
        }
    }

}