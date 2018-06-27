package com.coviam.codiecon.repository;

import com.coviam.codiecon.model.Interviewer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewerRepository extends MongoRepository<Interviewer, String> {
}
