package io.codefresh.gradleexample.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("bid_versions")
@AllArgsConstructor
@Data
public class BidVersion {

    @Id
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Integer version;
    @Column
    private Integer bidId;
}
