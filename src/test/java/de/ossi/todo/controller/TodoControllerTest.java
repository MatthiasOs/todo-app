package de.ossi.todo.controller;

import de.ossi.todo.model.TodoItem;
import de.ossi.todo.service.TodoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith({MockitoExtension.class})
class TodoControllerTest {
    public static final String DESCRIPTION = "Description";
    public static final String TITLE = "Title";
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TodoService todoService;

    @Test
    void shouldCreateTodo() throws Exception {
        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test\",\"description\":\"Hello\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnTodos() throws Exception {
        var newTodo = createTodoItem();
        when(todoService.getAllTodos()).thenReturn(List.of(newTodo));
        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", Matchers.is(TITLE)))
                .andExpect(jsonPath("$[0].description", Matchers.is(DESCRIPTION)))
                .andExpect(jsonPath("$[0].completed", Matchers.is(false)));
    }

    private static TodoItem createTodoItem() {
        var newTodo = new TodoItem();
        newTodo.setCompleted(false);
        newTodo.setDescription(DESCRIPTION);
        newTodo.setTitle(TITLE);
        return newTodo;
    }
}