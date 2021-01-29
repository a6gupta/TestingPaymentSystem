package com.ing.payments.paymentExecutor;

public class PaymentExecutor {

    String baseURI = "http://localhost:8080";

    public String getBaseURI() {
        return baseURI;
    }
    public boolean isSystemAvailable() {
        // In the real test need to verify with the base URL the system is up and running
        // In the below case mocking to return true assuming system is available
        return true;
    }
}
