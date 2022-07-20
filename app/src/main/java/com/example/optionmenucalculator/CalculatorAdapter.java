package com.example.optionmenucalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalculatorAdapter extends RecyclerView.Adapter<CalculatorAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<Results> resultsAL;
    public CalculatorAdapter(Activity activity,ArrayList <Results> resultsAL){
        this.activity = activity;
        this.resultsAL = resultsAL;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View viewResult = inflater.inflate(R.layout.result_layout,parent,false);
        return new ViewHolder(viewResult);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.num1TV.setText(String.valueOf(resultsAL.get(position).getNum1()));
        holder.operatorTV.setText(String.valueOf(resultsAL.get(position).getOperator()));
        holder.num2TV.setText(String.valueOf(resultsAL.get(position).getNum2()));
        holder.resultTV.setText(String.valueOf(resultsAL.get(position).getResult()));
    }

    @Override
    public int getItemCount() {
        return resultsAL.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView num1TV,num2TV, operatorTV,resultTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            num1TV=itemView.findViewById(R.id.num1);
            num2TV=itemView.findViewById(R.id.num2);
            operatorTV=itemView.findViewById(R.id.operator);
            resultTV=itemView.findViewById(R.id.result);
        }
    }
}
