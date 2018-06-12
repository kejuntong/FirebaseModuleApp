package kejuntong.com.samplemoduleapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private ArrayList<PostItem> postList;
    private ArrayList<User> userList;

    public AllPostAdapter(Context context, ArrayList<PostItem> postItemList, ArrayList<User> userList){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.postList = postItemList;
        this.userList = userList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }

        CircleImageView profileImage;
        TextView posterNameText;
        TextView captionText;
        ImageView postImage;
        TextView lastCommentText;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_all_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.profileImage = view.findViewById(R.id.profile_image);
        viewHolder.posterNameText = view.findViewById(R.id.poster_name);
        viewHolder.captionText = view.findViewById(R.id.post_caption);
        viewHolder.postImage = view.findViewById(R.id.post_image);
        viewHolder.lastCommentText = view.findViewById(R.id.text_last_comment);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        PostItem postItem = postList.get(position);
        User user = userList.get(position);

        if (user != null) {
            holder.posterNameText.setText(user.getUsername());
            Glide.with(mContext).load(user.getProfileImageUrl()).into(holder.profileImage);
        } else {
            holder.posterNameText.setText("empty user");
            holder.profileImage.setImageBitmap(null);
        }

        String imageUrl = postItem.getPhotoURL();
        Glide.with(mContext).load(imageUrl).into(holder.postImage);

        holder.captionText.setText(postItem.getCaption());

        String lastComment = postItem.getLatestCommentText();
        if (lastComment != null){
            holder.lastCommentText.setVisibility(View.VISIBLE);
            String commentText = postItem.getLatestCommentPoster() + ": " + lastComment;
            holder.lastCommentText.setText(commentText);
        } else {
            holder.lastCommentText.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }



}
