# TODO-App

## SpringbootApp

- Erstelle ein Springboot TODO-App mit der man Aufgaben (`TodoItem`) anlegen, ändern, löschen kann.
- Ein Springboot App besteht aus:
    - `TodoAppApplication`
        - Startet die App: `SpringApplication.run`
    - `TodoController` (`@RestController` und `@RequestMapping`)
        - Einsprungpunkt der Rest Requests
        - Ruft den `TodoService` auf (Injected über den Konstruktor `@Autowired`)
        - Methoden: getAllTodos, getTodoById, createTodo, updateTodo, deleteTodo
        - `TodoService` (`@Service`)
            - Ruft das `TodoRepository` auf (Injected über den Konstruktor `@Autowired`)
        - `TodoRepository` (`@Repository`)
            - Interface extends `JpaRepository`
        - Model: `TodoItem` (`@Entity`)
            - Mit Id als `@Id` und `@GeneratedValue(strategy = GenerationType.IDENTITY)`

## DB

- InMemory DB verwenden, zB H2

## Tests

- Tests: Die Springboot App testen mit `@SpringBootTest`
    - `@TestRestTemplate` zum Absetzen der Rest Requests
    - Tipp: `@LocalServerPort` um den Server-Port im Test dynamisch auszulesen
- Erweiterung: statt InMemory DB, die Daten persistieren

