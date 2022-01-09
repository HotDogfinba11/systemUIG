package com.google.android.systemui.elmyra.gates;

import android.content.Context;
import android.util.Log;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;

public class LockTask extends Gate {
    private boolean mIsBlocked;
    private final TaskStackChangeListener mTaskStackListener = new TaskStackChangeListener() {
        /* class com.google.android.systemui.elmyra.gates.LockTask.AnonymousClass1 */

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onLockTaskModeChanged(int i) {
            Log.d("Elmyra/LockTask", "Mode: " + i);
            if (i == 0) {
                LockTask.this.mIsBlocked = false;
            } else {
                LockTask.this.mIsBlocked = true;
            }
            LockTask.this.notifyListener();
        }
    };

    public LockTask(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public void onActivate() {
        TaskStackChangeListeners.getInstance().registerTaskStackListener(this.mTaskStackListener);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public void onDeactivate() {
        TaskStackChangeListeners.getInstance().unregisterTaskStackListener(this.mTaskStackListener);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.android.systemui.elmyra.gates.Gate
    public boolean isBlocked() {
        return this.mIsBlocked;
    }
}
