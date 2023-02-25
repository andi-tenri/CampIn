package com.example.campin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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

public class PinjamBarangActivity extends AppCompatActivity {

    TextView TVnama_barang,TVruangan,TVlokasi,navnamalengkap,navnim;
    String id,nama_barang,ruangan,lokasi,foto,jumlah, nama, nim;
    EditText ETjumlah, ETnama, ETnim;
    CircleImageView image;
    ProgressDialog progressDialog;
    Button BTNPinjam;
    ImageView ic_kembali;
    ImageButton tambah_foto_barang;

    SharedPreferences sharedPreferences;

    public static final String SHARED_PREF_NAME     = "camppref1";
    public static final String KEY_NAMA_LENGKAP     = "nama_lengkap";
    public static final String KEY_NIM              = "nim";

    String pilihan;
    private static final int PHOTO_REQUEST_GALLERY = 1;//gallery
    static final int REQUEST_TAKE_PHOTO = 1;
    final int CODE_GALLERY_REQUEST = 999;
    String rPilihan[]= {"Take a photo","From Album"};
    public final String APP_TAG = "MyApp";
    Bitmap bitMap = null;
    public String photoFileName = "photo.jpg";
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinjam_barang);

        //Intent, get data from main
        id                  = getIntent().getStringExtra("id");
        nama_barang         = getIntent().getStringExtra("nama_barang");
        ruangan             = getIntent().getStringExtra("ruangan");
        lokasi              = getIntent().getStringExtra("lokasi");


        TVnama_barang       = findViewById(R.id.TVP_nama_barang);
        TVruangan           = findViewById(R.id.TVP_ruangan);
        TVlokasi            = findViewById(R.id.TVP_lokasi);
        ETjumlah            = findViewById(R.id.ETP_jumlah);
        ETnama              = findViewById(R.id.ETP_nama);
        ETnim               = findViewById(R.id.ETP_nim);
        BTNPinjam           = findViewById(R.id.BTNPinjamBarang);
        ic_kembali          = findViewById(R.id.ic_kembali_pinjambarang);
        navnamalengkap      = findViewById(R.id.nama_navmenu);
        navnim              = findViewById(R.id.nim_navmenu);

        image               = findViewById(R.id.pic_barang);
        tambah_foto_barang  = findViewById(R.id.IM_foto_barang_pinjam);

        sharedPreferences   = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String namelengkap  = sharedPreferences.getString(KEY_NAMA_LENGKAP, null);
        String nim1          = sharedPreferences.getString(KEY_NIM, null);

        TVnama_barang.setText(nama_barang);
        TVruangan.setText(ruangan);
        TVlokasi.setText(lokasi);
        navnamalengkap.setText(namelengkap);
        navnim.setText(nim1);


        progressDialog = new ProgressDialog(this);

        ic_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PinjamBarangActivity.this,List_Barang_Activity.class);
                startActivity(intent);
            }
        });

        tambah_foto_barang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (bitMap != null) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PinjamBarangActivity.this);
                    alertDialogBuilder.setMessage("Do yo want to take photo again?");

                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //Toast.makeText(context,"You clicked yes button",Toast.LENGTH_LONG).show();
                            //call fuction of TakePhoto
                            TakePhoto();
                        }
                    });

                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
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

        BTNPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Meminjam Barang...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                jumlah     = ETjumlah.getText().toString();
                nama       = ETnama.getText().toString();
                nim        = ETnim.getText().toString();

                if (bitMap == null){

                    AlertDialog.Builder builder = new AlertDialog.Builder(PinjamBarangActivity.this);
                    builder.setMessage("Silahkan memotret kondisi barang sebelum dipinjam!");
                    AlertDialog alert1 = builder.create();
                    alert1.show();
                    progressDialog.dismiss();

                }
                else {

                    validasiData();

                }
            }
        });

    }

    void validasiData(){
        if(jumlah.equals("") || nama.equals("") || nim.equals("")) {
            progressDialog.dismiss();
            Toast.makeText(PinjamBarangActivity.this, "Periksa kembali data yang anda masukkan !", Toast.LENGTH_SHORT).show();
        }else {
            kirimData();
        }
    }

    // to take image
    public  void TakePhoto(){
        // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        AlertDialog.Builder builder = new AlertDialog.Builder(PinjamBarangActivity.this);
        builder.setTitle("Select");
        builder.setItems(rPilihan, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                pilihan = String.valueOf(rPilihan[which]);

                if (pilihan.equals("Take a photo"))
                {
                    // create Intent to take a picture and return control to the calling application
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Create a File reference to access to future access
                    photoFile = getPhotoFileUri(photoFileName);

                    // wrap File object into a content provider
                    // required for API >= 24
                    // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
                    String authorities = getPackageName() + ".fileprovider";
                    Uri fileProvider = FileProvider.getUriForFile(PinjamBarangActivity.this, authorities, photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

                    // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
                    // So as long as the result is not null, it's safe to use the intent.
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        // Start the image capture intent to take photo
                        startActivityForResult(intent, REQUEST_TAKE_PHOTO);

                    }
                }
                else
                {

                    ActivityCompat.requestPermissions(PinjamBarangActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            CODE_GALLERY_REQUEST);

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
                startActivityForResult(Intent.createChooser(intent, "Select Image"), CODE_GALLERY_REQUEST);
            }else{
                Toast.makeText(getApplicationContext(), "You don't have permission to access gallery!", Toast.LENGTH_LONG).show();
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
                // by this point we have the camera photo on disk
                //Bitmap takenImage = BitmapFactory.decodeFile(String.valueOf(photoFile));
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                bitMap = decodeSampledBitmapFromFile(String.valueOf(photoFile), 1000, 700);
                image.setImageBitmap(bitMap);
            } else { // Result was a failure
                Toast.makeText(PinjamBarangActivity.this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
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

    //get data photo
    public File getPhotoFileUri(String fileName)  {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            // Use `getExternalFilesDir` on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.
            File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d(APP_TAG, "failed to create directory");
            }
            File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

            return file;

        }
        return null;
    }

    //set photo
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

    // Returns true if external storage for photos is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }


    // get encode image to minimize image
    public String getStringImage(Bitmap bmp){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    void kirimData(){
        String foto = getStringImage(bitMap);
        AndroidNetworking.post("https://tkjalpha19.com/mobile/api_kelompok_1/addpeminjaman.php")
                .addBodyParameter("nama_barang",""+nama_barang)
                .addBodyParameter("ruangan",""+ruangan)
                .addBodyParameter("lokasi",""+lokasi)
                .addBodyParameter("jumlah",""+jumlah)
                .addBodyParameter("nama",""+nama)
                .addBodyParameter("nim",""+nim)
                .addBodyParameter("foto",""+foto)
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d("cekTambah",""+response);
                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan = response.getString("result");
                            Toast.makeText(PinjamBarangActivity.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            if(status){
                                new AlertDialog.Builder(PinjamBarangActivity.this)
                                        .setMessage("Berhasil Meminjam Barang!")
                                        .setCancelable(false)
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(PinjamBarangActivity.this, PeminjamanActivity.class);
                                                startActivity(i);
                                            }
                                        })
                                        .show();
                            }
                            else{
                                new AlertDialog.Builder(PinjamBarangActivity.this)
                                        .setMessage("Gagal Meminjam Barang!")
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(PinjamBarangActivity.this, List_Barang_Activity.class);
                                                startActivity(i);
                                            }
                                        })
                                        .setCancelable(false)
                                        .show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ErrorTambahData",""+anError.getErrorBody());
                    }
                });
    }

}