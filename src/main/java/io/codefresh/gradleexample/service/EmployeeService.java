package io.codefresh.gradleexample.service;

import io.codefresh.gradleexample.data.models.Employee;
import io.codefresh.gradleexample.repository.EmployeeRepository;
import io.codefresh.gradleexample.repository.OrganizationRepository;
import io.codefresh.gradleexample.repository.OrganizationResponsibleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final OrganizationRepository organizationRepository;
    private final OrganizationResponsibleRepository organizationResponsibleRepository;

    public Integer findUserByUsername(String username) {
        Optional<Employee> optionalEmployee = employeeRepository.findByUsername(username);
        if (!optionalEmployee.isPresent()) {
            return null;
        }
        Employee employee = optionalEmployee.get();

        return employee.getId();
    }

    public Integer findOrganizationResponsibleIdByUsername(String username) {

        Integer userId = findUserByUsername(username);
        if (userId == null) {
            return null;
        }

        return organizationResponsibleRepository.findByUserId(userId);
    }

    public Boolean findOrganizationById(Integer id) {
        return organizationRepository.existsById(id);
    }

    public Integer countOrganizationResponsible(Integer id) {
        return organizationResponsibleRepository.countByOrganizationId(id);
    }
}
