package com.example.billing

/**
 * Abstract contract for a payment provider.
 * Implementations (e.g., Wompi, Mercado Pago) translate the platform's
 * domain objects into provider‑specific API calls.
 */
interface PaymentProvider {
    fun createCustomer(email: String): String   // returns provider customer ID
    fun createCheckout(customerId: String, amountCents: Long, currency: String, returnUrl: String): String // checkout session URL
    fun createSubscription(customerId: String, planId: String): String // provider subscription ID
    fun cancelSubscription(subscriptionId: String): Boolean
    fun changePlan(subscriptionId: String, newPlanId: String): Boolean
    fun getSubscription(subscriptionId: String): ProviderSubscription? // status inquiry
    fun verifyWebhook(signature: String, payload: String): Boolean
}

data class ProviderSubscription(
    val id: String,
    val status: String, // e.g. ACTIVE, PAST_DUE, CANCELED
    val currentPeriodEnd: String?
)
