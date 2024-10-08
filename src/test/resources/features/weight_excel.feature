@weight
Feature: Test Input weight on the Website

  Scenario: Test with id and weight data
    Given the user is on the login page
    When the user enters the driver information
    Then the user should be able to login
    When the user click orders
    Then the user should be view Awaiting shipment
    When program reads weight excel





