package io.codefresh.gradleexample.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table("organization")
@AllArgsConstructor
@Data
public class Organization {

    @Id
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String type;
    @Column
    private Date created_at;
    @Column
    private Date updated_at;
}
