package com.xenome.Students_Record;


import androidx.appcompat.app.ActionBar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class DATABASE extends AppCompatActivity {

    TextView tv, tv2;
    String s1, s2, s3, data;
    Button b1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        Bundle extras = getIntent().getExtras();

        tv = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv2.setMovementMethod(new ScrollingMovementMethod());
        b1 = findViewById(R.id.save);

//        b2 = findViewById(R.id.View);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        s1 = (String) extras.get("Val");
        s2 = (String) extras.get("mes");
        s3 = (String) extras.get("id");

        tv.setText(s1);
        tv2.setText(s2);

        data = s2 + "\n\n";

//        File outerFolder = new File(Environment.getExternalStorageDirectory(), folder_main);
        final File SqliteDirectory = new File("/sdcard/Records/");
        SqliteDirectory.mkdirs();
        final String FileName = s3 +  "-data.txt";
          final File outputFile = new File(SqliteDirectory, FileName);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




      //  FileOutputStream fos = new FileOutputStream(outputFile);
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(outputFile);
                    fos.write(data.getBytes());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fos = openFileOutput(FileName, MODE_APPEND);
                    fos.write(data.getBytes());


                    Toast.makeText(DATABASE.this, "Saved To" + outputFile , Toast.LENGTH_LONG).show();
                    tv2.setText("");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        });



    }





    //    public void view(View v) {
//        FileInputStream fis = null;
//
//        try {
//            fis = openFileInput(FileName);
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader br = new BufferedReader(isr);
//            StringBuffer sb = new StringBuffer();
//
//            while ((data = br.readLine()) != null) {
//                sb.append(data).append("");
//            }
//
//            tv.setText("Saved Data");
//            tv2.setText(sb.toString());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fis != null) {
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    }
