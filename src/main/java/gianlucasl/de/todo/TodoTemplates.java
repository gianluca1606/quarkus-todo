package gianlucasl.de.todo;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

import java.util.List;

@CheckedTemplate
public class TodoTemplates {
    public static native TemplateInstance listTodos(List<TodoEntity> todos);

    public static native TemplateInstance createForm();

    public static native TemplateInstance index();

}