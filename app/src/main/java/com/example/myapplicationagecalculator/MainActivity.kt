package com.example.myapplicationagecalculator

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplicationagecalculator.databinding.ActivityMainBinding
import org.joda.time.Period
import org.joda.time.PeriodType
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var binding: ActivityMainBinding
    private var myBirthday = ""
    private var myToday = ""
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val calender = Calendar.getInstance()
        var year = calender[Calendar.YEAR]
        var month = calender[Calendar.MONTH]
        var day = calender[Calendar.DAY_OF_MONTH]
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        myToday = simpleDateFormat.format(Calendar.getInstance().time)
        binding.todayTxt.text = "today $myToday"
        binding.ageBtn.setOnClickListener{
            val datePickerDialog = DatePickerDialog(it.context, dateSetListener, year, month, day)
            datePickerDialog.datePicker.maxDate = Date().time
            datePickerDialog.show()
        }
        dateSetListener = DatePickerDialog.OnDateSetListener { _, myYear, myMonth, myDay ->
            year = myYear
            month = myMonth+1
            day = myDay
            myBirthday = "$day/$month/$year"
            binding.birthTxt.text = "My birthday $myBirthday"
        }
        binding.calculateBtn.setOnClickListener {
            val simpleDateFormat1 = SimpleDateFormat("dd/MM/yyyy")
            try {
                val date1 = simpleDateFormat1.parse(myBirthday)
                val date2 = simpleDateFormat1.parse(myToday)
                val startDate = date1?.time
                val endDate = date2?.time
                val period = Period(startDate!!, endDate!!, PeriodType.yearMonthDay())
                val years = period.years
                val months = period.months
                val days = period.days
                binding.ageTxt.text = "$years years $months months $days days"
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
    }
}