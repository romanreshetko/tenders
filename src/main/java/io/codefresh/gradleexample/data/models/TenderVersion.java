package io.codefresh.gradleexample.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("tender_versions")
@AllArgsConstructor
@Data
public class TenderVersion {

    @Id
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Integer version;
    @Column
    private Integer tenderId;
}
