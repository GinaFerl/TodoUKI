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

public class AddTodo extends AppCompatActivity {
    EditText etJudul, etDesk, etTgl;
    Button btBuat, btBatal;
    DatabaseHelper myDb;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        etJudul = findViewById(R.id.edt_jdl);
        etDesk = findViewById(R.id.edt_desk);
        etTgl = findViewById(R.id.edt_tgl);
        btBuat = findViewById(R.id.btn_add);
        btBatal = findViewById(R.id.btn_cancel);

        myDb = new DatabaseHelper(this);
        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                UpdateLabel(); //memanggil fungsi updateLabel()
            }
        };

        etTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddTodo.this, date,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btBuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = etJudul.getText().toString();
                String desc = etDesk.getText().toString();
                String date = etTgl.getText().toString();

                if (title.equals("") || date.equals("") || desc.equals("")){
                    if (title.equals("")){
                        etJudul.setError("Judul harus diisi");
                    }if (desc.equals("")){
                        etDesk.setError("Deskripsi harus diisi");
                    }if (date.equals("")){
                        etTgl.setError("Tanggal harus diisi");
                    }
                } else {
                    boolean isInserted = myDb.insertData(title, desc, date);
                    if (isInserted) {
                        Toast.makeText(AddTodo.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddTodo.this, "Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
                    }

                    startActivity(new Intent(AddTodo.this, MainActivity.class));
                    finish();

                }
            }
        });

        btBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddTodo.this, MainActivity.class));
                finish();
            }
        });

    }

    //fungsi untuk mengupdate isi etTgl dari value myCalendar
    private void UpdateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        etTgl.setText(simpleDateFormat.format(myCalendar.getTime()));
    }
}