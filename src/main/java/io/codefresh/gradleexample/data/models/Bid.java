package io.codefresh.gradleexample.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("bids")
@AllArgsConstructor
@Data
public class Bid {

    @Id
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String status;
    @Column
    private Integer tenderId;
    @Column
    private Integer organizationId;
    @Column
    private String creatorUsername;
    @Column
    private Integer version;
}
