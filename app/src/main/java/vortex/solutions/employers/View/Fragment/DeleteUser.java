package vortex.solutions.employers.View.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import vortex.solutions.employers.Presenter.ManageDb;
import vortex.solutions.employers.R;
import vortex.solutions.employers.View.Activity.Dashboard;

public class DeleteUser extends DialogFragment {

    //constants
    private static final String TAG = "Register";

    //vars
    private Activity currentActivity;

    public DeleteUser(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
        View v = currentActivity.getLayoutInflater().inflate(R.layout.delete_user, null);

        final TextInputEditText idEmployer = v.findViewById(R.id.deleteuser_edit_idFind);

        builder.setView(v)
                .setCancelable(false)
                .setPositiveButton(R.string.delete_employer, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!validateField(idEmployer).equalsIgnoreCase("")) {
                            ManageDb manageDb = new ManageDb((Dashboard) currentActivity);
                            manageDb.deleteUser(idEmployer.getText().toString());
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: Cancel create user");
                    }
                });

        return builder.create();
    }

    private String validateField(TextInputEditText field) {
        if (!field.getText().toString().trim().equals("") ||
                !field.getText().toString().equals(""))
            return field.getText().toString();
        else return "";
    }
}
