package com.example.final_project_ariel.AdaptersPackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project_ariel.R;

import java.util.ArrayList;

public class AdapterHCTotalPricesInTheBuilding extends  RecyclerView.Adapter<AdapterHCTotalPricesInTheBuilding.ViewHolderHCTotalPricesInTheBuilding> {

    private ArrayList<String> stringArrayList;

    public AdapterHCTotalPricesInTheBuilding(ArrayList<String> stringArrayList){
        this.stringArrayList = stringArrayList;
    }

    @NonNull
    @Override
    public ViewHolderHCTotalPricesInTheBuilding onCreateViewHolder(ViewGroup parent,int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.adapter_h_c_total_prices_in_the_building,parent,false);
        ViewHolderHCTotalPricesInTheBuilding viewHolder = new ViewHolderHCTotalPricesInTheBuilding(listItem);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderHCTotalPricesInTheBuilding holder, int position) {
        String stringData = stringArrayList.get(position);

        holder.textViewTotalPricesInTheBuilding.setText(stringData);
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public void setData(ArrayList<String> stringArrayList) {
        this.stringArrayList = stringArrayList;
        notifyDataSetChanged();
    }

    public static class ViewHolderHCTotalPricesInTheBuilding extends RecyclerView.ViewHolder {

        private final TextView textViewTotalPricesInTheBuilding;

        public ViewHolderHCTotalPricesInTheBuilding(View itemView) {
            super(itemView);

            textViewTotalPricesInTheBuilding = itemView.findViewById(R.id.textViewTotalPricesInTheBuilding);
        }

    }

}
