package com.xenome.Students_Record;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.security.Permission;

public class MainActivity extends AppCompatActivity {
    String[] Schools = new String[]{"Amity Institute of Information Technology", "Amity School of Engineering and Technology", "Amity School of Architecture and Planning", "Amity School of Mass Communication", "Amity School of Business"};
    EditText editTextId, editName, editCC, Eno, Course;
    AutoCompleteTextView autoCompleteTextView;


    Button buttonAdd, buttonDelete, buttonUpdate, buttonGetData, buttonViewAll, buttonDeleteall;

    private int STORAGE_PERMISSION_CODE = 1;


    Databasehelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new Databasehelper(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);


        editTextId = findViewById(R.id.editText_id);
        editName = findViewById(R.id.editText_name);
//        editEmail = findViewById(R.id.editText_email);
        editCC = findViewById(R.id.editText_CC);
        autoCompleteTextView = findViewById(R.id.av);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Schools);
        autoCompleteTextView.setAdapter(adapter);
        Eno = findViewById(R.id.enrollment);
        Course = findViewById(R.id.cname);



        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requeststoragepermission();
        }


        buttonAdd = findViewById(R.id.button_add);
        buttonGetData = findViewById(R.id.button_view);
        buttonUpdate = findViewById(R.id.button_update);
        buttonDelete = findViewById(R.id.button_delete);
        buttonViewAll = findViewById(R.id.button_viewAll);
        buttonDeleteall = findViewById(R.id.button_deleteAll);


        AddData();
        getData();
        viewAll();
        updatedata();
        deleteData();
        deleteAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== R.id.abt){
        AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
        ab.setMessage("This application is made for keeping records of Students,Faculties,Employees in a easy and safe manner, which can be saved so that all of your information will be at one place." + "\n\n" +
                "Developed by TRYFIND_AJ and RETR0").show(); }

        if (item.getItemId()== R.id.day){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        if (item.getItemId() == R.id.Nmode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }


        return super.onOptionsItemSelected(item);

    }




    public void AddData() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();
                String eno = Eno.getText().toString();
                String course = Course.getText().toString();
                if (name.equals(String.valueOf(""))) {
                    editName.setError("Enter Name");
                    return;
                }
                if (eno.equals(String.valueOf(""))) {
                    Eno.setError("Enter valid Enrollment id");
                    return;
                }
                if (course.equals(String.valueOf(""))) {
                    Course.setError("Course Name is Required");
                    return;
                }
                boolean isInserted = myDB.insertData(editName.getText().toString(), autoCompleteTextView.getText().toString(), editCC.getText().toString(), Eno.getText().toString(), Course.getText().toString());
                if (isInserted == true) {
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                }


                editName.getText().clear();
                autoCompleteTextView.getText().clear();
                editCC.getText().clear();
                Eno.getText().clear();
                editTextId.getText().clear();
                Course.getText().clear();

            }
        });
    }

    public void getData() {
        buttonGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editTextId.getText().toString();

                if (id.equals(String.valueOf(""))) {
                    editTextId.setError("Enter S.no");
                    return;
                }
                Cursor cursor = myDB.getData(id);
                String data = null;

                if (cursor.moveToNext()) {
                    data = "S no: " + cursor.getString(0) + "\n" +
                            "Name: " + cursor.getString(1) + "\n" +
                            "School:" + cursor.getString(2) + "\n" +
                            "Course Count:" + cursor.getString(3) + "\n" +
                            "Enrollment No:" + cursor.getString(4) + "\n" +
                            "Course:" + cursor.getString(5) + "\r\n";
                }
                showMessage("Details\n\n", data);
            }
        });
    }

    public void viewAll() {
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = myDB.getAllData();

                if (cursor.getCount() == 0) {
                    showMessage("Error", "Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    buffer.append("S no:" + cursor.getString(0) + "\n");
                    buffer.append("Name:" + cursor.getString(1) + "\n");
                    buffer.append("School:" + cursor.getString(2) + "\n");
                    buffer.append("CC:" + cursor.getString(3) + "\n");
                    buffer.append("Enrollment No:" + cursor.getString(4) + "\n");
                    buffer.append("Course:" + cursor.getString(5) + "\n\n");

                }
                showMessage("All Data\n\n", buffer.toString());
            }
        });

    }

    public void updatedata() {
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editTextId.getText().toString();

                if (id.equals(String.valueOf(""))) {
                    editTextId.setError("Enter S.no");
                    return;
                }

                boolean isUpdate = myDB.updateData(editTextId.getText().toString(),
                        editName.getText().toString(),
                        autoCompleteTextView.getText().toString(),
                        editCC.getText().toString(),
                        Eno.getText().toString(),
                        Course.getText().toString());

                if (isUpdate == true) {
                    Toast.makeText(MainActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteData() {
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRow = myDB.deleteData(editTextId.getText().toString());

                if (deletedRow > 0) {
                    Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(MainActivity.this, "Error in Deleting", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteAll() {
        buttonDeleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Integer deleteAll = myDB.deleteAll();
                                if (deleteAll == 0) {
                                    Toast.makeText(MainActivity.this, "All Deleted", Toast.LENGTH_SHORT).show();
                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
//                                Integer deleteAll = myDB.deleteAll();
//                                if (deleteAll == 1){
//                                    Toast.makeText(MainActivity.this,"Deleted ALl",Toast.LENGTH_SHORT).show();
//                                }

                                break;
                        }
                    }
                };
                AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
                ab.setMessage("Are You Sure to delete?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();


            }
        });

    }

    private void requeststoragepermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Grant this permission for Saving")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


        private void showMessage (String title, String message){
            Intent intent = new Intent(MainActivity.this, DATABASE.class);
            intent.putExtra("id", editTextId.getText().toString());
            intent.putExtra("Val", title);
            intent.putExtra("mes", message);
            startActivity(intent);
            //        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.create();
//        builder.setCancelable(true);
//        builder.setTitle(title);
//        builder.setMessage(message);
//        builder.show();
        }


}



