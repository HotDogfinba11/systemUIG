package com.android.wm.shell.hidedisplaycutout;

import android.content.Context;
import android.content.res.Configuration;
import android.os.SystemProperties;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import java.io.PrintWriter;

public class HideDisplayCutoutController {
    private final Context mContext;
    boolean mEnabled;
    private final HideDisplayCutoutImpl mImpl = new HideDisplayCutoutImpl();
    private final ShellExecutor mMainExecutor;
    private final HideDisplayCutoutOrganizer mOrganizer;

    public static HideDisplayCutoutController create(Context context, DisplayController displayController, ShellExecutor shellExecutor) {
        if (!SystemProperties.getBoolean("ro.support_hide_display_cutout", false)) {
            return null;
        }
        return new HideDisplayCutoutController(context, new HideDisplayCutoutOrganizer(context, displayController, shellExecutor), shellExecutor);
    }

    HideDisplayCutoutController(Context context, HideDisplayCutoutOrganizer hideDisplayCutoutOrganizer, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mOrganizer = hideDisplayCutoutOrganizer;
        this.mMainExecutor = shellExecutor;
        updateStatus();
    }

    public HideDisplayCutout asHideDisplayCutout() {
        return this.mImpl;
    }

    /* access modifiers changed from: package-private */
    public void updateStatus() {
        boolean z = this.mContext.getResources().getBoolean(17891575);
        if (z != this.mEnabled) {
            this.mEnabled = z;
            if (z) {
                this.mOrganizer.enableHideDisplayCutout();
            } else {
                this.mOrganizer.disableHideDisplayCutout();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onConfigurationChanged(Configuration configuration) {
        updateStatus();
    }

    public void dump(PrintWriter printWriter) {
        printWriter.print("HideDisplayCutoutController");
        printWriter.println(" states: ");
        printWriter.print("  ");
        printWriter.print("mEnabled=");
        printWriter.println(this.mEnabled);
        this.mOrganizer.dump(printWriter);
    }

    /* access modifiers changed from: private */
    public class HideDisplayCutoutImpl implements HideDisplayCutout {
        private HideDisplayCutoutImpl() {
        }

        @Override // com.android.wm.shell.hidedisplaycutout.HideDisplayCutout
        public void onConfigurationChanged(Configuration configuration) {
            HideDisplayCutoutController.this.mMainExecutor.execute(new HideDisplayCutoutController$HideDisplayCutoutImpl$$ExternalSyntheticLambda0(this, configuration));
        }

        /* access modifiers changed from: private */
        public /* synthetic */ void lambda$onConfigurationChanged$0(Configuration configuration) {
            HideDisplayCutoutController.this.onConfigurationChanged(configuration);
        }
    }
}
