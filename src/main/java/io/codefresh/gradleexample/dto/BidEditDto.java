package io.codefresh.gradleexample.dto;

import io.codefresh.gradleexample.data.models.Bid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;

@AllArgsConstructor
@Data
public class BidEditDto {

    public BidEditDto(Bid bid) {
        name = bid.getName();
        description = bid.getDescription();
    }

    private String name;

    private String description;
}
