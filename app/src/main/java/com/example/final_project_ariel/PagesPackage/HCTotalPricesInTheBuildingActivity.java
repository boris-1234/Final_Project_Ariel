package com.example.final_project_ariel.PagesPackage;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_ariel.AdaptersPackage.AdapterHCTotalPricesInTheBuilding;
import com.example.final_project_ariel.AdaptersPackage.AdapterTenant;
import com.example.final_project_ariel.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HCTotalPricesInTheBuildingActivity extends AppCompatActivity {

    private static final String TAG = "check1";
    private RecyclerView recyclerViewHCTotalPriceInTheBuilding;
    private FirebaseFirestore db;
    private final ArrayList<String> stringArrayList = new ArrayList<>();
    private AdapterHCTotalPricesInTheBuilding adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_c_total_prices_in_the_building);

        initUI();
        readFirestore();
    }

    private void initUI() {
        recyclerViewHCTotalPriceInTheBuilding = findViewById(R.id.recyclerViewHCTotalPriceInTheBuilding);
        recyclerViewHCTotalPriceInTheBuilding.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
    }

    private void readFirestore() {
        adapter = new AdapterHCTotalPricesInTheBuilding(stringArrayList);

        db.collection("tenant")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.i(TAG, document.getId() + "=>" + document.getData());

                            db.collection("tenant")
                                    .document(document.getId())
                                    .collection("monthsTPayNo")
                                    .get()
                                    .addOnCompleteListener(task2 -> {
                                        if (task2.isSuccessful()) {
                                            for (QueryDocumentSnapshot document2 : task2.getResult()) {
                                                Log.i(TAG, document2.getId() + " => " + document2.getData());

                                                stringArrayList.add(document.getId() + " => " + document2.getId());

                                                adapter.setData(stringArrayList);
                                            }
                                        } else {
                                            Log.e(TAG, "Error getting documents.", task2.getException());
                                        }
                                    });
                        }

                        recyclerViewHCTotalPriceInTheBuilding.setAdapter(adapter);
                    } else {
                        Log.e(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

}

// Organize the method readFirestore - the data that printed in LogCat print the number of tenant and the months that the tenant paid