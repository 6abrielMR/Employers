package vortex.solutions.employers.View.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import vortex.solutions.employers.Presenter.ManageTasks;
import vortex.solutions.employers.R;
import vortex.solutions.employers.View.Activity.Login;

public class Register extends DialogFragment {

    //constants
    private static final String TAG = "Register";

    //vars
    private Activity currentActivity;
    private FirebaseAuth mAuth;

    public Register(Activity currentActivity, FirebaseAuth mAuth) {
        this.currentActivity = currentActivity;
        this.mAuth = mAuth;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
        View v = currentActivity.getLayoutInflater().inflate(R.layout.register, null);

        final TextInputEditText email = v.findViewById(R.id.register_edit_email);
        final TextInputEditText password = v.findViewById(R.id.register_edit_password);

        builder.setView(v)
                .setCancelable(false)
                .setPositiveButton(R.string.register_true, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String emailV = validateField(email);
                        String passV = validateField(password);

                        if (!emailV.equalsIgnoreCase("")){
                            if (!passV.equalsIgnoreCase("")) {
                                Object[] parametersRegister = {
                                        currentActivity,
                                        mAuth,
                                        validateField(email),
                                        validateField(password)};

                                ManageTasks.createUser createUser = new ManageTasks.createUser();
                                createUser.execute(parametersRegister);
                            } else {
                                Log.d(TAG, "onClick: Error password null");
                                ((Login) currentActivity).FailureMessagge("Error Contraseña Vacío");
                            }
                        } else {
                            Log.d(TAG, "onClick: Error email null");
                            ((Login) currentActivity).FailureMessagge("Error Correo Electrónico Vacío");
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: Not Changes");
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
