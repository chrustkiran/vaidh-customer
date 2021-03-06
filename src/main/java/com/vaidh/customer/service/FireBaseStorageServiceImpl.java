package com.vaidh.customer.service;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vaidh.customer.message.FireBaseMessage;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class FireBaseStorageServiceImpl implements FireBaseStorageService {
    Logger logger = Logger.getLogger("FireBaseStorageServiceImpl");

    @Override
    public void sendMessage(String key, FireBaseMessage message) {
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseDatabase.child(key).setValue(message, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
               logger.info("completed adding data :: time :: " + message.getCreatedTime() + " :: key :: "
                       + key);
            }
        });
    }

    @Override
    public void sendMessage(String key, String message) {
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseDatabase.child(key).setValue(message, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                logger.info("completed adding data :: message :: " + message);
            }
        });
    }
}
