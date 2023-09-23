package com.example.land;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sender extends AppCompatActivity {

    private EditText senderEditText;
    private EditText receiverEditText;
    private EditText amountEditText;
    private Button addButton;

    // Firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sender);

        senderEditText = findViewById(R.id.editTextUsername);
        receiverEditText = findViewById(R.id.editTextPassword);
        amountEditText = findViewById(R.id.editamnt);
        addButton = findViewById(R.id.buttonLogin);

        // Initialize Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("transactions"); // Replace "transactions" with your desired Firebase database node

        addButton = findViewById(R.id.buttonLogin);
        addButton.setEnabled(true); // Make the button clickable
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTransactionToFirebase();
            }
        });
    }

    private void addTransactionToFirebase() {
        String sender = senderEditText.getText().toString().trim();
        String receiver = receiverEditText.getText().toString().trim();
        String amount = amountEditText.getText().toString().trim();

        // Validate input fields
        if (sender.isEmpty() || receiver.isEmpty() || amount.isEmpty()) {
            Toast.makeText(sender.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a Transaction object and push it to Firebase
        Transaction transaction = new Transaction(sender, receiver, Double.parseDouble(amount));

        databaseReference.push().setValue(transaction).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Data added successfully
                    Toast.makeText(sender.this, "Transaction added to Firebase", Toast.LENGTH_SHORT).show();
                    // Clear input fields
                    senderEditText.setText("");
                    receiverEditText.setText("");
                    amountEditText.setText("");
                } else {
                    // Error occurred
                    Toast.makeText(sender.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Firebase", "Error adding transaction to Firebase: " + task.getException());
                }
            }
        });

    }
}
