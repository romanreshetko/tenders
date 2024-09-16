package io.codefresh.gradleexample.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table("employee")
@AllArgsConstructor
@Data
public class Employee {

    @Id
    private Integer id;
    @Column
    private String username;
    @Column
    private String first_name;
    @Column
    private String last_name;
    @Column
    private Date created_at;
    @Column
    private Date updated_at;
}
