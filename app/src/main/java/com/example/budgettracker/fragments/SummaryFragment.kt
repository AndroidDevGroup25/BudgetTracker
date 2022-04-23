package com.example.budgettracker.fragments

import TransactionAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgettracker.R
import com.parse.FindCallback
import com.parse.ParseQuery
import com.example.budgettracker.Transaction

open class SummaryFragment : Fragment() {

    lateinit var transactionsRecyclerView: RecyclerView

    lateinit var adapter: TransactionAdapter

    var allTransactions: MutableList<Transaction> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transactionsRecyclerView = view.findViewById(R.id.rvTransactions)

        adapter = TransactionAdapter(requireContext(), allTransactions)
        transactionsRecyclerView.adapter = adapter
        transactionsRecyclerView.layoutManager =  LinearLayoutManager(requireContext())

        queryTransactions()
    }

    //MAKING QUERIES FOR TRANSACTIONS IN THE SERVER
    open fun queryTransactions() {
        val query: ParseQuery<Transaction> = ParseQuery.getQuery(Transaction::class.java)
        query.include(Transaction.KEY_USER)
        query.addDescendingOrder("createdAt")

        query.findInBackground(object: FindCallback<Transaction> {
            override fun done(transactions: MutableList<Transaction>?, e: com.parse.ParseException?) {
                if(e != null){
                    Log.e(TAG, "Error fetching transactions!")
                }else{
                    if(transactions != null){
                        for(transaction in transactions){
                            Log.i(
                                TAG, "Transaction " + transaction.getDescription() +", from: " +
                                        transaction.getUser()?.username)
                        }

                        allTransactions.addAll(transactions)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
    companion object{
        const val TAG = "SummaryFragment"
    }
}
