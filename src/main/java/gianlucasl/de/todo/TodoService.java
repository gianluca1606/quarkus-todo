package gianlucasl.de.todo;

import gianlucasl.de.todo.TodoEntity;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class TodoService {

    @Blocking
    public List<TodoEntity> getAllTodos() {
        return TodoEntity.listAll();
    }

    public TodoEntity getTodoById(Long id) {
        return TodoEntity.findById(id);
    }

    @Transactional
    public TodoEntity markTodoAsCompleted(Long todoId) {
        TodoEntity todo = TodoEntity.findById(todoId);
        if (todo != null) {
            todo.setDone(true);
        }
        return todo;
    }

    @Transactional
    public TodoEntity createTodo(TodoEntity todo) {
        todo.persist();
        return todo;
    }

    @Transactional
    public TodoEntity updateTodo(TodoEntity todo) {
        TodoEntity entity = TodoEntity.findById(todo.id);
        if (entity != null) {
            entity.setName(todo.getName());
            entity.setDescription(todo.getDescription());
            entity.setDone(todo.isDone());
        }
        return entity;
    }

    @Transactional
    public void deleteTodo(Long id) {
        TodoEntity.deleteById(id);
    }
}
