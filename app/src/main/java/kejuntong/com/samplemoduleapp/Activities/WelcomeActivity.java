package kejuntong.com.samplemoduleapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import kejuntong.com.samplemoduleapp.ModelClasses.User;
import kejuntong.com.samplemoduleapp.R;
import kejuntong.com.samplemoduleapp.UtilClasses.Constants;

/**
 * Created by kejuntong on 2018-08-05.
 */

public class WelcomeActivity extends Activity {

    final static int UPLOAD_IMAGE_INTENT = 1001;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    EditText userNameInputText;
    Button getStartButton;
    ImageView addPhotoButton;

    ProgressBar spinner;

    String userPhotoUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        addPhotoButton = (ImageView) findViewById(R.id.btn_add_photo);
        userNameInputText = (EditText) findViewById(R.id.user_name);
        getStartButton = (Button) findViewById(R.id.get_start);

        spinner = findViewById(R.id.progress_bar);

        setAddPhotoButton();
        setGetStartButton();
    }

    private void setAddPhotoButton(){
        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = mAuth.getCurrentUser();

                Intent uploadImageIntent = new Intent(WelcomeActivity.this, ImageCropActivity.class);
                uploadImageIntent.putExtra(Constants.CAPTURE_PHOTO_FROM, Constants.PHOTO_FROM_GALLERY);
                uploadImageIntent.putExtra(Constants.PHOTO_PATH, "profile_images/" + user.getUid());
                startActivityForResult(uploadImageIntent, UPLOAD_IMAGE_INTENT);
            }
        });
    }

    private void setGetStartButton(){
        getStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (userNameInputText.getText().toString().isEmpty()){
                    return;
                }

                String userId = mAuth.getCurrentUser().getUid();
                String userEmail = mAuth.getCurrentUser().getEmail();
                String userName = userNameInputText.getText().toString();
                User user = new User(userEmail, userPhotoUrl, userName);
                spinner.setVisibility(View.VISIBLE);
                mDatabase.getReference().child("user").child(userId).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        spinner.setVisibility(View.GONE);
                        startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        spinner.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPLOAD_IMAGE_INTENT){
            if (resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    userPhotoUrl = bundle.getString(Constants.PHOTO_URL);
                    Glide.with(WelcomeActivity.this).load(userPhotoUrl).into(addPhotoButton);
                }
            }
        }
    }
}
