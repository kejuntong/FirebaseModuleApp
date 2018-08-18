package kejuntong.com.samplemoduleapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

import kejuntong.com.samplemoduleapp.ModelClasses.Post;
import kejuntong.com.samplemoduleapp.R;
import kejuntong.com.samplemoduleapp.UtilClasses.Constants;

/**
 * Created by kejuntong on 2018-08-12.
 */

public class PostActivity extends AppCompatActivity {

    final static int UPLOAD_IMAGE_INTENT = 1001;

    EditText creditText;
    EditText contactNumberText;
    EditText postTitleText;
    EditText postDetailsText;
    ImageView postImageButton;
    Button postButton;
    ProgressBar progressBar;

    // true for provide, false for request
    boolean provideOrRequest = false;
    String postImageUrl;

    FirebaseDatabase mDatabase;
    String postId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        creditText = findViewById(R.id.credit);
        contactNumberText = findViewById(R.id.contact_number);
        postTitleText = findViewById(R.id.post_title);
        postDetailsText = findViewById(R.id.post_details);
        postImageButton = findViewById(R.id.post_photo);
        postButton = findViewById(R.id.post_button);
        progressBar = findViewById(R.id.progress_bar);

        mDatabase = FirebaseDatabase.getInstance();
        postId = mDatabase.getReference().child("posts").push().getKey();

        setButtons();
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if (view.getId() == R.id.option_provide){
            if (checked) {
                provideOrRequest = true;
            }
        }
        else if (view.getId() == R.id.option_request){
            if (checked) {
                provideOrRequest = false;
            }
        }
    }

    private void setButtons(){
        postImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent uploadImageIntent = new Intent(PostActivity.this, ImageCropActivity.class);
                uploadImageIntent.putExtra(Constants.CAPTURE_PHOTO_FROM, Constants.PHOTO_FROM_GALLERY);
                uploadImageIntent.putExtra(Constants.PHOTO_PATH, "post_images/" + postId + "/1");
                startActivityForResult(uploadImageIntent, UPLOAD_IMAGE_INTENT);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String creditString = creditText.getText().toString();
                String contactNumber = contactNumberText.getText().toString();
                String postTitle = postTitleText.getText().toString();
                String postDetails = postDetailsText.getText().toString();

                if (postId == null || postId.isEmpty() ||
                        postTitle.isEmpty() || creditString.isEmpty()){
                    return;
                }

                Post post = new Post(postId, provideOrRequest, Integer.valueOf(creditString),
                        postTitle, FirebaseAuth.getInstance().getCurrentUser().getUid());
                post.contact_number = contactNumber;
                post.post_details = postDetails;
                post.post_photo_url = postImageUrl;

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String posterId = user.getUid();
                String posterName = user.getDisplayName();
                String posterPhotoUrl = user.getPhotoUrl() == null ? "" : user.getPhotoUrl().toString();

                post.poster_id = posterId;
                post.poster_name = posterName;
                post.poster_photo_url = posterPhotoUrl;

                // TODO:::::::
//                Map<String, Object> postValue = post.t;

                progressBar.setVisibility(View.VISIBLE);
                mDatabase.getReference().child("posts").child(postId).setValue(post)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressBar.setVisibility(View.GONE);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        })
                        .addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {

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
                    postImageUrl = bundle.getString(Constants.PHOTO_URL);
                    Glide.with(PostActivity.this).load(postImageUrl).into(postImageButton);
                }
            }
        }
    }

}
