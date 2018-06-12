package kejuntong.com.samplemoduleapp.ModelClasses;

/**
 * Created by kejuntong on 2018-05-21.
 */

public class User {

    private String email = "";
    private String profileImageUrl = "";
    private String username = "";

    public User(){

    }

    public User(String email, String profileImageUrl, String username){
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.username = username;
    }

    public String getEmail(){
        return this.email;
    }
    public String getProfileImageUrl(){
        return this.profileImageUrl;
    }
    public String getUsername(){
        return this.username;
    }
}
