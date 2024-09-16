package io.codefresh.gradleexample.repository;

import io.codefresh.gradleexample.data.models.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Integer> {

}
