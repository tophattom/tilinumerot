package fi.rf.jaakko.tilinumerot;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaakko on 6.2.2016.
 */
public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.ViewHolder> implements ActionMode.Callback {

    private Context context;
    private Activity activity;

    private PeopleStorage people;
    private SparseBooleanArray selectedItems;

    private ActionMode actionMode;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private View personView;
        private TextView nameView;
        private TextView accountView;

        private ItemClickListener clickListener;

        public ViewHolder(View personView) {
            super(personView);

            this.personView = personView;
            this.nameView = (TextView) personView.findViewById(R.id.person_list_item_name);
            this.accountView = (TextView) personView.findViewById(R.id.person_list_item_account);

            personView.setOnClickListener(this);
            personView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onLongClick(v, getAdapterPosition());
            return true;
        }
    }

    public AccountListAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

        String filename = context.getResources().getString(R.string.save_filename);
        this.people = new PeopleStorage(context, filename);

        this.selectedItems = new SparseBooleanArray();
    }

    public void addPerson(Person person) {
        this.people.add(person);

        this.notifyDataSetChanged();
    }

    public Person getPerson(int position) {
        return people.get(position);
    }


    public void toggleSelection(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }

        String title = activity.getString(R.string.selected_count, selectedItems.size());
        actionMode.setTitle(title);

        notifyItemChanged(position);
    }

    public void clearSelection() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemsCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItemPositions() {
        List<Integer> items = new ArrayList<>(selectedItems.size());

        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }

        return items;
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

        holder.personView.setActivated(selectedItems.get(position, false));

        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, final int position) {
                if (actionMode != null) {
                    toggleSelection(position);
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setItems(R.array.click_actions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("Tilinumero", people.get(position).getAccount());
                            cm.setPrimaryClip(clip);

                            Toast.makeText(context, "Tilinumero kopioitu leikepöydälle", Toast.LENGTH_SHORT).show();
                        } else if (which == 1) {
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, people.get(position).getAccount());
                            sendIntent.setType("text/plain");

                            context.startActivity(sendIntent);
                        }
                    }
                });

                builder.create().show();
            }

            @Override
            public void onLongClick(View view, int position) {

                if (actionMode != null) {
                    return;
                }

                actionMode = activity.startActionMode(AccountListAdapter.this);
                toggleSelection(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();

        inflater.inflate(R.menu.menu_select_people, menu);

        FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() == R.id.delete_selected) {
            List<Integer> selectedItemPositions = getSelectedItemPositions();

            for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
                people.remove(selectedItemPositions.get(i));
            }

            mode.finish();
            notifyDataSetChanged();
        }

        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        clearSelection();
        actionMode = null;

        FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
    }
}
