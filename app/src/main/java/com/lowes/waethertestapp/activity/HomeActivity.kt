package com.lowes.waethertestapp.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.lowes.waethertestapp.R
import com.lowes.waethertestapp.callback.IView
import com.lowes.waethertestapp.databinding.ActivityMainBinding
import com.lowes.waethertestapp.util.CommonUtils.CITY_NAME

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.buttonLookup.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.buttonLookup.id -> lookUpWeather()
        }
    }

    private fun lookUpWeather() {
        val inputText = binding.editTextCityName.text.toString()
        if(inputText.isNotEmpty()) {
            val intent = Intent(this, WeatherForecastActivity::class.java)
            intent.putExtra(CITY_NAME, inputText)
            this.startActivity(intent)
        }else {
            showErrorDialog("Please enter valid City Name")
        }
    }

    private fun showErrorDialog(message: String?) {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Error")
        alertDialog.setMessage(message)
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "OK"
        ) { dialog, _ -> dialog.dismiss() }
        alertDialog.show()
    }
}