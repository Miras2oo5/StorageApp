package com.example.storageapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView tvData;
    private static final int REQUEST_CODE_WRITE_PERM = 401;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvData = findViewById(R.id.tvData);

        Button btnWriteFile = findViewById(R.id.btnWriteFile);
        Button btnReadFile = findViewById(R.id.btnReadFile);

        btnWriteFile.setOnClickListener(v -> writeFile("Hello: " + new Date(System.currentTimeMillis()).toString()));
        btnReadFile.setOnClickListener(v -> tvData.setText(readFile()));

        requestNeededPermission();
    }

    public void requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Необходимо разрешение для работы с файлами", Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_PERM);
        } else {
            Toast.makeText(this, "Разрешение уже получено", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // 🔥 Добавь эту строку

        if (requestCode == REQUEST_CODE_WRITE_PERM) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Разрешение получено", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Разрешение не получено", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void writeFile(String data) {
        String file = Environment.getExternalStorageDirectory() + "/test.txt";
        try (FileOutputStream os = new FileOutputStream(file)) {
            os.write(data.getBytes());
            os.flush();
            Toast.makeText(this, "Файл записан", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFile() {
        String result = "";
        String file = Environment.getExternalStorageDirectory() + "/test.txt";
        try (FileInputStream is = new FileInputStream(file)) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int ch;
            while ((ch = is.read()) != -1) {
                bos.write(ch);
            }
            result = bos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
