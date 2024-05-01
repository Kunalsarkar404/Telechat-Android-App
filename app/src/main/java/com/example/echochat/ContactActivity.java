package com.example.echochat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.echochat.firbase.FirebaseHelper;
import com.example.echochat.model.UserModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContactActivity extends AppCompatActivity {

    private FloatingActionButton fabAdd;
    private View addContactLayout;
    private ContactAdapter contactAdapter;
    private RecyclerView recyclerViewContacts;
    private List<UserModel> contactList;

    private EditText edFirstName, edLastName, edCountryCode, edPhoneNumber;
    private Button btnDone;
    private static final int REQUEST_ADD_CONTACT = 1;

    private FirebaseHelper firebaseHelper; // Instance of FirebaseHelper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        initViews();
        setupFabClickListener();
        setupClickListeners();

        ImageButton btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactActivity.this, MainActivity.class));
            }
        });

        recyclerViewContacts = findViewById(R.id.recycle_view);
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));

        contactList = new ArrayList<>();
        contactAdapter = new ContactAdapter(contactList, this);
        recyclerViewContacts.setAdapter(contactAdapter);

        // Retrieve contacts from Firestore
        retrieveContactsFromFirestore();
    }
    private void initViews() {
        fabAdd = findViewById(R.id.fabadd);
        addContactLayout = findViewById(R.id.addContactLayout);
        edFirstName = findViewById(R.id.ed_first_name);
        edLastName = findViewById(R.id.ed_last_name);
        edCountryCode = findViewById(R.id.ed_code);
        edPhoneNumber = findViewById(R.id.ed_phone);
        btnDone = findViewById(R.id.btn_done);
    }

    private void setupFabClickListener() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle visibility of addContactLayout
                toggleAddContactLayout();
                toggleFabAddButton();
            }
        });
    }

    private void setupClickListeners() {
        // Done button click listener
        btnDone = findViewById(R.id.btn_done);
        btnDone.setOnClickListener(v -> {
            if (validateForm()) {
                addContact();
                toggleAddContactLayout();
            }
        });
    }
    private boolean validateForm() {
        if (TextUtils.isEmpty(edFirstName.getText().toString().trim())) {
            edFirstName.setError("First name is required");
            return false;
        } else if (TextUtils.isEmpty(edLastName.getText().toString().trim())) {
            edLastName.setError("Last name is required");
            return false;
        } else if (TextUtils.isEmpty(edCountryCode.getText().toString().trim())) {
            edCountryCode.setError("Country code is required");
            return false;
        } else if (TextUtils.isEmpty(edPhoneNumber.getText().toString().trim())) {
            edPhoneNumber.setError("Phone number is required");
            return false;
        }
        return true;
    }

    private void addContact() {
        String firstName = edFirstName.getText().toString().trim();
        String lastName = edLastName.getText().toString().trim();
        String countryCode = edCountryCode.getText().toString().trim();
        String phoneNumber = edPhoneNumber.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            edFirstName.setError("First name is required");
            return;
        }
        if (TextUtils.isEmpty(lastName)) {
            edLastName.setError("Last name is required");
            return;
        }
        if (TextUtils.isEmpty(countryCode)) {
            edCountryCode.setError("Country code is required");
            return;
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            edPhoneNumber.setError("Phone number is required");
            return;
        }

        UserModel userModel = new UserModel();
        userModel.setUserId(UUID.randomUUID().toString());
        userModel.setUserName(firstName + " " + lastName);
        userModel.setPhone(countryCode + phoneNumber);

        FirebaseHelper.addContact(userModel); // Save user to Firebase (implement FirebaseHelper)
        retrieveContactsFromFirestore();
    }

    private void toggleAddContactLayout() {
        if (addContactLayout.getVisibility() == View.VISIBLE) {
            addContactLayout.setVisibility(View.GONE);
        } else {
            addContactLayout.setVisibility(View.VISIBLE);
        }
    }
    private void toggleFabAddButton() {
        if (addContactLayout.getVisibility() == View.VISIBLE) {
            fabAdd.setVisibility(View.GONE);
        } else {
            fabAdd.setVisibility(View.VISIBLE);
        }
    }

    private void retrieveContactsFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Contacts")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    contactList.clear(); // Clear existing list
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        UserModel contact = document.toObject(UserModel.class);
                        contactList.add(contact);
                    }
                    contactAdapter.notifyDataSetChanged(); // Update RecyclerView
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ContactActivity.this, "Failed to retrieve contacts", Toast.LENGTH_SHORT).show();
                    Log.e("ContactActivity", "Error retrieving contacts", e);
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD_CONTACT && resultCode == RESULT_OK) {
            // Refresh contacts after adding a new contact
            retrieveContactsFromFirestore();
            Toast.makeText(this, "Contact added successfully", Toast.LENGTH_SHORT).show();
        }
    }
}


