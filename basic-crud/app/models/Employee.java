package models;

import io.ebean.Finder;
import io.ebean.Model;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee extends Model {

    public static Finder<Long, Employee> employeeFinder = new Finder<>(Employee.class);
    @Id
    private Long id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "designation")
    private String designation;
}
