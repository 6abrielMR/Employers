package vortex.solutions.employers.View.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;

import vortex.solutions.employers.Presenter.Interface.CreateUserImpl;
import vortex.solutions.employers.Presenter.ManageTasks;
import vortex.solutions.employers.R;
import vortex.solutions.employers.View.Activity.Login;

public class CreateUser extends DialogFragment implements CreateUserImpl {

    //constants
    private static final String TAG = "Register";

    //vars
    private Activity currentActivity;

    public CreateUser(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
        View v = currentActivity.getLayoutInflater().inflate(R.layout.create_user, null);

        final TextInputEditText names = v.findViewById(R.id.createuser_edit_names);
        final TextInputEditText lastnames = v.findViewById(R.id.createuser_edit_lastnames);
        final TextInputEditText id = v.findViewById(R.id.createuser_edit_id);
        final TextInputEditText num1 = v.findViewById(R.id.createuser_edit_num1);
        final TextInputEditText num2 = v.findViewById(R.id.createuser_edit_num2);
        final TextInputEditText num3 = v.findViewById(R.id.createuser_edit_num3);
        final TextInputEditText email = v.findViewById(R.id.createuser_edit_email);
        final TextInputEditText salary = v.findViewById(R.id.createuser_edit_salary);
        ArrayAdapter adapterType = new ArrayAdapter(currentActivity,
                android.R.layout.simple_spinner_dropdown_item, new ArrayList(Arrays.asList("CC", "Nit")));
        final Spinner spnType = v.findViewById(R.id.createuser_spn);
        spnType.setAdapter(adapterType);

        builder.setView(v)
                .setPositiveButton(R.string.create_employer, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ManageTasks.ValidateCreateUser validateCreateUser = new ManageTasks.ValidateCreateUser();
                        validateCreateUser.execute(CreateUser.this,
                                new ArrayList<>(Arrays.asList(names.getText().toString(),
                                        lastnames.getText().toString(), spnType.getSelectedItem().toString(),
                                        id.getText().toString(),
                                        num1.getText().toString(), num2.getText().toString(),
                                        num3.getText().toString(), email.getText().toString(),
                                        salary.getText().toString())));
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

    @Override
    public void stateFields(int field) {
        switch (field) {
            case 1:
                Toast.makeText(currentActivity, "Nombres Vacíos", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(currentActivity, "Apellidos Vacíos", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(currentActivity, "Identificación Vacía", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(currentActivity, "Salario Vacío", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
