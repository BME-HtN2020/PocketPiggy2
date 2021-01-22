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

    val viewModel: PinViewModel by inject()

    private lateinit var binding: ActivityPinBinding

    private val pinWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            viewModel.onUIEvent(PinUIEvent.PinInput(s.toString()))
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        intent?.extras?.let {
            viewModel.name = it.getString("name") ?: ""
        }
        binding.editTextNumber.addTextChangedListener(pinWatcher)
        viewModel.viewState.observe(this, Observer {handleStateChange(it)})

    }

    override fun onBackPressed() {
        viewModel.onUIEvent(PinUIEvent.BackPressed)
        //Let the user exit the app when the input is cleared, not the best at the moment, but this is a quick thing
        viewModel.viewState.value?.let {
            if(it.pin.isBlank()) {
                super.onBackPressed()
            }
        }
    }


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
            mainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(mainActivity);
        }

        if(state.loginError) {
            Toast.makeText(applicationContext, "Login failed, pin is incorrect", Toast.LENGTH_SHORT).show()
            binding.editTextNumber.setText("")
            viewModel.onUIEvent(PinUIEvent.ErrorReset)
        }
    }
}