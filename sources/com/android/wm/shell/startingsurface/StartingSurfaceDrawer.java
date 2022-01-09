package com.android.wm.shell.startingsurface;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.TaskInfo;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.Display;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.window.SplashScreenView;
import android.window.StartingWindowInfo;
import android.window.TaskSnapshot;
import com.android.internal.R;
import com.android.internal.annotations.VisibleForTesting;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.TransactionPool;
import java.util.function.Supplier;

public class StartingSurfaceDrawer {
    static final boolean DEBUG_SPLASH_SCREEN = StartingWindowController.DEBUG_SPLASH_SCREEN;
    static final String TAG = "StartingSurfaceDrawer";
    private final SparseArray<SurfaceControlViewHost> mAnimatedSplashScreenSurfaceHosts = new SparseArray<>(1);
    private Choreographer mChoreographer;
    private final Context mContext;
    private final DisplayManager mDisplayManager;
    private final ShellExecutor mSplashScreenExecutor;
    @VisibleForTesting
    final SplashscreenContentDrawer mSplashscreenContentDrawer;
    private final SparseArray<StartingWindowRecord> mStartingWindowRecords = new SparseArray<>();

    public StartingSurfaceDrawer(Context context, ShellExecutor shellExecutor, TransactionPool transactionPool) {
        this.mContext = context;
        this.mDisplayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        this.mSplashScreenExecutor = shellExecutor;
        this.mSplashscreenContentDrawer = new SplashscreenContentDrawer(context, transactionPool);
        shellExecutor.execute(new StartingSurfaceDrawer$$ExternalSyntheticLambda2(this));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$new$0() {
        this.mChoreographer = Choreographer.getInstance();
    }

    private Context getDisplayContext(Context context, int i) {
        if (i == 0) {
            return context;
        }
        Display display = this.mDisplayManager.getDisplay(i);
        if (display == null) {
            return null;
        }
        return context.createDisplayContext(display);
    }

    private int getSplashScreenTheme(int i, ActivityInfo activityInfo) {
        if (i != 0) {
            return i;
        }
        if (activityInfo.getThemeResource() != 0) {
            return activityInfo.getThemeResource();
        }
        return 16974563;
    }

    /* access modifiers changed from: package-private */
    public void addSplashScreenStartingWindow(StartingWindowInfo startingWindowInfo, IBinder iBinder, @StartingWindowInfo.StartingWindowType int i) {
        ActivityManager.RunningTaskInfo runningTaskInfo = startingWindowInfo.taskInfo;
        ActivityInfo activityInfo = startingWindowInfo.targetActivityInfo;
        if (activityInfo == null) {
            activityInfo = runningTaskInfo.topActivityInfo;
        }
        if (activityInfo != null && activityInfo.packageName != null) {
            int i2 = runningTaskInfo.displayId;
            int i3 = runningTaskInfo.taskId;
            Context context = this.mContext;
            int splashScreenTheme = getSplashScreenTheme(startingWindowInfo.splashScreenThemeResId, activityInfo);
            boolean z = DEBUG_SPLASH_SCREEN;
            if (z) {
                Slog.d(TAG, "addSplashScreen " + activityInfo.packageName + " theme=" + Integer.toHexString(splashScreenTheme) + " task=" + runningTaskInfo.taskId + " suggestType=" + i);
            }
            Context displayContext = getDisplayContext(context, i2);
            if (displayContext != null) {
                if (splashScreenTheme != displayContext.getThemeResId()) {
                    try {
                        displayContext = displayContext.createPackageContextAsUser(activityInfo.packageName, 4, UserHandle.of(runningTaskInfo.userId));
                        displayContext.setTheme(splashScreenTheme);
                    } catch (PackageManager.NameNotFoundException e) {
                        Slog.w(TAG, "Failed creating package context with package name " + activityInfo.packageName + " for user " + runningTaskInfo.userId, e);
                        return;
                    }
                }
                Configuration configuration = runningTaskInfo.getConfiguration();
                if (configuration.diffPublicOnly(displayContext.getResources().getConfiguration()) != 0) {
                    if (z) {
                        Slog.d(TAG, "addSplashScreen: creating context based on task Configuration " + configuration + " for splash screen");
                    }
                    Context createConfigurationContext = displayContext.createConfigurationContext(configuration);
                    createConfigurationContext.setTheme(splashScreenTheme);
                    TypedArray obtainStyledAttributes = createConfigurationContext.obtainStyledAttributes(R.styleable.Window);
                    int resourceId = obtainStyledAttributes.getResourceId(1, 0);
                    if (resourceId != 0) {
                        try {
                            if (createConfigurationContext.getDrawable(resourceId) != null) {
                                if (z) {
                                    Slog.d(TAG, "addSplashScreen: apply overrideConfig" + configuration + " to starting window resId=" + resourceId);
                                }
                                displayContext = createConfigurationContext;
                            }
                        } catch (Resources.NotFoundException e2) {
                            Slog.w(TAG, "failed creating starting window for overrideConfig at taskId: " + i3, e2);
                            return;
                        }
                    }
                    obtainStyledAttributes.recycle();
                }
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(3);
                layoutParams.setFitInsetsSides(0);
                layoutParams.setFitInsetsTypes(0);
                layoutParams.format = -3;
                int i4 = 16843008;
                TypedArray obtainStyledAttributes2 = displayContext.obtainStyledAttributes(R.styleable.Window);
                if (obtainStyledAttributes2.getBoolean(14, false)) {
                    i4 = 17891584;
                }
                if (i != 4 || obtainStyledAttributes2.getBoolean(33, false)) {
                    i4 |= Integer.MIN_VALUE;
                }
                layoutParams.layoutInDisplayCutoutMode = obtainStyledAttributes2.getInt(50, layoutParams.layoutInDisplayCutoutMode);
                layoutParams.windowAnimations = obtainStyledAttributes2.getResourceId(8, 0);
                obtainStyledAttributes2.recycle();
                if (i2 == 0 && startingWindowInfo.isKeyguardOccluded) {
                    i4 |= 524288;
                }
                layoutParams.flags = 131096 | i4;
                layoutParams.token = iBinder;
                layoutParams.packageName = activityInfo.packageName;
                int i5 = layoutParams.privateFlags | 16;
                layoutParams.privateFlags = i5;
                layoutParams.privateFlags = i5 | 536870912;
                if (!displayContext.getResources().getCompatibilityInfo().supportsScreen()) {
                    layoutParams.privateFlags |= 128;
                }
                layoutParams.setTitle("Splash Screen " + activityInfo.packageName);
                SplashScreenViewSupplier splashScreenViewSupplier = new SplashScreenViewSupplier();
                FrameLayout frameLayout = new FrameLayout(displayContext);
                frameLayout.setPadding(0, 0, 0, 0);
                frameLayout.setFitsSystemWindows(false);
                StartingSurfaceDrawer$$ExternalSyntheticLambda6 startingSurfaceDrawer$$ExternalSyntheticLambda6 = new StartingSurfaceDrawer$$ExternalSyntheticLambda6(this, splashScreenViewSupplier, i3, iBinder, frameLayout);
                this.mSplashscreenContentDrawer.createContentView(displayContext, i, activityInfo, i3, new StartingSurfaceDrawer$$ExternalSyntheticLambda8(splashScreenViewSupplier));
                try {
                    if (addWindow(i3, iBinder, frameLayout, (WindowManager) displayContext.getSystemService(WindowManager.class), layoutParams, i)) {
                        this.mChoreographer.postCallback(2, startingSurfaceDrawer$$ExternalSyntheticLambda6, null);
                        this.mStartingWindowRecords.get(i3).mBGColor = splashScreenViewSupplier.get().getInitBackgroundColor();
                    }
                } catch (RuntimeException e3) {
                    Slog.w(TAG, "failed creating starting window at taskId: " + i3, e3);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$addSplashScreenStartingWindow$1(SplashScreenViewSupplier splashScreenViewSupplier, int i, IBinder iBinder, FrameLayout frameLayout) {
        Trace.traceBegin(32, "addSplashScreenView");
        SplashScreenView splashScreenView = splashScreenViewSupplier.get();
        StartingWindowRecord startingWindowRecord = this.mStartingWindowRecords.get(i);
        if (startingWindowRecord != null && iBinder == startingWindowRecord.mAppToken) {
            if (splashScreenView != null) {
                try {
                    frameLayout.addView(splashScreenView);
                } catch (RuntimeException e) {
                    String str = TAG;
                    Slog.w(str, "failed set content view to starting window at taskId: " + i, e);
                    splashScreenView = null;
                }
            }
            startingWindowRecord.setSplashScreenView(splashScreenView);
        }
        Trace.traceEnd(32);
    }

    /* access modifiers changed from: package-private */
    public int getStartingWindowBackgroundColorForTask(int i) {
        StartingWindowRecord startingWindowRecord = this.mStartingWindowRecords.get(i);
        if (startingWindowRecord == null) {
            return 0;
        }
        return startingWindowRecord.mBGColor;
    }

    /* access modifiers changed from: private */
    public static class SplashScreenViewSupplier implements Supplier<SplashScreenView> {
        private boolean mIsViewSet;
        private SplashScreenView mView;

        private SplashScreenViewSupplier() {
        }

        /* access modifiers changed from: package-private */
        public void setView(SplashScreenView splashScreenView) {
            synchronized (this) {
                this.mView = splashScreenView;
                this.mIsViewSet = true;
                notify();
            }
        }

        /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
        /* JADX WARNING: Missing exception handler attribute for start block: B:1:0x0001 */
        /* JADX WARNING: Removed duplicated region for block: B:1:0x0001 A[LOOP:0: B:1:0x0001->B:12:0x0001, LOOP_START, SYNTHETIC] */
        @Override // java.util.function.Supplier
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public android.window.SplashScreenView get() {
            /*
                r1 = this;
                monitor-enter(r1)
            L_0x0001:
                boolean r0 = r1.mIsViewSet     // Catch:{ all -> 0x000d }
                if (r0 != 0) goto L_0x0009
                r1.wait()     // Catch:{ InterruptedException -> 0x0001 }
                goto L_0x0001
            L_0x0009:
                android.window.SplashScreenView r0 = r1.mView
                monitor-exit(r1)
                return r0
            L_0x000d:
                r0 = move-exception
                monitor-exit(r1)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.startingsurface.StartingSurfaceDrawer.SplashScreenViewSupplier.get():android.window.SplashScreenView");
        }
    }

    /* access modifiers changed from: package-private */
    public int estimateTaskBackgroundColor(TaskInfo taskInfo) {
        ActivityInfo activityInfo = taskInfo.topActivityInfo;
        if (activityInfo == null) {
            return 0;
        }
        String str = activityInfo.packageName;
        int i = taskInfo.userId;
        try {
            Context createPackageContextAsUser = this.mContext.createPackageContextAsUser(str, 4, UserHandle.of(i));
            try {
                String splashScreenTheme = ActivityThread.getPackageManager().getSplashScreenTheme(str, i);
                int splashScreenTheme2 = getSplashScreenTheme(splashScreenTheme != null ? createPackageContextAsUser.getResources().getIdentifier(splashScreenTheme, null, null) : 0, activityInfo);
                if (splashScreenTheme2 != createPackageContextAsUser.getThemeResId()) {
                    createPackageContextAsUser.setTheme(splashScreenTheme2);
                }
                return this.mSplashscreenContentDrawer.estimateTaskBackgroundColor(createPackageContextAsUser);
            } catch (RemoteException | RuntimeException e) {
                String str2 = TAG;
                Slog.w(str2, "failed get starting window background color at taskId: " + taskInfo.taskId, e);
                return 0;
            }
        } catch (PackageManager.NameNotFoundException e2) {
            String str3 = TAG;
            Slog.w(str3, "Failed creating package context with package name " + str + " for user " + taskInfo.userId, e2);
            return 0;
        }
    }

    /* access modifiers changed from: package-private */
    public void makeTaskSnapshotWindow(StartingWindowInfo startingWindowInfo, IBinder iBinder, TaskSnapshot taskSnapshot) {
        int i = startingWindowInfo.taskInfo.taskId;
        lambda$makeTaskSnapshotWindow$2(i);
        TaskSnapshotWindow create = TaskSnapshotWindow.create(startingWindowInfo, iBinder, taskSnapshot, this.mSplashScreenExecutor, new StartingSurfaceDrawer$$ExternalSyntheticLambda4(this, i));
        if (create != null) {
            this.mStartingWindowRecords.put(i, new StartingWindowRecord(iBinder, null, create, 2));
        }
    }

    public void removeStartingWindow(int i, SurfaceControl surfaceControl, Rect rect, boolean z) {
        if (DEBUG_SPLASH_SCREEN) {
            String str = TAG;
            Slog.d(str, "Task start finish, remove starting surface for task " + i);
        }
        removeWindowSynced(i, surfaceControl, rect, z);
    }

    public void copySplashScreenView(int i) {
        StartingWindowRecord startingWindowRecord = this.mStartingWindowRecords.get(i);
        SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable = null;
        SplashScreenView splashScreenView = startingWindowRecord != null ? startingWindowRecord.mContentView : null;
        if (splashScreenView != null && splashScreenView.isCopyable()) {
            splashScreenViewParcelable = new SplashScreenView.SplashScreenViewParcelable(splashScreenView);
            splashScreenViewParcelable.setClientCallback(new RemoteCallback(new StartingSurfaceDrawer$$ExternalSyntheticLambda0(this, i)));
            splashScreenView.onCopied();
            this.mAnimatedSplashScreenSurfaceHosts.append(i, splashScreenView.getSurfaceHost());
        }
        if (DEBUG_SPLASH_SCREEN) {
            String str = TAG;
            Slog.v(str, "Copying splash screen window view for task: " + i + " parcelable: " + splashScreenViewParcelable);
        }
        ActivityTaskManager.getInstance().onSplashScreenViewCopyFinished(i, splashScreenViewParcelable);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$copySplashScreenView$4(int i, Bundle bundle) {
        this.mSplashScreenExecutor.execute(new StartingSurfaceDrawer$$ExternalSyntheticLambda3(this, i));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$copySplashScreenView$3(int i) {
        onAppSplashScreenViewRemoved(i, false);
    }

    public void onAppSplashScreenViewRemoved(int i) {
        onAppSplashScreenViewRemoved(i, true);
    }

    private void onAppSplashScreenViewRemoved(int i, boolean z) {
        SurfaceControlViewHost surfaceControlViewHost = this.mAnimatedSplashScreenSurfaceHosts.get(i);
        if (surfaceControlViewHost != null) {
            this.mAnimatedSplashScreenSurfaceHosts.remove(i);
            if (DEBUG_SPLASH_SCREEN) {
                String str = z ? "Server cleaned up" : "App removed";
                String str2 = TAG;
                Slog.v(str2, str + "the splash screen. Releasing SurfaceControlViewHost for task:" + i);
            }
            surfaceControlViewHost.getView().post(new StartingSurfaceDrawer$$ExternalSyntheticLambda1(surfaceControlViewHost));
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0054  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean addWindow(int r8, android.os.IBinder r9, android.view.View r10, android.view.WindowManager r11, android.view.WindowManager.LayoutParams r12, @android.window.StartingWindowInfo.StartingWindowType int r13) {
        /*
        // Method dump skipped, instructions count: 111
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.startingsurface.StartingSurfaceDrawer.addWindow(int, android.os.IBinder, android.view.View, android.view.WindowManager, android.view.WindowManager$LayoutParams, int):boolean");
    }

    private void saveSplashScreenRecord(IBinder iBinder, int i, View view, @StartingWindowInfo.StartingWindowType int i2) {
        this.mStartingWindowRecords.put(i, new StartingWindowRecord(iBinder, view, null, i2));
    }

    /* access modifiers changed from: private */
    /* renamed from: removeWindowNoAnimate */
    public void lambda$makeTaskSnapshotWindow$2(int i) {
        removeWindowSynced(i, null, null, false);
    }

    /* access modifiers changed from: package-private */
    public void onImeDrawnOnTask(int i) {
        StartingWindowRecord startingWindowRecord = this.mStartingWindowRecords.get(i);
        if (!(startingWindowRecord == null || startingWindowRecord.mTaskSnapshotWindow == null || !startingWindowRecord.mTaskSnapshotWindow.hasImeSurface())) {
            startingWindowRecord.mTaskSnapshotWindow.removeImmediately();
        }
        this.mStartingWindowRecords.remove(i);
    }

    /* access modifiers changed from: protected */
    public void removeWindowSynced(int i, SurfaceControl surfaceControl, Rect rect, boolean z) {
        StartingWindowRecord startingWindowRecord = this.mStartingWindowRecords.get(i);
        if (startingWindowRecord != null) {
            if (startingWindowRecord.mDecorView != null) {
                if (DEBUG_SPLASH_SCREEN) {
                    String str = TAG;
                    Slog.v(str, "Removing splash screen window for task: " + i);
                }
                if (startingWindowRecord.mContentView == null) {
                    Slog.e(TAG, "Found empty splash screen, remove!");
                    removeWindowInner(startingWindowRecord.mDecorView, false);
                } else if (startingWindowRecord.mSuggestType == 4) {
                    removeWindowInner(startingWindowRecord.mDecorView, false);
                } else if (z) {
                    this.mSplashscreenContentDrawer.applyExitAnimation(startingWindowRecord.mContentView, surfaceControl, rect, new StartingSurfaceDrawer$$ExternalSyntheticLambda7(this, startingWindowRecord));
                } else {
                    removeWindowInner(startingWindowRecord.mDecorView, true);
                }
                this.mStartingWindowRecords.remove(i);
            }
            if (startingWindowRecord.mTaskSnapshotWindow != null) {
                startingWindowRecord.mTaskSnapshotWindow.scheduleRemove(new StartingSurfaceDrawer$$ExternalSyntheticLambda5(this, i));
            }
        }
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$removeWindowSynced$5(StartingWindowRecord startingWindowRecord) {
        removeWindowInner(startingWindowRecord.mDecorView, true);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$removeWindowSynced$6(int i) {
        this.mStartingWindowRecords.remove(i);
    }

    private void removeWindowInner(View view, boolean z) {
        if (z) {
            view.setVisibility(8);
        }
        WindowManager windowManager = (WindowManager) view.getContext().getSystemService(WindowManager.class);
        if (windowManager != null) {
            windowManager.removeView(view);
        }
    }

    /* access modifiers changed from: private */
    public static class StartingWindowRecord {
        private final IBinder mAppToken;
        private int mBGColor;
        private SplashScreenView mContentView;
        private final View mDecorView;
        private boolean mSetSplashScreen;
        @StartingWindowInfo.StartingWindowType
        private int mSuggestType;
        private final TaskSnapshotWindow mTaskSnapshotWindow;

        StartingWindowRecord(IBinder iBinder, View view, TaskSnapshotWindow taskSnapshotWindow, @StartingWindowInfo.StartingWindowType int i) {
            this.mAppToken = iBinder;
            this.mDecorView = view;
            this.mTaskSnapshotWindow = taskSnapshotWindow;
            if (taskSnapshotWindow != null) {
                this.mBGColor = taskSnapshotWindow.getBackgroundColor();
            }
            this.mSuggestType = i;
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private void setSplashScreenView(SplashScreenView splashScreenView) {
            if (!this.mSetSplashScreen) {
                this.mContentView = splashScreenView;
                this.mSetSplashScreen = true;
            }
        }
    }
}
