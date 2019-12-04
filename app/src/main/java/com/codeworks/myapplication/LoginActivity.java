package com.codeworks.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private SignInButton googleSignInButton;
    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        Button login_button = findViewById(R.id.login_button);
        final TextInputEditText txt1 = findViewById(R.id.username);
        final TextInputEditText txt2 = findViewById(R.id.password);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt1.getText().equals("") || txt2.getText().equals("")){
                    Toast.makeText(LoginActivity.this, "Ingrese las Credenciales", Toast.LENGTH_SHORT).show();
                }else{
                    startMainActivity(LoginActivity.this);
                }
            }
        });
        googleSignInButton = findViewById(R.id.sign_in_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("users");
                //usersRef.child(account.getId()).setValue(account.getId());
                usersRef.child(account.getId()).child("mail").setValue(account.getEmail());
                usersRef.child(account.getId()).child("password").setValue("");
                usersRef.child(account.getId()).child("username").setValue(account.getDisplayName());
                usersRef.child(account.getId()).child("type").setValue("google");
                Toast.makeText(this, "Bienvenido, "+account.getDisplayName(), Toast.LENGTH_SHORT).show();
                startMainActivity(this);
            } catch (ApiException e) {
                Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
            }
        }
    }
    public static void startMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}