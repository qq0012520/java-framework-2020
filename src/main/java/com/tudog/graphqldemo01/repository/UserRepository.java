package com.tudog.graphqldemo01.repository;

import java.util.Optional;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.tudog.graphqldemo01.entity.User;

public interface UserRepository extends EntityGraphJpaRepository<User,Long>{
    Optional<User> findByAccount(String account);
}