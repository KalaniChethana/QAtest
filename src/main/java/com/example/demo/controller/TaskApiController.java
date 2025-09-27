package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskApiController {

    private final TaskRepository taskRepo;
    private final UserRepository userRepo;

    public TaskApiController(TaskRepository taskRepo, UserRepository userRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }

    // Get all tasks for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasks(@PathVariable Long userId) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        List<Task> tasks = taskRepo.findByUser(user.get());
        return ResponseEntity.ok(tasks);
    }

    // Add a new task
    @PostMapping("/add")
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        Optional<User> user = userRepo.findById(task.getUser().getId());
        if (user.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        task.setUser(user.get());
        Task savedTask = taskRepo.save(task);
        return ResponseEntity.ok(savedTask);
    }

    // Delete a task by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        Optional<Task> task = taskRepo.findById(id);
        if (task.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        taskRepo.delete(task.get());
        return ResponseEntity.ok().build();
    }

    // Mark task as done
    @PutMapping("/done/{id}")
    public ResponseEntity<Task> markDone(@PathVariable Long id) {
        Optional<Task> task = taskRepo.findById(id);
        if (task.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        Task t = task.get();
        t.setDone(true);
        taskRepo.save(t);
        return ResponseEntity.ok(t);
    }
}
