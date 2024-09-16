package io.codefresh.gradleexample.repository;

import io.codefresh.gradleexample.data.models.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    public Optional<Employee> findByUsername(String username);
}
