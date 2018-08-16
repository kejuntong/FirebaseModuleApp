package kejuntong.com.samplemoduleapp.ModelClasses;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kejuntong on 2018-04-27.
 */

public class Post {

    public String poster_id;
    public String poster_photo_url;
    public String contact_name;
    public String contact_number;

    public String post_title;
    // true for provide, false for request
    public boolean provide_or_request;
    public int service_category;
    public String labels;
    public int credit;
    public String post_details;

    public Post(){

    }

    public Post(int credit, String number, String title, String details, String photoUrl){
        this.credit = credit;
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
