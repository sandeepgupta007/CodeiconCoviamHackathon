package com.coviam.codiecon.controller;

import com.coviam.codiecon.dto.*;
import com.coviam.codiecon.service.SchedulerService;
import com.coviam.codiecon.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

/**
 * @author: Sandeep Gupta
 * */
@CrossOrigin
@RestController
@RequestMapping("schedule")
public class SchedulerController {

    @Autowired
    SchedulerService schedulerService;

    @Autowired
    UploadService uploadService;
    /**
     *  to create the admin.
     * */
    @RequestMapping("/create-admin")
    public ResponseDto<?> adminAuth(@RequestBody AdminDto adminDto) {
        Boolean response = schedulerService.createAdmin(adminDto);
        return new ResponseDto<Boolean>(response);
    }

    @RequestMapping("/valid-admin")
    public ResponseDto<?> adminValid(@RequestBody AdminDto adminDto){
        Boolean response = schedulerService.checkAdmin(adminDto);
        return new ResponseDto<Boolean>(response);
    }

    @RequestMapping("input-all")
    public ResponseDto<?> inputAllCandidate(@RequestBody InputAllObject inputAllObject){
        Boolean a = schedulerService.inputAll(inputAllObject);
        return new ResponseDto<Boolean>(a);
    }

    @RequestMapping("/candidate-preference")
    public Boolean candidate(@RequestParam String email,@RequestBody CandidatePreferenceDto candidatePreferenceDto) {
        return schedulerService.candidatePreference(email,candidatePreferenceDto);
    }

    @RequestMapping("/interviewer")
    public boolean interviewer(@RequestParam String email,@RequestBody List<String> preferenceDtos) {
        return schedulerService.interviewerPreference(email,preferenceDtos);
    }

    @RequestMapping("/get-all-schedule")
    public ResponseDto<?> getAllSchedule(@RequestParam String email){
        List<AlgoInputObject> algoInputObjectList = schedulerService.getAllAlgoinputObject(email);
        if(algoInputObjectList == null){
            return new ResponseDto<>(false);
        }
        return new ResponseDto<>(algoInputObjectList);
    }

    @RequestMapping("get-schedule-by-id")
    public ResponseDto<?> getSchedule(@RequestParam String email,@RequestParam String index){
        AlgoInputObject algoInputObject = schedulerService.getAlgoInputObjectById(email,index);
        if(algoInputObject == null){
          return new ResponseDto<>(false);
        }
        return new ResponseDto<>(algoInputObject);
    }

    @RequestMapping("is-visible-generate-button")
    public ResponseDto<?> isVisibleGenerateButton(@RequestParam String email,@RequestParam String index){
        Boolean a = schedulerService.isVisibleGenerate(email,index);
        return new ResponseDto<>(a);
    }

    @RequestMapping("/interview-scheduling")
    public ResponseDto<?> interviewScheduling(@RequestParam String email,@RequestParam Integer index){
        String result = schedulerService.runPythonScript(email,index);
        return new ResponseDto<String>(result);
    }

    @RequestMapping(value = "/candidate-auth", method = RequestMethod.POST)
    public ResponseDto<?> candidateAuth(@RequestBody CandidateDto candidate) {
        String result = schedulerService.checkCandidateAuthentication(candidate.getEmail() , candidate.getPassword());
        return new ResponseDto<String>(result);
    }

    @RequestMapping(value = "/interviewer-auth", method = RequestMethod.POST)
    public ResponseDto<?> interviweerAuth(@RequestBody InterviewerDto interview){
        String result = schedulerService.checkInterviewerAuthentication(interview.getEmail(), interview.getPassword());
        return new ResponseDto<String>(result);
    }

    @RequestMapping("/admin-input")
    public String adminInput() {
        return("");
    }


    @RequestMapping(value = "/create-candidate", method = RequestMethod.POST)
    public ResponseDto<?> createCandidate(@RequestBody CandidateDto candidateDto ) {
        Boolean createCandidate = schedulerService.createCandidate(candidateDto);
        return new ResponseDto<Boolean>(createCandidate);
    }

    @RequestMapping(value = "/is-candidate", method = RequestMethod.POST)
    public ResponseDto<?> isCandidate(@RequestBody CandidateDto candidateDto ) {
        Boolean isValid = schedulerService.isValidCandidate(candidateDto);
        return new ResponseDto<Boolean>(isValid);
    }

    @RequestMapping(value = "/create-interviewer", method = RequestMethod.POST)
    public ResponseDto<?> createInterviewer(@RequestBody  InterviewerDto interviewerDto){
        Boolean createInterViewer = schedulerService.createInterviewer(interviewerDto);
        return new ResponseDto<Boolean>(createInterViewer);
    }

    @RequestMapping(value = "/is-interviewer", method = RequestMethod.POST)
    public ResponseDto<?> isInterviewer(@RequestBody  InterviewerDto interviewerDto){
        Boolean isValid = schedulerService.isValidInterviewer(interviewerDto);
        return new ResponseDto<Boolean>(isValid);
    }

    @RequestMapping(value = "/schedule-basic-details",method = RequestMethod.GET)
    public ResponseDto<?> scheduleBasicDetails(@RequestParam String email,@RequestParam String startDate,@RequestParam String numberOfDays,@RequestParam String interviewDuration){
        Boolean a = schedulerService.basicScheduleDetails(email,startDate,numberOfDays,interviewDuration);
        return new ResponseDto<Boolean>(a);
    }

    @RequestMapping(value = "/upload-candidate/{email}", method = RequestMethod.POST)
    public String uploadCandidate(@PathVariable String email,@RequestParam(value = "upload_candidate_file") MultipartFile multiPartFile) throws IOException {
        uploadService.uploadFileCandidate(email,multiPartFile);
        return ("Uploaded Candidate file successfully");
    }

    @RequestMapping(value = "/upload-interview/{email}", method = RequestMethod.POST)
    public String uploadInterview(@PathVariable String email,@RequestParam(value = "upload_interviewer_file") MultipartFile multiPartFile) throws IOException {
        uploadService.uploadFileInterview(email,multiPartFile);
        return ("uploaded Interviewer file successfully");
    }
    @RequestMapping(value="/send-emails-before",method = RequestMethod.GET)
    public ResponseDto<?> sendEmailsBefore(@RequestParam String email){
        uploadService.SendEmails(email);
        return new ResponseDto<String>("Emails Sent");
     }
    @RequestMapping(value = "/get-output-by-id",method = RequestMethod.GET)
    public ResponseDto<?> getOutputById(@RequestParam String email,@RequestParam Integer index){
        return new ResponseDto<>(schedulerService.getAlgoOutputObject(email,index));
    }
}
