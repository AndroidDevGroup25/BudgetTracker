package depauw.datle.instagramy

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import java.util.*

@ParseClassName("Post")
class Transaction: ParseObject() {
    fun getDescription(): String? {
        return getString(KEY_DESCRIPTION)
    }
    fun setDescription(description: String) {
        put(KEY_DESCRIPTION, description)
    }
    fun getTransactionCost(): Int {
        return getInt(KEY_TRANSACTION_COST)
    }
    fun setTransactionCost(cost : Int) {
        put(KEY_TRANSACTION_COST, cost)
    }
    fun getUser(): ParseUser? {
        return getParseUser(KEY_USER)
    }
    fun setUser(user: ParseUser) {
        put(KEY_USER, user)
    }
    fun getDate(): Date? {
        return getDate(KEY_DATE)
    }
    fun setDate(date : Date) {
        put(KEY_DATE, date)
    }

    companion object {
        val KEY_DESCRIPTION = "description"
        val KEY_USER = "user"
        val KEY_DATE = "createdAt"
        val KEY_TRANSACTION_COST = "transactionCost"
    }
}