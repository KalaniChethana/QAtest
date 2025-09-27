Feature: Task Management
  As a user
  I want to add a new task
  So that I can track my work

  Scenario: Add a task successfully
    Given I am a registered user "john" with password "1234"
    When I add a new task "Finish Report"
    Then I should see "Finish Report" in my task list

  Scenario: Prevent adding empty task
    Given I am a registered user "john" with password "1234"
    When I try to add a new task with title ""
    Then the system should reject the task
