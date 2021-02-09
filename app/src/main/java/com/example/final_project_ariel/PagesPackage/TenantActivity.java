package com.example.final_project_ariel.PagesPackage;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_ariel.AdaptersPackage.AdapterTenant;
import com.example.final_project_ariel.ModelsPackage.TenantModel;
import com.example.final_project_ariel.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class TenantActivity extends AppCompatActivity {

    private static final String TAG = "Tenant";
    private FirebaseFirestore db;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final String userEmail = user.getEmail();
    private RecyclerView recyclerViewTenant;
    private final ArrayList<TenantModel> tenantModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant);

        initUI();
        readFirestore();
    }

    private void initUI() {
        recyclerViewTenant = findViewById(R.id.recyclerViewTenant);

        recyclerViewTenant.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
    }

    private void readFirestore() {
        tenantModelArrayList.clear();

        db.collection("tenant")
                .whereEqualTo("userName", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.i(TAG, document.getId() + " => " + document.getData());

                            db.collection("tenant")
                                    .document(document.getId())
                                    .collection("monthsTPayNo")
                                    .get()
                                    .addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            for (QueryDocumentSnapshot document2 : task2.getResult()) {
                                                Log.i(TAG, document2.getId() + " => " + document2.getData());

                                                TenantModel tenantModel = document2.toObject(TenantModel.class);
                                                tenantModel.setId(document2.getId());
                                                tenantModelArrayList.add(tenantModel);
                                            }

                                            AdapterTenant adapter = new AdapterTenant(tenantModelArrayList);
                                            recyclerViewTenant.setAdapter(adapter);
                                        } else {
                                            Log.e(TAG, "Error getting documents.", task2.getException());
                                        }
                                    });
                        }
                    } else {
                        Log.e(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

}
