package com.nnk.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.nnk.springboot.domain.DBUser;

@Repository
public interface DBUserRepository extends JpaRepository<DBUser, Integer>, JpaSpecificationExecutor<DBUser> {
    public DBUser findByUsername(String username);
}
