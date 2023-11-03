package com.nnk.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.nnk.springboot.domain.DBUser;

public interface DBUserRepository extends JpaRepository<DBUser, Integer>, JpaSpecificationExecutor<DBUser> {
    public DBUser findByUsername(String username);
}
