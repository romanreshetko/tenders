package io.codefresh.gradleexample.controller;

import io.codefresh.gradleexample.api.Api;
import io.codefresh.gradleexample.data.models.Bid;
import io.codefresh.gradleexample.data.models.Feedback;
import io.codefresh.gradleexample.data.models.Tender;
import io.codefresh.gradleexample.dto.BidDto;
import io.codefresh.gradleexample.dto.BidEditDto;
import io.codefresh.gradleexample.dto.TenderDto;
import io.codefresh.gradleexample.dto.TenderEditDto;
import io.codefresh.gradleexample.service.BidService;
import io.codefresh.gradleexample.service.TenderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;


@AllArgsConstructor
@RequestMapping("/api")
@RestController
public class Controller implements Api {

    private final TenderService tenderService;
    private final BidService bidService;

    @Override
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok().body("ok");
    }

    @Override
    @GetMapping("/tenders")
    public ResponseEntity<Iterable<Tender>> getTenders() {
        try {
            return ResponseEntity.ok().body(tenderService.getAllTenders());
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    @PostMapping("/tenders/new")
    public ResponseEntity<Tender> createTender(
            @RequestBody TenderDto tender
    ) {
        try {
            return ResponseEntity.ok().body(tenderService.createTender(tender));
        } catch (NoSuchElementException e) {
            if (Objects.equals(e.getMessage(), "user")) {
                return ResponseEntity.status(403).body(null);
            } else if (Objects.equals(e.getMessage(), "organization")){
                return ResponseEntity.status(404).body(null);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    @GetMapping("/tenders/my")
    public ResponseEntity<Iterable<Tender>> getTendersByUser(
            @RequestParam(name = "username") String username
    ) {
        try {
            return ResponseEntity.ok().body(tenderService.findByUsername(username));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(401).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    @GetMapping("/tenders/{tenderId}/status")
    public ResponseEntity<String> getTenderStatus(
            @PathVariable(name = "tenderId") Integer tenderId,
            @RequestParam(name = "username") String username
    ) {
        try {
            return ResponseEntity.ok().body(tenderService.findTenderStatus(tenderId, username));
        } catch (NoSuchElementException e) {
            if (Objects.equals(e.getMessage(), "user")) {
                return ResponseEntity.status(401).body(null);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    @PatchMapping("/tenders/{tenderId}/edit")
    public ResponseEntity<Tender> editTender(
            @PathVariable(name = "tenderId") Integer tenderId,
            @RequestBody TenderEditDto tender,
            @RequestParam(name = "username") String username
    ) {
        try {
            return ResponseEntity.ok().body(tenderService.editTender(tenderId, tender, username));
        } catch (NoSuchElementException e) {
            if (Objects.equals(e.getMessage(), "user")) {
                return ResponseEntity.status(403).body(null);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    @PutMapping("/tenders/{tenderId}/rollback/{version}")
    public ResponseEntity<Tender> rollbackTender(
            @RequestParam(name = "tenderId") Integer tenderId,
            @RequestParam(name = "version") Integer version,
            @RequestParam(name = "username") String username
    ) {
        try {
            return ResponseEntity.ok().body(tenderService.rollbackTender(tenderId, version, username));
        } catch (NoSuchElementException e) {
            if (Objects.equals(e.getMessage(), "user")) {
                return ResponseEntity.status(403).body(null);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    @PutMapping("/tenders/{tenderId}/status")
    public ResponseEntity<Tender> setTenderStatus(
            @PathVariable(name = "tenderId") Integer tenderId,
            @RequestParam(name = "status") String status,
            @RequestParam(name = "username") String username
    ) {
        try {
            return ResponseEntity.ok().body(tenderService.setTenderStatus(tenderId, status, username));
        } catch (NoSuchElementException e) {
            if (Objects.equals(e.getMessage(), "user")) {
                return ResponseEntity.status(403).body(null);
            } else if (Objects.equals(e.getMessage(), "tender")) {
                return ResponseEntity.status(404).body(null);
            } else {
                return ResponseEntity.status(400).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    @PostMapping("/bids/new")
    public ResponseEntity<Bid> createBid(
            @RequestBody BidDto bidDto
    ) {
        try {
            return ResponseEntity.ok().body(bidService.createBid(bidDto));
        } catch (NoSuchElementException e) {
            if (Objects.equals(e.getMessage(), "user")) {
                return ResponseEntity.status(403).body(null);
            } else if (Objects.equals(e.getMessage(), "organization")) {
                return ResponseEntity.status(403).body(null);
            } else {
                return ResponseEntity.status(403).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    @GetMapping("/bids/my")
    public ResponseEntity<Iterable<Bid>> getBidsByUser(
            @RequestParam(name = "username") String username
    ) {
        try {
            return ResponseEntity.ok().body(bidService.findByUsername(username));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(401).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    @GetMapping("/bids/{tenderId}/list")
    public ResponseEntity<Iterable<Bid>> getBidsByTender(
            @PathVariable(name = "tenderId") Integer tenderId,
            @RequestParam(name = "username") String username
    ) {
        try {
            return ResponseEntity.ok().body(bidService.findByTenderId(tenderId, username));
        } catch (NoSuchElementException e) {
            if (Objects.equals(e.getMessage(), "user")) {
                return ResponseEntity.status(403).body(null);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    @GetMapping("/bids/{bidId}/status")
    public ResponseEntity<String> getBidStatus(
            @PathVariable(name = "bidId") Integer bidId,
            @RequestParam(name = "username") String username
    ) {
        try {
            return ResponseEntity.ok().body(bidService.getBidStatus(bidId, username));
        } catch (NoSuchElementException e) {
            if (Objects.equals(e.getMessage(), "user")) {
                return ResponseEntity.status(401).body(null);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    @PatchMapping("/bids/{bidId}/edit")
    public ResponseEntity<Bid> editBid(
            @PathVariable(name = "bidId") Integer bidId,
            @RequestBody BidEditDto bid,
            @RequestParam(name = "username") String username
    ) {
        try {
            return ResponseEntity.ok().body(bidService.editBid(bidId, bid, username));
        } catch (NoSuchElementException e) {
            if (Objects.equals(e.getMessage(), "user")) {
                return ResponseEntity.status(403).body(null);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    @PutMapping("/bids/{bidId}/rollback/{version}")
    public ResponseEntity<Bid> rollbackBid(
            @PathVariable(name = "bidId") Integer bidId,
            @PathVariable(name = "version") Integer version,
            @RequestParam(name = "username") String username
    ) {
        try {
            return ResponseEntity.ok().body(bidService.rollbackBid(bidId, version, username));
        } catch (NoSuchElementException e) {
            if (Objects.equals(e.getMessage(), "user")) {
                return ResponseEntity.status(403).body(null);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    @PutMapping("/bids/{bidId}/status")
    public ResponseEntity<Bid> setBidStatus(
            @PathVariable(name = "bidId") Integer bidId,
            @RequestParam(name = "status") String status,
            @RequestParam(name = "username") String username
    ) {
        try {
            return ResponseEntity.ok().body(bidService.setBidStatus(bidId, status, username));
        } catch (NoSuchElementException e) {
            if (Objects.equals(e.getMessage(), "user")) {
                return ResponseEntity.status(403).body(null);
            } else if (Objects.equals(e.getMessage(), "bid")) {
                return ResponseEntity.status(404).body(null);
            } else {
                return ResponseEntity.status(400).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    @PutMapping("/bids/{bidId}/feedback")
    public ResponseEntity<Bid> createFeedback(
            @PathVariable(name = "bidId") Integer bidId,
            @RequestParam(name = "feedback") String feedback,
            @RequestParam(name = "username") String username
    ) {
        try {
            return ResponseEntity.ok().body(bidService.createFeedback(bidId, feedback, username));
        } catch (NoSuchElementException e) {
            if (Objects.equals(e.getMessage(), "user")) {
                return ResponseEntity.status(403).body(null);
            } else if (Objects.equals(e.getMessage(), "bid") ||
                    Objects.equals(e.getMessage(), "tender")) {
                return ResponseEntity.status(404).body(null);
            } else {
                return ResponseEntity.status(400).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    @GetMapping("/bids/{bidId}/reviews")
    public ResponseEntity<Iterable<Feedback>> getBidReviews(
            @PathVariable(name = "tenderId") Integer tenderId,
            @RequestParam(name = "authorUsername") String authorUsername,
            @RequestParam(name = "requesterUsername") String requesterUsername
    ) {
        try {
            return ResponseEntity.ok().body(bidService.getReviewsByAuthor(tenderId, authorUsername, requesterUsername));
        } catch (NoSuchElementException e) {
            if (Objects.equals(e.getMessage(), "user")) {
                return ResponseEntity.status(403).body(null);
            } else if (Objects.equals(e.getMessage(), "tender")) {
                return ResponseEntity.status(404).body(null);
            } else {
                return ResponseEntity.status(400).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Override
    @PutMapping("/bids/{bidId}/submit_decision")
    public ResponseEntity<Bid> submitDecision(
            @PathVariable(name = "bidId") Integer bidId,
            @RequestParam(name = "decision") String decision,
            @RequestParam(name = "username") String username
    ) {
        try {
            return ResponseEntity.ok().body(bidService.submitBidDecision(bidId, decision, username));
        } catch (NoSuchElementException e) {
            if (Objects.equals(e.getMessage(), "user")) {
                return ResponseEntity.status(403).body(null);
            } else if (Objects.equals(e.getMessage(), "tender") ||
                    Objects.equals(e.getMessage(), "bid")) {
                return ResponseEntity.status(404).body(null);
            } else {
                return ResponseEntity.status(400).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
