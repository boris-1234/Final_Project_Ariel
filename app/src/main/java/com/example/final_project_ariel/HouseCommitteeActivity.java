package com.example.final_project_ariel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HouseCommitteeActivity extends AppCompatActivity implements View.OnClickListener  {

    private Button btnTenantPayment;
    private Button btnTotalPricesInTheBuilding;
    private Button btnSetTenantPayments;
    private Button btnMonthlyIncomeViaMonths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_committee);

        initUI();
        initListeners();
        // Write in this Activity code that perform buttons click like in MainActivity
    }

    private void initUI() {
        btnTenantPayment = findViewById(R.id.btnTenantPayment);
        btnTotalPricesInTheBuilding = findViewById(R.id.btnTotalPricesInTheBuilding);
        btnSetTenantPayments = findViewById(R.id.btnSetTenantPayments);
        btnMonthlyIncomeViaMonths = findViewById(R.id.btnMonthlyIncomeViaMonths);
    }

    private void initListeners() {
        btnTenantPayment.setOnClickListener(this);
        btnTotalPricesInTheBuilding.setOnClickListener(this);
        btnSetTenantPayments.setOnClickListener(this);
        btnMonthlyIncomeViaMonths.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTenantPayment:
                break;
            case R.id.btnTotalPricesInTheBuilding:
                break;
            case R.id.btnSetTenantPayments:
                break;
            case R.id.btnMonthlyIncomeViaMonths:
                break;
        }
    }



}
