package io.codefresh.gradleexample.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table("organization_responsible")
@AllArgsConstructor
@Data
public class OrganizationResponsible {

    @Id
    private Integer id;
    @Column
    private Integer organization_id;
    @Column
    private Integer user_id;

}
