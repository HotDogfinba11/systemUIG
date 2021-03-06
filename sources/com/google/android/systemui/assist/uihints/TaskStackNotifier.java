package com.google.android.systemui.assist.uihints;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.util.Log;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;

class TaskStackNotifier implements NgaMessageHandler.ConfigInfoListener {
    private PendingIntent mIntent;
    private final TaskStackChangeListener mListener = new TaskStackChangeListener() {
        /* class com.google.android.systemui.assist.uihints.TaskStackNotifier.AnonymousClass1 */

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
            TaskStackNotifier.this.sendIntent();
        }

        @Override // com.android.systemui.shared.system.TaskStackChangeListener
        public void onTaskCreated(int i, ComponentName componentName) {
            TaskStackNotifier.this.sendIntent();
        }
    };
    private boolean mListenerRegistered = false;
    private final TaskStackChangeListeners mListeners = TaskStackChangeListeners.getInstance();

    TaskStackNotifier() {
    }

    @Override // com.google.android.systemui.assist.uihints.NgaMessageHandler.ConfigInfoListener
    public void onConfigInfo(NgaMessageHandler.ConfigInfo configInfo) {
        PendingIntent pendingIntent = configInfo.onTaskChange;
        this.mIntent = pendingIntent;
        if (pendingIntent != null && !this.mListenerRegistered) {
            this.mListeners.registerTaskStackListener(this.mListener);
            this.mListenerRegistered = true;
        } else if (pendingIntent == null && this.mListenerRegistered) {
            this.mListeners.unregisterTaskStackListener(this.mListener);
            this.mListenerRegistered = false;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void sendIntent() {
        PendingIntent pendingIntent = this.mIntent;
        if (pendingIntent != null) {
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                Log.e("TaskStackNotifier", "could not send intent", e);
            }
        }
    }
}
