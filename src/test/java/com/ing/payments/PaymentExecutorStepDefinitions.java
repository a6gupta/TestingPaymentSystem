package com.ing.payments;

import DeserializingResponseObj.TransactionResponseObj;
import com.ing.payments.paymentExecutor.PaymentExecutor;
import com.ing.payments.paymentExecutor.Transaction;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;

import java.util.Map;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentExecutorStepDefinitions {

    Transaction transaction;
    Response responsePaymentExecutor;

    @Given("The payment executor system is available")
    public void isPaymentExecutorAvailable() {
        PaymentExecutor paymentExecutor = new PaymentExecutor();
        assertThat(paymentExecutor.isSystemAvailable()).isTrue();
        // Specify Base URI for Payment Executor
        RestAssured.baseURI = paymentExecutor.getBaseURI();
        RestAssured.defaultParser = Parser.JSON;
    }

    @Given("The transaction amount is {float}")
    public void theTransactionAmount(Float transactionAmount) {
        transaction = new Transaction();
        transaction.setTransactionAmount(transactionAmount);
    }

    @And("The user transaction is created with following details")
    public void theUserTransactionIsCreatedWithFollowingDetails(DataTable dataTable) {
        transaction = new Transaction();
        for(Map<String, String> data: dataTable.asMaps()) {
            transaction.setTransactionAmount(Float.parseFloat(data.get("transactionAmount")));
            transaction.setDebtorAccount(data.get("debtorAccount"));
            transaction.setCreditorAccount(data.get("creditorAccount"));
            if(data.get("debtorAccountPresent").equals("yes")) {
                getDebtorAccountBalanace(transaction.getDebtorAccount());
            }
            if(data.get("creditorAccountPresent").equals("yes")) {
                getCreditorAccountBalanace(transaction.getCreditorAccount());
            }
        }
    }

    @Given("Debtor account balance is {string} for transaction")
    public void debtorAccountBalanceIsSufficientForTransaction(String balance) {
        if(balance.equalsIgnoreCase("sufficient")) {
            assertThat(transaction.getDebtorAccountBalance()).isGreaterThanOrEqualTo(transaction.getTransactionAmount());
        } else {
            assertThat(transaction.getDebtorAccountBalance()).isLessThan(transaction.getTransactionAmount());
        }
    }

    @When("Transaction is send to payment system for {string}")
    public void processTransaction(String expectedOutcome) {
        //Create Payload to send for processing transaction
        JSONObject requestParams = getTransactionPayload();
        responsePaymentExecutor = RestAssured.given().contentType(JSON).body(requestParams).when().post("/api/payment_executor/" + expectedOutcome);
    }

    @Then("Transaction is executed successfully by payment system")
    public void transactionShouldBeExecutedSuccessfully() {
        // Verify the transaction response received from Payment executed
        TransactionResponseObj responseObj = responsePaymentExecutor.as(TransactionResponseObj.class);
        assertThat(responsePaymentExecutor.statusCode()).isEqualTo(200);
        verifyResponse(responseObj, transaction);
    }

    @Then ("Transaction is {string} to the the database for processing")
    public void transactionInsertedToDatabaseForProcessing(String dbExpectedStatus) {
        Response response = RestAssured.given().when().param("account", transaction.getDebtorAccount()).get("/api/db/" + dbExpectedStatus);
        if(dbExpectedStatus.equalsIgnoreCase("inserted")) {
            assertThat(response.statusCode()).isEqualTo(200);
        } else {
            assertThat(response.statusCode()).isEqualTo(400);
        }
    }

    @Then("Transaction executed by payment system fails with {string}")
    public void transactionExecutedFailureWithErrorMessage(String errorMessage) {
        // Verify the error response
        assertThat(responsePaymentExecutor.statusCode()).isEqualTo(400);
        JsonPath jsonPath = new JsonPath(responsePaymentExecutor.getBody().asString());
        assertThat(jsonPath.getString("message")).isEqualToIgnoringCase(errorMessage);
    }

    private void verifyResponse(TransactionResponseObj responseObj, Transaction transaction) {
        // Verify Debtor Account
        assertThat(responseObj.getDebtor().getDebtor_account()).isEqualTo(transaction.getDebtorAccount());
        assertThat(responseObj.getDebtor().getAmount_debited()).isEqualTo(transaction.getTransactionAmount());
        assertThat(responseObj.getDebtor().getDebtor_previous_balance()).isEqualTo(transaction.getDebtorAccountBalance());
        assertThat(responseObj.getDebtor().getDebtor_updated_balance()).isEqualTo(transaction.getDebtorAccountBalance() - transaction.getTransactionAmount());

        //Verify Creditor Account
        assertThat(responseObj.getCreditor().getCreditor_account()).isEqualTo(transaction.getCreditorAccount());
        assertThat(responseObj.getCreditor().getCreditor_previous_balance()).isEqualTo(transaction.getCreditorAccountBalance());
        assertThat(responseObj.getCreditor().getCreditor_updated_balance()).isEqualTo(transaction.getCreditorAccountBalance() + transaction.getTransactionAmount());
    }

    private JSONObject getTransactionPayload() {
        //Create Payload to send for processing transaction
        JSONObject requestParams = new JSONObject();
        requestParams.put("transactionAmount", transaction.getTransactionAmount());
        requestParams.put("debtor_account", transaction.getDebtorAccount());
        requestParams.put("creditorAccount", transaction.getCreditorAccount());
        return requestParams;
    }

    private void getDebtorAccountBalanace(String debtorAccount) {
        // GET call to payment executor to verify the debtor account exists and get account balance
        Response response = RestAssured.given().when().param("account", debtorAccount).get("/api/getAccountBalance");
        assertThat(response.statusCode()).isEqualTo(200);
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        assertThat(debtorAccount).isEqualTo(jsonPath.getString("debtor_account"));
        transaction.setDebtorAccountBalance(jsonPath.getFloat("account_balance"));
    }

    private void getCreditorAccountBalanace(String creditorAccount) {
        // GET call to payment executor to verify the debtor account exists and get account balance
        Response response = RestAssured.given().when().param("account", creditorAccount).get("/api/getAccountBalance");
        assertThat(response.statusCode()).isEqualTo(200);
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        assertThat(creditorAccount).isEqualTo(jsonPath.getString("creditor_account"));
        transaction.setCreditorAccountBalance(jsonPath.getFloat("account_balance"));
    }
}
