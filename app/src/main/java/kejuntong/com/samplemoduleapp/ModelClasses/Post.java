package kejuntong.com.samplemoduleapp.ModelClasses;

/**
 * Created by kejuntong on 2018-04-27.
 */

public class Post {

    private String author = "";
    private String content = "";

    public Post(){

    }

    public Post(String author, String content){
        this.author = author;
        this.content = content;
    }

    public String getAuthor(){
        return author;
    }

    public String getContent(){
        return content;
    }

}
