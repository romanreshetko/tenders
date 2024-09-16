package io.codefresh.gradleexample.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("feedback")
@AllArgsConstructor
@Data
public class Feedback {

    @Id
    private Integer id;
    @Column
    private String description;
    @Column
    private String username;
    @Column
    private Integer bidId;
}
