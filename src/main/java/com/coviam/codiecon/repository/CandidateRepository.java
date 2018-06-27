package com.coviam.codiecon.repository;

import com.coviam.codiecon.model.Candidate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends MongoRepository<Candidate, String> {

    Candidate findByEmail(String email);
}
