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
    CalculatorAdapter adapter;
    ArrayList<Results> results = new ArrayList<>();
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
        dialog.setView(viewOptionMenu);
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
        DBClass db = new DBClass(this);
        if (!db.cursorToArray(results).isEmpty()){
            recycler.setLayoutManager(new LinearLayoutManager(this));
            results.clear();
            adapter = new CalculatorAdapter(this,db.cursorToArray(results));
            recycler.setAdapter(adapter);
        }
        sum.setOnClickListener(view -> {
            if (isEmpty()){
                val1.setError("Required");
                val2.setError("Required");
            }
            else if (val1.getText().toString().equals("")) val1.setError("Required");
            else if(val2.getText().toString().equals("")) val2.setError("Required");
            else{
                db.addOperation(new Results(val1.getText().toString(),val2.getText().toString(),'+',new DecimalFormat("#.###").format(sum())));
                dialog.dismiss();
                val1.setText("");
                val2.setText("");
                if (!db.cursorToArray(results).isEmpty()){
                    recycler.setLayoutManager(new LinearLayoutManager(this));
                    results.clear();
                    adapter = new CalculatorAdapter(this,db.cursorToArray(results));
                    recycler.setAdapter(adapter);
                }
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
                db.addOperation(new Results(val1.getText().toString(),val2.getText().toString(),'-',new DecimalFormat("#.###").format(sub())));
                dialog.dismiss();
                val1.setText("");
                val2.setText("");
                if (!db.cursorToArray(results).isEmpty()){
                    recycler.setLayoutManager(new LinearLayoutManager(this));
                    results.clear();
                    adapter = new CalculatorAdapter(this,db.cursorToArray(results));
                    recycler.setAdapter(adapter);
                }
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
                db.addOperation(new Results(val1.getText().toString(),val2.getText().toString(),'*',new DecimalFormat("#.###").format(multi())));
                dialog.dismiss();
                val1.setText("");
                val2.setText("");
                if (!db.cursorToArray(results).isEmpty()){
                    recycler.setLayoutManager(new LinearLayoutManager(this));
                    results.clear();
                    adapter = new CalculatorAdapter(this,db.cursorToArray(results));
                    recycler.setAdapter(adapter);
                }
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
                db.addOperation(new Results(val1.getText().toString(),val2.getText().toString(),'/',new DecimalFormat("#.###").format(divide())));
                dialog.dismiss();
                val1.setText("");
                val2.setText("");
                if (!db.cursorToArray(results).isEmpty()){
                    recycler.setLayoutManager(new LinearLayoutManager(this));
                    results.clear();
                    adapter = new CalculatorAdapter(this,db.cursorToArray(results));
                    recycler.setAdapter(adapter);
                }
            }
        });
    }
    private boolean isEmpty(){
        return val1.getText().toString().equals("")&&val2.getText().toString().equals("");
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