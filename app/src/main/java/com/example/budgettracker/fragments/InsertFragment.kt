package com.example.budgettracker.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.budgettracker.R
import com.parse.ParseUser
import com.example.budgettracker.Transaction
import java.lang.Double.parseDouble
import java.util.Calendar
import java.util.Date


/**
 * A simple [Fragment] subclass.
 * Use the [InsertFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InsertFragment() : Fragment() {
    private val datePickerDialog_dateSetListener = DatePickerDialog.OnDateSetListener { datePicker: DatePicker, i: Int, i1: Int, i2: Int ->
        //show date as text in editText_date
        editText_date.setText("$i1-$i2-$i")
    }
    private val imageButton_calendar_clickListener = View.OnClickListener {
        //open Date picker
        datePickerDialog.show()
    }

    private val button_submit_clickListener = View.OnClickListener {
        //gather details about the transaction
        val amount = parseDouble(editText_amount.text.toString())
        val description = editText_description.text.toString()
        val date = editText_date.text.toString()
        var isEssential = true
        if(radioGroup_essential_type.checkedRadioButtonId != IS_ESSENTIAL) {
            isEssential = false
        }
        var isIncome = true
        if(radioGroup_income_type.checkedRadioButtonId != IS_INCOME) {
            isIncome = false
        }

        //create Transaction object and save to Parse
        var transaction = Transaction()
        transaction.setDate(Date(date))
        transaction.setDescription(description)
        transaction.setCost(amount)
        transaction.setUser(ParseUser.getCurrentUser())
        transaction.setIsEssential(isEssential)
        transaction.setIsIncome(isIncome)
        transaction.setCategory(0)
        transaction.setName("Grocery")

        transaction.saveInBackground{
            if(it == null) {
                Log.i(TAG, "Transaction posted")
            }
            else {
                it.printStackTrace()
            }
        }
        //navigate to Summary fragment
    }

    //Button & ImageButtons
    private lateinit var button_submit: Button
    private lateinit var imageButton_add: ImageButton
    private lateinit var imageButton_calendar: ImageButton

    //EditTexts
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