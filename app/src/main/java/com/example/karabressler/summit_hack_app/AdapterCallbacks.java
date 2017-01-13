package com.example.karabressler.summit_hack_app;

import android.view.View;

/**
 * Created by theod on 1/12/2017.
 */

public interface AdapterCallbacks<JSONObject> {

    void onItemClicked(JSONObject object, View sharedView);
}