package thakare.xyz.uploadpicturetutorial;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageToUplaod, downloadedImage;
    Button bUploadImage, bDownloadImage, bSelectImage;
    EditText uploadImageName, downloadImageName;
    private static final int RESULT_LOAD_IMAGE = 9002;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageToUplaod = (ImageView) findViewById(R.id.imageToUplaod);
        downloadedImage = (ImageView) findViewById(R.id.downloadedImage);

        bSelectImage = (Button) findViewById(R.id.selectImageToUpload);
        bUploadImage = (Button) findViewById(R.id.bUploadImage);
        bDownloadImage = (Button) findViewById(R.id.bDownloadImage);

        uploadImageName = (EditText) findViewById(R.id.etUplaodNames);
        downloadImageName = (EditText) findViewById(R.id.etDownloadName);

        bSelectImage.setOnClickListener(this);
        bUploadImage.setOnClickListener(this);
        bDownloadImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bitmap image;
        String name;
        switch (v.getId()){
            case R.id.selectImageToUpload:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                break;
            case R.id.bUploadImage:
                image = ((BitmapDrawable) imageToUplaod.getDrawable()).getBitmap();
                name = uploadImageName.getText().toString();
                new ServerRequests(MainActivity.this).uploadImage(image, name, new GetUserCallback() {
                    @Override
                    public void flagged(boolean flag) {
                        if (flag == true){
                            Toast.makeText(MainActivity.this,"Image Upload Successful",Toast.LENGTH_SHORT).show();
                        } else {
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                            dialogBuilder.setMessage("Image Upload Failed. Try uploading again");
                            dialogBuilder.setPositiveButton("OK",null);
                            dialogBuilder.show();
                        }
                    }

                    @Override
                    public void imgData(Bitmap image) {}
                });
                break;
            case R.id.bDownloadImage:
                name = downloadImageName.getText().toString();
                new ServerRequests(MainActivity.this).fetchImage(name, new GetUserCallback() {
                    @Override
                    public void flagged(boolean flag) {}

                    @Override
                    public void imgData(Bitmap image) {
                        downloadedImage.setImageBitmap(image);
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            imageToUplaod.setImageURI(null);
            imageToUplaod.setImageURI(selectedImage);
            bSelectImage.setText("Change Image");
        }
    }

}
