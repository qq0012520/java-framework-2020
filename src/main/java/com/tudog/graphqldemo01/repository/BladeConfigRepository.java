package com.tudog.graphqldemo01.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.tudog.graphqldemo01.entity.BladeVisualConfig;

public interface BladeConfigRepository extends EntityGraphJpaRepository<BladeVisualConfig, Long> {
    
}