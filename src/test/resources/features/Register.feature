Feature: User Registration

  Scenario: Successful registration
    Given I open the registration page
    When I enter valid registration details
    And I accept terms and conditions
    And I submit the form
    Then I should see a confirmation message

  Scenario: Missing last name
    Given I open the registration page
    When I enter registration details with missing last name
    And I accept terms and conditions
    And I submit the form
    Then I should see an error for last name

  Scenario: Passwords do not match
    Given I open the registration page
    When I enter registration details with mismatched passwords
    And I accept terms and conditions
    And I submit the form
    Then I should see an error for password mismatch

  Scenario: Terms and conditions not accepted
    Given I open the registration page
    When I enter valid registration details
    And I do not accept terms and conditions
    And I submit the form
    Then I should see an error about accepting terms

