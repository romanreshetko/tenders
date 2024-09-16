package io.codefresh.gradleexample.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("tenders")
@AllArgsConstructor
@Data
public class Tender {

    @Id
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String serviceType;
    @Column
    private String status;
    @Column
    private Integer organizationId;
    @Column
    private String creatorUsername;
    @Column
    private Integer version;
}
