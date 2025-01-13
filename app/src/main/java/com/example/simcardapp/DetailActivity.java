package com.example.simcardapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.database.Cursor;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private SIMAdapter simAdapter;
    private int kkId;
    private TextView kkNameDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Detail SIM Card");
        }


        dbHelper = new DatabaseHelper(this);


        recyclerView = findViewById(R.id.recyclerView_sim);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        kkId = getIntent().getIntExtra("KK_ID", -1);
        String kkName = getIntent().getStringExtra("KK_NAME");


        kkNameDetail = findViewById(R.id.kk_name_detail);
        if (kkName != null) {
            kkNameDetail.setText(kkName);
        } else {
            Toast.makeText(this, "Invalid KK Name", Toast.LENGTH_SHORT).show();
        }


        loadSIMData();


        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(v -> {
            dbHelper.deleteSIMByKK(kkId);
            dbHelper.deleteKK(kkId);
            Toast.makeText(this, "KK and associated SIM cards deleted", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void loadSIMData() {
        Cursor cursor = dbHelper.getSIMsByKK(kkId);
        List<SIMModel> simList = new ArrayList<>();
        while (cursor.moveToNext()) {

            int simIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_SIM_ID);
            int simNumberIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_SIM_NUMBER);
            int simTypeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_SIM_TYPE);
            int kkIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_KK_ID_FK);


            if (simIdIndex != -1 && simNumberIndex != -1 && simTypeIndex != -1) {
                simList.add(new SIMModel(
                        cursor.getInt(simIdIndex),
                        cursor.getString(simNumberIndex),
                        cursor.getString(simTypeIndex),
                        cursor.getInt(kkIdIndex)
                ));
            }
        }
        cursor.close();


        simAdapter = new SIMAdapter(this, simList);
        recyclerView.setAdapter(simAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
