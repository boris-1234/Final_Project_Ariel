package com.example.final_project_ariel.PagesPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.final_project_ariel.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class HCUpdatePaymentTenant extends AppCompatActivity implements View.OnClickListener {

    private EditText etTenantNumber, etTenantMonth, etTenantPayment;
    private Button btnUpdate;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_c_update_payment_tenant);

        initUI();
        initListeners();
    }

    private void initUI() {
        etTenantNumber = findViewById(R.id.etTenantNumber);
        etTenantMonth = findViewById(R.id.etTenantMonth);
        etTenantPayment = findViewById(R.id.etTenantPayment);
        btnUpdate = findViewById(R.id.btnUpdate);

        db = FirebaseFirestore.getInstance();
    }

    private void initListeners() {
        btnUpdate.setOnClickListener(this);
    }

    private void updateTenantPayment() {
        Map<String, Object> map = new HashMap<>();
        map.put("paid", etTenantPayment.getText().toString());

        db.collection("tenant")
                .document(etTenantNumber.getText().toString())
                .collection("monthsTPayNo")
                .document(etTenantMonth.getText().toString())
                .set(map)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Updated", Toast.LENGTH_LONG).show();
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdate:
                updateTenantPayment();
                break;
        }
    }

}