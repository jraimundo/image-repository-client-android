package client.imagerepository.jrdev.com.imagerepositoryclient.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import client.imagerepository.jrdev.com.imagerepositoryclient.R;

public class Test1Activity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView mPictureTakenImageView;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        mPictureTakenImageView = (ImageView) findViewById(R.id.image_view_picture_taken);

        Button button1 = (Button) findViewById(R.id.button_take_picture);
        button1.setOnClickListener(mButtonTakePictureClickListener);

        mEditText = (EditText) findViewById(R.id.edit_text_url);

        Button button2 = (Button) findViewById(R.id.button_network_request);
        button2.setOnClickListener(mButtonNetworkRequestClickListener);
    }

    private View.OnClickListener mButtonTakePictureClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } else {
                Toast.makeText(getBaseContext(), "Could not find device's camera", Toast.LENGTH_LONG).show();
            }

        }
    };

    private View.OnClickListener mButtonNetworkRequestClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Instantiate the RequestQueue
            RequestQueue queue = Volley.newRequestQueue(Test1Activity.this);
//            String url = "http://178.62.10.164:8080/rest/array";
            String url = mEditText.getText().toString();

            // Request a string response from the provided URL
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, mResponseSuccessListener, mResponseErrorListener);

            // Add the reqeust to the RequestQueue
            queue.add(stringRequest);
        }
    };

    private Response.Listener<String> mResponseSuccessListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Toast.makeText(Test1Activity.this, response, Toast.LENGTH_LONG).show();
        }
    };

    private Response.ErrorListener mResponseErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(Test1Activity.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mPictureTakenImageView.setImageBitmap(imageBitmap);
        }
    }

}
