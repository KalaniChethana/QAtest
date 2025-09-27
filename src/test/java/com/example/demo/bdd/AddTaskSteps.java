package com.example.demo.bdd;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TaskService;
import com.example.demo.service.UserService;
import io.cucumber.java.en.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddTaskSteps {

    private UserRepository userRepo;
    private TaskRepository taskRepo;
    private UserService userService;
    private TaskService taskService;

    private User currentUser;
    private Task resultTask;
    private Exception exception;
    private List<Task> taskList;

    @Given("I am a registered user {string} with password {string}")
    public void iAmARegisteredUser(String username, String password) {
        userRepo = mock(UserRepository.class);
        taskRepo = mock(TaskRepository.class);

        userService = new UserService(userRepo);
        taskService = new TaskService(taskRepo);

        currentUser = new User();
        currentUser.setId(1L);
        currentUser.setUsername(username);
        currentUser.setPassword(password);

        when(userRepo.findByUsername(username)).thenReturn(currentUser);

        taskList = new ArrayList<>();
        when(taskRepo.findByUser(currentUser)).thenReturn(taskList);
    }

    @When("I add a new task {string}")
    public void iAddANewTask(String title) {
        try {
            Task task = new Task();
            task.setTitle(title);
            task.setUser(currentUser);

            when(taskRepo.save(any(Task.class))).thenAnswer(invocation -> {
                Task saved = invocation.getArgument(0);
                taskList.add(saved);
                return saved;
            });

            resultTask = taskService.addTask(title, currentUser);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("I should see {string} in my task list")
    public void iShouldSeeInMyTaskList(String title) {
        assertTrue(taskList.stream().anyMatch(task -> task.getTitle().equals(title)));
    }

    @When("I try to add a new task with title {string}")
    public void iTryToAddANewTaskWithTitle(String title) {
        try {
            resultTask = taskService.addTask(title, currentUser);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the system should reject the task")
    public void theSystemShouldRejectTheTask() {
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("Task title cannot be empty"));
    }
}
