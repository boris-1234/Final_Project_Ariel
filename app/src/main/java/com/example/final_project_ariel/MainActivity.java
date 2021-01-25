package com.example.final_project_ariel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup radioGroupBuilding, radioGroupLoginChangePassword;
    private EditText etUsername, etOldPassword, etPassword;
    private Button btnLoginChangePassword;
    private String typeBuilding, typeLoginChangePassword;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initListeners();
        radioGroupsClick();
    }

    private void initUI() {
        radioGroupBuilding = findViewById(R.id.radioGroupBuilding); // Init id of elements
        radioGroupLoginChangePassword = findViewById(R.id.radioGroupLoginChangePassword);
        etUsername = findViewById(R.id.etUsername);
        etOldPassword = findViewById(R.id.etOldPassword);
        etPassword = findViewById(R.id.etPassword);
        btnLoginChangePassword = findViewById(R.id.btnLoginChangePassword);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void initListeners() {
        btnLoginChangePassword.setOnClickListener(this);
        radioGroupBuilding.setOnClickListener(this);
        radioGroupLoginChangePassword.setOnClickListener(this);
    }

    private void radioGroupsClick() {
        radioGroupBuilding.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radioButtonHouseCommittee:
                    typeBuilding = "houseCommittee";

                    setVisibleElementsBuilding();
                    break;
                case R.id.radioButtonTenant:
                    typeBuilding = "tenant";

                    setVisibleElementsBuilding();
                    break;
            }
        });

        radioGroupLoginChangePassword.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radioButtonLogin:
                    btnLoginChangePassword.setText("Login");
                    typeLoginChangePassword = "login";
                    etPassword.setHint("Password");

                    setVisibleElementsLogin();
                    break;
                case R.id.radioButtonChangePassword:
                    btnLoginChangePassword.setText("Change password");
                    typeLoginChangePassword = "changePassword";
                    etPassword.setHint("New password");

                    setVisibleElementsChangePassword();
                    break;
            }
        });
    }

    private void setVisibleElementsBuilding() {
        radioGroupLoginChangePassword.setVisibility(View.VISIBLE);
    }

    private void setVisibleElementsLogin() {
        etUsername.setVisibility(View.VISIBLE);
        etPassword.setVisibility(View.VISIBLE);
        btnLoginChangePassword.setVisibility(View.VISIBLE);
        btnLoginChangePassword.setVisibility(View.VISIBLE);
        etOldPassword.setVisibility(View.GONE);
    }

    private void setVisibleElementsChangePassword() {
        etUsername.setVisibility(View.GONE);
        etPassword.setVisibility(View.VISIBLE);
        btnLoginChangePassword.setVisibility(View.VISIBLE);
        btnLoginChangePassword.setVisibility(View.VISIBLE);
        etOldPassword.setVisibility(View.VISIBLE);
    }

    private void login() {
        String email = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (!EmailPasswordValidator.getInstance().isValidEmail(email) && !EmailPasswordValidator.getInstance().isValidPassword(password)) {
            etUsername.setError("The email is invalid");
            etPassword.setError("The password is invalid");
            etUsername.requestFocus();
            etPassword.requestFocus();
        } else if (!EmailPasswordValidator.getInstance().isValidEmail(email)) {
            etUsername.setError("The email is invalid");
            etUsername.requestFocus();
        } else if (!EmailPasswordValidator.getInstance().isValidPassword(password)) {
            etPassword.setError("The password is invalid");
            etPassword.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_LONG).show();

                            Intent intent = null;
                            if (typeBuilding.equals("houseCommittee")) {
                                intent = new Intent(MainActivity.this, HouseCommitteeActivity.class);
                            } else if (typeBuilding.equals("tenant")) {
                                intent = new Intent(MainActivity.this, TenantActivity.class);
                            }
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void changePassword() {
        assert user != null;
        String email = user.getEmail();
        String oldPassword = etOldPassword.getText().toString();
        String newPassword = etPassword.getText().toString();
        assert email != null;
        AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);

        if (!EmailPasswordValidator.getInstance().isValidPassword(newPassword) && !EmailPasswordValidator.getInstance().isValidPassword(oldPassword)) {
            etPassword.setError("The new password is invalid");
            etOldPassword.setError("The old password is invalid");
            etPassword.requestFocus();
            etOldPassword.requestFocus();
        } else if (!EmailPasswordValidator.getInstance().isValidPassword(newPassword)) {
            etPassword.setError("The new password is invalid");
            etPassword.requestFocus();
        } else if (!EmailPasswordValidator.getInstance().isValidPassword(oldPassword)) {
            etOldPassword.setError("The old password is invalid");
            etOldPassword.requestFocus();
        } else {
            user.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    user.updatePassword(etPassword.getText().toString()).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Intent intent = null;
                            if (typeBuilding.equals("houseCommittee")) {
                                intent = new Intent(MainActivity.this, HouseCommitteeActivity.class);
                            } else if (typeBuilding.equals("tenant")) {
                                intent = new Intent(MainActivity.this, TenantActivity.class);
                            }
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Change password failed", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Change password failed", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    // Clicks of elements
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoginChangePassword:
                if (typeLoginChangePassword.equals("login")) {
                    login();
                } else if (typeLoginChangePassword.equals("changePassword") && user != null) {
                    changePassword();
                } else if (typeLoginChangePassword.equals("changePassword")) {
                    Toast.makeText(this, "The user not logged in", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

}
