package com.vaidh.customer.service;

import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vaidh.customer.dto.PushNotificationDTO;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;

@Service
public class FirebaseNotificationServiceImpl implements FirebaseNotificationService{
    private Logger logger = LoggerFactory.getLogger(FirebaseNotificationService.class);

    @Override
    public void sendMessage(PushNotificationDTO request) throws InterruptedException, ExecutionException {
        Message message = getPreconfiguredMessageWithData(request);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
        logger.info("Sent message with data. Topic: " + request.getTopic() + ", " + " msg "+jsonOutput + " response :" + response);
    }

    private Message getPreconfiguredMessageWithData(PushNotificationDTO request) {
        return getPreconfiguredMessageBuilder(request).setTopic(request.getTopic()).build();
    }
    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationDTO request) {
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());
        return Message.builder().setAndroidConfig(androidConfig).setApnsConfig(apnsConfig).setNotification(
                        new Notification(request.getTitle(), request.getMessage()));
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setTag(topic).build()).build();
    }
    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

/*    private Map<String, String> getSamplePayloadData() {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("messageId", "msgid");
        pushData.put("text", "txt");
        pushData.put("user", "chrust");
        return pushData;
    }*/
}
