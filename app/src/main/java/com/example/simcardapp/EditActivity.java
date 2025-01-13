package com.example.simcardapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {
    private EditText kkNameEditText, simTypeEditText, simNumberEditText;
    private Button addSimButton, saveButton, cancelButton;
    private LinearLayout simListLayout;
    private List<String[]> simCards = new ArrayList<>();
    private DatabaseHelper dbHelper;
    private int kkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Edit Data SIM");
        }


        dbHelper = new DatabaseHelper(this);


        kkNameEditText = findViewById(R.id.edit_kk_name);
        simTypeEditText = findViewById(R.id.edit_sim_type);
        simNumberEditText = findViewById(R.id.edit_sim_number);
        addSimButton = findViewById(R.id.button_add_sim);
        saveButton = findViewById(R.id.button_save);
        cancelButton = findViewById(R.id.button_cancel);
        simListLayout = findViewById(R.id.sim_list_layout);


        kkId = getIntent().getIntExtra("KK_ID", -1);
        String kkName = getIntent().getStringExtra("KK_NAME");

        if (kkId != -1) {
            kkNameEditText.setText(kkName);
        }


        addSimButton.setOnClickListener(v -> addSimCard());


        saveButton.setOnClickListener(v -> saveData());


        cancelButton.setOnClickListener(v -> finish());
    }

    private void addSimCard() {
        String simType = simTypeEditText.getText().toString().trim();
        String simNumber = simNumberEditText.getText().toString().trim();

        if (simType.isEmpty() || simNumber.isEmpty()) {
            Toast.makeText(this, "Tipe SIM Card dan Nomor tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }


        simCards.add(new String[]{simType, simNumber});


        TextView simTextView = new TextView(this);
        simTextView.setText("Tipe: " + simType + ", Nomor: " + simNumber);
        simTextView.setTextSize(16);
        simTextView.setPadding(8, 8, 8, 8);
        simListLayout.addView(simTextView);


        simTypeEditText.setText("");
        simNumberEditText.setText("");
    }

    private void saveData() {
        String updatedKKName = kkNameEditText.getText().toString().trim();

        if (updatedKKName.isEmpty()) {
            Toast.makeText(this, "Nama Kepala Keluarga tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }


        boolean kkUpdateSuccess = dbHelper.updateKK(kkId, updatedKKName);


        for (String[] simCard : simCards) {
            String simType = simCard[0];
            String simNumber = simCard[1];
            dbHelper.insertSIM(simNumber, simType, kkId);
        }

        if (kkUpdateSuccess) {
            Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
