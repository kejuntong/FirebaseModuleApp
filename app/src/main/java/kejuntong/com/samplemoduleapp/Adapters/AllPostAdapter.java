package kejuntong.com.samplemoduleapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kejuntong.com.samplemoduleapp.Activities.PostCommentsActivity;
import kejuntong.com.samplemoduleapp.Activities.UserProfileActivity;
import kejuntong.com.samplemoduleapp.ModelClasses.Post;
import kejuntong.com.samplemoduleapp.ModelClasses.PostItem;
import kejuntong.com.samplemoduleapp.ModelClasses.User;
import kejuntong.com.samplemoduleapp.R;
import kejuntong.com.samplemoduleapp.UtilClasses.Constants;

/**
 * Created by kejuntong on 2018-05-21.
 */

public class AllPostAdapter extends RecyclerView.Adapter<AllPostAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Post> postList;

    public AllPostAdapter(Context context, ArrayList<Post> postList){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.postList = postList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }

        CircleImageView profileImage;
        TextView posterNameText;
        TextView creditText;
        TextView captionText;
        ImageView postImage;
//        TextView lastCommentText;

        ImageButton replyButton;
        ImageButton likeButton;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_all_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.profileImage = view.findViewById(R.id.profile_image);
        viewHolder.posterNameText = view.findViewById(R.id.poster_name);
        viewHolder.creditText = view.findViewById(R.id.credit);
        viewHolder.captionText = view.findViewById(R.id.post_caption);
        viewHolder.postImage = view.findViewById(R.id.post_image);
//        viewHolder.lastCommentText = view.findViewById(R.id.text_last_comment);

        viewHolder.replyButton = view.findViewById(R.id.button_reply);
        viewHolder.likeButton = view.findViewById(R.id.button_like);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Post post = postList.get(position);

        holder.posterNameText.setText(post.poster_name);
        String posterPhotoUrl = post.poster_photo_url;
        if (posterPhotoUrl != null && !posterPhotoUrl.isEmpty()){
            Glide.with(mContext).load(posterPhotoUrl).into(holder.profileImage);
        } else {
            holder.profileImage.setImageBitmap(null);
        }

        holder.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, UserProfileActivity.class));
            }
        });

        Glide.with(mContext).load(post.post_photo_url).into(holder.postImage);

        boolean provideOrRequest = post.provide_or_request;
        if (provideOrRequest){
            holder.creditText.setText("provide service, need credit: " + String.valueOf(post.credit));
        } else {
            holder.creditText.setText("request service, provide credit: " + String.valueOf(post.credit));
        }

        holder.captionText.setText(post.post_title);

//        String lastComment = postItem.getLatestCommentText();
//        if (lastComment != null){
//            holder.lastCommentText.setVisibility(View.VISIBLE);
//            String commentText = postItem.getLatestCommentPoster() + ": " + lastComment;
//            holder.lastCommentText.setText(commentText);
//        } else {
//            holder.lastCommentText.setVisibility(View.GONE);
//        }

//        holder.replyButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String postKey = postKeys.get(position);
//                Intent intent = new Intent(mContext, PostCommentsActivity.class);
//                intent.putExtra(Constants.POST_KEY, postKey);
//                mContext.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }



}
