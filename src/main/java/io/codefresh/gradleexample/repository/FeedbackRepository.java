package io.codefresh.gradleexample.repository;

import io.codefresh.gradleexample.data.models.Feedback;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, Integer> {

    @Query("SELECT f FROM feedback f " +
            "INNER JOIN bids b ON f.bidId = b.id " +
            "WHERE b.creatorUsername = :username")
    public Iterable<Feedback> findFeedbackByBidAuthor(
            @Param("username") String username
    );
}
