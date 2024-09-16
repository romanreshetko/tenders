package io.codefresh.gradleexample.api;

import io.codefresh.gradleexample.data.models.Bid;
import io.codefresh.gradleexample.data.models.Feedback;
import io.codefresh.gradleexample.data.models.Tender;
import io.codefresh.gradleexample.dto.BidDto;
import io.codefresh.gradleexample.dto.BidEditDto;
import io.codefresh.gradleexample.dto.TenderDto;
import io.codefresh.gradleexample.dto.TenderEditDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface Api {

    public ResponseEntity<String> ping();

    public ResponseEntity<Iterable<Tender>> getTenders();

    public ResponseEntity<Tender> createTender(TenderDto tender);

    public ResponseEntity<Iterable<Tender>> getTendersByUser(String username);

    public ResponseEntity<String> getTenderStatus(Integer tenderId, String username);

    public ResponseEntity<Tender> editTender(Integer tenderId, TenderEditDto tender, String username);

    public ResponseEntity<Tender> rollbackTender(Integer tenderId, Integer version, String username);

    public ResponseEntity<Tender> setTenderStatus(Integer tenderId, String status, String username);

    public ResponseEntity<Bid> createBid(BidDto bidDto);

    public ResponseEntity<Iterable<Bid>> getBidsByUser(String username);

    public ResponseEntity<Iterable<Bid>> getBidsByTender(Integer tenderId, String username);

    public ResponseEntity<String> getBidStatus(Integer bidId, String username);

    public ResponseEntity<Bid> editBid(Integer bidId, BidEditDto bid, String username);

    public ResponseEntity<Bid> rollbackBid(Integer bidId, Integer version, String username);

    public ResponseEntity<Bid> setBidStatus(Integer bidId, String status, String username);

    public ResponseEntity<Bid> createFeedback(Integer bidId, String feedback, String username);

    public ResponseEntity<Iterable<Feedback>> getBidReviews(Integer tenderId, String authorUsername, String requesterUsername);

    public ResponseEntity<Bid> submitDecision(Integer bidId, String decision, String username);
}
