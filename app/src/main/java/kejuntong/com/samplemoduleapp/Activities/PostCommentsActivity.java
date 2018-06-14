package kejuntong.com.samplemoduleapp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kejuntong.com.samplemoduleapp.Adapters.PostCommentsAdapter;
import kejuntong.com.samplemoduleapp.ModelClasses.Comment;
import kejuntong.com.samplemoduleapp.ModelClasses.User;
import kejuntong.com.samplemoduleapp.R;
import kejuntong.com.samplemoduleapp.UtilClasses.Constants;

/**
 * Created by kejuntong on 2018-06-12.
 */

public class PostCommentsActivity extends Activity {

    FirebaseDatabase firebaseDatabase;

    RecyclerView mRecyclerView;
    PostCommentsAdapter mAdapter;
    ArrayList<Comment> commentArrayList;
    ArrayList<User> posters;

    String postKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comments);

        postKey = getIntent().getStringExtra(Constants.POST_KEY);
        firebaseDatabase = FirebaseDatabase.getInstance();

        mRecyclerView = findViewById(R.id.comments_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        commentArrayList = new ArrayList<>();
        posters = new ArrayList<>();
        mAdapter = new PostCommentsAdapter(this, commentArrayList, posters);
        mRecyclerView.setAdapter(mAdapter);

        loadData();
    }

    private void loadData(){
        DatabaseReference myRef = firebaseDatabase.getReference("post-comment-2").child(postKey);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long postCount = dataSnapshot.getChildrenCount();

                commentArrayList.clear();
                posters.clear();
                int position = 0;
                for (DataSnapshot postData : dataSnapshot.getChildren()){
                    Comment comment = postData.getValue(Comment.class);
                    commentArrayList.add(comment);
                    posters.add(null);
                    if (comment != null) {
                        fetchUser(position, comment.getUid(), postCount);
                    }
                    position++;
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchUser(final int position, String uid, final long postCount){
        if (uid == null){
            return;
        }
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users").child(uid);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                posters.set(position, user);

                if (position == postCount-1){
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
