import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgettracker.DetailActivity
import com.example.budgettracker.R
import com.example.budgettracker.Transaction


const val TRANSACTION_EXTRA = "TRANSACTION_EXTRA"

private const val TAG= "TransactionAdapter"

class TransactionAdapter(val context: Context, val transactions: MutableList<Transaction>): RecyclerView.Adapter<TransactionAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private val tvTransactionName = itemView.findViewById<TextView>(R.id.tvTransactionName)
        private val tvCreatedAt = itemView.findViewById<TextView>(R.id.tvCreatedAt)
        private val tvCost = itemView.findViewById<TextView>((R.id.tvCost))

        init {
            itemView.setOnClickListener(this)
        }
        fun bind(transaction: Transaction){
            //todo should we include usernames in the adapter?
            tvTransactionName.text = transaction.getDescription()
            //TODO The date format should be simple. Change to mm/dd/yy
            tvCost.text = transaction.getCost().toString()
            tvCreatedAt.text = transaction.getDate().toString().substring(0,11)
        }

        override fun onClick(p0: View?) {
            //1. Get notified of the particular transaction that was clicked on
            val transaction = transactions[adapterPosition]
            //Toast.makeText(context, transaction.getDescription(), Toast.LENGTH_SHORT).show()
            //2. Create an intent to navigate to the DetailActivity (detail view for transaction)
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(TRANSACTION_EXTRA, transaction)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionAdapter.ViewHolder {
        Log.i(TAG, "onCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionAdapter.ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder position $position")
        val transaction = transactions[position]
        holder.bind(transaction)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
}