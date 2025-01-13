package com.example.simcardapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private KKAdapter kkAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setLogo(R.drawable.ic_simcard);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }


        dbHelper = new DatabaseHelper(this);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            loadKKData();
            swipeRefreshLayout.setRefreshing(false);
        });


        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddDataActivity.class);
            startActivity(intent);
        });


        loadKKData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadKKData();
    }

    private void loadKKData() {
        Cursor cursor = dbHelper.getAllKK();
        List<KKModel> kkList = new ArrayList<>();
        while (cursor.moveToNext()) {

            int kkIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_KK_ID);
            int kkNameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_KK_NAME);

            if (kkIdIndex != -1 && kkNameIndex != -1) {
                kkList.add(new KKModel(
                        cursor.getInt(kkIdIndex),
                        cursor.getString(kkNameIndex)
                ));
            }
        }
        cursor.close();


        kkAdapter = new KKAdapter(kkList, this);
        recyclerView.setAdapter(kkAdapter);
    }
}
