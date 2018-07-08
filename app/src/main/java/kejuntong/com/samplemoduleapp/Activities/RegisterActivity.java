package kejuntong.com.samplemoduleapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.theartofdev.edmodo.cropper.CropImageActivity;

import kejuntong.com.samplemoduleapp.ModelClasses.User;
import kejuntong.com.samplemoduleapp.R;
import kejuntong.com.samplemoduleapp.UtilClasses.Constants;

/**
 * Created by kejuntong on 2018-04-22.
 */

public class RegisterActivity extends Activity {
    final static String TAG = "RegisterActivity";
    final static int UPLOAD_IMAGE_INTENT = 1001;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    Button registerButton;
    EditText email;
    EditText password;
    EditText passwordAgain;

    String userEmail = "";
    String userPassword = "";

    ProgressBar spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        passwordAgain = findViewById(R.id.password_again);
        registerButton = findViewById(R.id.register);

        spinner = findViewById(R.id.progress_bar);

        setRegisterButton();
    }

    private void setRegisterButton(){

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    return;
                }

                userEmail = email.getText().toString();
                userPassword = password.getText().toString();

                spinner.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                spinner.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    Toast.makeText(RegisterActivity.this, "Authentication success.",
                                            Toast.LENGTH_SHORT).show();

                                    Intent uploadImageIntent = new Intent(RegisterActivity.this, ImageCropActivity.class);
                                    uploadImageIntent.putExtra(Constants.CAPTURE_PHOTO_FROM, Constants.PHOTO_FROM_GALLERY);
                                    uploadImageIntent.putExtra(Constants.PHOTO_PATH, "profile_images/" + user.getUid());
                                    startActivityForResult(uploadImageIntent, UPLOAD_IMAGE_INTENT);
//                            updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                                }
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
                    String userId = mAuth.getCurrentUser().getUid();
                    String userPhotoUrl = bundle.getString(Constants.PHOTO_URL);
                    User user = new User(userEmail, userPhotoUrl, "Kevin");
                    spinner.setVisibility(View.VISIBLE);
                    mDatabase.getReference().child("user").child(userId).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            spinner.setVisibility(View.GONE);
                            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            spinner.setVisibility(View.GONE);
                        }
                    });

                }
            }
        }
    }
}
