package com.aboutme.core.domain.viewmodel;

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Base class for any view model in the application
 */
@Suppress("LeakingThis")
abstract class AboutMeViewModel<Event, UiEvent, State> : ViewModel() {

    //
    // Event
    //

    private val events: MutableSharedFlow<Event> = MutableSharedFlow()

    init {
        viewModelScope.launch {
            events.collect(::handleEvent)
        }
    }

    /**
     * Triggers the given event
     *
     * @param event The event to invoke
     */
    fun onEvent(event: Event) {
        viewModelScope.launch {
            events.emit(event)
        }
    }

    /**
     * Called for every event that is triggered
     *
     * @param event The event that was triggered
     */
    abstract fun handleEvent(event: Event)


    //
    // State
    //

    /**
     * The instance of the state the view model owns when it is created
     */
    abstract val initialState: State

    //by lazy because 'initialState' is not initialized by subclasses
    private val _state: MutableStateFlow<State> by lazy { MutableStateFlow(initialState) }

    /**
     * Contains a flow of all the values the state will contain
     */
    val state: StateFlow<State> by lazy { _state.asStateFlow() }

    /**
     * Updates the state
     *
     * @param state The new state
     */
    fun updateState(state: State) {
        viewModelScope.launch {
            _state.emit(state)
        }
    }

    /**
     * Updates the state
     *
     * @param function The function to evaluate the new state
     */
    fun updateState(function: (State) -> State) {
        viewModelScope.launch {
            _state.update(function)
        }
    }

    //
    // UI-Event
    //

    private val _uiEvents = MutableSharedFlow<UiEvent>()

    /**
     * Flow that contains all the ui events triggered from the view model
     */
    val uiEvents = _uiEvents.asSharedFlow()

    /**
     * Should be called when a ui event is triggered
     *
     * @param event The triggered event
     */
    fun triggerUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvents.emit(event)
        }
    }
}