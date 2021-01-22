package com.example.projectpiggy2.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectpiggy2.user.UserWrapper

/**
 * Helper enum to determine if we are inputting a PIN or confirming a PIN
 */
enum class PIN_TYPE {
    INPUT, CONFIRM
}

/**
 * This state is meant to reflect what the UI state looks like at any moment
 * during the view's lifecycle. Very handy for unit testing what the internal viewmodel
 * logic looks like without ever having to actually make a UI
 * Our pin is a string because technically 0123 is a valid 4 digit pin, but an int will make it 123
 */
data class PinViewState(
    val pin: String = "",
    val loginSuccess: Boolean = false,
    val loginError: Boolean = false,
    val buttonEnabled: Boolean = false,
    val buttonText: String = "Done",
    val inputType: PIN_TYPE = PIN_TYPE.INPUT
)

/**
 * These are the events that any UI can send to this viewmodel. Makes the viewmodel testable
 * and decouples it from the UI further than method calls
 */
sealed class PinUIEvent {
    data class PinSubmitted(val pin: String): PinUIEvent()
    data class ConfirmSubmitted(val confirm: String): PinUIEvent()
    data class PinInput(val pin: String): PinUIEvent()
    object BackPressed: PinUIEvent()
    object ErrorReset: PinUIEvent()
}

/** We inject the user information dependency using koin. This makes it so we can mock out user
 * info for unit testing easily by calling the constructor using a mock:
 * val viewModel = PinViewModel(mockUserImplementation)
 */
class PinViewModel(private val userInfo: UserWrapper): ViewModel() {


    /** Separated because observers subscribe to viewState, which is immutable. All modifications
     * to the viewstate should be done to the internal state and reflected to the immutable version
     */
    private val _viewState: MutableLiveData<PinViewState> = MutableLiveData()
    val viewState: LiveData<PinViewState> = _viewState

    /**
     * internal variable that we instantiate from intent data
     */
    var name: String = ""

    /**
     * Initialize this state with an empty viewstate since it's assumed the user is logging
     * in on each app opening
     */
    init {
        _viewState.value = PinViewState()
    }


    /**
     * Handle UI events here
     */
    fun onUIEvent(event: PinUIEvent) {
        when(event) {
            is PinUIEvent.PinSubmitted -> {
                _viewState.value = _viewState.value!!.copy(
                    pin = event.pin,
                    buttonText = "Confirm",
                    inputType = PIN_TYPE.CONFIRM
                )
            }

            is PinUIEvent.ConfirmSubmitted -> {
                _viewState.value?.let {state ->
                    if(state.pin == event.confirm) {
                        userInfo.createUser(state.pin, name)
                        _viewState.value = state.copy(
                            loginSuccess = true,
                            loginError = false
                        )
                    } else {
                        _viewState.value = state.copy(
                            loginError = true
                        )
                    }
                } ?: throw Error("ViewState is null")
            }

            is PinUIEvent.PinInput -> {
                _viewState.value = _viewState.value!!.copy(
                    buttonEnabled = true
                )
            }

            is PinUIEvent.BackPressed -> {
                _viewState.value = PinViewState()
            }
            is PinUIEvent.ErrorReset -> {
                _viewState.value = _viewState.value!!.copy(loginError = false)
            }
        }


    }




}