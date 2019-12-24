package com.jonytripura.foodreview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private static int PICK_IMAGE_REQUEST = 1;
    private Uri photo;
    private DatabaseReference myRootRef;

    private Button photoBtn;
    private EditText foodTitle, foodPrice, foodRating, foodDesc;
    private Spinner spinner;
    ArrayList<String> items = new ArrayList<String>();
    int currentItem = 0;
    String title, description, price, rating;
    private Toolbar toolbar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        foodTitle = findViewById(R.id.editText);
        foodDesc = findViewById(R.id.editText2);
        foodPrice = findViewById(R.id.editText33);
        foodRating= findViewById(R.id.editText34);
        photoBtn = findViewById(R.id.button);

        spinner = findViewById(R.id.spinner);

        progressBar = findViewById(R.id.progress_add);

        toolbar = findViewById(R.id.toolbar_admin_dashboard);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Admin Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        items.add("Pizza");
        items.add("Burger");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentItem == position) {


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "select picture"), PICK_IMAGE_REQUEST);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            photo = data.getData();
        }

    }

    public void Add(View view) {
        String item = spinner.getSelectedItem().toString();
        title = foodTitle.getText().toString().trim();
        description = foodDesc.getText().toString().trim();
        price = foodPrice.getText().toString().trim();
        rating = foodRating.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);
        if (item.equals("Pizza")) {

            if (photo != null) {
                StorageReference filepath = FirebaseStorage.getInstance().getReference().child("Pizza");
                final StorageReference file_name = filepath.child(System.currentTimeMillis() + ".jpg");

                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(rating) && !TextUtils.isEmpty(description)) {
                    file_name.putFile(photo).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            file_name.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    myRootRef = FirebaseDatabase.getInstance().getReference("Pizza");
                                    String Id = myRootRef.push().getKey();
                                    Pizza pizza = new Pizza(Id, title, price, rating, description, String.valueOf(uri));
                                    myRootRef.child(Id).setValue(pizza);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getApplicationContext(), "Item Added", Toast.LENGTH_SHORT).show();


                                }
                            });

                        }
                    });
                }
            } else if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(rating) && !TextUtils.isEmpty(description)) {
                myRootRef = FirebaseDatabase.getInstance().getReference("Pizza");
                String Id = myRootRef.push().getKey();
                Pizza pizza = new Pizza(Id, title, price, rating, description);
                myRootRef.child(Id).setValue(pizza);
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Item Added", Toast.LENGTH_SHORT).show();
            }else {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Please fill up all require information", Toast.LENGTH_SHORT).show();
            }

        } else {

            if (photo != null) {
                StorageReference filepath = FirebaseStorage.getInstance().getReference().child("Burger");
                final StorageReference file_name = filepath.child(System.currentTimeMillis() + ".jpg");

                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(rating) && !TextUtils.isEmpty(description)) {
                    file_name.putFile(photo).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            file_name.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    myRootRef = FirebaseDatabase.getInstance().getReference("Burger");
                                    String Id = myRootRef.push().getKey();
                                    Burger burger = new Burger(Id, title, price, rating, description, String.valueOf(uri));
                                    myRootRef.child(Id).setValue(burger);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getApplicationContext(), "Item Added", Toast.LENGTH_SHORT).show();


                                }
                            });

                        }
                    });
                }
            } else if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(rating) && !TextUtils.isEmpty(description)) {
                myRootRef = FirebaseDatabase.getInstance().getReference("Burger");
                String Id = myRootRef.push().getKey();
                Burger burger = new Burger(Id, title, price, rating, description);
                myRootRef.child(Id).setValue(burger);
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Item Added", Toast.LENGTH_SHORT).show();
            }
            else {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Please fill up all require information", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.logout:
                logout();
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }


    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent logout = new Intent(this, AdminLoginActivity.class);
        logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logout);
    }


    @Override
    public void onBackPressed() {
       super.onBackPressed();
    }

}
