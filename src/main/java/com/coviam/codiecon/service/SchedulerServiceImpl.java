package com.coviam.codiecon.service;


import com.coviam.codiecon.dto.*;
import com.coviam.codiecon.model.Admin;
import com.coviam.codiecon.model.Candidate;
import com.coviam.codiecon.model.CandidateInterviewerMap;
import com.coviam.codiecon.model.Interviewer;
import com.coviam.codiecon.repository.AdminRepository;
import com.coviam.codiecon.repository.CandidateInterviewMapRepository;
import com.coviam.codiecon.repository.CandidateRepository;
import com.coviam.codiecon.repository.InterviewerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Optional;

/**
 * @author: Sandeep Gupta
 * */
@Service
public class SchedulerServiceImpl implements SchedulerService{

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private InterviewerRepository interviewerRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Boolean createAdmin(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setEmail(adminDto.getEmail());
        admin.setName(adminDto.getName());
        admin.setPassword(adminDto.getPassword());
        admin.setAlgoInputObjectList(null);
        admin.setAlgoOutputObjectList(null);
        if(!adminRepository.existsById(admin.getEmail())){
            adminRepository.save(admin);
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkAdmin(AdminDto adminDto) {
        if(adminRepository.existsById(adminDto.getEmail())){
            return true;
        }
        return false;
    }


    @Override
    public Boolean inputAll(InputAllObject inputAllObject) {
        if(adminRepository.existsById(inputAllObject.getEmail())){
            Admin admin = adminRepository.findById(inputAllObject.getEmail()).get();
            if(admin.getAlgoInputObjectList() == null){
                List<AlgoInputObject> algoInputObjectList = new ArrayList<>();
                algoInputObjectList.add(inputAllObject.getAlgoInputObject());
                admin.setAlgoInputObjectList(algoInputObjectList);
            }
            else{
                List<AlgoInputObject> algoInputObjectList = new ArrayList<>();
                algoInputObjectList.add(inputAllObject.getAlgoInputObject());
                admin.setAlgoInputObjectList(algoInputObjectList);
            }
            adminRepository.save(admin);
            return true;
        }
        return false;
    }

    @Override
    public Boolean candidatePreference(String email, CandidatePreferenceDto candidatePreferenceDto){
        if(candidateRepository.existsById(email)){
            Candidate candidate = candidateRepository.findById(email).get();
            candidate.setDay(candidatePreferenceDto.getDay());
            String candidatePreference = String.valueOf(candidatePreferenceDto.getPreference());
            candidate.setPreference(candidatePreference);
            candidateRepository.save(candidate);
        }
        return true;
    }

    @Override
    public Boolean interviewerPreference(String email, List<String> preferenceDtos) {
        if(interviewerRepository.existsById(email)){
            Interviewer interviewer =  interviewerRepository.findById(email).get();
            List<String> stringList = new ArrayList<>();
            for(int i=0;i<preferenceDtos.size();i++){
                for(int j=0;j<preferenceDtos.get(i).length();j++){
                    if(preferenceDtos.get(i).charAt(j) == 1){
                        stringList.add(String.valueOf(i));
                        stringList.add(String.valueOf(j+1));
                    }
                }
            }
            interviewer.setAvailablityOfInterviewer(stringList);
            interviewerRepository.save(interviewer);
            return true;
        }
        return false;
    }

    @Override
    public String runPythonScript(String email,Integer index){
        Boolean isCreatedInputFile = generateInputFile(email,index);
        if(isCreatedInputFile == true){
            String s = getOutput();
            Boolean isOutputFileCreated = readOutputfile(email,index);
            if(isOutputFileCreated) {
                return "True";
            }
        }
        return "False";
    }

    public Boolean readOutputfile(String email,Integer index) {
        File file = new File("/Users/sandeepgupta/Documents/codeicon/codiecon/src/main/resources/out");
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            AlgoOutputObject algoOutputObject = new AlgoOutputObject();
            List<InterviewCandidateMapped> interviewCandidateMappeds = new ArrayList<>();
            String st;
            AlgoInputObject algoInputObjectList = adminRepository.findById(email).get().getAlgoInputObjectList().get(index);
            while ((st = br.readLine()) != null){
                String w[]=st.split(" ");
                InterviewCandidateMapped interviewCandidateMapped = new InterviewCandidateMapped();
                interviewCandidateMapped.setCandidate(algoInputObjectList.getCandidateDtoList().get(Integer.valueOf(w[3])).getEmail());
                interviewCandidateMapped.setDay(Integer.valueOf(w[0]));
                interviewCandidateMapped.setInterviewer(algoInputObjectList.getInterviewerDtoList().get(Integer.valueOf(w[2])).getEmail());
                interviewCandidateMapped.setTimeSlot(Integer.valueOf(w[1]));
                interviewCandidateMappeds.add(interviewCandidateMapped);
            }
            algoOutputObject.setInterviewCandidateMappedList(interviewCandidateMappeds);
            Admin admin = adminRepository.findById(email).get();
            List<AlgoOutputObject> algoOutputObjectList =  admin.getAlgoOutputObjectList();
            algoOutputObjectList.set(index,algoOutputObject);
            adminRepository.save(admin);
            return true;
        }
        catch (IOException e){
            System.out.println(e);
        }
        return false;
    }

    public Boolean generateInputFile(String email,Integer index){
        try {
            FileWriter fw = new FileWriter("/Users/sandeepgupta/Documents/codeicon/codiecon/src/main/resources/in");
            if(adminRepository.existsById(email)){
                Admin admin = adminRepository.findById(email).get();
                List<AlgoInputObject> algoInputObjectList = admin.getAlgoInputObjectList();
                if(algoInputObjectList != null) {
                    AlgoInputObject algoInputObject = algoInputObjectList.get(index);
                    fw.write(algoInputObject.getNumberOfDays().toString());
                    fw.write('\n');
                    fw.write(algoInputObject.getInterviewDuration().toString());
                    fw.write('\n');
                    fw.write(String.valueOf(algoInputObject.getCandidateDtoList().size()));
                    fw.write('\n');
                    for (int i = 0; i < algoInputObject.getCandidateDtoList().size(); i++) {
                        String preference = algoInputObject.getCandidateDtoList().get(i).getPreference();
                        fw.write(preference.charAt(0));
                        fw.write(' ');
                        fw.write(preference.charAt(1));
                        fw.write(' ');
                        fw.write(preference.charAt(2));
                        fw.write(' ');
                        fw.write(preference.charAt(3));
                        fw.write(' ');
                        fw.write(String.valueOf(algoInputObject.getNumberOfDays()));
                        fw.write('\n');
                    }
                    fw.write(String.valueOf(algoInputObject.getInterviewerDtoList().size()));
                    fw.write('\n');
                    for (int i = 0; i < algoInputObject.getInterviewerDtoList().size(); i++) {
                        fw.write(String.valueOf(algoInputObject.getInterviewerDtoList().get(i).getAvailablityOfInterviewer().size()));
                        fw.write(' ');
                        for (int j = 0; j < algoInputObject.getInterviewerDtoList().get(i).getAvailablityOfInterviewer().size(); j++) {
                            fw.write(algoInputObject.getInterviewerDtoList().get(i).getAvailablityOfInterviewer().get(j));
                            fw.write(' ');
                        }
                        fw.write('\n');
                    }
                    fw.close();
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getOutput() {
        String s = null;
        String outputString = null;
        try {

            Process p = Runtime.getRuntime().exec("python3 /Users/sandeepgupta/Documents/codeicon/codiecon/src/main/resources/interviewScheduling.py");
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null){
//              System.out.println(s);
                outputString += s;
            }
            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
        return outputString;
    }

    public void schedule() {

//        AlgoInputObject algoInputObject = new AlgoInputObject();
//        algoInputObject.setNumberOfInterviewers(((int) interviewerRepository.count()));
//        algoInputObject.setNumberOfCandidates((int) candidateRepository.count());
//
//        List<String> candidatePreferences = new ArrayList<>();
//        List<String> interviewerPreferences = new ArrayList<>();
//
//        List<Candidate> candidates = candidateRepository.findAll();
//        for( Candidate candidate : candidates){
//            candidatePreferences.add(candidate.getPreference());
//        }
//        algoInputObject.setCandidatePreferences(candidatePreferences);
//
//        List<Interviewer> interviewers = interviewerRepository.findAll();
//        for( Interviewer interviewer : interviewers){
//            interviewerPreferences.add(interviewer.getPreference());
//        }
//
//        algoInputObject.setNumberOfDays(NUMBER_OF_DAYS);
//        algoInputObject.setInterviewDuration(INTERVIEW_DURATION);
//
//        //call python function and get list of CandidateInterviewerMap
//
//        List<CandidateInterviewerMap> interviewList = new ArrayList<>();
//
//        for(CandidateInterviewerMap candidateInterviewerMap : interviewList){
//            candidateInterviewMapRepository.save(candidateInterviewerMap);
//        }
    }

    @Override
    public String checkCandidateAuthentication(String email, String pass) {
        Candidate candidateDetails = candidateRepository.findByEmail(email);
        if(null != candidateDetails){
            if(pass.equals(candidateDetails.getPassword())) {
                return email;
            }else{
                return "User Not Found";
            }
        }
        return "User Not Found";
    }

    @Override
    public String checkInterviewerAuthentication(String email, String pass) {
        Interviewer interviewerDetails = interviewerRepository.findById(email).get();
        if (null != interviewerDetails){
            if(pass.equals(interviewerDetails.getPassword())){
                return email;
            }else{
                return "User Not Found";
            }
        }
        return "User Not Found";
    }

    @Override
    public Boolean createCandidate(CandidateDto candidateDto) {
        Candidate candidate = new Candidate();
        BeanUtils.copyProperties(candidateDto, candidate);
        candidateRepository.save(candidate);
        return true;
    }

    @Override
    public AlgoOutputObject getAlgoOutputObject(String email, Integer index) {
        if(adminRepository.existsById(email)){
            return adminRepository.findById(email).get().getAlgoOutputObjectList().get(index);
        }
        return null;
    }

    @Override
    public List<AlgoInputObject> getAllAlgoinputObject(String email) {
        if(adminRepository.existsById(email)){
            return adminRepository.findById(email).get().getAlgoInputObjectList();
        }
        return null;
    }

    @Override
    public AlgoInputObject getAlgoInputObjectById(String email, String index) {
        if(adminRepository.existsById(email)){
            if(adminRepository.findById(email).get().getAlgoInputObjectList().size() > Integer.valueOf(index)){
                Admin admin = adminRepository.findById(email).get();
                AlgoInputObject algoInputObject =  admin.getAlgoInputObjectList().get(Integer.valueOf(index));
                List<CandidateDto> candidateDtoList = algoInputObject.getCandidateDtoList();
                for(int i=0;i<candidateDtoList.size();i++){
                    if(candidateDtoList.get(i).getDay() == -1){
                        if(candidateRepository.findById(candidateDtoList.get(i).getEmail()).get().getDay() != -1){
                            candidateDtoList.get(i).setDay(candidateRepository.findById(candidateDtoList.get(i).getEmail()).get().getDay());
                        }
                    }
                }

                algoInputObject.setCandidateDtoList(candidateDtoList);

                List<InterviewerDto> interviewerDtoList = algoInputObject.getInterviewerDtoList();
                for(int i=0;i<interviewerDtoList.size();i++){
                    if(interviewerDtoList.get(i).getAvailablityOfInterviewer() == null){
                        if(interviewerRepository.findById(interviewerDtoList.get(i).getEmail()).get().getAvailablityOfInterviewer() != null){
                            interviewerDtoList.get(i).setAvailablityOfInterviewer(interviewerRepository.findById(interviewerDtoList.get(i).getEmail()).get().getAvailablityOfInterviewer());
                        }
                    }
                }
                algoInputObject.setInterviewerDtoList(interviewerDtoList);

                List<AlgoInputObject> algoInputObjectList = admin.getAlgoInputObjectList();
                algoInputObjectList.set(Integer.valueOf(index),algoInputObject);
                admin.setAlgoInputObjectList(algoInputObjectList);
                adminRepository.save(admin);
                return algoInputObject;
            }
            return null;
        }
        return null;
    }

    @Override
    public Boolean isVisibleGenerate(String email, String index) {
        if(adminRepository.existsById(email)){
            if(adminRepository.findById(email).get().getAlgoOutputObjectList().get(Integer.valueOf(index)) != null){
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean isValidCandidate(CandidateDto candidateDto) {
        if(candidateRepository.existsById(candidateDto.getEmail())){
            return true;
        }
        return false;
    }

    @Override
    public Boolean isValidInterviewer(InterviewerDto interviewerDto){
        if(interviewerRepository.existsById(interviewerDto.getEmail())){
            return true;
        }
        return false;
    }

    @Override
    public Boolean basicScheduleDetails(String email, String startDate, String numberOfDays, String interviewDuration) {
        if(adminRepository.existsById(email)){
            AlgoInputObject algoInputObject = new AlgoInputObject();
            try {
                algoInputObject.setStartDate(new SimpleDateFormat("dd-MM-yyyy").parse(startDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            algoInputObject.setNumberOfDays(Integer.valueOf(numberOfDays));
            algoInputObject.setInterviewDuration(Integer.valueOf(interviewDuration));
            algoInputObject.setInterviewerDtoList(null);
            algoInputObject.setCandidateDtoList(null);
            Admin admin = new Admin();
            admin = adminRepository.findById(email).get();
            List<AlgoInputObject> algoInputObjectList = admin.getAlgoInputObjectList();
            if(algoInputObjectList == null){

                List<AlgoInputObject> algoInputObjectList1 = new ArrayList<>();
                algoInputObjectList1.add(algoInputObject);

                List<AlgoOutputObject> algoOutputObjectList1 = new ArrayList<>();
                algoOutputObjectList1.add(null);

                admin.setAlgoInputObjectList(algoInputObjectList1);
                admin.setAlgoOutputObjectList(algoOutputObjectList1);
            }
            else{
                algoInputObjectList.add(algoInputObject);
                List<AlgoOutputObject> algoOutputObjectList = admin.getAlgoOutputObjectList();
                algoOutputObjectList.add(null);
                admin.setAlgoOutputObjectList(algoOutputObjectList);
                admin.setAlgoInputObjectList(algoInputObjectList);
            }
            adminRepository.save(admin);
            return true;
        }
        return false;
    }

    @Override
    public Boolean createInterviewer(InterviewerDto interviewerDto){
        Interviewer interviewer = new Interviewer();
        BeanUtils.copyProperties(interviewerDto, interviewer);
        interviewerRepository.save(interviewer);
        return true;
    }
}
