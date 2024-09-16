package io.codefresh.gradleexample.repository;

import io.codefresh.gradleexample.data.models.TenderVersion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenderVersionRepository extends CrudRepository<TenderVersion, Integer> {

    public Optional<TenderVersion> findByVersionAndTenderID(Integer version, Integer tenderId);
}
