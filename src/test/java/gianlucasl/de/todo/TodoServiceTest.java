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
        todo.setName("Test Todo");
        todo.setDescription("This is a test todo");
        todo.setDone(false);

        // Create a new todo
        todoService.createTodo(todo);
        assertNotNull(todo.id);

        // Retrieve the todo by ID
        TodoEntity retrievedTodo = todoService.getTodoById(todo.id);
        assertNotNull(retrievedTodo);
        assertEquals("Test Todo", retrievedTodo.getName());
        assertEquals("This is a test todo", retrievedTodo.getDescription());
        assertEquals(false, retrievedTodo.isDone());
    }

    @Test
    public void testMarkTodoAsCompleted() {
        TodoEntity todo = new TodoEntity();
        todo.setName("Incomplete Todo");
        todo.setDescription("This todo is not completed");
        todo.setDone(false);

        todoService.createTodo(todo);

        // Mark the todo as completed
        TodoEntity completedTodo = todoService.markTodoAsCompleted(todo.id);
        assertNotNull(completedTodo);
        assertTrue(completedTodo.isDone());
    }

    @Test
    public void testUpdateTodo() {
        TodoEntity todo = new TodoEntity();
        todo.setName("Old Name");
        todo.setDescription("Old Description");
        todo.setDone(false);

        todoService.createTodo(todo);

        // Update the todo
        todo.setName("New Name");
        todo.setDescription("New Description");
        todo.setDone(true);


        TodoEntity updatedTodo = todoService.updateTodo(todo);
        assertNotNull(updatedTodo);
        assertEquals("New Name", updatedTodo.getName());
        assertEquals("New Description", updatedTodo.getDescription());
        assertEquals(true, updatedTodo.isDone());
    }

    @Test
    public void testDeleteTodo() {
        TodoEntity todo = new TodoEntity();
        todo.setName("Todo to Delete");
        todo.setDescription("This todo will be deleted");
        todo.setDone(false);

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
        todo1.setName("Todo 1");
        todo1.setDescription("Description 1");
        todo1.setDone(false);

        TodoEntity todo2 = new TodoEntity();
        todo2.setName("Todo 2");
        todo2.setDescription("Description 2");
        todo2.setDone(true);

        todoService.createTodo(todo1);
        todoService.createTodo(todo2);

        // Get all todos
        List<TodoEntity> todos = todoService.getAllTodos();
        assertNotNull(todos);
        assertEquals(2, todos.size());
    }

}