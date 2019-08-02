package com.amazonaws.lambda.demo;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;


public class EmailSender {
	

    
    
    public void sendEmail(String alertlevel) {
    	
        final String FROM = "Alain.DelRosario@sunlife.com";  // Replace with your "From" address. This address must be verified.
        final String TO = "Alain.DelRosario@sunlife.com"; // Replace with a "To" address. If you have not yet requested
                                                          // production access, this address must be verified.
        String BODY = "Data: A" + alertlevel + " was reported from Helpdesk. Please do the necessary action.";
        String SUBJECT = "Alert Level: " + alertlevel;
    	
    	
        Destination destination = new Destination().withToAddresses(new String[]{TO});

        // Create the subject and body of the message.
        Content subject = new Content().withData(SUBJECT);
        Content textBody = new Content().withData(BODY);
        Body body = new Body().withText(textBody);

        // Create a message with the specified subject and body.
        Message message = new Message().withSubject(subject).withBody(body);

        // Assemble the email.
        SendEmailRequest request = new SendEmailRequest().withSource(FROM).withDestination(destination).withMessage(message);

        try {
            System.out.println("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");

            /*
             * The ProfileCredentialsProvider will return your [default]
             * credential profile by reading from the credentials file located at
             * (C:\\Users\\IJ28\\.aws\\credentials).
             *
             * TransferManager manages a pool of threads, so we create a
             * single instance and share it throughout our application.
             */
            
            /*
            ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
            try {
                credentialsProvider.getCredentials();
            } catch (Exception e) {
                throw new AmazonClientException(
                        "Cannot load the credentials from the credential profiles file. " +
                        "Please make sure that your credentials file is at the correct " +
                        "location (C:\\Users\\IJ28\\.aws\\credentials), and is in valid format.",
                        e);
            }
            */

            // Instantiate an Amazon SES client, which will make the service call with the supplied AWS credentials.
            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
                //.withCredentials(credentialsProvider)
                .withRegion("us-east-1")
                .build();

            // Send the email.
            client.sendEmail(request);
            System.out.println("******************** Email sent! ******************");

        } catch (Exception ex) {
            System.out.println("############ The email was not sent.");
            System.out.println("############ Error message: " + ex.getMessage());
        }
    }
    
    
    

}
