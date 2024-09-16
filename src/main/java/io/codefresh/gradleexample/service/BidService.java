package io.codefresh.gradleexample.service;

import io.codefresh.gradleexample.data.models.*;
import io.codefresh.gradleexample.dto.BidDto;
import io.codefresh.gradleexample.dto.BidEditDto;
import io.codefresh.gradleexample.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;


@AllArgsConstructor
@Service
public class BidService {

    private final BidRepository bidRepository;
    private final BidsVersionRepository bidsVersionRepository;
    private final TenderRepository tenderRepository;
    private final EmployeeService employeeService;
    private final FeedbackRepository feedbackRepository;
    private final BidApprovalRepository bidApprovalRepository;

    public Bid createBid(BidDto bid) {
        if (employeeService.findUserByUsername(bid.getCreatorUsername()) == null) {
            throw new NoSuchElementException("user");
        }
        if (!employeeService.findOrganizationById(bid.getOrganizationId())) {
            throw new NoSuchElementException("organization");
        }
        Bid b = new Bid(
                null,
                bid.getName(),
                bid.getDescription(),
                bid.getStatus(),
                bid.getTenderId(),
                bid.getOrganizationId(),
                bid.getCreatorUsername(),
                1
        );

        return bidRepository.save(b);
    }

    public Iterable<Bid> findByUsername(String username) {
        if (employeeService.findUserByUsername(username) == null) {
            throw new NoSuchElementException();
        }
        return bidRepository.findByCreatorUserName(username);
    }

    public Iterable<Bid> findByTenderId(Integer tenderId, String username) {
        Optional<Tender> optionalTender = tenderRepository.findById(tenderId);
        if (!optionalTender.isPresent()) {
            throw new NoSuchElementException("tender");
        }
        Tender t = optionalTender.get();
        Integer organizationId = employeeService.findOrganizationResponsibleIdByUsername(username);
        if (!Objects.equals(organizationId, t.getOrganizationId())) {
            throw new NoSuchElementException("user");
        }
        return bidRepository.findByTenderId(tenderId);
    }

    public String getBidStatus(Integer bidId, String username) {
        if (employeeService.findUserByUsername(username) == null) {
            throw new NoSuchElementException("user");
        }

        Optional<Bid> optionalBid = bidRepository.findById(bidId);
        if (!optionalBid.isPresent()) {
            throw new NoSuchElementException();
        }
        Bid b = optionalBid.get();

        return b.getStatus();
    }

    public Bid editBid(Integer bidId, BidEditDto bidEditDto, String username) {

        Optional<Bid> optionalBid = bidRepository.findById(bidId);
        if (!optionalBid.isPresent()) {
            throw new NoSuchElementException("bid");
        }
        Bid b = optionalBid.get();
        if (!Objects.equals(b.getStatus(), BidStatus.CREATED.value) &&
                !Objects.equals(b.getStatus(), BidStatus.PUBLISHED.value)) {
            throw new NoSuchElementException("bid");
        }

        Integer organizationId = employeeService.findOrganizationResponsibleIdByUsername(username);
        if (!Objects.equals(organizationId, b.getOrganizationId())) {
            throw new NoSuchElementException("user");
        }

        BidVersion bidVersion = new BidVersion(
                null,
                b.getName(),
                b.getDescription(),
                b.getVersion(),
                b.getId()
        );
        bidsVersionRepository.save(bidVersion);

        b.setName(bidEditDto.getName());
        b.setDescription(bidEditDto.getDescription());
        b.setVersion(b.getVersion() + 1);
        return bidRepository.save(b);
    }

    public Bid rollbackBid(Integer bidId, Integer version, String username) {

        Optional<Bid> optionalBid = bidRepository.findById(bidId);
        if (!optionalBid.isPresent()) {
            throw new NoSuchElementException("bid");
        }
        Bid b = optionalBid.get();
        if (!Objects.equals(b.getStatus(), BidStatus.CREATED.value) &&
                !Objects.equals(b.getStatus(), BidStatus.PUBLISHED.value)) {
            throw new NoSuchElementException("bid");
        }

        Integer organizationId = employeeService.findOrganizationResponsibleIdByUsername(username);
        if (!Objects.equals(organizationId, b.getOrganizationId())) {
            throw new NoSuchElementException("user");
        }

        Optional<BidVersion> optionalBidVersion = bidsVersionRepository.findByVersionAndBidId(version, bidId);
        if (!optionalBidVersion.isPresent()) {
            throw new NoSuchElementException();
        }
        BidVersion bidVersion = optionalBidVersion.get();

        b.setName(bidVersion.getName());
        b.setDescription(bidVersion.getDescription());
        b.setVersion(b.getVersion() + 1);

        return bidRepository.save(b);
    }

    public Bid setBidStatus(Integer id, String status, String username) {
        if (!Objects.equals(status, BidStatus.PUBLISHED.value) && !Objects.equals(status, BidStatus.CANCELED.value) &&
                !Objects.equals(status, BidStatus.APPROVED.value) && !Objects.equals(status, BidStatus.REJECTED.value)) {
            throw new NoSuchElementException("status");
        }

        Optional<Bid> optionalBid = bidRepository.findById(id);
        if (!optionalBid.isPresent()) {
            throw new NoSuchElementException("bid");
        }
        Bid b = optionalBid.get();
        if (!Objects.equals(b.getCreatorUsername(), username) &&
                !Objects.equals(b.getOrganizationId(), employeeService.findOrganizationResponsibleIdByUsername(username))) {
            throw new NoSuchElementException("user");
        }

        b.setStatus(status);

        return bidRepository.save(b);
    }

    public Bid createFeedback(Integer id, String feedback, String username) {
        Optional<Bid> optionalBid = bidRepository.findById(id);
        if (!optionalBid.isPresent()) {
            throw new NoSuchElementException("bid");
        }
        Bid b = optionalBid.get();
        if (!Objects.equals(b.getStatus(), BidStatus.CREATED.value) &&
                !Objects.equals(b.getStatus(), BidStatus.PUBLISHED.value)) {
            throw new NoSuchElementException("bid");
        }

        Integer organizationId = employeeService.findOrganizationResponsibleIdByUsername(username);

        Optional<Tender> optionalTender = tenderRepository.findById(b.getTenderId());
        if (!optionalTender.isPresent()) {
            throw new NoSuchElementException("tender");
        }
        Tender t = optionalTender.get();

        if (!Objects.equals(t.getOrganizationId(), organizationId)) {
            throw new NoSuchElementException("user");
        }

        Feedback f = new Feedback(
                null,
                feedback,
                username,
                id
        );

        feedbackRepository.save(f);

        return b;
    }

    public Iterable<Feedback> getReviewsByAuthor(Integer tenderId, String author, String requester) {

        Optional<Tender> optionalTender = tenderRepository.findById(tenderId);
        if (!optionalTender.isPresent()) {
            throw new NoSuchElementException("tender");
        }
        Tender t = optionalTender.get();

        Integer organizationId = employeeService.findOrganizationResponsibleIdByUsername(requester);
        if (!Objects.equals(organizationId, t.getOrganizationId())) {
            throw new NoSuchElementException("user");
        }

        return feedbackRepository.findFeedbackByBidAuthor(author);
    }

    public Bid submitBidDecision(Integer bidId, String decision, String username) {
        Optional<Bid> optionalBid = bidRepository.findById(bidId);
        if (!optionalBid.isPresent()) {
            throw new NoSuchElementException("bid");
        }
        Bid b = optionalBid.get();
        if (!Objects.equals(b.getStatus(), BidStatus.PUBLISHED.value)) {
            throw new NoSuchElementException("bid");
        }

        Integer organizationId = employeeService.findOrganizationResponsibleIdByUsername(username);
        Optional<Tender> optionalTender = tenderRepository.findById(b.getTenderId());
        if (!optionalTender.isPresent()) {
            throw new NoSuchElementException("tender");
        }
        Tender t = optionalTender.get();
        if (!Objects.equals(organizationId, t.getOrganizationId())) {
            throw new NoSuchElementException("user");
        }

        if (Objects.equals(decision, BidStatus.REJECTED.value)) {
            b.setStatus(decision);
            return bidRepository.save(b);
        }

        if (!Objects.equals(decision, BidStatus.APPROVED.value)) {
            throw new NoSuchElementException("status");
        }

        Optional<BidApproval> optionalBidApproval = bidApprovalRepository.findByBidId(bidId);
        if (!optionalBidApproval.isPresent()) {
            Integer quorum = employeeService.countOrganizationResponsible(t.getOrganizationId());
            if (quorum > 3) {
                quorum = 3;
            }
            BidApproval bidApproval = new BidApproval(
                    null,
                    bidId,
                    quorum,
                    1
            );
            bidApprovalRepository.save(bidApproval);
            if (bidApproval.getApprovedNumber() >= bidApproval.getQuorum()) {
                b.setStatus(decision);
                t.setStatus(TenderStatus.CLOSED.value);
                tenderRepository.save(t);
                return bidRepository.save(b);
            }
        } else {
            BidApproval bidApproval = optionalBidApproval.get();
            bidApproval.setApprovedNumber(bidApproval.getApprovedNumber() + 1);
            bidApprovalRepository.save(bidApproval);
            if (bidApproval.getApprovedNumber() >= bidApproval.getQuorum()) {
                b.setStatus(decision);
                t.setStatus(TenderStatus.CLOSED.value);
                tenderRepository.save(t);
                return bidRepository.save(b);
            }
        }

        return b;
    }
}
