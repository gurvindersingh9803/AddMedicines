package com.example.addmedicines;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AddMed extends AppCompatActivity {


    private DatabaseReference mDatabase;
    EditText medName,medComposition,MRP,PTR,company;
    String mName,mComposition,mCompany,key;
    int mrp,ptr;
    ProgressDialog pd;
    private Spinner QuantitySpinner;
    public static String QuantityID="";
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;

    ImageView imageView;
    Uri mImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    Button mButtonUpload, order;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_med);

        mDatabase = FirebaseDatabase.getInstance().getReference("medicines");
        mButtonUpload = findViewById(R.id.upload);
        pd = new ProgressDialog(AddMed.this);

        mStorageRef = FirebaseStorage.getInstance().getReference("images");

        order = findViewById(R.id.orders);
        medName = findViewById(R.id.medicineName);
        medComposition = findViewById(R.id.composition);
        MRP = findViewById(R.id.MRP);
        PTR = findViewById(R.id.PTR);
        company = findViewById(R.id.company);
        QuantitySpinner = findViewById(R.id.spinnerQuantity);
        imageView = findViewById(R.id.mImageView);
        quantity();

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddMed.this,Orders.class);
                startActivity(i);
            }
        });
        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(AddMed.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });
    }

    private void quantity() {

        List<String> medicines_categories = new ArrayList<>();

        medicines_categories.add("Syrups");
        medicines_categories.add("Gels");
        medicines_categories.add("Creams");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,medicines_categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        QuantitySpinner.setAdapter(dataAdapter);

        QuantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                QuantityID = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
           Toast.makeText(parent.getContext(), QuantityID, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


    }



    public void pickImagesMedicine(View view){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(imageView);
        }
    }

    public String getFileExtension(Uri uri){

        ContentResolver contentResolver = this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    public void uploadFile() {
        if (mImageUri != null) {


            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {


                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(AddMed.this, "Upload successful", Toast.LENGTH_LONG).show();
                            pd.dismiss();

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {




                                    key = mDatabase.push().getKey();
                                    mName = medName.getText().toString();
                                    mComposition = medComposition.getText().toString();
                                    mrp = Integer.parseInt(MRP.getText().toString());
                                    ptr = Integer.parseInt(PTR.getText().toString());


                                    Model model = new Model(mName,uri.toString(),mComposition,mCompany,QuantityID,ptr,mrp);

                                    //String url = uri.toString();

                                    String uploadId = mDatabase.push().getKey();

                                    mDatabase.child(uploadId).setValue(model);
                                    medName.setText("");
                                    medComposition.setText("");
                                    company.setText("");
                                    MRP.setText("");
                                    PTR.setText("");


                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddMed.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.setTitle("Uploading.....");
                            pd.show();


                        }
                    });





        } else {
            Toast.makeText(AddMed.this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}