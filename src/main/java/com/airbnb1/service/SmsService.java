package com.airbnb1.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phone_number}")
    private String fromPhoneNumber;

    // Constructor to initialize Twilio SDK with credentials
    public SmsService(
            @Value("${twilio.accountSid}") String accountSid,
            @Value("${twilio.authToken}") String authToken) {
        this.accountSid = accountSid;
        this.authToken = authToken;
        Twilio.init(accountSid, authToken); // Initialize Twilio SDK
    }

    public void sendSms(String toPhoneNumber, String messageBody) {

       Twilio.init(accountSid,authToken); //Twilio.init to perform login operations

        Message message = Message.creator(
                new PhoneNumber(toPhoneNumber),//object of TophoneNUmber and
                new PhoneNumber(fromPhoneNumber),//object of fromPhoneNumber
                messageBody
                ).create();

        System.out.println("Sent SMS message successfully!" + message.getSid());
    }
}
