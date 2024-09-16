package io.codefresh.gradleexample.repository;

import io.codefresh.gradleexample.data.models.BidApproval;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BidApprovalRepository extends CrudRepository<BidApproval, Integer> {

    public Optional<BidApproval> findByBidId(Integer bidId);
}
