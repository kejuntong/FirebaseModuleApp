package kejuntong.com.samplemoduleapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kejuntong.com.samplemoduleapp.ModelClasses.PostItem;
import kejuntong.com.samplemoduleapp.ModelClasses.User;
import kejuntong.com.samplemoduleapp.R;

/**
 * Created by kejuntong on 2018-05-21.
 */

public class AllPostAdapter extends RecyclerView.Adapter<AllPostAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<PostItem> itemList;

    private DatabaseReference userDbReference;

    public AllPostAdapter(Context context, FirebaseDatabase database, ArrayList<PostItem> postItemList){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.itemList = postItemList;

        this.userDbReference = database.getReference("users");
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }

        CircleImageView profileImage;
        TextView posterNameText;
        TextView captionText;
        ImageView postImage;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_all_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.profileImage = view.findViewById(R.id.profile_image);
        viewHolder.posterNameText = view.findViewById(R.id.poster_name);
        viewHolder.captionText = view.findViewById(R.id.post_caption);
        viewHolder.postImage = view.findViewById(R.id.post_image);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        PostItem item = itemList.get(position);

        String posterUid = item.getUid();
        userDbReference.child(posterUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    holder.posterNameText.setText(user.getUsername());
                    Glide.with(mContext).load(user.getProfileImageUrl()).into(holder.profileImage);
                } else {
                    holder.posterNameText.setText("wtf");
                    holder.profileImage.setImageBitmap(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        String imageUrl = item.getPhotoUrl();
        Glide.with(mContext).load(imageUrl).into(holder.postImage);

        holder.captionText.setText(item.getCaption());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }



}
