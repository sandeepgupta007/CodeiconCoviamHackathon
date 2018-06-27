package com.coviam.codiecon.email;


import com.coviam.codiecon.Exceptions.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Component("MailSender")
public class MailSender {

    private final String CANDIDATE_VERIFICATION="candidate_login_set_preference";
    private final String CANDIDATE_INTERVIEW="candidate_interview_timings";
    private final String INTERVIEWER_INTERVIEW="interviewer_interview_timing";
    private final String INTERVIEWER_VERIFICATION="interviewer_login_set_preference";

    @Value("${emailSender}")
    private String emailSender;

    @Autowired
    JavaMailSender mailSender;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public MailSender() {
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
                    "        <hr><a href="+candidateLoginAndVerification.getRedirectLink()+">Visit Link to login</a><br>\n" +
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
                            "    <th>candidateName</th>\n" +
                            "    <th>candidateEmail</th> \n" +
                            "    <th>startTime</th>\n" +
                            "    <th>endTime</th>\n" +
                            "    <th>dateOfInterview</th>\n" +
                            "  </tr>";

                    for( InterviewerCandidateMapping interviewerCandidateMapping:interviewDetailsInterviewer.getInterviewDetails()){
                        context += "  <tr>\n" +
                                "    <th>"+interviewerCandidateMapping.getCandidateName()+"</th>\n" +
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
                    "    <h3 align=\"center\">Application Approved for Interview </h2>\n" +
                    "    <center>\n" +
                    "    <div class =\"main\">\n" +
                    "        <br><div><h4>Login Details</h4><hr>UserId<br>"+mailObject.getEmail()+"<br><hr></div>\n" +
                    "        <hr><a href="+interviewerTiming.getRedirectLink()+">Visit Link to login and set preferred timing for interviews</a><br>\n" +
                    "    <br></div>\n" +
                    "    </center>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>";
        }
        
        return context;
    }
}