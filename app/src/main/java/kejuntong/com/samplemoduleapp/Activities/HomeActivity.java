package kejuntong.com.samplemoduleapp.Activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kejuntong.com.samplemoduleapp.Fragments.AllPostFragment;
import kejuntong.com.samplemoduleapp.Fragments.BaseFragment;
import kejuntong.com.samplemoduleapp.Fragments.SettingFragment;
import kejuntong.com.samplemoduleapp.R;
import kejuntong.com.samplemoduleapp.UtilClasses.Constants;

public class HomeActivity extends AppCompatActivity {

    RelativeLayout fragmentContainer;
    BottomNavigationView bottomNavigationBar;

    FirebaseUser currentUser;

    AllPostFragment allPostFragment;
    SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragmentContainer = findViewById(R.id.fragment_container);
        bottomNavigationBar = (BottomNavigationView) findViewById(R.id.navigation);

        setBottomNavigationBar();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null){
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            Uri photoUrl = currentUser.getPhotoUrl();
            boolean emailVerified = currentUser.isEmailVerified();
            String uid = currentUser.getUid();

            Toast.makeText(HomeActivity.this, "name: " + name + ", email: " + email
                    + ", photoUrl: " + photoUrl + ", emailVerified: " + emailVerified + " uid: " + uid,
                    Toast.LENGTH_LONG).show();
        }


//        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                .setDisplayName("Kevin Tong")
//                .setPhotoUri(Uri.parse("http://kejuntong.com/1.pic.jpg"))
//                .build();
//
//        currentUser.updateProfile(profileUpdates)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Log.d("Kejun", "enter");
//                        if (task.isSuccessful()) {
//                            Log.d("Kejun", "success");
//                        }
//                    }
//                });


        allPostFragment = new AllPostFragment();
        switchFragment(allPostFragment);

    }

    private void setBottomNavigationBar(){
        bottomNavigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_page_1:
                        if (allPostFragment == null){
                            allPostFragment = new AllPostFragment();
                        }
                        switchFragment(allPostFragment);
                        return true;
                    case R.id.navigation_page_2:
                        startActivity(new Intent(HomeActivity.this, PostActivity.class));
                        return true;
                    case R.id.navigation_page_3:
                        if (settingFragment == null){
                            settingFragment = new SettingFragment();
                        }
                        switchFragment(settingFragment);
                        return true;
                }
                return false;
            }
        });
    }

    public void switchFragment(BaseFragment fragment) {
        hideFragments(fragment.getFragmentName());
        FragmentTransaction txn = getFragmentManager().beginTransaction();
        Fragment existingFragment = getFragmentManager().findFragmentByTag(fragment.getFragmentName());
        if (existingFragment != null) {
            txn.show(existingFragment).commit();
        } else {
            txn.add(R.id.fragment_container, fragment, fragment.getFragmentName()).commit();
            getFragmentManager().executePendingTransactions();
        }
    }

    private void hideFragments(String excludeFragmentName) {
        Fragment firstFragment = getFragmentManager().findFragmentByTag(Constants.FIRST_FRAGMENT);
        Fragment secondFragment = getFragmentManager().findFragmentByTag(Constants.SECOND_FRAGMENT);
        Fragment thirdFragment = getFragmentManager().findFragmentByTag(Constants.THIRD_FRAGMENT);

        FragmentTransaction txn = getFragmentManager().beginTransaction();

        if (firstFragment != null
                && excludeFragmentName != null && !excludeFragmentName.equals(Constants.FIRST_FRAGMENT)) {
            txn.hide(firstFragment);
        }

        if (secondFragment != null
                && excludeFragmentName != null && !excludeFragmentName.equals(Constants.SECOND_FRAGMENT)){
            txn.hide(secondFragment);
        }

        if (thirdFragment != null
                && excludeFragmentName != null && !excludeFragmentName.equals(Constants.THIRD_FRAGMENT)){
            txn.hide(thirdFragment);
        }

        txn.commit();
        getFragmentManager().executePendingTransactions();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
