package com.example.optionmenucalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View viewOptionMenu = inflater.inflate(R.layout.alert_calculator,null);
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
        operations = new ArrayList<>();
        database = db.getWritableDatabase();
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
                operations.clear();
                database.execSQL("DELETE FROM OPERATIONS");
                adapter=getAdapter(db);
                return super.onOptionsItemSelected(item);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = getAdapter(db);
        recycler.setAdapter(adapter);
        sum.setOnClickListener(view -> {
            if (isTextEmpty()){
                val1.setError("Required");
                val2.setError("Required");
            }
            else if (val1.getText().toString().equals("")) val1.setError("Required");
            else if(val2.getText().toString().equals("")) val2.setError("Required");
            else{
                db.addOperation(
                        new Operations(
                                val1.getText().toString(),
                                val2.getText().toString(),
                                '+',
                                new DecimalFormat("#.###").format(sum())
                        )
                );
                adapter = getAdapter(db);
                recycler.setAdapter(adapter);
                dialog.dismiss();
                val1.setText("");
                val2.setText("");
            }
        });
        sub.setOnClickListener(view -> {
            if (isTextEmpty()){
                val1.setError("Required");
                val2.setError("Required");
            }
            else if (val1.getText().toString().equals("")) val1.setError("Required");
            else if(val2.getText().toString().equals("")) val2.setError("Required");
            else{
                db.addOperation(
                        new Operations(
                                val1.getText().toString(),
                                val2.getText().toString(),
                                '-',
                                new DecimalFormat("#.###").format(sub())
                        )
                );
                dialog.dismiss();
                val1.setText("");
                val2.setText("");
                adapter = getAdapter(db);
                recycler.setAdapter(adapter);
            }
        });
        multi.setOnClickListener(view -> {
            if (isTextEmpty()){
                val1.setError("Required");
                val2.setError("Required");
            }
            else if (val1.getText().toString().equals("")) val1.setError("Required");
            else if(val2.getText().toString().equals("")) val2.setError("Required");
            else{
                db.addOperation(
                        new Operations(
                                val1.getText().toString(),
                                val2.getText().toString(),
                                '*',
                                new DecimalFormat("#.###").format(multi())
                        )
                );
                dialog.dismiss();
                val1.setText("");
                val2.setText("");
                adapter =getAdapter(db);
                recycler.setAdapter(adapter);
            }
        });
        divide.setOnClickListener(view -> {
            if (isTextEmpty()){
                val1.setError("Required");
                val2.setError("Required");
            }
            else if (val1.getText().toString().equals("")) val1.setError("Required");
            else if(val2.getText().toString().equals("")) val2.setError("Required");
            else if(Float.parseFloat(val2.getText().toString())==0) val2.setError("Can not divide by 0");
            else{
                db.addOperation(
                        new Operations(
                                val1.getText().toString(),
                                val2.getText().toString(),
                                '/',
                                new DecimalFormat("#.###").format(divide())
                        )
                );
                dialog.dismiss();
                val1.setText("");
                val2.setText("");
                adapter =getAdapter(db);
                recycler.setAdapter(adapter);
            }
        });
    }

    private CalculatorAdapter getAdapter(@NonNull DBClass db) {
        recycler.setLayoutManager(new LinearLayoutManager(this));
        operations.clear();
        operations = db.showOperations(operations);
        adapter = new CalculatorAdapter(this,operations);
        return adapter;
    }

    private boolean isTextEmpty(){
        return val1.getText().toString().equals("") && val2.getText().toString().equals("");
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
}