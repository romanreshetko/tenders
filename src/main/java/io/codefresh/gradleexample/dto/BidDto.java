package io.codefresh.gradleexample.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;

@AllArgsConstructor
@Data
public class BidDto {

    private String name;

    private String description;

    private String status;

    private Integer tenderId;

    private Integer organizationId;

    private String creatorUsername;
}
