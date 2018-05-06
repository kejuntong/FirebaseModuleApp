package kejuntong.com.samplemoduleapp.Fragments;

import android.app.Fragment;

/**
 * Created by kejuntong on 2018-05-06.
 */

public class BaseFragment extends Fragment{

    String fragmentName;

    public void setFragmentName(String name){
        this.fragmentName = name;
    }

    public String getFragmentName(){
        return fragmentName;
    }

}
