package ul.ulstu.tamada.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "customers")
public class Customer extends User {

    private String name;

    private String surname;

    private String patronymic;
}
