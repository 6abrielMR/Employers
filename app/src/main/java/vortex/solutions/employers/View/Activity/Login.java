package vortex.solutions.employers.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import vortex.solutions.employers.Presenter.Interface.LoginImpl;
import vortex.solutions.employers.R;
import vortex.solutions.employers.View.Fragment.FailureMessagge;
import vortex.solutions.employers.View.Fragment.Register;
import vortex.solutions.employers.View.Fragment.SuccessMessagge;

public class Login extends AppCompatActivity implements LoginImpl {

    //vars
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        init();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void RegisterUser(View view) {
        Register register = new Register(this, mAuth);
        register.show(getSupportFragmentManager(), getLocalClassName());
    }

    @Override
    public void SuccesMessagge(String messagge) {
        SuccessMessagge successMessagge = new SuccessMessagge(this, messagge);
        successMessagge.show(getSupportFragmentManager(), getLocalClassName());
    }

    @Override
    public void FailureMessagge(String messagge) {
        FailureMessagge failureMessagge = new FailureMessagge(this, messagge);
        failureMessagge.show(getSupportFragmentManager(), getLocalClassName());
    }
}
