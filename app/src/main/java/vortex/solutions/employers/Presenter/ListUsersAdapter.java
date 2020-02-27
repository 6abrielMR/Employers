package vortex.solutions.employers.Presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vortex.solutions.employers.Model.User;
import vortex.solutions.employers.R;

public class ListUsersAdapter extends RecyclerView.Adapter<ListUsersAdapter.ListUsersViewHolder> {

    //constants
    private static final String NOTHING = "nothing";

    //vars
    private Context context;
    private List<User> listUsers;

    public ListUsersAdapter(Context context, List<User> listUsers) {
        this.context = context;
        this.listUsers = listUsers;
    }

    @NonNull
    @Override
    public ListUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.list_user_item, parent, false);
        return new ListUsersViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListUsersViewHolder holder, int position) {
        holder.names.setText(listUsers.get(position).getNames());
        holder.lastnames.setText(listUsers.get(position).getLastnames());
        holder.id.setText("Identificado con " +
                listUsers.get(position).getTypeId() + " " + listUsers.get(position).getId());
        if (!listUsers.get(position).getNum1().equals(NOTHING) ||
                !listUsers.get(position).getNum2().equals(NOTHING) ||
                !listUsers.get(position).getNum3().equals(NOTHING)) {
            holder.num1.setText("Tel 1: " + listUsers.get(position).getNum1());
            holder.num2.setText("Tel 2: " + listUsers.get(position).getNum2());
            holder.num3.setText("Tel 3: " + listUsers.get(position).getNum3());
        } else {
            holder.numbers.setVisibility(View.GONE);
        }
        if (!listUsers.get(position).getEmail().equals(NOTHING)) {
            holder.email.setText(listUsers.get(position).getEmail());
        } else {
            holder.email.setVisibility(View.GONE);
        }
        holder.salary.setText(listUsers.get(position).getSalary() + " COP");
    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    static class ListUsersViewHolder extends RecyclerView.ViewHolder {

        TextView names;
        TextView lastnames;
        TextView id;
        TextView num1;
        TextView num2;
        TextView num3;
        TextView email;
        TextView salary;
        LinearLayout numbers;

        ListUsersViewHolder(@NonNull View v) {
            super(v);
            names = v.findViewById(R.id.listuseritem_names);
            lastnames = v.findViewById(R.id.listuseritem_lastnames);
            id = v.findViewById(R.id.listuseritem_id);
            num1 = v.findViewById(R.id.listuseritem_num1);
            num2 = v.findViewById(R.id.listuseritem_num2);
            num3 = v.findViewById(R.id.listuseritem_num3);
            email = v.findViewById(R.id.listuseritem_email);
            salary = v.findViewById(R.id.listuseritem_salary);
            numbers = v.findViewById(R.id.listuseritem_numbers);
        }
    }
}
