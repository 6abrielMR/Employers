package vortex.solutions.employers.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import vortex.solutions.employers.Presenter.Interface.LoginImpl;
import vortex.solutions.employers.Presenter.ManageTasks;
import vortex.solutions.employers.R;
import vortex.solutions.employers.View.Fragment.FailureMessagge;
import vortex.solutions.employers.View.Fragment.Register;
import vortex.solutions.employers.View.Fragment.SuccessMessagge;

public class Login extends AppCompatActivity implements LoginImpl {

    //vars
    private FirebaseAuth mAuth;

    //widgets
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputLayout lEmail;
    private TextInputLayout lPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        init();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.login_edit_email);
        password = findViewById(R.id.login_edit_password);
        lEmail = findViewById(R.id.login_layout_edit_email);
        lPassword = findViewById(R.id.login_layout_edit_password);
    }

    public void LoginUser(View view) {
        lEmail.setError(null);
        lPassword.setError(null);
        Object[] parametersLogin = {this, mAuth, email, password};
        ManageTasks.loginUser loginUser = new ManageTasks.loginUser();
        loginUser.execute(parametersLogin);
    }

    public void RegisterUser(View view) {
        Register register = new Register(this, mAuth);
        register.show(getSupportFragmentManager(), getLocalClassName());
    }

    @Override
    public void goToNextScreen(boolean state) {
        startActivity(new Intent(this, Dashboard.class));
    }

    @Override
    public void FailureInput(int field, String messagge) {
        if (field == 0)
            lPassword.setError(messagge);
        else
            lEmail.setError(messagge);
    }

    @Override
    public void SuccesMessagge(final String messagge) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SuccessMessagge successMessagge = new SuccessMessagge(Login.this, messagge);
                successMessagge.show(getSupportFragmentManager(), getLocalClassName());
            }
        });
    }

    @Override
    public void FailureMessagge(final String messagge) {
        lEmail.setError(null);
        lPassword.setError(null);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FailureMessagge failureMessagge = new FailureMessagge(Login.this, messagge);
                failureMessagge.show(getSupportFragmentManager(), getLocalClassName());
            }
        });
    }
}
