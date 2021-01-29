Feature: Payment executor processing bank transactions


  Scenario Outline: As a customer, when transaction amount equal to or greater than 1000000.0  payment validation fails
    Given The payment executor system is available
    And The user transaction is created with following details
    | transactionAmount   | debtorAccount | debtorAccountPresent |  creditorAccount | creditorAccountPresent |
    |<transactionAmount>  |<debtorAccount>|        <present>     | <creditorAccount>|          <present>     |
    And   Debtor account balance is <balance> for transaction
    When  Transaction is send to payment system for <processingWithExpectedOutcome>
    Then  Transaction is "not_inserted" to the the database for processing
    And   Transaction executed by payment system fails with <errorMessage>
    Examples:
    | transactionAmount | debtorAccount | present | balance          | creditorAccount | processingWithExpectedOutcome           | errorMessage                            |
    | 1000000           | ING98NL2345   |  yes    | "not sufficient" |   ING21NL7698   | "transaction_amount_validation_failure" | "Transaction amount validation failure" |
    | 1240000           | ING98NL2345   |  yes    | "not sufficient" |   ING21NL7698   | "transaction_amount_validation_failure" | "Transaction amount validation failure" |

  Scenario: As a customer, I should be able to perform a transaction successfully if the transaction amount is within the limit and account balance is sufficient
    Given The payment executor system is available
    And The user transaction is created with following details
    | transactionAmount   | debtorAccount | debtorAccountPresent |  creditorAccount | creditorAccountPresent |
    |         200         | ING98NL2345   |        yes           |  ING21NL7698     |          yes           |
    And  Debtor account balance is "sufficient" for transaction
    When Transaction is send to payment system for "happy_case"
    Then Transaction is "inserted" to the the database for processing
    And Transaction is executed successfully by payment system

  Scenario: As a customer, I should be able to perform a transaction failure as the transaction amount is within the limit but account balance is not-sufficient
    Given The payment executor system is available
    And The user transaction is created with following details
      | transactionAmount   | debtorAccount | debtorAccountPresent |  creditorAccount | creditorAccountPresent |
      |         2200         | ING98NL2345   |        yes           |  ING21NL7698     |          yes          |
    And  Debtor account balance is "not_sufficient" for transaction
    When Transaction is send to payment system for "non_sufficient_balance"
    Then Transaction executed by payment system fails with "Transaction failed due to insufficient balance"


## Other Scenarios :
#
#  1. Transaction Amount :  Junk(Invalid value) , Debtor Account Available : NA , Creditor Account Available : NA , Balance Sufficient : NA
#  2. Transaction Amount : Integer amount < 1000000 , Debtor Account Available : Yes , Creditor Account Available : Yes , Balance Sufficient : Yes
#  3. Transaction Amount : Amount < 1000000 , Debtor Account Available : Yes , Creditor Account Available : Yes , Balance Sufficient : No
#  4. Transaction Amount : Amount < 1000000 , Debtor Account Available : No , Creditor Account Available : Yes , Balance Sufficient : NA
#  5. Transaction Amount : Amount < 1000000 , Debtor Account Available : No , Creditor Account Available : No , Balance Sufficient : NA
#  6. Transaction Amount : Amount < 1000000 , Debtor Account Available : Yes , Creditor Account Available : No , Balance Sufficient : Yes
#  7. Transaction Amount : Amount < 1000000 , Debtor Account Available : Yes , Creditor Account Available : No , Balance Sufficient : No
#  8. Transaction Amount : Amount < 1000000 , Debtor Account Available : Yes , Creditor Account Available : Yes , Balance Sufficient : Yes (Booking System is unavailable)
#  9. Transaction Amount : Amount < 1000000 , Debtor Account Available : Yes , Creditor Account Available : Yes , Balance Sufficient : Yes (Booking System is unavailable after Debtor check)
#  10. Transaction Amount : Amount < 1000000 , Debtor Account Available : Yes , Creditor Account Available : Yes , Balance Sufficient : Yes (Booking System is unavailable after Creditor check)
#  11. Transaction Amount : Amount < 1000000 , Debtor Account Available : Yes , Creditor Account Available : Yes , Balance Sufficient : Yes (Database inaccessible)
#  12. And Other similar disaster recovery scenarios such as payment is successfully processed but the Booking system shuts down
#  13. Suppose threshold limit of Payment executor is 100000 transaction per second test the Payment executor is able to handle the threshold limit successfully
#  14. Test the HA for Payment executor by executing transaction above the threshold limit and the payment executor is able to handle the successfully as another instance of the Payment executor should be launched and system should be available

