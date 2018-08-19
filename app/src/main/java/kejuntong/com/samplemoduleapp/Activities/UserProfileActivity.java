package kejuntong.com.samplemoduleapp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import kejuntong.com.samplemoduleapp.Interfaces.UtilCallbackInterface;
import kejuntong.com.samplemoduleapp.ModelClasses.User;
import kejuntong.com.samplemoduleapp.R;
import kejuntong.com.samplemoduleapp.UtilClasses.Constants;
import kejuntong.com.samplemoduleapp.UtilClasses.DatabaseMethods;

/**
 * Created by kejuntong on 2018-08-18.
 */

public class UserProfileActivity extends Activity {

    ImageView userPhotoImageView;
    TextView userNameTextView;
    Button sendCreditButton;
    ProgressBar progressBar;

    FirebaseDatabase mDatabase;
    String userId;
    User mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mDatabase = FirebaseDatabase.getInstance();
        userId = getIntent().getStringExtra(Constants.INTENT_EXTRA_USER_ID);

        userPhotoImageView = findViewById(R.id.btn_add_photo);
        userNameTextView = findViewById(R.id.user_name);
        sendCreditButton = findViewById(R.id.send_credit_button);

        progressBar = findViewById(R.id.progress_bar);

        if (userId.equals(FirebaseAuth.getInstance().getUid())){
            sendCreditButton.setVisibility(View.GONE);
        }

        loadUserData(userId, new UtilCallbackInterface() {
            @Override
            public void onCallback(Object object) {
                setSendCreditButton();
            }
        });
    }

    private void loadUserData(String id, final UtilCallbackInterface cbi){
        if (id == null || id.isEmpty()){
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        DatabaseMethods.getUserFromFirebase(mDatabase, id, new UtilCallbackInterface() {
            @Override
            public void onCallback(Object object) {
                progressBar.setVisibility(View.GONE);
                if (object != null){
                    mUser = (User) object;
                    Glide.with(UserProfileActivity.this).load(mUser.profileImageUrl).into(userPhotoImageView);
                    userNameTextView.setText(mUser.username);
                    cbi.onCallback(null);
                } else {

                }
            }
        });
    }

    private void setSendCreditButton(){
        sendCreditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDialog.Builder builder = new MaterialDialog.Builder(UserProfileActivity.this)
                        .customView(R.layout.view_dialog_send_credit, true);
                final MaterialDialog dialog = builder.build();

                View dialogView = dialog.getCustomView();
                if (dialogView != null){
                    final EditText creditText = dialogView.findViewById(R.id.dialog_input);
                    Button sendButton = dialogView.findViewById(R.id.dialog_pos_button);
                    Button cancelButton = dialogView.findViewById(R.id.dialog_neg_button);

                    sendButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String creditString = creditText.getText().toString();
                            if (creditString.isEmpty()){
                                return;
                            }

                            //TODO::::
                            progressBar.setVisibility(View.VISIBLE);
                            final int addCredit = Integer.valueOf(creditString);
                            mDatabase.getReference().child("user").child(userId).child("credit").setValue(mUser.credit + addCredit)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressBar.setVisibility(View.GONE);
                                    mUser.credit = mUser.credit + addCredit;
                                    Toast.makeText(UserProfileActivity.this, "sent successfully", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                        }
                    });

                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }

                dialog.show();
            }
        });
    }
}
