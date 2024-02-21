package com.hrbp.feedback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrbp.feedback.model.entity.ActionItem;

@Repository
public interface ActionItemRepository extends JpaRepository<ActionItem, Integer> {

}
