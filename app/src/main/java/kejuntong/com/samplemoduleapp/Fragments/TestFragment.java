package kejuntong.com.samplemoduleapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kejuntong.com.samplemoduleapp.Activities.HomeActivityBackup;
import kejuntong.com.samplemoduleapp.Activities.ImageCropActivity;
import kejuntong.com.samplemoduleapp.ModelClasses.Post;
import kejuntong.com.samplemoduleapp.R;
import kejuntong.com.samplemoduleapp.UtilClasses.Constants;

/**
 * Created by kejuntong on 2018-05-06.
 */

public class TestFragment extends BaseFragment {

    View fragmentView;

    TextView mTextMessage;
    EditText postInput;
    Button testButton;
    Button uploadImageButton;
    TextView textData;
    Button logoutButton;

    FirebaseDatabase firebaseDatabase;

    public TestFragment(){
        setFragmentName(Constants.THIRD_FRAGMENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_test, container, false);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();

        mTextMessage = fragmentView.findViewById(R.id.message);
        postInput = fragmentView.findViewById(R.id.post_input);
        testButton = fragmentView.findViewById(R.id.button_test);
        uploadImageButton = fragmentView.findViewById(R.id.button_upload_image);
        textData = fragmentView.findViewById(R.id.text_data);
        logoutButton = fragmentView.findViewById(R.id.button_logout);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (postInput.getText().toString().isEmpty()){
                    return;
                }
                DatabaseReference myRef = firebaseDatabase.getReference("post");
                String key = myRef.push().getKey();
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                myRef.child(key).setValue(new Post(currentUser == null ? "me" : currentUser.getUid(),
                        postInput.getText().toString()));
            }
        });

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ImageCropActivity.class);
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
                getActivity().finish();
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
}
