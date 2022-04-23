package com.example.budgettracker.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.budgettracker.R
import com.example.budgettracker.Transaction
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.parse.ParseUser


class ProfileFragment : Fragment() {

    lateinit var avatarField: ImageView

    lateinit var usernameField: TextView
    lateinit var totalInc: TextView
    lateinit var totalExp: TextView
    lateinit var balanceField: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)

    }


    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {

        val currentUser = ParseUser.getCurrentUser()

        avatarField = view.findViewById(R.id.ivAvatar)

        usernameField = view.findViewById(R.id.profile_username)
        usernameField.setText(currentUser.username)

        balanceField = view.findViewById(R.id.profile_balance)
        balanceField.setText("$500.00")

        val chart = view.findViewById(R.id.chart) as PieChart

        val entries: MutableList<PieEntry> = ArrayList()
        entries.add(PieEntry(18.5f, "Green"))
        entries.add(PieEntry(26.7f, "Yellow"))
        entries.add(PieEntry(24.0f, "Red"))
        entries.add(PieEntry(30.8f, "Blue"))
        val set = PieDataSet(entries, "Election Results")
        val data = PieData(set)
        chart.data = data
        chart.invalidate()

    }

    companion object {
        const val TAG = "ProfileFragment"
    }
}