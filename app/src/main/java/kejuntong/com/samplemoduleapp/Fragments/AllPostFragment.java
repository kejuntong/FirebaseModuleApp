package kejuntong.com.samplemoduleapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kejuntong.com.samplemoduleapp.Adapters.AllPostAdapter;
import kejuntong.com.samplemoduleapp.ModelClasses.PostItem;
import kejuntong.com.samplemoduleapp.ModelClasses.User;
import kejuntong.com.samplemoduleapp.R;
import kejuntong.com.samplemoduleapp.UtilClasses.Constants;
import kejuntong.com.samplemoduleapp.UtilClasses.LoadFBDataSynchronously;

/**
 * Created by kejuntong on 2018-05-06.
 */

public class AllPostFragment extends BaseFragment {

    final static String TAG = "AllPostFragment";

    View fragmentView;
    FirebaseDatabase firebaseDatabase;

    RecyclerView allPostRecyclerView;
    AllPostAdapter mAdapter;
    ArrayList<PostItem> postItems;
    ArrayList<User> posters;

    public AllPostFragment(){
        setFragmentName(Constants.FIRST_FRAGMENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        firebaseDatabase = FirebaseDatabase.getInstance();

        fragmentView = inflater.inflate(R.layout.fragment_all_post, container, false);
        allPostRecyclerView = fragmentView.findViewById(R.id.all_post_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        allPostRecyclerView.setLayoutManager(linearLayoutManager);

        postItems = new ArrayList<>();
        posters = new ArrayList<>();
        mAdapter = new AllPostAdapter(getActivity(), postItems, posters);
        allPostRecyclerView.setAdapter(mAdapter);

        setOnDataChange();

        return fragmentView;
    }

    private void setOnDataChange(){
        DatabaseReference myRef = firebaseDatabase.getReference("Posts2");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long postCount = dataSnapshot.getChildrenCount();
                Log.d(TAG, "total count: " + postCount);

                postItems.clear();
                posters.clear();
                int position = 0;
                for (DataSnapshot postData : dataSnapshot.getChildren()){
                    PostItem post = postData.getValue(PostItem.class);
                    postItems.add(post);
                    posters.add(null);
                    if (post != null) {
                        fetchUser(position, post.getUid(), postCount);
                    }
                    position++;
                    Log.d(TAG, "position: " + position);
                }

//                mAdapter.notifyDataSetChanged();
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

//        DataSnapshot dataSnapshot = LoadFBDataSynchronously.loadSynchronous(myRef);
//        User user = dataSnapshot.getValue(User.class);
//        posters.set(position, user);
//        if (position == postCount){
//            mAdapter.notifyDataSetChanged();
//        }

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
