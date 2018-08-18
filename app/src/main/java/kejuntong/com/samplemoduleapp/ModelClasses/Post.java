package kejuntong.com.samplemoduleapp.ModelClasses;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kejuntong on 2018-04-27.
 */

public class Post {
    public String post_id;
    // true for provide, false for request
    public boolean provide_or_request;
    public String contact_name;
    public String contact_number;
    public String post_title;
    public int service_category;
    public String labels;
    public int credit;
    public String post_details;
    public String post_photo_url;

    public String poster_id;
    public String poster_name;
    public String poster_photo_url;

    public Post(){

    }

    public Post(String postId,boolean provideOrRequest, int credit, String number,
                String title, String details, String postPhotoUrl, String posterId,
                String posterName, String posterPhotoUrl){
        this.post_id = postId;
        this.provide_or_request = provideOrRequest;
        this.credit = credit;
        this.contact_number = number;
        this.post_title = title;
        this.post_details = details;
        this.post_photo_url = postPhotoUrl;

        this.poster_id = posterId;
        this.poster_name = posterName;
        this.poster_photo_url = posterPhotoUrl;
    }

    public Post(String postId, boolean provideOrRequest, int credit, String title, String posterId){
        this.post_id = postId;
        this.provide_or_request = provideOrRequest;
        this.credit = credit;
        this.post_title = title;
        this.poster_id = posterId;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("post_id", post_id);
        result.put("provide_or_request", provide_or_request);
        result.put("contact_name", contact_name);
        result.put("contact_number", contact_number);
        result.put("post_title", post_title);
        result.put("service_category", service_category);
        result.put("labels", labels);
        result.put("credit", credit);
        result.put("post_details", post_details);
        result.put("post_photo_url", post_photo_url);

        result.put("poster_id", poster_id);
        result.put("poster_name", poster_name);
        result.put("poster_photo_url", poster_photo_url);

        return result;
    }

}
