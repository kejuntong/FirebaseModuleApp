package kejuntong.com.samplemoduleapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kejuntong.com.samplemoduleapp.ModelClasses.Comment;
import kejuntong.com.samplemoduleapp.ModelClasses.PostItem;
import kejuntong.com.samplemoduleapp.ModelClasses.User;
import kejuntong.com.samplemoduleapp.R;

/**
 * Created by kejuntong on 2018-05-21.
 */

public class PostCommentsAdapter extends RecyclerView.Adapter<PostCommentsAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Comment> commentList;
    private ArrayList<User> userList;

    public PostCommentsAdapter(Context context, ArrayList<Comment> commentList, ArrayList<User> userList){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.commentList = commentList;
        this.userList = userList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }

        CircleImageView profileImage;
        TextView posterNameText;
        TextView commentText;
        ImageButton replyButton;
        ImageButton likeButton;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_post_comment, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.profileImage = view.findViewById(R.id.user_photo);
        viewHolder.posterNameText = view.findViewById(R.id.user_name);
        viewHolder.commentText = view.findViewById(R.id.text_comment);
        viewHolder.replyButton = view.findViewById(R.id.reply_button);
        viewHolder.likeButton = view.findViewById(R.id.like_button);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        User user = userList.get(position);

        if (user != null) {
            holder.posterNameText.setText(user.getUsername());
            Glide.with(mContext).load(user.getProfileImageUrl()).into(holder.profileImage);
        } else {
            holder.posterNameText.setText("empty user");
            holder.profileImage.setImageBitmap(null);
        }

        holder.commentText.setText(comment.getCommentText());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }



}
