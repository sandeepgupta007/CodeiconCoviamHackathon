package com.coviam.codiecon.repository;

import com.coviam.codiecon.model.CandidateInterviewerMap;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateInterviewMapRepository extends MongoRepository<CandidateInterviewerMap,String> {

}
