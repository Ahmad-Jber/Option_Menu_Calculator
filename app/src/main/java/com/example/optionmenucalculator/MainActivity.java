package com.example.optionmenucalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private AlertDialog dialog;
    private EditText val1,val2;
    private Button sum, sub, multi, divide;
    private RecyclerView recycler;
    private DBClass db;
    private CalculatorAdapter adapter;
    private ArrayList<Operations> operations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View viewOptionMenu = inflater.inflate(R.layout.alert_calculator,null);
        operations = new ArrayList<>();
        dialog = builder.create();
        recycler=findViewById(R.id.view);
        dialog.setContentView(R.layout.alert_calculator);
        val1 = viewOptionMenu.findViewById(R.id.val1);
        val2 = viewOptionMenu.findViewById(R.id.val2);
        sum = viewOptionMenu.findViewById(R.id.sum);
        sub = viewOptionMenu.findViewById(R.id.sub);
        multi = viewOptionMenu.findViewById(R.id.multi);
        divide = viewOptionMenu.findViewById(R.id.divide);
        db = new DBClass(this);
        dialog.setView(viewOptionMenu);
        dialog.setTitle("Calculator");
        adapter = new CalculatorAdapter(this);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        buttonsAction();
        adapter.setArrayList(db.getOperations());
        recycler.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.calc_item:{
                dialog.show();
                return super.onOptionsItemSelected(item);
            }
            case R.id.delete_history:{
                db.deleteOperations();
                operations.clear();
                recycler.setAdapter(null);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isEmpty(TextView t1){
        return t1.getText().toString().equals("");
    }
    private float sum(){
        return Float.parseFloat(val1.getText().toString())+Float.parseFloat(val2.getText().toString());
    }
    private float sub(){
        return Float.parseFloat(val1.getText().toString())-Float.parseFloat(val2.getText().toString());
    }
    private float multi(){
        return Float.parseFloat(val1.getText().toString())*Float.parseFloat(val2.getText().toString());
    }
    private float divide(){
        return Float.parseFloat(val1.getText().toString())/Float.parseFloat(val2.getText().toString());
    }
    private void buttonsAction(){
        sum.setOnClickListener(view -> checkOperation('+'));
        sub.setOnClickListener(view -> checkOperation('-'));
        multi.setOnClickListener(view -> checkOperation('*'));
        divide.setOnClickListener(view -> {
            if (val2.getText().toString().equals("0")){
                val2.setError("Can not divide on 0");
            }
            else
                checkOperation('/');
        });
    }
    private boolean validation(){
        return !isEmpty(val1) && !isEmpty(val2);
    }
    private void operations(char c, float result){
        Operations operation = new Operations(
                val1.getText().toString(),
                val2.getText().toString(),
                c,
                new DecimalFormat("#.###").format(result)
        );
        db.addOperation(operation);
        operations.add(operation);
        adapter.setArrayList(operations);
        recycler.setAdapter(adapter);
        dialog.dismiss();
        val1.setText("");
        val2.setText("");
    }
    private void checkOperation(char c){
        if (validation()){
            switch (c) {
                case '+': {
                    operations('+', sum());
                    break;
                }
                case '-': {
                    operations('-', sub());
                    break;
                }
                case '*': {
                    operations('*', multi());
                    break;
                }
                case '/': {
                    operations('/', divide());
                    break;
                }
            }
        }
        else{
            if (isEmpty(val1)){
                val1.setError("Required");
            }
            if (isEmpty(val2)){
                val2.setError("Required");
            }
        }
    }
}