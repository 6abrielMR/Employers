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

}
