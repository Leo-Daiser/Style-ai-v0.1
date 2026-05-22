package com.example.styleai.data.repository

import com.example.styleai.data.mock.MockData
import com.example.styleai.domain.model.CreditBalance
import com.example.styleai.domain.model.PurchasePlan
import com.example.styleai.domain.repository.BillingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class BillingRepositoryImpl : BillingRepository {

    private val balance = MutableStateFlow(CreditBalance(totalCredits = 3, isProUser = false))

    override fun getCreditBalance(): Flow<CreditBalance> = balance

    override fun getPurchasePlans(): List<PurchasePlan> {
        return MockData.samplePurchasePlans + MockData.sampleCreditPacks
    }

    override suspend fun purchasePlan(planId: String): Boolean {
        // Mocking successful receipt validation
        val plan = getPurchasePlans().find { it.id == planId } ?: return false
        
        balance.update { current ->
            when (planId) {
                "plan_free" -> current
                "plan_full_report" -> current.copy(isProUser = true)
                "plan_pro_sub" -> current.copy(isProUser = true, totalCredits = current.totalCredits + 50)
                "pack_10" -> current.copy(totalCredits = current.totalCredits + 10)
                "pack_30" -> current.copy(totalCredits = current.totalCredits + 30)
                "pack_100" -> current.copy(totalCredits = current.totalCredits + 100)
                else -> current
            }
        }
        return true
    }

    override suspend fun useCredit(amount: Int): Boolean {
        var success = false
        balance.update { current ->
            if (current.isProUser || current.totalCredits >= amount) {
                success = true
                if (current.isProUser) current else current.copy(totalCredits = current.totalCredits - amount)
            } else {
                success = false
                current
            }
        }
        return success
    }

    override suspend fun refundCredit(amount: Int) {
        balance.update { current ->
            if (current.isProUser) current else current.copy(totalCredits = current.totalCredits + amount)
        }
    }
}
