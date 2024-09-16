package io.codefresh.gradleexample.repository;

import io.codefresh.gradleexample.data.models.Tender;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TenderRepository extends CrudRepository<Tender, Integer> {

    public Iterable<Tender> findAllByCreatorUsername(String username);
}
