package com.example.final_project_ariel.PagesPackage;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_ariel.AdaptersPackage.AdapterHCTenantPayment;
import com.example.final_project_ariel.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class HCTenantPaymentActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "check1";
    private FirebaseFirestore db;
    private RecyclerView recyclerViewHCTenantPayments;
    private final ArrayList<String> stringArrayList = new ArrayList<>();
    private Button btnHCTenantPayment;
    private EditText etHCTenantPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_c_tenant_payment);

        initUI();
        initListeners();
    }

    private void initUI() {
        btnHCTenantPayment = findViewById(R.id.btnHCTenantPayment);
        recyclerViewHCTenantPayments = findViewById(R.id.recyclerViewHCTenantPayments);
        etHCTenantPayment = findViewById(R.id.etHCTenantPayment);

        recyclerViewHCTenantPayments.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
    }

    private void initListeners() {
        btnHCTenantPayment.setOnClickListener(this);
    }

    private void readFirestore() {
        stringArrayList.clear();

        db.collection("tenant")
                .document(etHCTenantPayment.getText().toString())
                .collection("monthsTPayNo")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.i(TAG, document.getId() + " => " + document.getData());

                            stringArrayList.add(document.getId());
                        }

                        AdapterHCTenantPayment adapter = new AdapterHCTenantPayment(stringArrayList);
                        recyclerViewHCTenantPayments.setAdapter(adapter);
                    } else {
                        Log.e(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnHCTenantPayment:
                readFirestore();
                break;
        }
    }

}
