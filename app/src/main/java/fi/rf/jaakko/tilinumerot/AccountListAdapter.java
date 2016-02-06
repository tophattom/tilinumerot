package fi.rf.jaakko.tilinumerot;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jaakko on 6.2.2016.
 */
public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.ViewHolder> {
    private ArrayList<String> people;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(TextView t) {
            super(t);
            this.textView = t;
        }
    }

    public AccountListAdapter(ArrayList<String> people) {
        this.people = people;
    }

    public void addPerson(String name) {
        this.people.add(name);

        this.notifyDataSetChanged();
    }

    @Override
    public AccountListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = new TextView(parent.getContext());

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(people.get(position));
    }

    @Override
    public int getItemCount() {
        return people.size();
    }
}
