package de.ossi.todo.controller;

import de.ossi.todo.model.TodoItem;
import de.ossi.todo.service.TodoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoControllerTest {
    private static final String DESCRIPTION = "Description";
    private static final String TITLE = "Title";
    private final TodoItem todoItem = createTodoItem();

    @InjectMocks
    private TodoController todoController;

    @Mock
    private TodoService todoService;

    @Test
    void shouldCreateTodo() throws Exception {
        when(todoService.createTodo(any())).thenAnswer(a -> a.getArguments()[0]);
        var newTodoItem = todoController.createTodo(todoItem);
        assertThat(newTodoItem)
                .extracting(TodoItem::getTitle, TodoItem::getDescription, TodoItem::isCompleted)
                .containsExactly(TITLE, DESCRIPTION, false);
    }

    @Test
    void shouldReturnTodos() throws Exception {
        when(todoService.getAllTodos()).thenReturn(List.of(todoItem));
        List<TodoItem> allTodos = todoController.getAllTodos();
        assertThat(allTodos)
                .singleElement()
                .extracting(TodoItem::getTitle, TodoItem::getDescription, TodoItem::isCompleted)
                .containsExactly(TITLE, DESCRIPTION, false);
    }

    private static TodoItem createTodoItem() {
        var newTodo = new TodoItem();
        newTodo.setCompleted(false);
        newTodo.setDescription(DESCRIPTION);
        newTodo.setTitle(TITLE);
        return newTodo;
    }
}