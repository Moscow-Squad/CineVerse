package com.moscow.cineverse.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<T, E>(
    initialState: T
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<T> = _uiState.asStateFlow()

    private val _uiEvent = Channel<E>()
    val uiEvent = _uiEvent.receiveAsFlow()

    protected fun updateState(transform: (T) -> T) {
        _uiState.update { transform(it) }
    }

    protected fun sendEvent(event: E) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    protected fun <R> launchWithResult(
        action: suspend () -> R,
        onSuccess: (R) -> Unit,
        onError: (Throwable) -> Unit,
        onStart: () -> Unit = {},
        onFinally: () -> Unit = {}
    ) {
        viewModelScope.launch {
            onStart()
            runCatching { action() }
                .onSuccess(onSuccess)
                .onFailure(onError)
            onFinally()
        }
    }
}