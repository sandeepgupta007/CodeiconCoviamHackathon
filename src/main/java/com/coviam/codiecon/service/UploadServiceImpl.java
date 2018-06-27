package com.coviam.codiecon.service;
import com.coviam.codiecon.Exceptions.CustomException;
import com.coviam.codiecon.dto.*;
import com.coviam.codiecon.email.*;
import com.coviam.codiecon.model.Admin;
import com.coviam.codiecon.model.Candidate;
import com.coviam.codiecon.model.Interviewer;
import com.coviam.codiecon.repository.AdminRepository;
import com.coviam.codiecon.repository.CandidateRepository;
import com.coviam.codiecon.repository.InterviewerRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UploadServiceImpl implements UploadService{

    private final String CANDIDATE_VERIFICATION="candidate_login_set_preference";
    private final String CANDIDATE_INTERVIEW="candidate_interview_timings";
    private final String INTERVIEWER_INTERVIEW="interviewer_interview_timing";
    private final String INTERVIEWER_VERIFICATION="interviewer_login_set_preference";

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private InterviewerRepository interviewerRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Value("${redirectLinkCandidate}")
    private String redirectLinkCandidate;

    @Value("${redirectLinkInterviewer}")
    private String redirectLinkInterviewer;

    @Value("${emailSender}")
    private String emailSender;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public void batchStoreCandidate(String email,List<Candidate> candidateList) {
        for (int i = 0; i < candidateList.size(); i++) {
            candidateRepository.save(candidateList.get(i));
        }
        List<CandidateDto> candidateDtoList = new ArrayList<>();

        for(int i=0;i<candidateList.size();i++){
            CandidateDto candidateDto = new CandidateDto();
            candidateDto.setEmail(candidateList.get(i).getEmail());
            candidateDto.setPreference(candidateList.get(i).getPreference());
            candidateDto.setPassword(candidateList.get(i).getPassword());
            candidateDto.setDay(candidateList.get(i).getDay());
            candidateDto.setName(candidateList.get(i).getName());
            candidateDtoList.add(candidateDto);
        }
        System.out.println(email);
        if(adminRepository.existsById(email)){
            Admin admin = adminRepository.findById(email).get();
            List<AlgoInputObject> algoInputObjectList = admin.getAlgoInputObjectList();
            AlgoInputObject algoInputObject = algoInputObjectList.get(admin.getAlgoInputObjectList().size()-1);
            algoInputObject.setCandidateDtoList(candidateDtoList);
            algoInputObjectList.set(admin.getAlgoInputObjectList().size()-1,algoInputObject);
            admin.setAlgoInputObjectList(algoInputObjectList);
            adminRepository.save(admin);
        }
    }

    public void uploadFileCandidate(String email,MultipartFile multipartFile) throws IOException {
        System.out.println("checking .. ");
        File file = convertMultiPartToFile(multipartFile);
        List<Candidate> candidateArrayList = new ArrayList<>();

        try (Reader reader = new FileReader(file);) {
            @SuppressWarnings("unchecked")
            CsvToBean<UploadDto> csvToBean = new CsvToBeanBuilder<UploadDto>(reader).withType(UploadDto.class)
                    .withIgnoreLeadingWhiteSpace(true).build();
            List<UploadDto> uploadDtos = csvToBean.parse();


            for (UploadDto uploadDto : uploadDtos) {
                System.out.println(uploadDto);
                Candidate candidate = new Candidate();
                candidate.setEmail(uploadDto.getEmail());
                candidate.setName(uploadDto.getName());
                candidate.setDay(-1);
                candidate.setPassword(encryptPassword());
                candidate.setPreference("NONE");
                candidateArrayList.add(candidate);
            }
            batchStoreCandidate(email,candidateArrayList);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public String encryptPassword(){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public void batchStoreInterview(String email,List<Interviewer> interviewerList) {
        for (int i = 0; i < interviewerList.size(); i++) {
            interviewerRepository.save(interviewerList.get(i));
        }
        List<InterviewerDto> interviewers = new ArrayList<>();
        for(int i=0;i<interviewerList.size();i++){
            InterviewerDto interviewer = new InterviewerDto();
            interviewer.setName(interviewerList.get(i).getName());
            interviewer.setAvailablityOfInterviewer(interviewerList.get(i).getAvailablityOfInterviewer());
            interviewer.setEmail(interviewerList.get(i).getEmail());
            interviewer.setPassword(interviewerList.get(i).getPassword());
            interviewers.add(interviewer);
        }

        if(adminRepository.existsById(email)){
            Admin admin = adminRepository.findById(email).get();
            List<AlgoInputObject> algoInputObjectList = admin.getAlgoInputObjectList();
            AlgoInputObject algoInputObject = algoInputObjectList.get(admin.getAlgoInputObjectList().size()-1);
            algoInputObject.setInterviewerDtoList(interviewers);
            algoInputObjectList.set(admin.getAlgoInputObjectList().size()-1,algoInputObject);
            admin.setAlgoInputObjectList(algoInputObjectList);
            adminRepository.save(admin);
        }
    }

    public void uploadFileInterview(String email,MultipartFile multipartFile) throws IOException {

        File file = convertMultiPartToFile(multipartFile);
        List<Interviewer> interviewerArrayList = new ArrayList<>();

        try (Reader reader = new FileReader(file);) {
            @SuppressWarnings("unchecked")
            CsvToBean<UploadDto> csvToBean = new CsvToBeanBuilder<UploadDto>(reader).withType(UploadDto.class)
                    .withIgnoreLeadingWhiteSpace(true).build();
            List<UploadDto> uploadDtos = csvToBean.parse();


            for (UploadDto uploadDto:uploadDtos) {
                Interviewer interviewer = new Interviewer();
                interviewer.setEmail(uploadDto.getEmail());
                interviewer.setName(uploadDto.getName());
                interviewer.setPassword(encryptPassword());
                interviewer.setAvailablityOfInterviewer(null);
                interviewerArrayList.add(interviewer);
            }
            batchStoreInterview(email,interviewerArrayList);
        }
    }

    public void SendEmails(String email) {
        MailSender mailSender = new MailSender();
        Optional<Admin> adminOpt=adminRepository.findByEmail(email);
        Admin admin=adminOpt.get();
        List<AlgoInputObject> inputObjectList= admin.getAlgoInputObjectList();
        int lastIndex=inputObjectList.size()-1;
        List<CandidateDto> candidateList= inputObjectList.get(lastIndex).getCandidateDtoList();
        for(CandidateDto candidate:candidateList){
            MailElements mailElements = new MailElements(candidate.getEmail(),"Response Mail - Interview");
            CandidateLoginAndSetPrefferedTiming candidateLoginAndSetPrefferedTiming = new CandidateLoginAndSetPrefferedTiming(candidate.getPassword(),redirectLinkCandidate);
            System.out.println(candidateLoginAndSetPrefferedTiming);
            try {
                sendEmail(mailElements,"candidate_login_set_preference",candidateLoginAndSetPrefferedTiming,null,null,null);
            } catch (CustomException e) {
                e.printStackTrace();
            }
        }
        List<InterviewerDto> interviewerList= inputObjectList.get(lastIndex).getInterviewerDtoList();
        for(InterviewerDto interviewer:interviewerList){
            MailElements mailElements = new MailElements(interviewer.getEmail(),"Interview-Timing Set Preference");
            InterviewerTiming interviewerTiming = new InterviewerTiming(redirectLinkInterviewer,interviewer.getPassword());
            try {
                sendEmail(mailElements,"interviewer_login_set_preference",null,null,null,interviewerTiming);
            } catch (CustomException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendEmail(MailElements mailObject, String mailType, CandidateLoginAndSetPrefferedTiming candidateLoginAndSetPrefferedTiming,
                          CandidateInterviewDetail candidateInterviewDetail, InterviewDetailsInterviewer interviewDetailsInterviewer,
                          InterviewerTiming interviewerTiming) throws CustomException {

        if(mailObject==null)
            throw new CustomException("MailElements not set");

        if(mailType==null)
            throw new CustomException("mailType is null");

        if(mailType!=CANDIDATE_VERIFICATION && mailType != CANDIDATE_INTERVIEW && mailType != INTERVIEWER_INTERVIEW && mailType != INTERVIEWER_VERIFICATION)
            throw new CustomException("mailType invalid");

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            String context = set_context(mailObject,mailType,candidateLoginAndSetPrefferedTiming, candidateInterviewDetail, interviewDetailsInterviewer,interviewerTiming);
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            mimeMessageHelper.setSubject(mailObject.getSubjectOfMail());
            mimeMessageHelper.setFrom(emailSender);
            mimeMessageHelper.setTo(mailObject.getEmail());
            mimeMessageHelper.setText(context, true);
            logger.info("Sending...");
            mailSender.send(mimeMessageHelper.getMimeMessage());
            logger.info("Done!");

        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
    }

    private String set_context(MailElements mailObject,String mailType,CandidateLoginAndSetPrefferedTiming candidateLoginAndVerification,
                               CandidateInterviewDetail candidateInterviewDetail, InterviewDetailsInterviewer interviewDetailsInterviewer,
                               InterviewerTiming interviewerTiming){
        String context = null;
        if(mailType==CANDIDATE_VERIFICATION){
            context = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Response Mail - Interview</title>\n" +
                    "    <style>\n" +
                    "        hr {width: 200px;\n" +
                    "        }\n" +
                    "        .main{\n" +
                    "            width:300px;\n" +
                    "            background-color:#A0B2DA;\n" +
                    "            text-align:center;\n" +
                    "            margin:30px;\n" +
                    "            font-family:sans-serif;\n"+
                    "            font-size:10px;\n"+
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <h3 align=\"center\">Application Approved for Interview </h2>\n" +
                    "    <center>\n" +
                    "    <div class =\"main\">\n" +
                    "        <br><div><h4>Login Details</h4><hr>UserId<br>"+mailObject.getEmail()+"<br><hr></div>\n" +
                    "        Login Password<br>"+candidateLoginAndVerification.getGeneratedPassword()+"<br><hr>\n" +
                    "        <hr><a href='"+candidateLoginAndVerification.getRedirectLink()+"'>visit here to go to Login</a><br>\n" +
                    "    <br></div>\n" +
                    "    </center>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>";
        }

        if(mailType==CANDIDATE_INTERVIEW){
            context = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Response Mail - Interview Timing</title>\n" +
                    "    <style>\n" +
                    "        hr {width: 200px;\n" +
                    "        }\n" +
                    "        .main{\n" +
                    "            width:300px;\n" +
                    "            background-color:#A0B2DA;\n" +
                    "            text-align:center;\n" +
                    "            margin:30px;\n" +
                    "            font-family:sans-serif;\n"+
                    "            font-size:10px;\n"+
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <h3 align=\"center\">Interview-Timing</h2>\n" +
                    "    <center>\n" +
                    "    <div class =\"main\">\n" +
                    "        <br><div><h4>Scheduled Interview</h4><hr>UserId<br>"+mailObject.getEmail()+"<br><hr></div>\n" +
                    "        <center><h4>Interview Timing</h4>:</center><br><hr>"+
                    "          <center>Start Time:   "+candidateInterviewDetail.getStartTime()+"</center><br>" +
                    "           <center>End Time:   "+candidateInterviewDetail.getEndTime()+"</center><br>"+
                    "           <center>Date:   "+candidateInterviewDetail.getInterviewDate()+"</center><br>"+
                    "        <br>\n" +
                    "    <br><h3>Please be on time for the interview</h3></div>\n" +
                    "    </center>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>";
        }

        if(mailType==INTERVIEWER_INTERVIEW){
            context = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Scheduled Interview Timing</title>\n" +
                    "    <style>\n" +
                    "        hr {width: 200px;\n" +
                    "        }\n" +
                    "        .main{\n" +
                    "            width:300px;\n" +
                    "            background-color:#A0B2DA;\n" +
                    "            text-align:center;\n" +
                    "            margin:30px;\n" +
                    "            font-family:sans-serif;\n"+
                    "            font-size:10px;\n"+
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <h3 align=\"center\">Interview Timings</h2>\n" +
                    "    <center>\n" +
                    "    <div class =\"main\">\n" ;

            context += "<table style=\"width:45%\">\n" +
                    "  <tr>\n" +
//                    "    <th>candidateName</th>\n" +
                    "    <th>candidateEmail</th> \n" +
                    "    <th>startTime</th>\n" +
                    "    <th>endTime</th>\n" +
                    "    <th>dateOfInterview</th>\n" +
                    "  </tr>";

            for( InterviewerCandidateMapping interviewerCandidateMapping:interviewDetailsInterviewer.getInterviewDetails()){
                context += "  <tr>\n" +
//                        "    <th>"+interviewerCandidateMapping.getCandidateName()+"</th>\n" +
                        "    <th>"+interviewerCandidateMapping.getCandidateEmail()+"</th>\n" +
                        "    <th>"+interviewerCandidateMapping.getStartTime()+"</th>\n" +
                        "    <th>"+interviewerCandidateMapping.getEndTime()+"</th>\n" +
                        "    <th>"+interviewerCandidateMapping.getDateOfInterview()+"</th>\n" +
                        "  </tr>";
            }

            //create table for interview timing dynamically
            context += "<br></div>\n" +
                    "</center>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>";
        }

        if(mailType==INTERVIEWER_VERIFICATION){
            context = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Set Preferred Interview Timings</title>\n" +
                    "    <style>\n" +
                    "        hr {width: 200px;\n" +
                    "        }\n" +
                    "        .main{\n" +
                    "            width:300px;\n" +
                    "            background-color:#A0B2DA;\n" +
                    "            text-align:center;\n" +
                    "            margin:30px;\n" +
                    "            font-family:sans-serif;\n"+
                    "            font-size:10px;\n"+
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <h3 align=\"center\">Set preferred timing For Interviews </h2>\n" +
                    "    <center>\n" +
                    "    <div class =\"main\">\n" +
                    "        <br><div><h4>Login Details</h4><hr>UserId<br>"+mailObject.getEmail()+"<br><hr></div>\n" +
                    "         Login Password<br>"+interviewerTiming.getPassword()+"<br><hr>\n" +
                    "        <hr><a href='"+interviewerTiming.getRedirectLink()+"'>Visit Link to login and set preferred timing for interviews</a><br>\n" +
                    "    <br></div>\n" +
                    "    </center>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>";
        }
        return context;
    }
}
