package com.example.travelmanager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DataDisplay extends AppCompatActivity {

    List<Data> dataList = new ArrayList<>();
    RecyclerView rvData;
    RVDataAdapter adapter;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);
        mSharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);
        onRestore();
        initView();
        initListener();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Gson gson = new Gson();
        String json = gson.toJson(dataList);
        Intent intent = new Intent();
        intent.putExtra("Return_cat", json);
        intent.putExtra("C_NAME", getIntent().getStringExtra("C_NAME"));
        setResult(2, intent);
        finish();
        return true;
    }

    private void onRestore() {
        String json = getIntent().getStringExtra("CATEGORY_LIST");
        Gson gson = new Gson();
        ArrayList<Category> categories = gson.fromJson(json,
                new TypeToken<ArrayList<Category>>(){}.getType());
        for (Category c: categories){
            if (c.getName().equals(getIntent().getStringExtra("C_NAME"))){
                if (c.cat_list.size() > 0){
                    dataList.addAll(c.cat_list);
                    break;
                }
            }
        }
    }

    private void initListener() {
        TextView tvTitle = findViewById(R.id.categ_title);
        final EditText editName = findViewById(R.id.name_edit);
        final EditText editExp = findViewById(R.id.epxense_edit);
        tvTitle.setText(getIntent().getStringExtra("C_NAME"));
        Button button = findViewById(R.id.add_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String nameText = editName.getText().toString();
                String expText = editExp.getText().toString();
                if (nameText.length() > 0 && expText.length() > 0) {
                    Log.d("D", "hi" + nameText + expText);
                    dataList.add(new Data(nameText,
                            Float.parseFloat(expText)));
                } else {
                    Toast.makeText(DataDisplay.this, "Cannot leave fields empty",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Gson gson = new Gson();
        String json = gson.toJson(dataList);
        Intent intent = new Intent();
        intent.putExtra("Return_cat", json);
        intent.putExtra("C_NAME", getIntent().getStringExtra("C_NAME"));
        setResult(2, intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Gson gson = new Gson();
        String json = gson.toJson(dataList);
        Intent intent = new Intent();
        intent.putExtra("Return_cat", json);
        intent.putExtra("C_NAME", getIntent().getStringExtra("C_NAME"));
        setResult(2, intent);
        finish();
    }



    private void initView() {
        rvData = findViewById(R.id.rv_data);
        adapter = new RVDataAdapter(this, dataList, getIntent());
        rvData.setAdapter(adapter);
        rvData.setLayoutManager(new LinearLayoutManager(this));
    }
}
