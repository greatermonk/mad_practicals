package com.example.basicui;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Practical28 extends AppCompatActivity {

    private TextInputLayout usernameLayout, passwordLayout;
    private TextInputEditText editTextUsername, editTextPassword;
    private Button buttonLogin;

    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_]{4,20}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    private final Pattern usernamePattern = Pattern.compile(USERNAME_PATTERN);
    private final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practical28);
        initializeViews();
        setupTextWatchers();
        setupLoginButton();
    }
    private void initializeViews() {
        usernameLayout = findViewById(R.id.usernameLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

    }

    private void setupTextWatchers() {
        // Username validation on text change
        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                validateUsername(s.toString());
                updateLoginButtonState();
            }
        });

        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                validatePassword(s.toString());
                updateLoginButtonState();
            }});}

    private void setupLoginButton() {
        buttonLogin.setOnClickListener(v -> {
            if (isValidUsername(Objects.requireNonNull(editTextUsername.getText()).toString()) &&
                    isValidPassword(Objects.requireNonNull(editTextPassword.getText()).toString())) {
                Toast.makeText(Practical28.this, "Login successful", Toast.LENGTH_LONG).show();
            }});}
    private void validateUsername(String username) {
        if (username.isEmpty()) {
            usernameLayout.setError("Username cannot be empty");
        } else if (!isValidUsername(username)) {
            usernameLayout.setError("Username must be 4-20 characters and contain only letters, numbers, and underscores");
        } else {
            usernameLayout.setError(null);
            usernameLayout.setErrorEnabled(false);}}
    private void validatePassword(String password) {
        if (password.isEmpty()) {
            passwordLayout.setError("Password cannot be empty");
        } else if (!isValidPassword(password)) {
            passwordLayout.setError("Password must be at least 8 characters and include uppercase, lowercase, number, and special character");
        } else {
            passwordLayout.setError(null);
            passwordLayout.setErrorEnabled(false);}}

    private boolean isValidUsername(String username) {
        Matcher matcher = usernamePattern.matcher(username);
        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }

    private void updateLoginButtonState() {
        String username = Objects.requireNonNull(editTextUsername.getText()).toString();
        String password = Objects.requireNonNull(editTextPassword.getText()).toString();
        buttonLogin.setEnabled(isValidUsername(username) && isValidPassword(password));
    }}