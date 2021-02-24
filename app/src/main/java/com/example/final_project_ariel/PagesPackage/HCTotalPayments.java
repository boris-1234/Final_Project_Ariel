package com.example.final_project_ariel.PagesPackage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_ariel.AdaptersPackage.AdapterHCTotalPricesInTheBuilding;
import com.example.final_project_ariel.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HCTotalPayments extends AppCompatActivity {

    private static final String TAG = "check1";
    private RecyclerView recyclerViewHCTotalPayments;
    private FirebaseFirestore db;
    private final ArrayList<String> stringArrayList = new ArrayList<>();
    private AdapterHCTotalPricesInTheBuilding adapter;
    private final Map<String, Integer> map = new HashMap<>();
    private ListenerRegistration listenerRegistration;
    private Map<Integer, Integer> totalSum = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_c_total_payments);
        adapter = new AdapterHCTotalPricesInTheBuilding(stringArrayList);

        initUI();
        readFirestore();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        listenerRegistration.remove();
    }

    private void initUI() {
        recyclerViewHCTotalPayments = findViewById(R.id.recyclerViewHCTotalPayments);
        recyclerViewHCTotalPayments.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHCTotalPayments.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
    }

    private void readFirestore() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {

            listenerRegistration = db.collection("tenant")
                    .addSnapshotListener((value, error) -> {
                        for (DocumentSnapshot doc : value) {
                            listenerRegistration = db.collection("tenant")
                                    .document(doc.getId())
                                    .collection("monthsTPayNo")
                                    .addSnapshotListener((value1, error1) -> {
                                        //totalSum.clear();
                                        for (DocumentSnapshot doc1 : value1) {
                                            JSONObject jsonObject = new JSONObject(doc1.getData());
                                            try {
                                                Log.i(TAG, jsonObject.getInt("paid") + " " + doc1.getId());
                                                map.put(doc1.getId(), jsonObject.getInt("paid"));
                                                stringArrayList.clear();
                                                for (int i = 0; i < 12; i++)
                                                    stringArrayList.add("");
                                                int i = Integer.parseInt(doc1.getId());
                                                if (map.containsKey(String.valueOf(i))) {
                                                    //for (int sum : map.values()) {
                                                    if (totalSum.get(i) != null) {
                                                        totalSum.put(i, totalSum.get(i) + map.get(i + ""));
                                                        //Log.i (TAG, "Debug 1: totalSum =" + totalSum.toString() + " i = " + i);
                                                    }
                                                    else {
                                                        totalSum.put(i, map.get(i + ""));
                                                        //Log.i (TAG, "Debug 2: totalSum2 =" + totalSum.toString()+ " i = " + i);
                                                    }

                                                    //}
                                                }


                                                //stringArrayList.set(i - 1, i + " => " + totalSum.get(i));
                                                for (i = 1; i < 13; i++)
                                                    if (totalSum.get(i) != null)
                                                        stringArrayList.set(i-1, i + " => " + totalSum.get(i));
                                                adapter.setData(stringArrayList);

                                                //}

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                        }


                        /*stringArrayList.clear();
                        for (int i = 1; i <= 12; i++) {
                            int totalSum = 0;

                            if (map.containsKey(String.valueOf(i))) {
                                for (int sum : map.values()) {
                                    totalSum += sum;
                                }
                            }

                            stringArrayList.add(i + " => " + totalSum);
                        }*/

                        //adapter.setData(stringArrayList);
                        //recyclerViewHCTotalPayments.setAdapter(adapter);
                    });
        }, 0);
    }

}

// The for loops of Firestore not waiting for the finishing to show us the data in the RecyclerView
