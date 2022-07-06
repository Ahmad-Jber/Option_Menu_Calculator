package com.example.optionmenucalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private AlertDialog dialog;
    private TextView result;
    private EditText val1,val2;
    private Button sum, sub, multi, divide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_calculator,null);
        result = findViewById(R.id.result);
        dialog = builder.create();
        dialog.setContentView(R.layout.alert_calculator);
        val1 = view.findViewById(R.id.val1);
        val2 = view.findViewById(R.id.val2);
        sum = view.findViewById(R.id.sum);
        sub = view.findViewById(R.id.sub);
        multi = view.findViewById(R.id.multi);
        divide = view.findViewById(R.id.divide);
        dialog.setView(view);
        dialog.setTitle("Calculator");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        dialog.show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sum.setOnClickListener(view -> {
            if (isEmpty()){
                val1.setError("Required");
                val2.setError("Required");
            }
            else if (val1.getText().toString().equals("")) val1.setError("Required");
            else if(val2.getText().toString().equals("")) val2.setError("Required");
            else{
                result.setText(new DecimalFormat("#.###").format(sum()));
                dialog.dismiss();
                val1.setText("");
                val2.setText("");
            }
        });
        sub.setOnClickListener(view -> {
            if (isEmpty()){
                val1.setError("Required");
                val2.setError("Required");
            }
            else if (val1.getText().toString().equals("")) val1.setError("Required");
            else if(val2.getText().toString().equals("")) val2.setError("Required");
            else{
                result.setText(new DecimalFormat("#.###").format(sub()));
                dialog.dismiss();
                val1.setText("");
                val2.setText("");
            }
        });
        multi.setOnClickListener(view -> {
            if (isEmpty()){
                val1.setError("Required");
                val2.setError("Required");
            }
            else if (val1.getText().toString().equals("")) val1.setError("Required");
            else if(val2.getText().toString().equals("")) val2.setError("Required");
            else{
                result.setText(new DecimalFormat("#.###").format(multi()));
                dialog.dismiss();
                val1.setText("");
                val2.setText("");
            }
        });
        divide.setOnClickListener(view -> {
            if (isEmpty()){
                val1.setError("Required");
                val2.setError("Required");
            }
            else if (val1.getText().toString().equals("")) val1.setError("Required");
            else if(val2.getText().toString().equals("")) val2.setError("Required");
            else if(Float.parseFloat(val2.getText().toString())==0) val2.setError("Can not divide by 0");
            else{
                result.setText(new DecimalFormat("#.###").format(divide()));
                dialog.dismiss();
                val1.setText("");
                val2.setText("");
            }
        });
    }
    private boolean isEmpty(){
        return val1.getText().toString().equals("")&&val2.getText().toString().equals("");
    }
    private double sum(){
        return Float.parseFloat(val1.getText().toString())+Float.parseFloat(val2.getText().toString());
    }
    private double sub(){
        return Float.parseFloat(val1.getText().toString())-Float.parseFloat(val2.getText().toString());
    }
    private double multi(){
        return Float.parseFloat(val1.getText().toString())*Float.parseFloat(val2.getText().toString());
    }
    private double divide(){
        return Float.parseFloat(val1.getText().toString())/Float.parseFloat(val2.getText().toString());
    }
}