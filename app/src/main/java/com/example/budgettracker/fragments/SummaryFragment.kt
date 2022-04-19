package com.example.budgettracker.fragments

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

    /*BEGINNING OF ADAPTER CLASS*/
    class TransactionAdapter(val context: Context, val transactions: MutableList<Transaction>): RecyclerView.Adapter<TransactionAdapter.ViewHolder>(){
            class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
                val tvTransactionName: TextView
                val tvCreatedAt: TextView
                val tvCost : TextView

                init {
                    tvTransactionName = itemView.findViewById(R.id.tvTransactionName)
                    tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt)
                    tvCost = itemView.findViewById((R.id.tvCost))
                }
                fun bind(transaction: Transaction){

                    //todo should we include usernames in the adapter?
                    tvTransactionName.text = transaction.getDescription()
                    //TODO
                    //tvCost.text = transaction.getCost().toString()
                    tvCreatedAt.text = transaction.getDate().toString()
                }
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionAdapter.ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent,false)
            return ViewHolder(view)
        }

        //TODO COMPLETE THIS METHOD
        override fun onBindViewHolder(holder: TransactionAdapter.ViewHolder, position: Int) {
            val post = transactions.get(position)
           // holder.bind(transaction)
        }

        override fun getItemCount(): Int {
            return transactions.size
        }
    }
    /*END OF ADAPTER CLASS*/


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

    //MAKING QUERIES FOR POSTS IN THE SERVER todo
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
