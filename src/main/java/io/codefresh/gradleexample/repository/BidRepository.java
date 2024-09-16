package io.codefresh.gradleexample.repository;

import io.codefresh.gradleexample.data.models.Bid;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BidRepository extends CrudRepository<Bid, Integer> {

    public Iterable<Bid> findByCreatorUserName(String username);

    public Iterable<Bid> findByTenderId(Integer tenderId);
}
