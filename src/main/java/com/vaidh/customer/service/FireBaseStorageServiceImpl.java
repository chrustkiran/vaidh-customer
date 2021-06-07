package com.vaidh.customer.service;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.stereotype.Service;

@Service
public class FireBaseStorageServiceImpl implements FireBaseStorageService {
    @Override
    public void saveTestDate() {
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("/public");
        firebaseDatabase.child("test").setValue("added", new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference ref) {
                System.out.println("compledted adding data");
            }
        });
    }
}
