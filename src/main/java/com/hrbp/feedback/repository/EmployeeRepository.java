package com.hrbp.feedback.repository;

import com.hrbp.feedback.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	Optional<Employee> findById(Integer employeeId);

	@Query("FROM Employee WHERE buHead.id = :employeeId")
	List<Employee> findEmployeesByBuHeadId(Integer employeeId);

	@Query("FROM Employee WHERE manager.id = :employeeId")
	List<Employee> findEmployeesByManagerId(Integer employeeId);

}
