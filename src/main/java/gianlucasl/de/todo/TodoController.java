package gianlucasl.de.todo;

import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoController {

    @Inject
    TodoService todoService;

    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    @Blocking
    public TemplateInstance getTodos() {
        List<TodoEntity> todos = todoService.getAllTodos();
        return TodoTemplates.listTodos(todos, false);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getTodoPage() {
        return TodoTemplates.index();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    @Blocking
    @Path("/{id}/complete")
    public TemplateInstance markTodoAsCompleted(@PathParam("id") Long id) {
        TodoEntity todo = todoService.markTodoAsCompleted(id);
        return TodoTemplates.listTodos(List.of(todo), false);
    }

    @GET
    @Path("/{id}/edit")
    @Produces(MediaType.TEXT_HTML)
    @Blocking
    public TemplateInstance getTodoForEdit(@PathParam("id") Long id) {
        TodoEntity todo = todoService.getTodoById(id);
        return TodoTemplates.listTodos(List.of(todo), true);
    }

    @PUT
    @Path("/{id}/update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    @Blocking
    public TemplateInstance updateTodo(@PathParam("id") Long id, @FormParam("name") String name, @FormParam("description") String description) {
        TodoEntity todo = todoService.getTodoById(id);
        if (todo != null) {
            todo.setName(name);
            todo.setDescription(description);
        }
        todoService.updateTodo(todo);

        return TodoTemplates.listTodos(List.of(todo), false);
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    @Blocking
    public Response deleteTodoById(@PathParam("id") Long id) {
        todoService.deleteTodo(id);
        return Response.status(200).header("Content-Type", "text/html; charset=utf-8").entity("").build();
    }

    @GET
    @Path("/create")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getCreateTodoForm() {
        return TodoTemplates.createForm();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Blocking
    public TemplateInstance createTodo(@FormParam("name") String name, @FormParam("description") String description) {
        TodoEntity todo = new TodoEntity();
        todo.setName(name);
        todo.setDescription(description);
        todo.setDone(false);
        todoService.createTodo(todo);
        return TodoTemplates.listTodos(List.of(todo), false);
    }

}