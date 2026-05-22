package com.example.styleai.domain.repository

import com.example.styleai.domain.model.CreditBalance
import com.example.styleai.domain.model.PurchasePlan
import kotlinx.coroutines.flow.Flow

interface BillingRepository {
    fun getCreditBalance(): Flow<CreditBalance>
    fun getPurchasePlans(): List<PurchasePlan>
    suspend fun purchasePlan(planId: String): Boolean
    suspend fun useCredit(amount: Int): Boolean
    suspend fun refundCredit(amount: Int)
}
