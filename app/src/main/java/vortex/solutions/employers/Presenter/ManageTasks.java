package vortex.solutions.employers.Presenter;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;

import vortex.solutions.employers.View.Activity.Login;
import vortex.solutions.employers.View.Fragment.CreateUser;

public class ManageTasks {

    //constants
    private static final String TAG = "ManageTasks";
    private static boolean state = false;
    private static int stateError = 3;

    public static class createUser extends AsyncTask<Object, Void, ArrayList<Object>> {

        @Override
        protected ArrayList<Object> doInBackground(Object... objects) {

            ArrayList<Object> response = new ArrayList<>();

            ((FirebaseAuth) objects[1]).createUserWithEmailAndPassword(
                    (String) objects[2],
                    (String) objects[3])
                    .addOnCompleteListener((Login) objects[0], new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "createUserWithEmail: Success");
                                state = true;
                            } else {
                                Log.d(TAG, "createUserWithEmail: Failure");
                                state = false;
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "createUserWithEmail: Failure");
                            Log.d(TAG, "onFailure: " + e.getMessage());
                            state = false;
                        }
                    });

            response.add(state);
            response.add(objects[0]);

            return response;
        }

        @Override
        protected void onPostExecute(ArrayList<Object> response) {
            if ((boolean) response.get(0))
                ((Login) response.get(1)).SuccesMessagge("Registro Exitoso");
            else
                ((Login) response.get(1)).FailureMessagge("Error al Registrar");
        }
    }

    public static class loginUser extends AsyncTask<Object, Void, ArrayList<Object>> {

        @Override
        protected ArrayList<Object> doInBackground(final Object... objects) {

            String email = validateField((TextInputEditText) objects[2]);
            String password = validateField((TextInputEditText) objects[3]);

            if (!email.equalsIgnoreCase("")) {
                if (!password.equalsIgnoreCase("")) {
                    ((FirebaseAuth) objects[1]).signInWithEmailAndPassword(
                            email,
                            password)
                            .addOnCompleteListener((Login) objects[0], new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        ((Login) objects[0]).goToNextScreen(true);
                                    } else {
                                        ((Login) objects[0]).FailureMessagge("Datos Incorrectos");
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.getMessage());
                                    ((Login) objects[0]).FailureMessagge("Datos Incorrectos");
                                }
                            });
                } else {
                    state = false;
                    stateError = 0;
                }
            } else {
                state = false;
                stateError = 1;
            }

            ArrayList<Object> paremetersLogin = new ArrayList<>();
            paremetersLogin.add(stateError);
            paremetersLogin.add(objects[0]);

            return paremetersLogin;
        }

        @Override
        protected void onPostExecute(ArrayList<Object> response) {
            if ((int) response.get(0) == 0) {
                ((Login) response.get(1)).FailureInput((int) response.get(0), "Contraseña Vacía");
                stateError = 3;
            } else if ((int) response.get(0) == 1) {
                ((Login) response.get(1)).FailureInput((int) response.get(0), "Correo Electrónico Vacío");
                stateError = 3;
            } else {
                stateError = 3;
            }
        }

        private String validateField(TextInputEditText field) {
            if (!field.getText().toString().trim().equals("") ||
                    !field.getText().toString().equals(""))
                return field.getText().toString();
            else return "";
        }
    }


    public static class ValidateCreateUser extends AsyncTask<Object, Void, ArrayList<Object>> {

        @Override
        protected ArrayList<Object> doInBackground(Object... objects) {

            ArrayList<Object> paremetersCreateUser = new ArrayList<>();
            ArrayList fields = (ArrayList) objects[1];
            int validate = validateFields((String) fields.get(0), (String) fields.get(1),
                    (String) fields.get(3), (String) fields.get(8));

            if (validate == 0) {
                paremetersCreateUser.add(objects[0]);
                paremetersCreateUser.add(true);
                paremetersCreateUser.add(validate);
                paremetersCreateUser.add(fields);
            } else {
                paremetersCreateUser.add(objects[0]);
                paremetersCreateUser.add(false);
                paremetersCreateUser.add(validate);
                paremetersCreateUser.add(fields);
            }

            return paremetersCreateUser;
        }

        @Override
        protected void onPostExecute(ArrayList<Object> response) {
            ArrayList fields = (ArrayList) response.get(3);
            if (!(boolean) response.get(1)) {
                ((CreateUser) response.get(0)).stateFields((int) response.get(2));
            } else {
                ManageDb db = new ManageDb();
                db.createUser((String) fields.get(0), (String) fields.get(1),
                        (String) fields.get(2), (String) fields.get(3), (String) fields.get(4),
                        (String) fields.get(5), (String) fields.get(6), (String) fields.get(7),
                        (String) fields.get(8));
            }
        }

        private int validateFields(String field1, String field2, String field3, String field8) {
            if (!(field1.trim().equalsIgnoreCase(""))) {
                if (!(field2.trim().equalsIgnoreCase(""))) {
                    if (!(field3.trim().equalsIgnoreCase(""))) {
                        if (!(field8.trim().equalsIgnoreCase(""))) {
                            return 0;
                        } else {
                            return 4;
                        }
                    } else {
                        return 3;
                    }
                } else {
                    return 2;
                }
            } else {
                return 1;
            }
        }
    }

}
