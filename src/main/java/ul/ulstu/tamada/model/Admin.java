package ul.ulstu.tamada.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "admins")
public class Admin extends User {
}
