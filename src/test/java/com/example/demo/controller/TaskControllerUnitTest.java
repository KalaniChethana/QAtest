package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;

import java.util.Optional;

import static org.mockito.Mockito.*;

class TaskControllerUnitTest {

    @Test
    void testAddTask() {
        TaskRepository repo = mock(TaskRepository.class);
        TaskController controller = new TaskController(repo);

        User user = new User();
        user.setId(1L);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        controller.addTask("UnitTest Task", session);

        verify(repo, times(1)).save(any(Task.class));
    }

    @Test
    void testDeleteTask() {
        TaskRepository repo = mock(TaskRepository.class);
        TaskController controller = new TaskController(repo);

        User user = new User();
        user.setId(1L);

        Task task = new Task();
        task.setId(1L);
        task.setUser(user);

        when(repo.findById(1L)).thenReturn(Optional.of(task));

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", user);

        controller.deleteTask(1L, session);

        verify(repo, times(1)).delete(task);
    }
}
