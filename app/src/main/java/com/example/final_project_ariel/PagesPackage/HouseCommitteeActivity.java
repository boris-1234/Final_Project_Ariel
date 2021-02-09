package com.example.final_project_ariel.PagesPackage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project_ariel.R;

public class HouseCommitteeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnTenantPayment, btnTotalPricesInTheBuilding, btnSetTenantPayments, btnMonthlyIncomeViaMonths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_committee);

        initUI();
        initListeners();
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
                Intent intentTenantPayment = new Intent(this, HCTenantPaymentActivity.class);
                startActivity(intentTenantPayment);
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
