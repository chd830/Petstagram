package com.petstagram.test;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TestRepository extends MongoRepository<Test, String> {
    List<Test> findByuserName(String userName);
}
