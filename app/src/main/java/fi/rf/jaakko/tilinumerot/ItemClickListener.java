package fi.rf.jaakko.tilinumerot;

import android.view.View;

/**
 * Created by jaakko on 6.2.2016.
 */
public interface ItemClickListener {
    public void onClick(View view, int position);
    public void onLongClick(View view, int position);
}
