package vortex.solutions.employers.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import vortex.solutions.employers.Presenter.Interface.DashboardImpl;
import vortex.solutions.employers.R;
import vortex.solutions.employers.View.Fragment.CreateUser;
import vortex.solutions.employers.View.Fragment.DeleteUser;
import vortex.solutions.employers.View.Fragment.FailureMessagge;
import vortex.solutions.employers.View.Fragment.ListUsers;
import vortex.solutions.employers.View.Fragment.SuccessMessagge;
import vortex.solutions.employers.View.Fragment.UpdateUser;

public class Dashboard extends AppCompatActivity implements DashboardImpl {

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

    public void listUsers(View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListUsers listUsers = new ListUsers(Dashboard.this);
                listUsers.show(getSupportFragmentManager(), getLocalClassName());
            }
        });
    }

    public void updateUser(View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UpdateUser updateUser = new UpdateUser(Dashboard.this);
                updateUser.show(getSupportFragmentManager(), getLocalClassName());
            }
        });
    }

    public void deleteUser(View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DeleteUser deleteUser = new DeleteUser(Dashboard.this);
                deleteUser.show(getSupportFragmentManager(), getLocalClassName());
            }
        });
    }

    public void Logout(View view) {
        mAuth.signOut();
        Log.d(TAG, "logout: Logout");
        Toast.makeText(this, "Sesión Cerrada", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, Login.class));
    }

    @Override
    public void showStateProccess(boolean state, final String messagge) {
        if (state) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SuccessMessagge successMessagge = new SuccessMessagge(Dashboard.this, messagge);
                    successMessagge.show(getSupportFragmentManager(), getLocalClassName());
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    FailureMessagge failureMessagge = new FailureMessagge(Dashboard.this, messagge);
                    failureMessagge.show(getSupportFragmentManager(), getLocalClassName());
                }
            });
        }
    }
}
