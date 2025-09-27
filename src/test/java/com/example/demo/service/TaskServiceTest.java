package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    private TaskRepository taskRepo;
    private TaskService taskService;
    private User user;

    @BeforeEach
    void setUp() {
        taskRepo = Mockito.mock(TaskRepository.class);
        taskService = new TaskService(taskRepo);

        user = new User();
        user.setId(1L);
        user.setUsername("john");
    }

    // RED: expect exception for empty title
    @Test
    void addTask_ShouldThrowException_WhenTitleIsEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskService.addTask("", user);
        });
        assertTrue(exception.getMessage().contains("Task title cannot be empty"));
    }


}
