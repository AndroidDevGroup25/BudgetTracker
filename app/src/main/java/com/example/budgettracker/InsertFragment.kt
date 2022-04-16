package com.example.budgettracker

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import java.lang.Double.parseDouble
import java.util.Calendar


/**
 * A simple [Fragment] subclass.
 * Use the [InsertFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InsertFragment() : Fragment() {
    private val datePickerDialog_dateSetListener = DatePickerDialog.OnDateSetListener { datePicker: DatePicker, i: Int, i1: Int, i2: Int ->
        //show date as text in editText_date
        editText_date.setText("$i-$i1-$i2")
    }
    private val imageButton_calendar_clickListener = View.OnClickListener() {
        //open Date picker
        datePickerDialog.show()
    }

    private val button_submit_clickListener = View.OnClickListener() {
        //gather details about the transaction
        val amount = parseDouble(editText_amount.text.toString())
        val description = editText_description.text.toString()
        val date = editText_date.text.toString()

        //create Transaction object in parse


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

        button_submit.setOnClickListener(button_submit_clickListener)
        imageButton_calendar.setOnClickListener(imageButton_calendar_clickListener)

        calendar = Calendar.getInstance()
        datePickerDialog = DatePickerDialog(requireContext(), datePickerDialog_dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    }
}