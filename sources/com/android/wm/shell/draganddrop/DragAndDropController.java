package com.android.wm.shell.draganddrop;

import android.content.ClipDescription;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Slog;
import android.util.SparseArray;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.wm.shell.R;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.splitscreen.SplitScreenController;
import java.util.Optional;

public class DragAndDropController implements DisplayController.OnDisplaysChangedListener, View.OnDragListener {
    private static final String TAG = DragAndDropController.class.getSimpleName();
    private final Context mContext;
    private final DisplayController mDisplayController;
    private final SparseArray<PerDisplay> mDisplayDropTargets = new SparseArray<>();
    private SplitScreenController mSplitScreen;
    private final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();

    public DragAndDropController(Context context, DisplayController displayController) {
        this.mContext = context;
        this.mDisplayController = displayController;
    }

    public void initialize(Optional<SplitScreenController> optional) {
        this.mSplitScreen = optional.orElse(null);
        this.mDisplayController.addDisplayWindowListener(this);
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public void onDisplayAdded(int i) {
        if (ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled) {
            ShellProtoLogImpl.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -1006733970, 1, null, Long.valueOf((long) i));
        }
        Context createWindowContext = this.mDisplayController.getDisplayContext(i).createWindowContext(2038, null);
        WindowManager windowManager = (WindowManager) createWindowContext.getSystemService(WindowManager.class);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2038, 16777224, -3);
        layoutParams.privateFlags |= -2147483568;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle("ShellDropTarget");
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(createWindowContext).inflate(R.layout.global_drop_target, (ViewGroup) null);
        frameLayout.setOnDragListener(this);
        frameLayout.setVisibility(4);
        DragLayout dragLayout = new DragLayout(createWindowContext, this.mSplitScreen);
        frameLayout.addView(dragLayout, new FrameLayout.LayoutParams(-1, -1));
        try {
            windowManager.addView(frameLayout, layoutParams);
            this.mDisplayDropTargets.put(i, new PerDisplay(i, createWindowContext, windowManager, frameLayout, dragLayout));
        } catch (WindowManager.InvalidDisplayException unused) {
            Slog.w(TAG, "Unable to add view for display id: " + i);
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public void onDisplayConfigurationChanged(int i, Configuration configuration) {
        if (ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled) {
            ShellProtoLogImpl.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, 2057038970, 1, null, Long.valueOf((long) i));
        }
        PerDisplay perDisplay = this.mDisplayDropTargets.get(i);
        if (perDisplay != null) {
            perDisplay.rootView.requestApplyInsets();
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public void onDisplayRemoved(int i) {
        if (ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled) {
            ShellProtoLogImpl.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -1382704050, 1, null, Long.valueOf((long) i));
        }
        PerDisplay perDisplay = this.mDisplayDropTargets.get(i);
        if (perDisplay != null) {
            perDisplay.wm.removeViewImmediate(perDisplay.rootView);
            this.mDisplayDropTargets.remove(i);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00cf A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00d0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onDrag(android.view.View r17, android.view.DragEvent r18) {
        /*
        // Method dump skipped, instructions count: 314
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.draganddrop.DragAndDropController.onDrag(android.view.View, android.view.DragEvent):boolean");
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$onDrag$0(PerDisplay perDisplay) {
        if (perDisplay.activeDragCount == 0) {
            setDropTargetWindowVisibility(perDisplay, 4);
        }
    }

    private boolean handleDrop(DragEvent dragEvent, PerDisplay perDisplay) {
        SurfaceControl dragSurface = dragEvent.getDragSurface();
        perDisplay.activeDragCount--;
        return perDisplay.dragLayout.drop(dragEvent, dragSurface, new DragAndDropController$$ExternalSyntheticLambda1(this, perDisplay, dragSurface));
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDrop$1(PerDisplay perDisplay, SurfaceControl surfaceControl) {
        if (perDisplay.activeDragCount == 0) {
            setDropTargetWindowVisibility(perDisplay, 4);
        }
        this.mTransaction.reparent(surfaceControl, null);
        this.mTransaction.apply();
    }

    private void setDropTargetWindowVisibility(PerDisplay perDisplay, int i) {
        if (ShellProtoLogCache.WM_SHELL_DRAG_AND_DROP_enabled) {
            long j = (long) perDisplay.displayId;
            ShellProtoLogImpl.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, 1184615936, 5, null, Long.valueOf(j), Long.valueOf((long) i));
        }
        perDisplay.rootView.setVisibility(i);
        if (i == 0) {
            perDisplay.rootView.requestApplyInsets();
        }
    }

    private String getMimeTypes(ClipDescription clipDescription) {
        String str = "";
        for (int i = 0; i < clipDescription.getMimeTypeCount(); i++) {
            if (i > 0) {
                str = str + ", ";
            }
            str = str + clipDescription.getMimeType(i);
        }
        return str;
    }

    /* access modifiers changed from: private */
    public static class PerDisplay {
        int activeDragCount;
        final Context context;
        final int displayId;
        final DragLayout dragLayout;
        boolean isHandlingDrag;
        final FrameLayout rootView;
        final WindowManager wm;

        PerDisplay(int i, Context context2, WindowManager windowManager, FrameLayout frameLayout, DragLayout dragLayout2) {
            this.displayId = i;
            this.context = context2;
            this.wm = windowManager;
            this.rootView = frameLayout;
            this.dragLayout = dragLayout2;
        }
    }
}
