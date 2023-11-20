package gianlucasl.de.todo;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "todo")
public class TodoEntity extends PanacheEntity {
    public String name;
    public String description;
    public boolean done;
}