package de.ossi.todo.controller;

import de.ossi.todo.model.TodoItem;
import de.ossi.todo.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {
    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public List<TodoItem> getAllTodos() {
        return service.getAllTodos();
    }

    @GetMapping("/completed")
    public List<TodoItem> getAllCompletedTodos() {
        return service.getAllCompletedTodos();
    }

    @GetMapping("/{id}")
    public TodoItem getTodoById(@PathVariable Long id) {
        return service.getTodoById(id);
    }

    @PostMapping
    public TodoItem createTodo(@RequestBody TodoItem todo) {
        return service.createTodo(todo);
    }

    @PutMapping("/{id}")
    public TodoItem updateTodo(@PathVariable Long id, @RequestBody TodoItem updated) {
        return service.updateTodo(id, updated);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        service.deleteTodo(id);
    }
}
