package fi.rf.jaakko.tilinumerot;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jaakko on 6.2.2016.
 */
public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Person> people;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameView;
        private TextView accountView;

        private ItemClickListener clickListener;

        public ViewHolder(View personView) {
            super(personView);

            this.nameView = (TextView) personView.findViewById(R.id.person_list_item_name);
            this.accountView = (TextView) personView.findViewById(R.id.person_list_item_account);

            personView.setOnClickListener(this);
        }

        public void setClickListener(ItemClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }

    public AccountListAdapter(Context context, ArrayList<Person> people) {
        this.context = context;
        this.people = people;
    }

    public void addPerson(Person person) {
        this.people.add(person);

        this.notifyDataSetChanged();
    }

    public Person getPerson(int position) {
        return people.get(position);
    }

    @Override
    public AccountListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.person_layout, parent, false);

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Person person = people.get(position);

        holder.nameView.setText(person.getName());
        holder.accountView.setText(person.getAccount());

        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setItems(R.array.actions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Toast.makeText(context, "Tilinumero kopioitu leikepöydälle", Toast.LENGTH_SHORT).show();
                        } else if (which == 1) {

                        }
                    }
                });

                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return people.size();
    }
}
