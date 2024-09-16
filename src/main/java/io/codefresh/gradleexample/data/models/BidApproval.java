package io.codefresh.gradleexample.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("bid_approval")
@AllArgsConstructor
@Data
public class BidApproval {

    @Id
    private Integer id;
    @Column
    private Integer bidId;
    @Column
    private Integer quorum;
    @Column
    private Integer approvedNumber;
}
