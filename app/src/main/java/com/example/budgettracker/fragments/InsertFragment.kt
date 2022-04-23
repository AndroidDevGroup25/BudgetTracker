package com.example.budgettracker.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.budgettracker.MainActivity
import com.example.budgettracker.R
import com.example.budgettracker.Transaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.ParseUser
import java.lang.Double.parseDouble
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [InsertFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InsertFragment(bottom_navigation: BottomNavigationView) : Fragment() {
    private val datePickerDialog_dateSetListener = DatePickerDialog.OnDateSetListener { datePicker: DatePicker, i: Int, i1: Int, i2: Int ->
        setEditTextDate(i, i1, i2)
    }

    private fun setEditTextDate(i: Int, i1: Int, i2: Int) {
        //show date as text in editText_date
        val month = if(i1 + 1 < 10) {
            "0" + (i1 + 1)
        }
        else {
            (i1 + 1).toString()
        }
        val day = if(i2 < 10) {
            "0$i2"
        } else {
            (i2).toString()
        }
        editText_date.setText("${month}-${day}-$i")
    }

    private val imageButton_calendar_clickListener = View.OnClickListener {
        //open Date picker
        datePickerDialog.show()
    }

    private val button_submit_clickListener = View.OnClickListener {
        val success = postTransaction()
        if(!success) return@OnClickListener

        //navigate to Summary fragment
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.flContainer, SummaryFragment()).commit()
        bottom_navigation.selectedItemId = R.id.action_summary
    }

    private fun postTransaction(): Boolean {
        //gather details about the transaction
        val name = editText_name.text.toString()
        val amount = parseAmount(editText_amount.text.toString())
        if(amount == 0.0) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Empty amount").setMessage("You have not input the amount of this transaction")
            builder.setPositiveButton("OK", null)
            builder.create().show()
            return false
        }
        val description = editText_description.text.toString()

        val date = parseDate(editText_date.text.toString())
        var isEssential = true
        if(radioGroup_essential_type.checkedRadioButtonId != IS_ESSENTIAL) {
            isEssential = false
        }
        var isIncome = true
        if(radioGroup_income_type.checkedRadioButtonId != IS_INCOME) {
            isIncome = false
        }

        //create Transaction object and save to Parse
        var transaction =  Transaction()

        transaction.setDate(date)
        transaction.setDescription(description)
        transaction.setCost(amount)
        transaction.setUser(ParseUser.getCurrentUser())
        transaction.setIsEssential(isEssential)
        transaction.setIsIncome(isIncome)
        transaction.setCategory(0)
        transaction.setName(name)

        transaction.saveInBackground{
            if(it == null) {
                Log.i(TAG, "Transaction posted")
            }
            else {
                it.printStackTrace()
            }
        }
        return true
    }

    private fun parseAmount(toString: String): Double {
        return if(toString == null || toString.isEmpty()) {
            0.0
        } else parseDouble(toString)
    }

    private fun parseDate(toString: String): Date {
        if(toString.isEmpty()) return Date()
        val f = SimpleDateFormat("MM-dd-yyyy")

        return f.parse(toString)
    }

    //Button & ImageButtons
    private lateinit var button_submit: Button
    private lateinit var imageButton_add: ImageButton
    private lateinit var imageButton_calendar: ImageButton

    //EditTexts
    private lateinit var editText_name: EditText
    private lateinit var editText_amount: EditText
    private lateinit var editText_description: EditText
    private lateinit var editText_date: EditText

    private lateinit var radioGroup_essential_type: RadioGroup
    private lateinit var radioGroup_income_type: RadioGroup

    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var calendar: Calendar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_submit = view.findViewById(R.id.button_submit)
        imageButton_add = view.findViewById(R.id.imageButton_add)
        imageButton_calendar = view.findViewById(R.id.imageButton_calendar)

        editText_name = view.findViewById(R.id.editText_name)
        editText_amount = view.findViewById(R.id.editText_amount)
        editText_description = view.findViewById(R.id.editText_description)
        editText_date = view.findViewById(R.id.editText_date)

        radioGroup_essential_type = view.findViewById(R.id.radioGroup_essential_type)
        radioGroup_income_type = view.findViewById(R.id.radioGroup_income_type)

        button_submit.setOnClickListener(button_submit_clickListener)
        imageButton_calendar.setOnClickListener(imageButton_calendar_clickListener)

        calendar = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(requireContext(), datePickerDialog_dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    }

    companion object {
        val IS_ESSENTIAL = R.id.radioButton_essential
        val IS_INCOME = R.id.radioButton_income
        val TAG = "InsertFragment"
    }
}