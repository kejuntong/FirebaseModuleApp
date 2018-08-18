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

import com.afollestad.materialdialogs.MaterialDialog;

import kejuntong.com.samplemoduleapp.R;

/**
 * Created by kejuntong on 2018-08-18.
 */

public class UserProfileActivity extends Activity {

    ImageView userPhotoImageView;
    TextView userNameTextView;
    Button sendCreditButton;

    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userPhotoImageView = findViewById(R.id.btn_add_photo);
        userNameTextView = findViewById(R.id.user_name);
        sendCreditButton = findViewById(R.id.send_credit_button);

        progressBar = findViewById(R.id.progress_bar);

        setSendCreditButton();

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
                    EditText creditText = dialogView.findViewById(R.id.dialog_input);
                    Button sendButton = dialogView.findViewById(R.id.dialog_pos_button);
                    Button cancelButton = dialogView.findViewById(R.id.dialog_neg_button);

                    sendButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

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
