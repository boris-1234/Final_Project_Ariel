package com.example.final_project_ariel.AdaptersPackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_ariel.R;

import java.util.ArrayList;

public class AdapterHCTenantPayment extends RecyclerView.Adapter<AdapterHCTenantPayment.ViewHolderHCTenantPayment> {

    private final ArrayList<String> stringArrayList;

    public AdapterHCTenantPayment(ArrayList<String> stringArrayList) {
        this.stringArrayList = stringArrayList;
    }

    @NonNull
    @Override
    public ViewHolderHCTenantPayment onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.adapter_h_c_tenant_payment, parent, false);
        ViewHolderHCTenantPayment viewHolder = new ViewHolderHCTenantPayment(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderHCTenantPayment holder, int position) {
        String stringData = stringArrayList.get(position);
        holder.textViewMonthsPaid.setText(stringData);
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public static class ViewHolderHCTenantPayment extends RecyclerView.ViewHolder {

        private final TextView textViewMonthsPaid;

        public ViewHolderHCTenantPayment(View itemView) {
            super(itemView);

            textViewMonthsPaid = itemView.findViewById(R.id.textViewMonthsPaid);
        }

    }

}
