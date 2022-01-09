package com.google.android.systemui.gamedashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public final class GameDndConfigActivity extends Activity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        startActivityForResult(new Intent("com.google.android.settings.games.GAME_SETTINGS").setPackage("com.android.settings"), 0);
        finish();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        finish();
    }
}
