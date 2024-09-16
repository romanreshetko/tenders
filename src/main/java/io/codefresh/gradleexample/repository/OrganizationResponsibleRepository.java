package io.codefresh.gradleexample.repository;

import io.codefresh.gradleexample.data.models.OrganizationResponsible;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationResponsibleRepository extends CrudRepository<OrganizationResponsible, Integer> {

    @Query("SELECT organization_id FROM organization_responsible " +
            "WHERE organization_responsible.user_id = :user_id")
    public Integer findByUserId(
            @Param("user_id") Integer userId
    );

    @Query("SELECT count(*) FROM organization_responsible " +
            "WHERE organization_responsible.organization_id = :organization_id")
    public Integer countByOrganizationId(
            @Param("organization_id") Integer organization_id
    );
}
