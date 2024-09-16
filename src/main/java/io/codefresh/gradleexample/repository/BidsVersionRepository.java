package io.codefresh.gradleexample.repository;

import io.codefresh.gradleexample.data.models.BidVersion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BidsVersionRepository extends CrudRepository<BidVersion, Integer> {

    public Optional<BidVersion> findByVersionAndBidId(Integer version, Integer bidId);
}
