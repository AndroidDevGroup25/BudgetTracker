package com.example.budgettracker

import TRANSACTION_EXTRA
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

private const val TAG = "DetailActivity"
class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val transaction = intent.getParcelableExtra<Transaction>(TRANSACTION_EXTRA) as Transaction
        Log.i(TAG, "Transaction is $transaction")
    }
}