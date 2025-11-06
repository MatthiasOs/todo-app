package de.ossi.todo;

import de.ossi.todo.model.TodoItem;
import de.ossi.todo.repository.TodoRepository;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoAppApplicationTest {
    private static final String DESCRIPTION = "Description";
    private static final String TITLE = "Title";
    private final TodoItem todoItem = createTodoItem();
    private final Condition<TodoItem> defaultTodoItem = isTodoItem(todoItem);

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    void cleanDatabase() {
        todoRepository.deleteAll();
    }

    private String baseUrl() {
        return "http://localhost:" + port + "/api/v1/todos";
    }

    @Test
    void shouldCreateAndGetTodoItem() {
        var newTodo = createTodoItem();
        ResponseEntity<TodoItem> postResponse = restTemplate.postForEntity(baseUrl(), newTodo, TodoItem.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        var createdTodoItem = postResponse.getBody();
        assertThat(createdTodoItem)
                .satisfies(defaultTodoItem);

        var allTodoItems = restReadAll();
        assertThat(allTodoItems)
                .singleElement()
                .satisfies(defaultTodoItem);
    }

    @Test
    void shouldCreateAndGetByIdTodoItem() {
        var newTodo = createTodoItem();
        ResponseEntity<TodoItem> postResponse = restTemplate.postForEntity(baseUrl(), newTodo, TodoItem.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        var createdTodoItem = postResponse.getBody();
        assertThat(createdTodoItem)
                .satisfies(defaultTodoItem)
                .extracting(TodoItem::getId)
                .isNotNull();

        var byIdTodoItem = restTemplate.getForEntity(baseUrl() + "/" + createdTodoItem.getId(), TodoItem.class).getBody();
        assertThat(byIdTodoItem)
                .satisfies(defaultTodoItem)
                .extracting(TodoItem::getId)
                .isNotNull()
                .isEqualTo(createdTodoItem.getId());
    }

    @Test
    void shouldDeleteTodoItem() {
        var newTodo = createTodoItem();
        ResponseEntity<TodoItem> postResponse = restTemplate.postForEntity(baseUrl(), newTodo, TodoItem.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(postResponse.getBody()).isNotNull();

        restTemplate.delete(baseUrl() + "/" + postResponse.getBody().getId());

        var allTodoItems = restReadAll();
        assertThat(allTodoItems)
                .isEmpty();
    }

    @Test
    void shouldUpdateTodoItem() {
        var newTodo = createTodoItem();
        ResponseEntity<TodoItem> postResponse = restTemplate.postForEntity(baseUrl(), newTodo, TodoItem.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(postResponse.getBody()).isNotNull();

        var updatedTodo = createTodoItem();
        updatedTodo.setTitle("NewTitle");
        updatedTodo.setDescription("NewDescription");
        updatedTodo.setCompleted(true);

        restTemplate.exchange(baseUrl() + "/" + postResponse.getBody().getId(), HttpMethod.PUT, new HttpEntity<>(updatedTodo), TodoItem.class);

        var allTodoItems = restReadAll();
        assertThat(allTodoItems)
                .singleElement()
                .doesNotHave(defaultTodoItem)
                .satisfies(isTodoItem(updatedTodo));
    }

    @Test
    void shouldReturnOnlyCompletedTodoItem() {
        var completedTodo = createTodoItem(true);
        var newTodo = createTodoItem();
        restTemplate.postForEntity(baseUrl(), completedTodo, TodoItem.class);
        restTemplate.postForEntity(baseUrl(), newTodo, TodoItem.class);

        var responseBody = restTemplate.getForEntity(baseUrl() + "/completed", TodoItem[].class).getBody();
        assertThat(responseBody).isNotNull();

        assertThat(responseBody)
                .singleElement()
                .doesNotHave(isTodoItem(newTodo))
                .satisfies(isTodoItem(completedTodo));
    }

    private List<TodoItem> restReadAll() {
        var responseBody = restTemplate.getForEntity(baseUrl(), TodoItem[].class).getBody();
        assertThat(responseBody).isNotNull();
        return Arrays.asList(responseBody);
    }

    private static TodoItem createTodoItem(boolean completed) {
        var newTodo = new TodoItem();
        newTodo.setCompleted(completed);
        newTodo.setDescription(DESCRIPTION);
        newTodo.setTitle(TITLE);
        return newTodo;
    }

    private static TodoItem createTodoItem() {
        return createTodoItem(false);
    }

    private static Condition<TodoItem> isTodoItem(TodoItem todoItem) {
        return new Condition<>(
                t -> t.getDescription().equals(todoItem.getDescription())
                        && t.getTitle().equals(todoItem.getTitle())
                        && t.isCompleted() == todoItem.isCompleted(),
                "Is equal to: %s", todoItem);
    }
}