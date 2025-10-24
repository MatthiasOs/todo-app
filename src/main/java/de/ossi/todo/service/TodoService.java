package de.ossi.todo.service;

import de.ossi.todo.model.TodoItem;
import de.ossi.todo.repository.TodoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoItem> getAllTodos() {
        return todoRepository.findAll();
    }

    public List<TodoItem> getAllTodosCompleted() {
        return todoRepository.findByCompleted(true);
    }

    /**
     * Returns the {@link TodoItem} with the provided id.
     *
     * @throws EntityNotFoundException when no {@link TodoItem} with the id is found.
     */
    public TodoItem getTodoById(Long id) {
        return todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Todo Item with id %s not found!".formatted(id)));
    }

    public TodoItem createTodo(TodoItem todoItem) {
        return todoRepository.save(todoItem);
    }

    public TodoItem updateTodo(Long id, TodoItem updated) {
        var todoItemOld = getTodoById(id);
        todoItemOld.setTitle(updated.getTitle());
        todoItemOld.setDescription(updated.getDescription());
        todoItemOld.setCompleted(updated.isCompleted());
        return todoRepository.save(updated);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}
