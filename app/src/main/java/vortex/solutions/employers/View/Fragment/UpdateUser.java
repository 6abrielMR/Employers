package vortex.solutions.employers.View.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;

import vortex.solutions.employers.Model.User;
import vortex.solutions.employers.Presenter.Interface.UpdateUserImpl;
import vortex.solutions.employers.Presenter.ManageDb;
import vortex.solutions.employers.Presenter.ManageTasks;
import vortex.solutions.employers.R;
import vortex.solutions.employers.View.Activity.Login;

public class UpdateUser extends DialogFragment implements UpdateUserImpl {

    //constants
    private static final String TAG = "Register";

    //vars
    private Activity currentActivity;

    //widgets
    private ScrollView mainContent;
    private TextInputEditText names;
    private TextInputEditText lastnames;
    private TextInputEditText id;
    private TextInputEditText num1;
    private TextInputEditText num2;
    private TextInputEditText num3;
    private TextInputEditText email;
    private TextInputEditText salary;

    public UpdateUser(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
        View v = currentActivity.getLayoutInflater().inflate(R.layout.update_user, null);

        mainContent = v.findViewById(R.id.updateuser_main_content);
        final TextInputEditText idEmployer = v.findViewById(R.id.updateuser_edit_idFind);
        names = v.findViewById(R.id.updateuser_edit_names);
        lastnames = v.findViewById(R.id.updateuser_edit_lastnames);
        id = v.findViewById(R.id.updateuser_edit_id);
        num1 = v.findViewById(R.id.updateuser_edit_num1);
        num2 = v.findViewById(R.id.updateuser_edit_num2);
        num3 = v.findViewById(R.id.updateuser_edit_num3);
        email = v.findViewById(R.id.updateuser_edit_email);
        salary = v.findViewById(R.id.updateuser_edit_salary);
        ImageButton btnFind = v.findViewById(R.id.updateuser_btn_search);
        ArrayAdapter adapterType = new ArrayAdapter(currentActivity,
                android.R.layout.simple_spinner_dropdown_item, new ArrayList(Arrays.asList("CC", "Nit")));
        final Spinner spnType = v.findViewById(R.id.updateuser_spn);
        spnType.setAdapter(adapterType);

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManageDb db = new ManageDb();
                db.getEmployer(UpdateUser.this, idEmployer.getText().toString());
            }
        });

        builder.setView(v)
                .setCancelable(false)
                .setPositiveButton(R.string.update_employer, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!validateField(idEmployer).equalsIgnoreCase("")){
                            ManageTasks.ValidateUpdateUser validateUpdateUser = new ManageTasks.ValidateUpdateUser();
                            validateUpdateUser.execute(UpdateUser.this,
                                    new ArrayList<>(Arrays.asList(names.getText().toString(),
                                            lastnames.getText().toString(), spnType.getSelectedItem().toString(),
                                            id.getText().toString(),
                                            num1.getText().toString(), num2.getText().toString(),
                                            num3.getText().toString(), email.getText().toString(),
                                            salary.getText().toString())));
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

    @Override
    public void loadEmployer(User user) {
        names.setText(user.getNames());
        lastnames.setText(user.getLastnames());
        id.setText(user.getId());
        num1.setText(user.getNum1());
        num2.setText(user.getNum2());
        num3.setText(user.getNum3());
        email.setText(user.getEmail());
        salary.setText(user.getSalary());
        mainContent.setVisibility(View.VISIBLE);
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
