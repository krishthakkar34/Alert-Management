package com.example.alertmanagement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.example.alertmanagement.R;
import com.google.android.material.textfield.TextInputLayout;

public class Loginpage extends AppCompatActivity {

    private TextInputEditText usernameEditText, pinEditText;
    private Button loginButton;
    private TextView signupTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        // Initialize your views
        usernameEditText = findViewById(R.id.textInputUsername);
        pinEditText = findViewById(R.id.textInputPin);
        loginButton = findViewById(R.id.button3);
        signupTextView = findViewById(R.id.textViewLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String pin = pinEditText.getText().toString();

                if (validateInputs(username, pin)) {
                    // Perform login logic here
                    // You can authenticate the user, check credentials, etc.
                    // For now, let's display a toast message as an example
                    Toast.makeText(Loginpage.this, "Login successful!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement the logic to navigate to the signup activity here
            }
        });
    }

    private boolean validateInputs(String username, String pin) {
        // Implement input validation logic here
        // Check for empty fields, correct username and pin, etc.
        // Return true if all inputs are valid, otherwise return false

        if (username.isEmpty()) {
            usernameEditText.setError("Username is required");
            return false;
        }

        if (pin.isEmpty()) {
            pinEditText.setError("PIN is required");
            return false;
        }

        return true;
    }
}

