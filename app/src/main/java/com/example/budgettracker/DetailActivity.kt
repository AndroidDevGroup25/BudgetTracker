package com.example.budgettracker

import TRANSACTION_EXTRA
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

private const val TAG = "DetailActivity"
class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val transaction = intent.getParcelableExtra<Transaction>(TRANSACTION_EXTRA) as Transaction
        Log.i(TAG, "Transaction is $transaction")

        findViewById<TextView>(R.id.tv_description).setText(transaction.getDescription())
        if (transaction.isEssential())
            findViewById<TextView>(R.id.tv_category).setText("Essential")
        else
            findViewById<TextView>(R.id.tv_category).setText("Non-essential")
        findViewById<TextView>(R.id.tv_amount).setText("$" + transaction.getCost())
        findViewById<TextView>(R.id.tv_date).setText(transaction.getDate().toString().substring(0,11))
    }
}