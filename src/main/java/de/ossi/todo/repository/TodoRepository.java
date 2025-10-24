package de.ossi.todo.repository;

import de.ossi.todo.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoItem, Long> {
    List<TodoItem> findByCompleted(boolean completed);
}
