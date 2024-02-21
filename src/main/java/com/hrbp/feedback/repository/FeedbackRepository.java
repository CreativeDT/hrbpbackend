package com.hrbp.feedback.repository;

import com.hrbp.feedback.model.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

	List<Feedback> findByCreatorId(Integer creatorId);

	List<Feedback> findByEmployeeId(Integer employeeId);


}
