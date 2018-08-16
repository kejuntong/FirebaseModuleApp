package kejuntong.com.samplemoduleapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import kejuntong.com.samplemoduleapp.Interfaces.UtilCallbackInterface;
import kejuntong.com.samplemoduleapp.R;
import kejuntong.com.samplemoduleapp.UtilClasses.DatabaseMethods;

/**
 * Created by kejuntong on 2018-04-22.
 */

public class LoginActivity extends Activity {
    final static String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;

    EditText emailInput;
    EditText passwordInput;
    TextView forgotButton;
    Button loginButton;
    TextView registerButton;
    ProgressBar spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);

        forgotButton = findViewById(R.id.forgot_button);
        loginButton = findViewById(R.id.login_button);
        setLoginButton();

        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        spinner = findViewById(R.id.progress_bar);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            spinner.setVisibility(View.VISIBLE);
            DatabaseMethods.getUserFromFirebase(firebaseDatabase, user.getUid(), new UtilCallbackInterface() {
                @Override
                public void onCallback(Object object) {
                    spinner.setVisibility(View.GONE);
                    if (object != null){
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    } else {
                        startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
                    }
                }
            });
        } else {
            Toast.makeText(LoginActivity.this, "null", Toast.LENGTH_LONG).show();
        }

    }

    private void setLoginButton(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (emailInput.getText().toString().isEmpty() || passwordInput.getText().toString().isEmpty()){
                    return;
                }

                spinner.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(emailInput.getText().toString(), passwordInput.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                spinner.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
                                }

                                // ...
                            }
                        });


            }
        });
    }

}
