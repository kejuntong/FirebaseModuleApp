package kejuntong.com.samplemoduleapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kejuntong.com.samplemoduleapp.R;
import kejuntong.com.samplemoduleapp.UtilClasses.Constants;

/**
 * Created by kejuntong on 2018-05-06.
 */

public class AllPostFragment extends BaseFragment {

    View fragmentView;

    public AllPostFragment(){
        setFragmentName(Constants.FIRST_FRAGMENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_all_post, container, false);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
