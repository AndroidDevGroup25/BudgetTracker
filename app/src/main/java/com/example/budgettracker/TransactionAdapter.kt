import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.budgettracker.R
import com.example.budgettracker.Transaction

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
            //TODO The date format should be simple. Change to mm/dd/yy
            tvCost.text = transaction.getCost().toString()
            tvCreatedAt.text = transaction.getDate().toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionAdapter.ViewHolder, position: Int) {
        val transaction = transactions.get(position)
        holder.bind(transaction)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
}
/*END OF ADAPTER CLASS*/