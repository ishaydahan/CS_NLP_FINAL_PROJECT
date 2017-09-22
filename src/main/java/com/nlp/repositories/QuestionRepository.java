package com.nlp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nlp.models.Question;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {

}

