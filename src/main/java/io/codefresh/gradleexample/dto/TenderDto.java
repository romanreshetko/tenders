package io.codefresh.gradleexample.dto;

import io.codefresh.gradleexample.data.models.TenderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TenderDto {

    private String name;

    private String description;

    private String serviceType;

    private TenderStatus status;

    private Integer organizationId;

    private String creatorUsername;
}
