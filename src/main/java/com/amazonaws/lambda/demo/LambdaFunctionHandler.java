package com.amazonaws.lambda.demo;

import java.util.Map;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class LambdaFunctionHandler implements RequestHandler<Map<String, Object>, Object> {

    @Override
    public Object handleRequest(Map<String, Object> input, Context context) {
        context.getLogger().log("Input: " + input);
        
        
        Map<String, Object> botMap = (Map<String, Object>)input.get("bot");
        String botName = (String) botMap.get("name");
        
        Map<String, Object> currentIntentMap = (Map<String, Object>)input.get("currentIntent");
        String intentName = (String) currentIntentMap.get("name");
        
        Map<String, Object> slotsMap = (Map<String, Object>)currentIntentMap.get("slots");
        String alertLevel = (String) slotsMap.get("AlertLevel");
        
        
        context.getLogger().log(">>>>>>>>>>>>>>>>>REQUEST CAME FROM BOT = " + botName);
        context.getLogger().log(">>>>>>>>>>>>>>>>>REQUEST CAME FROM INTENT = " + intentName);
        context.getLogger().log(">>>>>>>>>>>>>>>>>REQUEST CAME FROM ALERT = " + alertLevel);
        

		String myQueueUrl = "ajdel-queue";
        AmazonSQS sqs = AmazonSQSClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .build();
		//FOR SENDING MESSAGES
        context.getLogger().log("###############################Sending a message to MyQueue.\n");
        sqs.sendMessage(new SendMessageRequest(myQueueUrl, alertLevel));
        
        
        
        
        AlertResponse alertResponse = new AlertResponse();
        Message message = new Message();
        message.setContentType("PlainText");
        message.setContent("Thank you for your report, I have started the configured alerting procedures for a " + alertLevel + " Alert.");
        
        DialogAction dialogAction = new DialogAction();
        dialogAction.setType("Close");
        dialogAction.setFulfillmentState("Fulfilled");
        dialogAction.setMessage(message);
        
        alertResponse.setDialogAction(dialogAction);
        
        return alertResponse;
    }

}
