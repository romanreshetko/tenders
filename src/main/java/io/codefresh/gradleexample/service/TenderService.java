package io.codefresh.gradleexample.service;

import io.codefresh.gradleexample.data.models.Tender;
import io.codefresh.gradleexample.data.models.TenderStatus;
import io.codefresh.gradleexample.data.models.TenderVersion;
import io.codefresh.gradleexample.dto.TenderDto;
import io.codefresh.gradleexample.dto.TenderEditDto;
import io.codefresh.gradleexample.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TenderService {

    private final TenderRepository tenderRepository;
    private final TenderVersionRepository tenderVersionRepository;
    private final EmployeeService employeeService;

    public Iterable<Tender> getAllTenders() {
        return tenderRepository.findAll();
    }

    public Tender createTender(TenderDto tender) {
        if (employeeService.findOrganizationResponsibleIdByUsername(tender.getCreatorUsername()) == null) {
            throw new NoSuchElementException("user");
        }
        if (!employeeService.findOrganizationById(tender.getOrganizationId())) {
            throw new NoSuchElementException("organization");
        }
        Tender t = new Tender(
                null,
                tender.getName(),
                tender.getDescription(),
                tender.getServiceType(),
                TenderStatus.CREATED.value,
                tender.getOrganizationId(),
                tender.getCreatorUsername(),
                1
        );
        return tenderRepository.save(t);
    }

    public Iterable<Tender> findByUsername(String username) {
        if (employeeService.findOrganizationResponsibleIdByUsername(username) == null) {
            throw new NoSuchElementException();
        }
        return tenderRepository.findAllByCreatorUsername(username);
    }

    public String findTenderStatus(Integer tenderId, String username) {
        if (employeeService.findUserByUsername(username) == null) {
            throw new NoSuchElementException("user");
        }

        Optional<Tender> optionalTender = tenderRepository.findById(tenderId);
        if (!optionalTender.isPresent()) {
            throw new NoSuchElementException();
        }
        Tender t = optionalTender.get();

        return t.getStatus();
    }

    public Tender editTender(Integer id, TenderEditDto tenderEditDto, String username) {

        Optional<Tender> optionalTender = tenderRepository.findById(id);
        if (!optionalTender.isPresent()) {
            throw new NoSuchElementException("tender");
        }
        Tender t = optionalTender.get();
        if (Objects.equals(t.getStatus(), TenderStatus.CLOSED.value)) {
            throw new NoSuchElementException("tender");
        }

        Integer organizationId = employeeService.findOrganizationResponsibleIdByUsername(username);
        if (!Objects.equals(organizationId, t.getOrganizationId())) {
            throw new NoSuchElementException("user");
        }
        TenderVersion oldVersionTender = new TenderVersion(
                null,
                t.getName(),
                t.getDescription(),
                t.getVersion(),
                t.getId()
        );

        tenderVersionRepository.save(oldVersionTender);

        t.setName(tenderEditDto.getName());
        t.setDescription(tenderEditDto.getDescription());
        t.setVersion(t.getVersion() + 1);

        return tenderRepository.save(t);
    }

    public Tender rollbackTender(Integer id, Integer version, String username) {

        Optional<Tender> optionalTender = tenderRepository.findById(id);
        if (!optionalTender.isPresent()) {
            throw new NoSuchElementException("tender");
        }
        Tender t = optionalTender.get();
        if (Objects.equals(t.getStatus(), TenderStatus.CLOSED.value)) {
            throw new NoSuchElementException("tender");
        }

        Integer organizationId = employeeService.findOrganizationResponsibleIdByUsername(username);
        if (!Objects.equals(organizationId, t.getOrganizationId())) {
            throw new NoSuchElementException("user");
        }
        
        Optional<TenderVersion> optionalTenderVersion = tenderVersionRepository.findByVersionAndTenderID(version, t.getId());
        if (!optionalTenderVersion.isPresent()) {
            throw new NoSuchElementException();
        }
        TenderVersion tenderVersion = optionalTenderVersion.get();

        t.setName(tenderVersion.getName());
        t.setDescription(tenderVersion.getDescription());
        t.setVersion(t.getVersion() + 1);

        return tenderRepository.save(t);
    }

    public Tender setTenderStatus(Integer id, String status, String username) {
        if (!Objects.equals(status, TenderStatus.PUBLISHED.value) && !Objects.equals(status, TenderStatus.CLOSED.value)) {
            throw new NoSuchElementException("status");
        }
        Optional<Tender> optionalTender = tenderRepository.findById(id);
        if (!optionalTender.isPresent()) {
            throw new NoSuchElementException("tender");
        }
        Tender t = optionalTender.get();
        if (!Objects.equals(employeeService.findOrganizationResponsibleIdByUsername(username), t.getOrganizationId())) {
            throw new NoSuchElementException("user");
        }

        t.setStatus(status);

        return tenderRepository.save(t);
    }


}
