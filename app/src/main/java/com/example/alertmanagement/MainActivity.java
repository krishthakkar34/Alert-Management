package com.example.alertmanagement;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.net.Uri;
import android.graphics.Bitmap;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Permission;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText name;
    private EditText email;
    private Button buttonChoose;
    private Button buttonUpload;
    private int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private static int REQUEST_CODE = 120;
    private Bitmap bitmap;
    OutputStream outputStream;
    private Uri filePath;
    private String imagename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button add_btn = findViewById(R.id.addbtn);
        Button view_btn = findViewById(R.id.viewbtn);
        name = findViewById(R.id.edittextname);
        email = findViewById(R.id.edittextemail);
        add_btn.setOnClickListener(this);
        view_btn.setOnClickListener(this);
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);

        imageView = (ImageView) findViewById(R.id.imageView);

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }
    private void showFileChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }
    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode()== Activity.RESULT_OK) {
                        // There are no request codes
                        filePath = result.getData().getData();
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                            imageView.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            });

    private void uploadImage(){

        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show();
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File dir = cw.getDir("imageDir", Context.MODE_PRIVATE);

            Toast.makeText(this, ""+dir, Toast.LENGTH_LONG).show();
            if (!dir.exists()){

                dir.mkdir();

            }
            //ImageView imageView = findViewById(R.id.imageView);
            //BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            //Bitmap bitmap = drawable.getBitmap();
            imagename = "profile.jpg";
            File file = new File(dir,imagename);
            Log.d("filedir",""+file);
            try {
                outputStream = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            Toast.makeText(MainActivity.this,"Successfuly Saved",Toast.LENGTH_SHORT).show();

            try {
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            askPermission();
        }
    }
    private void askPermission() {
        Toast.makeText(this, "askforpermission", Toast.LENGTH_SHORT).show();
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_MEDIA_IMAGES},REQUEST_CODE);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {

        if (requestCode == REQUEST_CODE)
        {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                uploadImage();
            }else {
                Toast.makeText(MainActivity.this,"Please provide the required permissions",Toast.LENGTH_SHORT).show();
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.addbtn:
                String stringname = name.getText().toString();
                String stringemail = email.getText().toString();
                String stringimage = imagename;
                if(stringname.length() <= 0 || stringemail.length() <= 0){
                    Toast.makeText(this, "Enter name and email", Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseHelperClass databaseHelperClass = new DatabaseHelperClass(MainActivity.this);
                    EmployeeModelClass employeeModelClass = new EmployeeModelClass(stringname,stringemail,stringimage);
                    databaseHelperClass.addEmployee(employeeModelClass);
                    Toast.makeText(this, "Employee Insterted successfully.", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                }
                break;
            case R.id.viewbtn:
                //Toast.makeText(this, "view clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,EmployeeViewActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonChoose:
                    showFileChooser();
                    break;
            case R.id.buttonUpload:
                uploadImage();
                break;
            default:
                break;
        }

    }
}