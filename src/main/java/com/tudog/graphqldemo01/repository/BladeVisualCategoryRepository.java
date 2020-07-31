package com.tudog.graphqldemo01.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.tudog.graphqldemo01.entity.BladeVisualCategory;

public interface BladeVisualCategoryRepository extends EntityGraphJpaRepository<BladeVisualCategory, Long> {
    
}