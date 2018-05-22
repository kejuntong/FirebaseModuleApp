package kejuntong.com.samplemoduleapp.ModelClasses;

/**
 * Created by kejuntong on 2018-05-21.
 */

public class PostItem {

    private String caption;
    private String photoURL;
    private String uid;

    public PostItem(){

    }

    public PostItem(String caption, String photoUrl, String userId){
        this.caption = caption;
        this.photoURL = photoUrl;
        this.uid = userId;
    }

    public String getCaption(){
        return this.caption;
    }
    public String getPhotoUrl(){
        return photoURL;
    }
    public String getUid(){
        return this.uid;
    }
}
