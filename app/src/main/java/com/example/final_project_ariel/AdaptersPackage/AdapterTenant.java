package com.example.final_project_ariel.AdaptersPackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_ariel.ModelsPackage.TenantModel;
import com.example.final_project_ariel.R;

import java.util.ArrayList;

public class AdapterTenant extends RecyclerView.Adapter<AdapterTenant.ViewHolderTenant> {

    private ArrayList<TenantModel> tenantModels;

    public AdapterTenant(ArrayList<TenantModel> tenantModels) {
        this.tenantModels = tenantModels;
    }

    @NonNull
    @Override
    public ViewHolderTenant onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.adapter_tenant, parent, false);
        ViewHolderTenant viewHolder = new ViewHolderTenant(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderTenant holder, int position) {
        TenantModel tenantModel = tenantModels.get(position);
        holder.textViewPaid.setText(String.valueOf(tenantModel.getPaid()));
    }

    @Override
    public int getItemCount() {
        return tenantModels.size();
    }

    public static class ViewHolderTenant extends RecyclerView.ViewHolder {

        private TextView textViewPaid;

        public ViewHolderTenant(View itemView) {
            super(itemView);

            textViewPaid = itemView.findViewById(R.id.textViewPaid);
        }

    }

}
