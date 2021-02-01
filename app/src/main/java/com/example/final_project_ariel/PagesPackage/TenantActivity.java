package com.example.final_project_ariel.PagesPackage;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_ariel.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class TenantActivity extends AppCompatActivity {

    private static final String TAG = "Tenant";
    private FirebaseFirestore db;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final String userEmail = user.getEmail();
    private RecyclerView recyclerViewTenant;

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
                                            }
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

// (1) 1. Set in 'activity_tenant' xml RecyclerView tag - V
// (1) 2. Create class(Model) with the name 'TenantModel' - V
// (1.5) 3. In the class 'TenantModel' put field that called 'paid' and create getters and setters to this field - V
// (1) 4. Put in 'TenantActivity' class all the necessary data to use with RecyclerView - V
// (1.5) 5. Create new xml with the name 'adapter_tenant' and put him TextView tag that show later the paid from firestore(like: 5,000) - V
// (5) 6. Create new class with the name 'AdapterTenant' and put him all the data you need to show data in the RecyclerView - V
// (2.5) 7. Put the data from the inner read firestore in the RecyclerView
