package com.example.styleai.data.repository

import com.example.styleai.data.mock.MockData
import com.example.styleai.domain.model.CreditBalance
import com.example.styleai.domain.model.PurchasePlan
import com.example.styleai.domain.repository.BillingRepository
import com.example.styleai.data.local.StyleDataStoreKeys
import com.example.styleai.data.local.dataStore
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BillingRepositoryImpl(private val context: android.content.Context? = null) : BillingRepository {

    private val repositoryScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val balance = MutableStateFlow(CreditBalance(totalCredits = 3, isProUser = false))

    init {
        context?.let { ctx ->
            repositoryScope.launch {
                ctx.dataStore.data.collect { prefs ->
                    val totalCredits = prefs[StyleDataStoreKeys.MOCK_CREDITS] ?: 3
                    val isProUser = prefs[StyleDataStoreKeys.IS_PRO_USER] ?: false
                    balance.value = CreditBalance(totalCredits = totalCredits, isProUser = isProUser)
                }
            }
        }
    }

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
                    context?.let { ctx ->
                        repositoryScope.launch {
                            ctx.dataStore.edit { p ->
                                p[StyleDataStoreKeys.IS_REPORT_UNLOCKED] = true
                            }
                        }
                    }
                    current
                }
                "plan_pro_sub" -> {
                    val nextCredits = current.totalCredits + 30
                    context?.let { ctx ->
                        repositoryScope.launch {
                            ctx.dataStore.edit { p ->
                                p[StyleDataStoreKeys.IS_PRO_USER] = true
                                p[StyleDataStoreKeys.MOCK_CREDITS] = nextCredits
                            }
                        }
                    }
                    current.copy(isProUser = true, totalCredits = nextCredits)
                }
                "pack_10" -> {
                    val nextCredits = current.totalCredits + 10
                    context?.let { ctx ->
                        repositoryScope.launch {
                            ctx.dataStore.edit { p ->
                                p[StyleDataStoreKeys.MOCK_CREDITS] = nextCredits
                            }
                        }
                    }
                    current.copy(totalCredits = nextCredits)
                }
                "pack_30" -> {
                    val nextCredits = current.totalCredits + 30
                    context?.let { ctx ->
                        repositoryScope.launch {
                            ctx.dataStore.edit { p ->
                                p[StyleDataStoreKeys.MOCK_CREDITS] = nextCredits
                            }
                        }
                    }
                    current.copy(totalCredits = nextCredits)
                }
                "pack_100" -> {
                    val nextCredits = current.totalCredits + 100
                    context?.let { ctx ->
                        repositoryScope.launch {
                            ctx.dataStore.edit { p ->
                                p[StyleDataStoreKeys.MOCK_CREDITS] = nextCredits
                            }
                        }
                    }
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
                context?.let { ctx ->
                    repositoryScope.launch {
                        ctx.dataStore.edit { p ->
                            p[StyleDataStoreKeys.MOCK_CREDITS] = nextCredits
                        }
                    }
                }
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
            context?.let { ctx ->
                repositoryScope.launch {
                    ctx.dataStore.edit { p ->
                        p[StyleDataStoreKeys.MOCK_CREDITS] = nextCredits
                    }
                }
            }
            current.copy(totalCredits = nextCredits)
        }
    }
}
