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

import kejuntong.com.samplemoduleapp.Interfaces.UtilCallbackInterface;
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
    TextView remainingCreditText;
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

        addPhotoButton = fragmentView.findViewById(R.id.btn_add_photo);
        userNameTextView = fragmentView.findViewById(R.id.user_name);
        remainingCreditText = fragmentView.findViewById(R.id.remaining_credits);
        logoutButton = fragmentView.findViewById(R.id.logout_button);

        DatabaseMethods.registerUserChangeListener(mDatabase, FirebaseAuth.getInstance().getCurrentUser().getUid(), new UtilCallbackInterface() {
            @Override
            public void onCallback(Object object) {
                if (object != null){
                    currentUser = (User) object;
                    Glide.with(getActivity()).load(currentUser.getProfileImageUrl()).into(addPhotoButton);
                    userNameTextView.setText(currentUser.getUsername());

                    remainingCreditText.setText("You have " + String.valueOf(currentUser.credit) + " credits");
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
}
