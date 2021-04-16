package com.gferl.latihanuki;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditTodo extends AppCompatActivity {
    EditText etJudul, etDesk, etTgl;
    Button btUpdate, btHapus;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        etJudul = findViewById(R.id.edt_jdl);
        etDesk = findViewById(R.id.edt_desk);
        etTgl = findViewById(R.id.edt_tgl);
        btUpdate = findViewById(R.id.btn_edt);
        btHapus = findViewById(R.id.btn_delete);

        myCalendar = Calendar.getInstance();
        myDb = new DatabaseHelper(this);

        etJudul.setText(getIntent().getStringExtra("titletodo"));
        etDesk.setText(getIntent().getStringExtra("desctodo"));
        etTgl.setText(getIntent().getStringExtra("datetodo"));

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                UpdateLabel();
            }
        };

        etTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditTodo.this, date,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etJudul.getText().toString();
                String desc = etDesk.getText().toString();
                String date = etTgl.getText().toString();
                String id = getIntent().getStringExtra("idtodo");

                if (title.equals("") || desc.equals("") || date.equals("")) {
                    if (title.equals("")){
                        etJudul.setError("Judul harus diisi");
                    }if (desc.equals("")){
                        etDesk.setError("Deskripsi harus diisi");
                    }if (date.equals("")){
                        etTgl.setError("Tanggal harus diisi");
                    }
                } else {
                    boolean isUpdate = myDb.updateData(title, desc, date, id);

                    if (isUpdate) {
                        Toast.makeText(EditTodo.this, "Data berhasil diubah", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditTodo.this, "Data gagal diubah", Toast.LENGTH_SHORT).show();
                    }

                    startActivity(new Intent(EditTodo.this, MainActivity.class));
                    finish();
                }
            }
        });

        btHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = getIntent().getStringExtra("idtodo");
                Integer deletedRows = myDb.deleteData(id);

                if (deletedRows > 0) {
                    Toast.makeText(EditTodo.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditTodo.this, "Data gagal dihapus", Toast.LENGTH_SHORT).show();
                }

                startActivity(new Intent(EditTodo.this, MainActivity.class));
                finish();
            }
        });
    }

    private void UpdateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        etTgl.setText(simpleDateFormat.format(myCalendar.getTime()));
    }
}