package com.aboutme.app;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aboutme.core.data.repository.PreferencesRepository
import com.aboutme.core.model.preferences.SyncOption
import com.aboutme.core.sync.SyncController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AboutMeAppViewModel @Inject constructor(
    val preferencesRepository: PreferencesRepository,
    val syncController: SyncController
): ViewModel() {

    init {
        viewModelScope.launch {
            val prefs = preferencesRepository.preferences.first()
            if(prefs.syncPreferences.syncOption == SyncOption.OnEnter) {
                syncController.syncNow()
            }
        }
    }

}