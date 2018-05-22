package kejuntong.com.samplemoduleapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import kejuntong.com.samplemoduleapp.R;
import kejuntong.com.samplemoduleapp.UtilClasses.Constants;

/**
 * Created by kejuntong on 2018-05-06.
 */

public class AllPostFragment extends BaseFragment {

    View fragmentView;
    FirebaseDatabase firebaseDatabase;

    RecyclerView allPostRecyclerView;
    AllPostAdapter mAdapter;
    ArrayList<PostItem> postItems;

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
        mAdapter = new AllPostAdapter(getActivity(), firebaseDatabase, postItems);
        allPostRecyclerView.setAdapter(mAdapter);

        setOnDataChange();

        return fragmentView;
    }

    private void setOnDataChange(){
        DatabaseReference myRef = firebaseDatabase.getReference("Posts2");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postItems.clear();
                for (DataSnapshot postData : dataSnapshot.getChildren()){
                    PostItem post = postData.getValue(PostItem.class);
                    postItems.add(post);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
