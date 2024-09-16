package io.codefresh.gradleexample.dto;

import io.codefresh.gradleexample.data.models.Tender;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TenderEditDto {

    public TenderEditDto(Tender tender) {
        name = tender.getName();
        description = tender.getDescription();
    }

    private String name;

    private String description;
}
