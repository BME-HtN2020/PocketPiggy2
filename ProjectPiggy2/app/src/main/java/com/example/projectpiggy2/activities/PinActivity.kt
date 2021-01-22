package com.example.projectpiggy2.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.example.projectpiggy2.MainActivity
import com.example.projectpiggy2.R
import com.example.projectpiggy2.databinding.ActivityPinBinding
import com.example.projectpiggy2.viewmodels.PIN_TYPE
import com.example.projectpiggy2.viewmodels.PinUIEvent
import com.example.projectpiggy2.viewmodels.PinViewModel
import com.example.projectpiggy2.viewmodels.PinViewState
import org.koin.android.ext.android.inject

class PinActivity: AppCompatActivity() {

    /**
     * Injecting out viewmodel into our activity using some di magic
     */
    val viewModel: PinViewModel by inject()

    /**
     * This is using the new layout binding library in android:
     * https://developer.android.com/topic/libraries/view-binding
     */
    private lateinit var binding: ActivityPinBinding

    /**
     * Sends our pin inputs to the viewmodel to do some simple calculations on
     */
    private val pinWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            viewModel.onUIEvent(PinUIEvent.PinInput(s.toString()))
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * setting up our view binding for our xml
         */
        binding = ActivityPinBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /**
         * Process the name that was passed from the previous activity
         */
        intent?.extras?.let {
            viewModel.name = it.getString("name") ?: ""
        }

        binding.editTextNumber.addTextChangedListener(pinWatcher)

        /**
         * Setting up an observer for our viewmodel's viewstate
         */
        viewModel.viewState.observe(this, Observer {handleStateChange(it)})

    }

    /**
     * Change the android back press so it clears the inputs first, then moves the user out of the app
     */
    override fun onBackPressed() {
        viewModel.onUIEvent(PinUIEvent.BackPressed)
        //Let the user exit the app when the input is cleared, not the best at the moment, but this is a quick thing
        viewModel.viewState.value?.let {
            if(it.pin.isBlank()) {
                super.onBackPressed()
            }
        }
    }


    /**
     * What happens when the viewmodel's state updates
     */
    fun handleStateChange(state: PinViewState) {
        binding.doneButton.text = state.buttonText
        binding.doneButton.isEnabled = state.buttonEnabled

        if(state.buttonEnabled) {
            binding.doneButton.setOnClickListener {
                val inputPin = binding.editTextNumber.text.toString()
                when(state.inputType) {
                    PIN_TYPE.INPUT -> {
                        viewModel.onUIEvent(PinUIEvent.PinSubmitted(inputPin))
                        binding.editTextNumber.setText("")
                    }
                    PIN_TYPE.CONFIRM -> {
                        viewModel.onUIEvent(PinUIEvent.ConfirmSubmitted(inputPin))
                    }
                }

            }
        } else {
            binding.doneButton.setOnClickListener(null)
        }


        if(state.loginSuccess) {
            val mainActivity = Intent(getApplicationContext(), MainActivity::class.java);
            /**
             * Clear the backstack. We don't want the user going back to login after logging in
             */
            mainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(mainActivity);
        }

        /**
         * If pins don't match, clear the confirmation input and have the user submit another pin
         */
        if(state.loginError) {
            Toast.makeText(applicationContext, "Login failed, pin is incorrect", Toast.LENGTH_SHORT).show()
            binding.editTextNumber.setText("")
            viewModel.onUIEvent(PinUIEvent.ErrorReset)
        }
    }
}