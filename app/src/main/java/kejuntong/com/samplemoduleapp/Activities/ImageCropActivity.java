package kejuntong.com.samplemoduleapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;

import kejuntong.com.samplemoduleapp.R;
import kejuntong.com.samplemoduleapp.UtilClasses.Constants;
import kejuntong.com.samplemoduleapp.UtilClasses.UtilMethods;

/**
 * Created by kejuntong on 2018-04-29.
 */

public class ImageCropActivity extends Activity {

    final static int GALLERY_REQUEST = 1001;
    final static int CAMERA_REQUEST = 1002;
    final static String TEMP_PHOTO = "temp_photo.jpg";

    StorageReference mStorageRef;

    Button cancelButton;
    TextView cropButton;
    CropImageView cropImageView;

    ProgressBar spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_crop);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        cancelButton = (Button) findViewById(R.id.cancel_button);
        cropButton = (TextView) findViewById(R.id.crop_button);
        cropImageView = (CropImageView) findViewById(R.id.image_to_crop);
        spinner = findViewById(R.id.progress_bar);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            int photoFrom = bundle.getInt(Constants.CAPTURE_PHOTO_FROM);

            if (photoFrom == Constants.PHOTO_FROM_GALLERY) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            } else if (photoFrom == Constants.PHOTO_FROM_CAMERA) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(getApplicationContext().getCacheDir(), TEMP_PHOTO);
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                }
            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && data != null) {

            Uri imageUri = data.getData();

            setPhotoToCrop(imageUri);
        }

        else if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST){
            File photoFile = new File(getCacheDir(), TEMP_PHOTO);
            Uri imageUri = Uri.fromFile(photoFile);

            setPhotoToCrop(imageUri);
        }

        else {
            finish();
        }

    }

    private void setPhotoToCrop(Uri imageUri){
        findViewById(R.id.crop_layout).setVisibility(View.VISIBLE);
        cropImageView.setCropShape(CropImageView.CropShape.RECTANGLE);
        cropImageView.setAspectRatio(2, 1);
        cropImageView.setFixedAspectRatio(true);

        cropImageView.setImageUriAsync(imageUri);

        cropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spinner.setVisibility(View.VISIBLE);

                int width = UtilMethods.dpToPx(360) > 2000 ? 2000 : UtilMethods.dpToPx(360);
                int height = UtilMethods.dpToPx(180) > 1000 ? 1000 : UtilMethods.dpToPx(180);

                final Bitmap croppedBitmap = cropImageView.getCroppedImage(width, height);

                UtilMethods.saveBitmapToStorage(getApplicationContext(), croppedBitmap, Constants.CROPPED_IMAGE_FILE);
                Uri file = UtilMethods.getFileUriFromStorage(getApplicationContext(), Constants.CROPPED_IMAGE_FILE);

                if (file != null){
                    StorageReference riversRef = mStorageRef.child("images/test.jpg");
                    riversRef.putFile(file)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Get a URL to the uploaded content
                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    // ...
                                    spinner.setVisibility(View.GONE);
                                }
                            });
                }



//                setResult(RESULT_OK);
//                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        File photoFile = new File(getCacheDir(), TEMP_PHOTO);
        photoFile.delete();
    }
}
