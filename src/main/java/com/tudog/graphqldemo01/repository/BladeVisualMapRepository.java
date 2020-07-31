package com.tudog.graphqldemo01.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.tudog.graphqldemo01.entity.BladeVisualMap;

public interface BladeVisualMapRepository extends EntityGraphJpaRepository<BladeVisualMap, Long> {
    
}