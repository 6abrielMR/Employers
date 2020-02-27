package vortex.solutions.employers.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import vortex.solutions.employers.R;
import vortex.solutions.employers.View.Fragment.CreateUser;

public class Dashboard extends AppCompatActivity {

    //constants
    private static final String TAG = "Dashboard";

    //vars
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        init();

    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void createUser(View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CreateUser createUser = new CreateUser(Dashboard.this);
                createUser.show(getSupportFragmentManager(), getLocalClassName());
            }
        });
    }

    public void Logout(View view) {
        mAuth.signOut();
        Log.d(TAG, "logout: Logout");
        Toast.makeText(this, "Sesión Cerrada", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, Login.class));
    }
}
