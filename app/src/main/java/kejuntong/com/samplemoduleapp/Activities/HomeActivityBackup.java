package kejuntong.com.samplemoduleapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kejuntong.com.samplemoduleapp.ModelClasses.Post;
import kejuntong.com.samplemoduleapp.R;
import kejuntong.com.samplemoduleapp.UtilClasses.Constants;

public class HomeActivityBackup extends AppCompatActivity {

    BottomNavigationView bottomNavigationBar;

    TextView mTextMessage;
    EditText postInput;
    Button testButton;
    Button uploadImageButton;
    TextView textData;
    Button logoutButton;

    FirebaseDatabase firebaseDatabase;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseDatabase = FirebaseDatabase.getInstance();

        mTextMessage = (TextView) findViewById(R.id.message);
        postInput = findViewById(R.id.post_input);
        testButton = findViewById(R.id.button_test);
        uploadImageButton = findViewById(R.id.button_upload_image);
        textData = findViewById(R.id.text_data);
        logoutButton = findViewById(R.id.button_logout);
        bottomNavigationBar = (BottomNavigationView) findViewById(R.id.navigation);

        setBottomNavigationBar();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null){
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            Uri photoUrl = currentUser.getPhotoUrl();
            boolean emailVerified = currentUser.isEmailVerified();
            String uid = currentUser.getUid();

            Toast.makeText(HomeActivityBackup.this, "name: " + name + ", email: " + email
                    + ", photoUrl: " + photoUrl + ", emailVerified: " + emailVerified + " uid: " + uid,
                    Toast.LENGTH_LONG).show();
        }

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (postInput.getText().toString().isEmpty()){
                    return;
                }
                DatabaseReference myRef = firebaseDatabase.getReference("post");
                String key = myRef.push().getKey();
                myRef.child(key).setValue(new Post(currentUser == null ? "me" : currentUser.getUid(),
                        postInput.getText().toString()));
            }
        });

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivityBackup.this, ImageCropActivity.class);
                intent.putExtra(Constants.CAPTURE_PHOTO_FROM, Constants.PHOTO_FROM_GALLERY);
                startActivity(intent);
            }
        });

        setOnDataChange();

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                finish();
            }
        });
    }

    private void setBottomNavigationBar(){
        bottomNavigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_page_1:
                        mTextMessage.setText(R.string.page_1);
                        return true;
                    case R.id.navigation_page_2:
                        mTextMessage.setText(R.string.page_2);
                        return true;
                    case R.id.navigation_page_3:
                        mTextMessage.setText(R.string.page_3);
                        return true;
                }
                return false;
            }
        });
    }

    private void setOnDataChange(){
        DatabaseReference myRef = firebaseDatabase.getReference("post");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String allPosts = "";
                for (DataSnapshot postData : dataSnapshot.getChildren()){
                    Post post = postData.getValue(Post.class);
                    allPosts = allPosts + "post by: " + post.getAuthor() +
                            ", post content: " + post.getContent() + "\n";
                }
                textData.setText(allPosts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
