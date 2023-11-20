package gianlucasl.de.todo;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@QuarkusTest
public class TodoServiceTest {

    @Inject
    TodoService todoService;

    @Test
    public void testCreateAndGetTodo() {
        TodoEntity todo = new TodoEntity();
        todo.name = "Test Todo";
        todo.description = "This is a test todo";
        todo.done = false;

        // Create a new todo
        todoService.createTodo(todo);
        assertNotNull(todo.id);

        // Retrieve the todo by ID
        TodoEntity retrievedTodo = todoService.getTodoById(todo.id);
        assertNotNull(retrievedTodo);
        assertEquals("Test Todo", retrievedTodo.name);
        assertEquals("This is a test todo", retrievedTodo.description);
        assertEquals(false, retrievedTodo.done);
    }

    @Test
    public void testMarkTodoAsCompleted() {
        TodoEntity todo = new TodoEntity();
        todo.name = "Incomplete Todo";
        todo.description = "This todo is not completed";
        todo.done = false;

        todoService.createTodo(todo);

        // Mark the todo as completed
        TodoEntity completedTodo = todoService.markTodoAsCompleted(todo.id);
        assertNotNull(completedTodo);
        assertTrue(completedTodo.done);
    }

    @Test
    public void testUpdateTodo() {
        TodoEntity todo = new TodoEntity();
        todo.name = "Old Name";
        todo.description = "Old Description";
        todo.done = false;

        todoService.createTodo(todo);

        // Update the todo
        todo.name = "New Name";
        todo.description = "New Description";
        todo.done = true;

        TodoEntity updatedTodo = todoService.updateTodo(todo);
        assertNotNull(updatedTodo);
        assertEquals("New Name", updatedTodo.name);
        assertEquals("New Description", updatedTodo.description);
        assertEquals(true, updatedTodo.done);
    }

    @Test
    public void testDeleteTodo() {
        TodoEntity todo = new TodoEntity();
        todo.name = "Todo to Delete";
        todo.description = "This todo will be deleted";
        todo.done = false;

        todoService.createTodo(todo);

        // Delete the todo
        todoService.deleteTodo(todo.id);

        // Verify that the todo is deleted
        TodoEntity deletedTodo = todoService.getTodoById(todo.id);
        assertNull(deletedTodo);
    }

    @Test
    public void testGetAllTodos() {
        TodoEntity todo1 = new TodoEntity();
        todo1.name = "Todo 1";
        todo1.description = "Description 1";
        todo1.done = false;

        TodoEntity todo2 = new TodoEntity();
        todo2.name = "Todo 2";
        todo2.description = "Description 2";
        todo2.done = true;

        todoService.createTodo(todo1);
        todoService.createTodo(todo2);

        // Get all todos
        List<TodoEntity> todos = todoService.getAllTodos();
        assertNotNull(todos);
        assertEquals(2, todos.size());
    }

}