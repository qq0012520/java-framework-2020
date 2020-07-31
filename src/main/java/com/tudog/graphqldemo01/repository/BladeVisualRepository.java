package com.tudog.graphqldemo01.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.tudog.graphqldemo01.entity.BladeVisual;
import com.tudog.graphqldemo01.entity.BladeVisualCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BladeVisualRepository extends EntityGraphJpaRepository<BladeVisual, Long> {
    Page<BladeVisual> findByCategory(BladeVisualCategory category,Pageable pageable);
}