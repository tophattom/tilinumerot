package fi.rf.jaakko.tilinumerot;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AddPersonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Context appContext = getApplicationContext();
        final Drawable dismissIcon = ContextCompat.getDrawable(appContext, android.R.drawable.ic_delete);
        dismissIcon.setColorFilter(ContextCompat.getColor(appContext, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(dismissIcon);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_person, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                EditText nameField = (EditText) findViewById(R.id.new_person_name);
                EditText accountField = (EditText) findViewById(R.id.new_person_account);

                String name = nameField.getText().toString();
                String account = accountField.getText().toString();

                Person newPerson = new Person(name, account);

                Intent data = new Intent();
                data.putExtra("person", newPerson);
                setResult(RESULT_OK, data);
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
