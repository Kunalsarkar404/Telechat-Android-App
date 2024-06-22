package com.example.echochat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.echochat.model.MessageModel;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private EditText messageBox;
    private ImageButton emojiBtn, attachBtn, micBtn, sendBtn;

    private RecyclerView recyclerChat;
    private MessageAdapter messageAdapter;
    private List<MessageModel> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Initialize views
        messageBox = findViewById(R.id.messagebox);
        emojiBtn = findViewById(R.id.emojibtn);
        attachBtn = findViewById(R.id.attachbtn);
        micBtn = findViewById(R.id.micbtn);
        sendBtn = findViewById(R.id.sendBtn);
        recyclerChat = findViewById(R.id.recycler_chat);

        // Initialize message list and adapter
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList, this);
        recyclerChat.setLayoutManager(new LinearLayoutManager(this));
        recyclerChat.setAdapter(messageAdapter);

        // Add TextWatcher to the message box
        messageBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed before text is changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    // Show send button and hide attach and mic buttons
                    sendBtn.setVisibility(View.VISIBLE);
                    attachBtn.setVisibility(View.GONE);
                    micBtn.setVisibility(View.GONE);
                } else {
                    // Hide send button and show attach and mic buttons
                    sendBtn.setVisibility(View.GONE);
                    attachBtn.setVisibility(View.VISIBLE);
                    micBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed after text is changed
            }
        });

        // Set onClickListener for send button
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle send button click
                String message = messageBox.getText().toString().trim();
                if (!message.isEmpty()) {
                    sendMessage(message);
                    messageBox.setText(""); // Clear the message box after sending the message
                }
            }
        });
        emojiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(messageBox, InputMethodManager.SHOW_IMPLICIT);
            }
        });

    }

    private void sendMessage(String message) {
        // Create a new message object
        MessageModel newMessage = new MessageModel();
        newMessage.setMessageText(message);

        // Add the new message to the list
        messageList.add(newMessage);

        // Notify the adapter about the new item
        messageAdapter.notifyItemInserted(messageList.size() - 1);

        // Scroll the RecyclerView to the latest message
        recyclerChat.scrollToPosition(messageList.size() - 1);
    }
}
