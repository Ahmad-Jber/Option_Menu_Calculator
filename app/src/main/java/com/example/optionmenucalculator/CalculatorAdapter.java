package com.example.optionmenucalculator;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalculatorAdapter extends RecyclerView.Adapter<CalculatorAdapter.ViewHolder> {
    private final Activity activity;
    private final ArrayList<Operations> operationsAL;
    public CalculatorAdapter(Activity activity,ArrayList <Operations> operationsAL){
        this.activity = activity;
        this.operationsAL = operationsAL;
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
        holder.num1TV.setText(String.valueOf(operationsAL.get(position).getNum1()));
        holder.operatorTV.setText(String.valueOf(operationsAL.get(position).getOperator()));
        holder.num2TV.setText(String.valueOf(operationsAL.get(position).getNum2()));
        holder.resultTV.setText(String.valueOf(operationsAL.get(position).getResult()));
    }

    @Override
    public int getItemCount() {
        return operationsAL.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView num1TV,num2TV, operatorTV,resultTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            num1TV=itemView.findViewById(R.id.num1);
            num2TV=itemView.findViewById(R.id.num2);
            operatorTV=itemView.findViewById(R.id.operator);
            resultTV=itemView.findViewById(R.id.result);
        }
    }
}
