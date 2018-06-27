package com.coviam.codiecon.service;

import com.coviam.codiecon.model.Candidate;
import com.coviam.codiecon.model.Interviewer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UploadService {
    void batchStoreCandidate(String email,List<Candidate> candidateList);
    void uploadFileCandidate(String email,MultipartFile multipartFile) throws IOException;
    void uploadFileInterview(String email,MultipartFile multipartFile) throws IOException;
    void batchStoreInterview(String email,List<Interviewer> interviewerList);
    void SendEmails(String email);
}
