package fi.rf.jaakko.tilinumerot;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jaakko on 6.2.2016.
 */
public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.ViewHolder> {
    private ArrayList<Person> people;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView;
        public TextView accountView;

        public ViewHolder(View personView) {
            super(personView);

            this.nameView = (TextView) personView.findViewById(R.id.person_list_item_name);
            this.accountView = (TextView) personView.findViewById(R.id.person_list_item_account);
        }
    }

    public AccountListAdapter(ArrayList<Person> people) {
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
    }

    @Override
    public int getItemCount() {
        return people.size();
    }
}
