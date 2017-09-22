package com.nlp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nlp.models.Test;

@Repository
public interface TestRepository extends MongoRepository<Test, String> {

}