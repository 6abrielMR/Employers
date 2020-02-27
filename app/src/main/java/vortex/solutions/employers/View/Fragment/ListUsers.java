package vortex.solutions.employers.View.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vortex.solutions.employers.Model.User;
import vortex.solutions.employers.Presenter.Interface.ListUsersImpl;
import vortex.solutions.employers.Presenter.ListUsersAdapter;
import vortex.solutions.employers.Presenter.ManageDb;
import vortex.solutions.employers.Presenter.ManageTasks;
import vortex.solutions.employers.R;
import vortex.solutions.employers.View.Activity.Dashboard;
import vortex.solutions.employers.View.Activity.Login;

public class ListUsers extends DialogFragment implements ListUsersImpl {

    //constants
    private static final String TAG = "Register";

    //vars
    private Activity currentActivity;

    //widgets
    private RecyclerView listUsers;

    public ListUsers(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
        View v = currentActivity.getLayoutInflater().inflate(R.layout.list_users, null);

        listUsers = v.findViewById(R.id.listusers_recycler);
        ManageDb db = new ManageDb((Dashboard) currentActivity);
        db.getListUsers(ListUsers.this);

        builder.setView(v)
                .setCancelable(false);

        return builder.create();
    }

    @Override
    public void showList(List<User> list) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(currentActivity);
        ListUsersAdapter mAdapter = new ListUsersAdapter(currentActivity, list);

        listUsers.setLayoutManager(layoutManager);
        listUsers.setAdapter(mAdapter);
    }

    @Override
    public Dashboard getMainContext() {
        return ((Dashboard) currentActivity);
    }
}
