package kejuntong.com.samplemoduleapp.ModelClasses;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kejuntong on 2018-04-27.
 */

public class Post {

//    private String poster_id;
    public String poster_photo_url;
//    private String contact_name;
    public String contact_number;

    public String post_title;
    // true for provide, false for request
//    private boolean provide_or_request;
//    private int service_category;
//    private String labels;
//    private int credit;
    public String post_details;

    public Post(){

    }

    public Post(String number, String title, String details, String photoUrl){
        this.contact_number = number;
        this.post_title = title;
        this.post_details = details;
        this.poster_photo_url = photoUrl;
    }

//    public Map<String, Object> toMap(){
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("contact_number", )
//    }

}
