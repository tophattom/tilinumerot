package fi.rf.jaakko.tilinumerot;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

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
}
