package com.example.echochat.firbase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.echochat.model.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseHelper {
    private static final String TAG = "FirebaseHelper";

    // Method to add a contact to Firestore
    public static void addContact(UserModel user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            // Use the current user's UID to store the contact under the "Contacts" collection
            db.collection("Contacts").document(user.getUserId())
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "addContact: Contact added successfully");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "addContact: Failed to add contact", e);
                        }
                    });
        } else {
            Log.d(TAG, "addContact: User is not logged in");
        }
    }

    // Method to retrieve user by phone number (not fully implemented)
    public static void getUser(String phone) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Example query to fetch user data by phone number
        db.collection("Users").whereEqualTo("phone", phone)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // Handle user retrieval success
                    Log.d(TAG, "getUser: User found with phone number: " + phone);
                    // TODO: Implement logic to process retrieved user data
                })
                .addOnFailureListener(e -> {
                    // Handle failure in retrieving user data
                    Log.e(TAG, "getUser: Failed to retrieve user", e);
                });
    }

    public static void getCurrentProfileInfo(String phone) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser!=null){
            // Example query to fetch user data by phone number
            db.collection("Users").whereEqualTo("userId", firebaseUser.getUid())
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        // Handle user retrieval success
                        Log.d(TAG, "getUser: User found with phone number: " + phone);
                        // TODO: Implement logic to process retrieved user data
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure in retrieving user data
                        Log.e(TAG, "getUser: Failed to retrieve user", e);
                    });
        }

    }

}
