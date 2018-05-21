package kejuntong.com.samplemoduleapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kejuntong.com.samplemoduleapp.ModelClasses.PostItem;
import kejuntong.com.samplemoduleapp.R;

/**
 * Created by kejuntong on 2018-05-21.
 */

public class AllPostAdapter extends RecyclerView.Adapter<AllPostAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<PostItem> itemList;

    public AllPostAdapter(Context context, ArrayList<PostItem> postItemList){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        itemList = postItemList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }

        TextView captionText;
        ImageView postImage;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_all_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.captionText = view.findViewById(R.id.post_caption);
        viewHolder.postImage = view.findViewById(R.id.post_image);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PostItem item = itemList.get(position);
        String imageUrl = item.getPhotoUrl();
        Glide.with(mContext).load(imageUrl).into(holder.postImage);

        holder.captionText.setText(item.getCaption());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }



}
