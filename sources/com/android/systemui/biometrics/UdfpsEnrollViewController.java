package com.android.systemui.biometrics;

import android.graphics.PointF;
import com.android.systemui.R$integer;
import com.android.systemui.biometrics.UdfpsEnrollHelper;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.util.ViewController;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class UdfpsEnrollViewController extends UdfpsAnimationViewController<UdfpsEnrollView> {
    private final UdfpsEnrollHelper mEnrollHelper;
    private final UdfpsEnrollHelper.Listener mEnrollHelperListener = new UdfpsEnrollHelper.Listener() {
        /* class com.android.systemui.biometrics.UdfpsEnrollViewController.AnonymousClass1 */

        @Override // com.android.systemui.biometrics.UdfpsEnrollHelper.Listener
        public void onEnrollmentProgress(int i, int i2) {
            ((UdfpsEnrollView) ((ViewController) UdfpsEnrollViewController.this).mView).onEnrollmentProgress(i, i2);
        }

        @Override // com.android.systemui.biometrics.UdfpsEnrollHelper.Listener
        public void onEnrollmentHelp(int i, int i2) {
            ((UdfpsEnrollView) ((ViewController) UdfpsEnrollViewController.this).mView).onEnrollmentHelp(i, i2);
        }

        @Override // com.android.systemui.biometrics.UdfpsEnrollHelper.Listener
        public void onLastStepAcquired() {
            ((UdfpsEnrollView) ((ViewController) UdfpsEnrollViewController.this).mView).onLastStepAcquired();
        }
    };
    private final int mEnrollProgressBarRadius = getContext().getResources().getInteger(R$integer.config_udfpsEnrollProgressBar);

    /* access modifiers changed from: package-private */
    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public String getTag() {
        return "UdfpsEnrollViewController";
    }

    @Override // com.android.systemui.Dumpable, com.android.systemui.biometrics.UdfpsAnimationViewController
    public /* bridge */ /* synthetic */ void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(fileDescriptor, printWriter, strArr);
    }

    protected UdfpsEnrollViewController(UdfpsEnrollView udfpsEnrollView, UdfpsEnrollHelper udfpsEnrollHelper, StatusBarStateController statusBarStateController, StatusBar statusBar, DumpManager dumpManager) {
        super(udfpsEnrollView, statusBarStateController, statusBar, dumpManager);
        this.mEnrollHelper = udfpsEnrollHelper;
        ((UdfpsEnrollView) this.mView).setEnrollHelper(udfpsEnrollHelper);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.ViewController, com.android.systemui.biometrics.UdfpsAnimationViewController
    public void onViewAttached() {
        super.onViewAttached();
        if (this.mEnrollHelper.shouldShowProgressBar()) {
            this.mEnrollHelper.setListener(this.mEnrollHelperListener);
        }
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public PointF getTouchTranslation() {
        if (!this.mEnrollHelper.isGuidedEnrollmentStage()) {
            return new PointF(0.0f, 0.0f);
        }
        return this.mEnrollHelper.getNextGuidedEnrollmentPoint();
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public int getPaddingX() {
        return this.mEnrollProgressBarRadius;
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public int getPaddingY() {
        return this.mEnrollProgressBarRadius;
    }
}
