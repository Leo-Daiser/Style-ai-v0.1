package com.example.styleai.data.repository

import com.example.styleai.data.mock.MockData
import com.example.styleai.domain.model.CreditBalance
import com.example.styleai.domain.model.PurchasePlan
import com.example.styleai.domain.repository.BillingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class BillingRepositoryImpl(private val context: android.content.Context? = null) : BillingRepository {

    private val prefs by lazy {
        context?.getSharedPreferences("styleai_prefs", android.content.Context.MODE_PRIVATE)
    }

    private val balance = MutableStateFlow(
        CreditBalance(
            totalCredits = prefs?.getInt("mock_credits", 3) ?: 3,
            isProUser = prefs?.getBoolean("is_pro_user", false) ?: false
        )
    )

    override fun getCreditBalance(): Flow<CreditBalance> = balance

    override fun getPurchasePlans(): List<PurchasePlan> {
        return MockData.samplePurchasePlans + MockData.sampleCreditPacks
    }

    override suspend fun purchasePlan(planId: String): Boolean {
        // Mocking successful receipt validation
        val plan = getPurchasePlans().find { it.id == planId } ?: return false
        
        balance.update { current ->
            val next = when (planId) {
                "plan_free" -> current
                "plan_full_report" -> {
                    prefs?.edit()?.putBoolean("is_report_unlocked", true)?.apply()
                    current
                }
                "plan_pro_sub" -> {
                    val nextCredits = current.totalCredits + 30
                    prefs?.edit()?.putBoolean("is_pro_user", true)?.putInt("mock_credits", nextCredits)?.apply()
                    current.copy(isProUser = true, totalCredits = nextCredits)
                }
                "pack_10" -> {
                    val nextCredits = current.totalCredits + 10
                    prefs?.edit()?.putInt("mock_credits", nextCredits)?.apply()
                    current.copy(totalCredits = nextCredits)
                }
                "pack_30" -> {
                    val nextCredits = current.totalCredits + 30
                    prefs?.edit()?.putInt("mock_credits", nextCredits)?.apply()
                    current.copy(totalCredits = nextCredits)
                }
                "pack_100" -> {
                    val nextCredits = current.totalCredits + 100
                    prefs?.edit()?.putInt("mock_credits", nextCredits)?.apply()
                    current.copy(totalCredits = nextCredits)
                }
                else -> current
            }
            next
        }
        return true
    }

    override suspend fun useCredit(amount: Int): Boolean {
        var success = false
        balance.update { current ->
            if (current.totalCredits >= amount) {
                success = true
                val nextCredits = current.totalCredits - amount
                prefs?.edit()?.putInt("mock_credits", nextCredits)?.apply()
                current.copy(totalCredits = nextCredits)
            } else {
                success = false
                current
            }
        }
        return success
    }

    override suspend fun refundCredit(amount: Int) {
        balance.update { current ->
            val nextCredits = current.totalCredits + amount
            prefs?.edit()?.putInt("mock_credits", nextCredits)?.apply()
            current.copy(totalCredits = nextCredits)
        }
    }
}
