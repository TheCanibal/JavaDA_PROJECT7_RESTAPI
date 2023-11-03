package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.DBUser;
import com.nnk.springboot.repositories.DBUserRepository;

@Service
public class DBUserService {

    @Autowired
    private DBUserRepository userRepository;

    public List<DBUser> findAll() {
	return userRepository.findAll();
    }

    public DBUser save(DBUser user) {
	return userRepository.save(user);
    }

    public Optional<DBUser> findById(int id) {
	return userRepository.findById(id);
    }

    public void delete(DBUser user) {
	userRepository.delete(user);
    }

    public DBUser findByUsername(String username) {
	return userRepository.findByUsername(username);
    }

}
