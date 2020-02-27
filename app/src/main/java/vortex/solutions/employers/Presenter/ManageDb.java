package vortex.solutions.employers.Presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import vortex.solutions.employers.Model.User;
import vortex.solutions.employers.View.Activity.Dashboard;
import vortex.solutions.employers.View.Fragment.ListUsers;
import vortex.solutions.employers.View.Fragment.UpdateUser;

public class ManageDb {

    //constants
    private static final String TAG = "ManageDb";
    private static final String EMPLOYERS = "employers";
    private static final String EMPLOYERS_DELETED = "employersDeleted";
    private static final String NOTHING = "nothing";
    private static final String NAMES = "names";
    private static final String LASTNAMES = "lastnames";
    private static final String TYPEID = "typeId";
    private static final String ID = "id";
    private static final String NUM1 = "num1";
    private static final String NUM2 = "num2";
    private static final String NUM3 = "num3";
    private static final String EMAIL = "email";
    private static final String SALARY = "salary";
    private static final String TIMESTAMP = "timesTamp";
    private static final String LASTKNOWNCHANGE = "lastKnownChange";

    //vars
    private Dashboard context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private User employer;

    public ManageDb(Dashboard context) {
        this.context = context;
    }

    public void createUser(String names, String lastnames,
                           String typeId, String id, String num1,
                           String num2, String num3, String email,
                           String salary) {

        if (num1.equals("")) num1 = NOTHING;
        if (num2.equals("")) num2 = NOTHING;
        if (num3.equals("")) num3 = NOTHING;
        if (email.equals("")) email = NOTHING;
        
        db.collection(EMPLOYERS)
                .document(String.valueOf(id))
                .set(new User(names, lastnames, typeId, id, num1, num2, num3, email, salary,
                        getTimestamp(), getTimestamp()), SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        context.showStateProccess(true, "Empleado Creado Correctamente");
                        Log.d(TAG, "onSuccess: Registrado");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onSuccess: No Registrado");
                        e.printStackTrace();
                        context.showStateProccess(false, "Error al Registrar");
                    }
                });

    }

    public void getListUsers(final ListUsers context) {
        final ArrayList<User> users = new ArrayList<>();

        db.collection(EMPLOYERS)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot employer : task.getResult()) {
                                Log.d(TAG, "onComplete: " + employer.getString("names"));
                                users.add(new User(employer.getString(NAMES),
                                        employer.getString(LASTNAMES),
                                        employer.getString(TYPEID),
                                        employer.getString(ID),
                                        employer.getString(NUM1),
                                        employer.getString(NUM2),
                                        employer.getString(NUM3),
                                        employer.getString(EMAIL),
                                        employer.getString(SALARY),
                                        employer.getString(TIMESTAMP),
                                        employer.getString(LASTKNOWNCHANGE)));
                            }

                            context.showList(users);
                        } else {
                            Log.d(TAG, "onComplete: Error Get Employers");
                            (context.getMainContext()).showStateProccess(false,
                                    "Error al Listar Empleados");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Error response get Employers");
                        e.printStackTrace();
                        (context.getMainContext()).showStateProccess(false,
                                "Error al Obtener Empleados");
                    }
                });
    }

    public void getEmployer(final UpdateUser context, String idEmployer) {
        db.collection(EMPLOYERS)
                .document(idEmployer)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        context.loadEmployer(new User(documentSnapshot.getString(NAMES),
                                documentSnapshot.getString(LASTNAMES),
                                documentSnapshot.getString(TYPEID),
                                documentSnapshot.getString(ID),
                                documentSnapshot.getString(NUM1),
                                documentSnapshot.getString(NUM2),
                                documentSnapshot.getString(NUM3),
                                documentSnapshot.getString(EMAIL),
                                documentSnapshot.getString(SALARY),
                                documentSnapshot.getString(TIMESTAMP),
                                documentSnapshot.getString(LASTKNOWNCHANGE)));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Error to getEmployer - UpdateEmployer");
                        e.printStackTrace();
                        (context.getMainContext()).showStateProccess(false,
                                "Error al Obtener Empleado");
                    }
                });
    }

    public void updateUser(String names, String lastnames,
                           String typeId, String id, String num1,
                           String num2, String num3, String email,
                           String salary) {

        if (num1.equals("")) num1 = NOTHING;
        if (num2.equals("")) num2 = NOTHING;
        if (num3.equals("")) num3 = NOTHING;
        if (email.equals("")) email = NOTHING;

        db.collection(EMPLOYERS)
                .document(String.valueOf(id))
                .set(new User(names, lastnames, typeId, id, num1, num2, num3, email, salary,
                        getTimestamp(), getTimestamp()), SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: Actualizado");
                        context.showStateProccess(true, "Empleado Actualizado");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onSuccess: No Actualizado");
                        e.printStackTrace();
                        context.showStateProccess(false, "Error al Actualizar");
                    }
                });

    }

    public void deleteUser(final String id) {
        db.collection(EMPLOYERS)
                .document(id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        setEmployerForDelete(new User(documentSnapshot.getString(NAMES),
                                documentSnapshot.getString(LASTNAMES),
                                documentSnapshot.getString(TYPEID),
                                documentSnapshot.getString(ID),
                                documentSnapshot.getString(NUM1),
                                documentSnapshot.getString(NUM2),
                                documentSnapshot.getString(NUM3),
                                documentSnapshot.getString(EMAIL),
                                documentSnapshot.getString(SALARY),
                                documentSnapshot.getString(TIMESTAMP),
                                documentSnapshot.getString(LASTKNOWNCHANGE)));
                        onlyDeleteEmployer(documentSnapshot.getString(ID));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Error al Eliminar");
                        e.printStackTrace();
                        context.showStateProccess(false, "Error al Eliminar");
                    }
                });
    }

    private void onlyDeleteEmployer(String id) {
        db.collection(EMPLOYERS)
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: Delted");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Error to Delete Employer");
                        e.printStackTrace();
                        context.showStateProccess(false, "Error al Eliminar");
                    }
                });
    }

    private void setEmployerForDelete(User employerDeleted) {
        db.collection(EMPLOYERS_DELETED)
                .document(employerDeleted.getId())
                .set(employerDeleted)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: saved deleted");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Error to Send Employer");
                        e.printStackTrace();
                        context.showStateProccess(false, "Error al Eliminar");
                    }
                });
    }

    private String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
        return sdf.format(new Date());
    }

}
