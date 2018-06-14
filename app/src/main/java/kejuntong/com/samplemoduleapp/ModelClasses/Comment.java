package kejuntong.com.samplemoduleapp.ModelClasses;

/**
 * Created by kejuntong on 2018-06-12.
 */

public class Comment {

    private String commentText = "";
    private String uid = "";

    public Comment(){

    }

    public Comment(String commentText, String uid){
        this.commentText = commentText;
        this.uid = uid;
    }

    public String getCommentText(){
        return this.commentText;
    }
    public String getUid(){
        return this.uid;
    }

}
