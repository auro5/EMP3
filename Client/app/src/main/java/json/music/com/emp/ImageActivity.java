package json.music.com.emp;

/**
 * Created by Aurobind on 31-03-2017.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener  {

    private Button buttonChoose;
    private Button buttonUpload;
    private Uri fileUri;
    private ImageView imageView;
    String picturePath;
    Uri selectedImage;

    //   private EditText editTextName;

    private Bitmap bitmap;
    Bitmap photo;

    private int PICK_IMAGE_REQUEST = 1;

    //private String UPLOAD_URL ="http://emoplayer.pe.hu/upload.php";
    private String UPLOAD_URL = "http://192.168.43.72/UploadImage/upload.php";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_main);

        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);

        //   editTextName = (EditText) findViewById(R.id.editText);

        imageView  = (ImageView) findViewById(R.id.imageView);

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return encodedImage;
    }

    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response

                        Toast.makeText(ImageActivity.this, s , Toast.LENGTH_LONG).show();
                        Intent i = new Intent(ImageActivity.this, LanguageSelect.class);
                        i.putExtra("detected_emotion",s);
                        startActivity(i);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(ImageActivity.this, "Unable to upload image. Try again", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(ImageActivity.this, LanguageSelect.class);
                        startActivity(i);

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Getting Image Name
                // String name = editTextName.getText().toString().trim();
                String name="hello";

                //Creating parameters
                Map<String,String> params = new Hashtable<>();

                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, name);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void showFileChooser() {
        /*Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);*/
        Intent camera_intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(camera_intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 ) {

            Uri filePath = data.getData();
            // try {
            //Getting the Bitmap from Gallery
            //bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            bitmap = (Bitmap) data.getExtras().get("data");
            //Setting the Bitmap to ImageView
            imageView.setImageBitmap(bitmap);
            /*} catch (IOException e) {
                e.printStackTrace();
            }*/
        }

    }

    @Override
    public void onClick(View v) {

        if(v == buttonChoose){
            showFileChooser();
        }

        if(v == buttonUpload){
            uploadImage();
        }
    }
}