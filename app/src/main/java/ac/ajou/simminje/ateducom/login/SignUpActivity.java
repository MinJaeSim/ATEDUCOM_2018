package ac.ajou.simminje.ateducom.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import ac.ajou.simminje.ateducom.R;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private String email;
    private String password;
    private String nickName;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button signUpButton = findViewById(R.id.button_sign_up);

        progressDialog = new ProgressDialog(this);

        EditText emailEditText = findViewById(R.id.enroll_email_edit);
        EditText passwordEditText = findViewById(R.id.enroll_password_edit);
        EditText nickNameEditText = findViewById(R.id.nick_name_edit);
        auth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(v -> {
            showProgressDialog("Please wait");

            email = emailEditText.getText().toString();
            password = passwordEditText.getText().toString();
            nickName = nickNameEditText.getText().toString();

            if (email.length() <= 0) emailEditText.setError("Please input id");
            else if (password.length() <= 0) passwordEditText.setError("Please input password");
            else if (nickName.length() <= 0) nickNameEditText.setError("Please input name");
            else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(nickName).build();

                            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    dismissProgressDialog();
                                    Intent intent = new Intent(getBaseContext(), SignInActivity.class);

                                    startActivity(intent);
                                    finish();
                                }
                            });
                        } else {
                            Snackbar.make(v, "please check again", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void showProgressDialog(String text) {
        progressDialog.setMessage(text);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }
}
