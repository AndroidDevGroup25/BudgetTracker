package com.example.budgettracker

import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import java.io.File
import java.util.Date

@ParseClassName("Transaction")
class Transaction: ParseObject() {
    fun getDescription(): String? {
        return getString(KEY_DESCRIPTION)
    }
    fun setDescription(description: String) {
        put(KEY_DESCRIPTION, description)
    }
    fun getCost(): Int {
        return getInt(KEY_COST)
    }
    fun setCost(cost : Double) {
        put(KEY_COST, cost)
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
    fun isIncome(): Boolean {
        return getBoolean(KEY_IS_INCOME)
    }
    fun setIsIncome(isIncome: Boolean) {
        put(KEY_IS_INCOME, isIncome)
    }
    fun getItemCount(): Int {
        return getInt(KEY_ITEM_COUNT)
    }
    fun setItemCount(itemCount: Int) {
        put(KEY_ITEM_COUNT, itemCount)
    }
    fun isEssential(): Boolean {
        return getBoolean(KEY_IS_ESSENTIAL)
    }
    fun setIsEssential(isIncome: Boolean) {
        put(KEY_IS_ESSENTIAL, isIncome)
    }
    fun getCategory(): Int {
        return getInt(KEY_CATEGORY)
    }
    fun setCategory(category: Int) {
        put(KEY_CATEGORY, category)
    }

    fun setName(name: String) {
        put(KEY_NAME, name)
    }
    fun getName(): String? {
        return getString(KEY_NAME)
    }
    fun getReceipt(): ParseFile? {
        return getParseFile(KEY_RECEIPT)
    }
    fun setReceipt(receipt: ParseFile) {
        put(KEY_RECEIPT, receipt)
    }
    companion object {
        const val KEY_DESCRIPTION = "description"
        const val KEY_USER = "user"
        const val KEY_DATE = "date"
        const val KEY_COST = "cost"
        const val KEY_IS_INCOME = "isIncome"
        const val KEY_ITEM_COUNT = "itemCount"
        const val KEY_IS_ESSENTIAL ="isEssential"
        const val KEY_CATEGORY = "category_id"
        const val KEY_NAME = "name"
        const val KEY_RECEIPT = "receipt"
    }
}