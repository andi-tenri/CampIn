package com.example.campin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailKontakStaffActivity extends AppCompatActivity {

    EditText ETNamaStaff,ETIdStaf,ETNohp;
    String id,nama_staff,id_staff,no_hpstaff,foto;
    CircleImageView image;
    ProgressDialog progressDialog;
    ImageView BTNHapus,BTNKembali;
    ImageButton BTNImage;
    Button BTNupdate;

    String pilihan;
    private static final int PHOTO_REQUEST_GALLERY = 1;//gallery
    static final int REQUEST_TAKE_PHOTO = 1;
    final int CODE_GALLERY_REQUEST = 999;
    String rPilihan[]= {"Memotret foto", "Dari album"};
    public final String APP_TAG = "MyApp";
    public String photoFileName = "photo.jpg";
    File photoFile;

    Bitmap bitMap = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kontak_staff);

        //Intent, get data from main
        id          = getIntent().getStringExtra("id");
        nama_staff  = getIntent().getStringExtra("nama");
        id_staff    = getIntent().getStringExtra("id_staff");
        no_hpstaff  = getIntent().getStringExtra("no_hp");
        foto        = getIntent().getStringExtra("foto");


        ETNamaStaff       = findViewById(R.id.ETUp_namastaff_profil);
        ETIdStaf          = findViewById(R.id.ETUp_idstaff_profil);
        ETNohp            = findViewById(R.id.ETUp_nohp_staff);
        image             = findViewById(R.id.pic_profil_staff);
        BTNImage          = findViewById(R.id.IM_ubahfoto_staff);
        BTNupdate         = findViewById(R.id.BTNUbahKontakStaff);
        BTNHapus          = findViewById(R.id.BTNHapusDataKontak);
        BTNKembali        = findViewById(R.id.BTN_kembali_detailkontakstaffadmin);

        BTNKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailKontakStaffActivity.this,KontakAdminActivity.class);
                startActivity(intent);
            }
        });

        ETNamaStaff.setText(nama_staff);
        ETIdStaf.setText(id_staff);
        ETNohp.setText(no_hpstaff);


        if (foto.equals("")) {
            Picasso.get().load("https://tkjalpha19.com/mobile/image/noimages.png").into(image);
        } else {
            Picasso.get().load("https://tkjalpha19.com/mobile/image/" + foto).into(image);
        }

        progressDialog = new ProgressDialog(this);

        BTNImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitMap != null) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailKontakStaffActivity.this);
                    alertDialogBuilder.setMessage("Do yo want to take photo again?");

                    alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //Toast.makeText(context,"You clicked yes button",Toast.LENGTH_LONG).show();
                            //call fuction of TakePhoto
                            TakePhoto();
                        }
                    });
                    alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                } else {
                    TakePhoto();
                }
            }
        });

        BTNHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DetailKontakStaffActivity.this)
                        .setMessage("Apakah kamu ingin menghapus data staff "+nama_staff+"?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progressDialog.setMessage("Menghapus...");
                                progressDialog.setCancelable(false);
                                progressDialog.show();

                                AndroidNetworking.post("https://tkjalpha19.com/mobile/api_kelompok_1/hapusstaff.php")
                                        .addBodyParameter("id_staff",""+id_staff)
                                        .setPriority(Priority.MEDIUM)
                                        .build()
                                        .getAsJSONObject(new JSONObjectRequestListener() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                progressDialog.dismiss();
                                                try {
                                                    Boolean status = response.getBoolean("status");
                                                    Log.d("status",""+status);
                                                    String result = response.getString("result");
                                                    if(status){
                                                        new AlertDialog.Builder(DetailKontakStaffActivity.this)
                                                                .setMessage("Data Staf telah dihapus!")
                                                                .setCancelable(false)
                                                                .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        Intent i = new Intent(DetailKontakStaffActivity.this, KontakAdminActivity.class);
                                                                        startActivity(i);
                                                                        DetailKontakStaffActivity.this.finish();
                                                                    }
                                                                })
                                                                .show();

                                                    }else{
                                                        Toast.makeText(DetailKontakStaffActivity.this, ""+result, Toast.LENGTH_SHORT).show();
                                                    }
                                                }catch (Exception e){
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onError(ANError anError) {
                                                anError.printStackTrace();
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();


            }
        });

        BTNupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DetailKontakStaffActivity.this)
                        .setMessage("Apakah kamu ingin mengubah data ini?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                progressDialog.setMessage("Mengubah Data...");
                                progressDialog.setCancelable(false);
                                progressDialog.show();

                                nama_staff    = ETNamaStaff.getText().toString();
                                id_staff      = ETIdStaf.getText().toString();
                                no_hpstaff    = ETNohp.getText().toString();
                                foto          = getStringImage(bitMap);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        validatingData();
                                    }
                                },1000);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();

            }
        });
    }

    public  void TakePhoto(){
        // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailKontakStaffActivity.this);
        builder.setTitle("Pilih");
        builder.setItems(rPilihan, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                pilihan = String.valueOf(rPilihan[which]);

                if (pilihan.equals("Memotret foto")) {
                    // create Intent to take a picture and return control to the calling application
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Create a File reference to access to future access
                    photoFile = getPhotoFileUri(photoFileName);

                    // wrap File object into a content provider
                    // required for API >= 24
                    // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
                    String authorities = getPackageName() + ".fileprovider";
                    Uri fileProvider = FileProvider.getUriForFile(DetailKontakStaffActivity.this, authorities, photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

                    // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
                    // So as long as the result is not null, it's safe to use the intent.
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        // Start the image capture intent to take photo
                        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                    }
                } else {
                    ActivityCompat.requestPermissions(DetailKontakStaffActivity.this, new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE}, CODE_GALLERY_REQUEST);
                }
            }
        });
        builder.show();
    }

    //permission
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CODE_GALLERY_REQUEST){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), CODE_GALLERY_REQUEST);
            }else{
                Toast.makeText(getApplicationContext(), "Kamu tidak memiliki akses ke galeri!", Toast.LENGTH_LONG).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        //set photo size
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_TAKE_PHOTO) {

            if (resultCode == Activity.RESULT_OK) {
                bitMap = decodeSampledBitmapFromFile(String.valueOf(photoFile), 1000, 700);
                image.setImageBitmap(bitMap);
            } else { // Result was a failure
                Toast.makeText(DetailKontakStaffActivity.this, "Foto tidak dapat di potret!", Toast.LENGTH_SHORT).show();
            }

        } else {

            if (intent != null) {
                Uri photoUri = intent.getData();
                // Do something with the photo based on Uri
                bitMap = null;
                try {
                    //InputStream inputStream = getContentResolver().openInputStream(photoUri);
                    //bitMap = BitmapFactory.decodeStream(inputStream);

                    //btnImage.setVisibility(View.VISIBLE);
                    bitMap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Load the selected image into a preview
                image.setImageBitmap(bitMap);
            }
        }
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) { // BEST QUALITY MATCH

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    //get data photo
    public File getPhotoFileUri(String fileName)  {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d(APP_TAG, "failed to create directory");
            }
            File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
            return file;
        }
        return null;
    }
    // Returns true if external storage for photos is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    void validatingData(){
        if(nama_staff.equals("") || id_staff.equals("") || no_hpstaff.equals("")){
            progressDialog.dismiss();
            Toast.makeText(DetailKontakStaffActivity.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else {
            updateData();
        }
    }

    void updateData(){
        String foto = getStringImage(bitMap);
        AndroidNetworking.post("https://tkjalpha19.com/mobile/api_kelompok_1/updatekontak.php")
                .addBodyParameter("id", "" + id)
                .addBodyParameter("nama", "" + nama_staff)
                .addBodyParameter("id_staff", "" + id_staff)
                .addBodyParameter("no_hp", "" + no_hpstaff)
                .addBodyParameter("foto", "" + foto)
                .setPriority(Priority.MEDIUM)
                .setTag("Update Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d("cekUpdate", "" + response);
                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan = response.getString("result");
                            Toast.makeText(DetailKontakStaffActivity.this, "" + pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);

                            if (status) {
                                new AlertDialog.Builder(DetailKontakStaffActivity.this)
                                        .setMessage("Data berhasil di update!")
                                        .setCancelable(false)
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(DetailKontakStaffActivity.this, KontakAdminActivity.class);
                                                startActivity(i);
                                                DetailKontakStaffActivity.this.finish();
                                            }
                                        })
                                        .show();
                            } else {
                                new AlertDialog.Builder(DetailKontakStaffActivity.this)
                                        .setMessage("Gagal mengupdate data !")
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = getIntent();
                                                setResult(RESULT_CANCELED, i);
                                                DetailKontakStaffActivity.this.finish();
                                            }
                                        })
                                        .setCancelable(false)
                                        .show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.d("Cannot update your data",""+anError.getErrorBody());
                    }
                });
    }

}