package com.example.weather.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.weather.R
import com.example.weather.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.http.GET
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date



class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    //needed
    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btn_click_me = findViewById(R.id.btninput) as Button



            GET = getSharedPreferences(packageName, MODE_PRIVATE)
            SET = GET.edit()

            viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)




//        userIn.setText(cName)

//            viewModel.refreshData(cName!!)




        btn_click_me.setOnClickListener {

            mainContainer.visibility = View.GONE
            errotext.visibility = View.GONE
            Loader.visibility = View.GONE
            var cName = GET.getString("cityName", userIn.text.toString())
            var cityname = GET.getString("cityName", cName)
            userIn.setText(cityname)
            viewModel.refreshData(cityname!!)



            getLiveData(cityname)

        }

    }
    private fun getLiveData(cityname: String){
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        viewModel.weather_data.observe(this, Observer { data->
            data?.let{
                mainContainer.visibility = View.VISIBLE
                Loader.visibility = View.GONE

                temp.text = data.main.temp.toString()+"°C"
                val updatedAtText = currentDate
                updated_at.text = "Updated at: "+ currentDate
                address.text = cityname +" , "+ data.sys.country.toString()
                val sunrisex =data.sys.sunrise
                sunrise.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrisex.toLong()*1000))
                val sunsetx = data.sys.sunset
                sunset.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunsetx.toLong()*1000))
                wind.text = data.wind.speed.toString()
                pressure.text = data.main.pressure.toString()
                humidity.text = data.main.humidity.toString()
                temp_min.text = "Temp Min " + data.main.tempMin.toString()
                temp_max.text = "Temp Max " + data.main.tempMax.toString()
            }
        })

        viewModel.weather_load.observe(this, Observer { load->
            load?.let {
                if(it){
                    Loader.visibility = View.VISIBLE
                    errotext.visibility = View.GONE
                    mainContainer.visibility = View.GONE
                }else{
                    errotext.visibility = View.GONE
                }
            }
        })
    }
}