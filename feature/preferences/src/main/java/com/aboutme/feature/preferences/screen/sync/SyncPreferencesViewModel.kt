package com.aboutme.feature.preferences.screen.sync;

import androidx.lifecycle.viewModelScope
import com.aboutme.core.data.repository.PreferencesRepository
import com.aboutme.core.data.repository.SyncResultRepository
import com.aboutme.core.domain.viewmodel.AboutMeViewModel
import com.aboutme.core.model.preferences.SyncOption
import com.aboutme.core.sync.SyncController
import com.aboutme.core.sync.SyncMonitor
import com.aboutme.feature.preferences.model.toDuration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SyncPreferencesViewModel @Inject constructor(
    val prefRepo: PreferencesRepository,
    val syncMonitor: SyncMonitor,
    val syncRepo: SyncResultRepository,
    val syncController: SyncController
) : AboutMeViewModel<SyncPreferencesEvent, SyncPreferencesUiEvent, Unit>() {

    override val initialState = Unit

    val syncPrefs = prefRepo
        .preferences
        .map { it.syncPreferences }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val syncState = createSyncState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SyncState.None
        )

    override fun handleEvent(event: SyncPreferencesEvent) {
        when (event) {
            is SyncPreferencesEvent.ChangeSyncOption -> {
                val newSyncPref = syncPrefs.value?.copy(
                    syncOption = event.syncOption
                ) ?: return
                viewModelScope.launch {
                    prefRepo.updateSyncPreferences(newSyncPref)
                }
            }

            is SyncPreferencesEvent.ChangeSyncPeriod -> {
                val newSyncPref = syncPrefs.value?.copy(
                    period = event.syncPeriod.toDuration()
                ) ?: return
                viewModelScope.launch {
                    prefRepo.updateSyncPreferences(newSyncPref)
                }
            }

            SyncPreferencesEvent.Return -> {
                triggerUiEvent(SyncPreferencesUiEvent.Return)
            }

            SyncPreferencesEvent.ToggleSyncEnabled -> {
                val newSyncPref = syncPrefs.value?.copy(
                    syncOption = if (syncPrefs.value?.syncOption != SyncOption.Not) SyncOption.Not
                    else SyncOption.OnEnter
                ) ?: return
                viewModelScope.launch {
                    prefRepo.updateSyncPreferences(newSyncPref)
                }
            }

            SyncPreferencesEvent.GoToAuth -> TODO()
            is SyncPreferencesEvent.SyncInfo -> TODO()
            SyncPreferencesEvent.SyncNow -> {
                viewModelScope.launch {
                    syncController.syncNow()
                }
            }
        }
    }

    private fun createSyncState(): Flow<SyncState> {
        val recentFlow = syncRepo
            .getMostRecent()
        val syncingFlow = syncMonitor.isSyncing()

        return combine(
            flow = recentFlow,
            flow2 = syncingFlow
        ) { syncResult, isSyncing ->
            if (isSyncing) SyncState.Syncing
            else if (syncResult != null) SyncState.NotSyncing(syncResult)
            else SyncState.None
        }
    }
}