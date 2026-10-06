package com.example.billing

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * Stub implementation of Wompi that simply logs the calls.
 * Replace with real HTTP calls to the Wompi sandbox when ready.
 */
@Component
class WompiPaymentProvider : PaymentProvider {
    private val log = LoggerFactory.getLogger(WompiPaymentProvider::class.java)

    override fun createCustomer(email: String): String {
        log.info("[Wompi] createCustomer called for email={}", email)
        return "wompi-cust-${"%06d".format((0..999999).random())}"
    }

    override fun createCheckout(customerId: String, amountCents: Long, currency: String, returnUrl: String): String {
        log.info("[Wompi] createCheckout for customerId={}, amount={}, currency={}, returnUrl={}",
            customerId, amountCents, currency, returnUrl)
        // Return a dummy checkout URL – the front‑end will redirect here.
        return "https://sandbox.wompi.co/checkout/${"%08d".format((0..99999999).random())}"
    }

    override fun createSubscription(customerId: String, planId: String): String {
        log.info("[Wompi] createSubscription for customerId={}, planId={}", customerId, planId)
        return "wompi-sub-${"%06d".format((0..999999).random())}"
    }

    override fun cancelSubscription(subscriptionId: String): Boolean {
        log.info("[Wompi] cancelSubscription id={}", subscriptionId)
        return true
    }

    override fun changePlan(subscriptionId: String, newPlanId: String): Boolean {
        log.info("[Wompi] changePlan subscriptionId={}, newPlanId={}", subscriptionId, newPlanId)
        return true
    }

    override fun getSubscription(subscriptionId: String): ProviderSubscription? {
        log.info("[Wompi] getSubscription id={}", subscriptionId)
        return ProviderSubscription(id = subscriptionId, status = "ACTIVE", currentPeriodEnd = null)
    }

    override fun verifyWebhook(signature: String, payload: String): Boolean {
        // In a real implementation you would verify the HMAC signature.
        log.info("[Wompi] verifyWebhook signature={}, payload length={}", signature, payload.length)
        return true
    }
}
