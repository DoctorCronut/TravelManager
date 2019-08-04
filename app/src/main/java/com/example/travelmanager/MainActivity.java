package com.example.travelmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private List<Category> categories = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = getSharedPreferences("DATA", MODE_PRIVATE);
        initDefaultCategories();
        restoreIfNeeded();
        initView();
        initListeners();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void restoreIfNeeded() {
        String obtainList = mSharedPreferences.getString("CATEGORY_LIST", "NF");
        Gson gson = new Gson();
        if (!obtainList.equals("NF")) {
            categories = gson.fromJson(obtainList, new TypeToken<ArrayList<Category>>() {
            }.getType());
        }
    }

    private void initListeners() {
        //changed to clear all for lack of time
        Button button = findViewById(R.id.add_categ_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                /*alertDialog.setTitle("Add a New Category");
                alertDialog.setMessage("Enter Category Name");*/
                alertDialog.setTitle("Alert!");
                alertDialog.setMessage("Are you sure you want to clear all of your data?");

                /*final EditText input = new EditText(MainActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                categories.add(new Category(input.getText().toString()));
                                adapter.notifyItemInserted(categories.size() - 1);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });*/
                //Clear all version
                alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        categories = new ArrayList<>();
                        initDefaultCategories();
                        initView();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alertDialog.show();
            }
        });
    }

    private void initView() {
        recyclerView = findViewById(R.id.rv_category);
        adapter = new MyAdapter(categories, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TextView tvExpense = findViewById(R.id.expense_state);
        tvExpense.setText(String.format(Locale.US, "Expenses: $%.2f", total_sum()));
    }

    private void initDefaultCategories() {
        categories.add(new Category("Travel"));
        categories.add(new Category("Food"));
        categories.add(new Category("Entertainment"));
        categories.add(new Category("Transportation"));
        categories.add(new Category("Hotel"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Gson gson = new Gson();
        if (resultCode == 2) {
            ArrayList<Data> dataList = gson.fromJson(data.getStringExtra("Return_cat"),
                    new TypeToken<ArrayList<Data>>() {
                    }.getType());
            for (Category c : categories) {
                if (c.getName().equals(data.getStringExtra("C_NAME"))) {
                    c.cat_list.clear();
                    c.cat_list.addAll(dataList);
                    break;
                }
            }
            String json = gson.toJson(categories);
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString("CATEGORY_LIST", json);
            editor.apply();
        }
        TextView tvExpense = findViewById(R.id.expense_state);
        tvExpense.setText(String.format(Locale.US, "Expenses: $%.2f", total_sum()));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        /*Gson gson = new Gson();
        String obtainList = mSharedPreferences.getString("CATEGORY_LIST", "NF");
        if(!obtainList.equals("NF")){
            Category[] test = gson.fromJson(obtainList, Category[].class);
            for (Category c: test){
                if (!categories.contains(c.getName())){
                    categories.add(c);
                }
            }
        }*/
    }

    public void onClickCalled(Category item) {
        String name = item.getName();
        Intent intent = new Intent(this, DataDisplay.class);
        intent.putExtra("C_NAME", name);
        //intent.putStringArrayListExtra("", fillNames());
        Gson gson = new Gson();
        String json = gson.toJson(categories);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        intent.putExtra("CATEGORY_LIST", json);
        editor.putString("CATEGORY_LIST", json);
        editor.apply();
        startActivityForResult(intent, 1);
    }

   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        Gson gson = new Gson();
        String json = gson.toJson(categories);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("CATEGORY_LIST", json);
        editor.apply();
    }*/

    public float total_sum() {
        float total = 0;
        for (Category c : categories) {
            total += c.calculte_expense_sum();
        }
        return total;
    }

    /*private ArrayList<String> fillNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Category c : categories) {
            names.add(c.getName());
        }
        return names;
    }*/

}
