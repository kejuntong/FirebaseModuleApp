package kejuntong.com.samplemoduleapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import kejuntong.com.samplemoduleapp.Interfaces.DatabaseCallback;
import kejuntong.com.samplemoduleapp.ModelClasses.User;
import kejuntong.com.samplemoduleapp.R;
import kejuntong.com.samplemoduleapp.UtilClasses.Constants;
import kejuntong.com.samplemoduleapp.UtilClasses.DatabaseMethods;

/**
 * Created by kejuntong on 2018-08-07.
 */

public class SettingFragment extends BaseFragment {

    View fragmentView;
    FirebaseDatabase mDatabase;

    ImageView addPhotoButton;
    TextView userNameTextView;
    Button logoutButton;

    User currentUser;

    public SettingFragment(){
        setFragmentName(Constants.THIRD_FRAGMENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_setting, container, false);

        mDatabase = FirebaseDatabase.getInstance();

        addPhotoButton = (ImageView) fragmentView.findViewById(R.id.btn_add_photo);
        userNameTextView = (TextView) fragmentView.findViewById(R.id.user_name);
        logoutButton = (Button) fragmentView.findViewById(R.id.logout_button);

        DatabaseMethods.getUserFromFirebase(mDatabase, FirebaseAuth.getInstance().getCurrentUser().getUid(), new DatabaseCallback() {
            @Override
            public void onDataCallback(Object object) {
                if (object != null){
                    currentUser = (User) object;
                    loadUserInfoToUI(currentUser);
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
            }
        });

        return fragmentView;
    }

    private void loadUserInfoToUI(User user){
        Glide.with(getActivity()).load(user.getProfileImageUrl()).into(addPhotoButton);
        userNameTextView.setText(user.getUsername());
    }
}
