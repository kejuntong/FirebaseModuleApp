package kejuntong.com.samplemoduleapp.ModelClasses;

/**
 * Created by kejuntong on 2018-05-21.
 */

public class PostItem {

    private String caption;
    private String photoURL;
    private String uid;
    private String latestCommentText;
    private String latestCommentPoster;

    public PostItem(){

    }

    public PostItem(String caption, String photoURL, String userId,
                    String commentText, String commentPoster){
        this.caption = caption;
        this.photoURL = photoURL;
        this.uid = userId;
        this.latestCommentText = commentText;
        this.latestCommentPoster = commentPoster;
    }

    public String getCaption(){
        return this.caption;
    }
    public String getPhotoURL(){
        return photoURL;
    }
    public String getUid(){
        return this.uid;
    }
    public String getLatestCommentText(){
        return this.latestCommentText;
    }
    public String getLatestCommentPoster(){
        return this.latestCommentPoster;
    }
}
