@weight_manual
Feature: Test Input weight on the Website

  Scenario: Test with id and weight data
    Given the user is on the login page
    When the user enters the driver information
    Then the user should be able to login
    When the user click onboard
    When the user click orders
    Then the user should be view Awaiting shipment
    When I enter the following data:
      | id          | weight |
      | 3436242733  | 5       |
      |             |         |
    Then the data should be submitted successfully
    When the user click batch
    When the user click "105110" batch





