package com.android.wm.shell;

import com.android.wm.shell.apppairs.AppPairsController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.hidedisplaycutout.HideDisplayCutoutController;
import com.android.wm.shell.legacysplitscreen.LegacySplitScreenController;
import com.android.wm.shell.onehanded.OneHandedController;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.splitscreen.SplitScreenController;
import java.io.PrintWriter;
import java.util.Optional;

public final class ShellCommandHandlerImpl {
    private final Optional<AppPairsController> mAppPairsOptional;
    private final Optional<HideDisplayCutoutController> mHideDisplayCutout;
    private final HandlerImpl mImpl = new HandlerImpl();
    private final Optional<LegacySplitScreenController> mLegacySplitScreenOptional;
    private final ShellExecutor mMainExecutor;
    private final Optional<OneHandedController> mOneHandedOptional;
    private final Optional<Pip> mPipOptional;
    private final ShellTaskOrganizer mShellTaskOrganizer;
    private final Optional<SplitScreenController> mSplitScreenOptional;

    public ShellCommandHandlerImpl(ShellTaskOrganizer shellTaskOrganizer, Optional<LegacySplitScreenController> optional, Optional<SplitScreenController> optional2, Optional<Pip> optional3, Optional<OneHandedController> optional4, Optional<HideDisplayCutoutController> optional5, Optional<AppPairsController> optional6, ShellExecutor shellExecutor) {
        this.mShellTaskOrganizer = shellTaskOrganizer;
        this.mLegacySplitScreenOptional = optional;
        this.mSplitScreenOptional = optional2;
        this.mPipOptional = optional3;
        this.mOneHandedOptional = optional4;
        this.mHideDisplayCutout = optional5;
        this.mAppPairsOptional = optional6;
        this.mMainExecutor = shellExecutor;
    }

    public ShellCommandHandler asShellCommandHandler() {
        return this.mImpl;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void dump(PrintWriter printWriter) {
        this.mShellTaskOrganizer.dump(printWriter, "");
        printWriter.println();
        printWriter.println();
        this.mPipOptional.ifPresent(new ShellCommandHandlerImpl$$ExternalSyntheticLambda9(printWriter));
        this.mLegacySplitScreenOptional.ifPresent(new ShellCommandHandlerImpl$$ExternalSyntheticLambda7(printWriter));
        this.mOneHandedOptional.ifPresent(new ShellCommandHandlerImpl$$ExternalSyntheticLambda8(printWriter));
        this.mHideDisplayCutout.ifPresent(new ShellCommandHandlerImpl$$ExternalSyntheticLambda6(printWriter));
        printWriter.println();
        printWriter.println();
        this.mAppPairsOptional.ifPresent(new ShellCommandHandlerImpl$$ExternalSyntheticLambda5(printWriter));
        printWriter.println();
        printWriter.println();
        this.mSplitScreenOptional.ifPresent(new ShellCommandHandlerImpl$$ExternalSyntheticLambda10(printWriter));
    }

    /* access modifiers changed from: private */
    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* access modifiers changed from: public */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0048, code lost:
        if (r3.equals("moveToSideStage") == false) goto L_0x0014;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean handleCommand(java.lang.String[] r7, java.io.PrintWriter r8) {
        /*
        // Method dump skipped, instructions count: 184
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.ShellCommandHandlerImpl.handleCommand(java.lang.String[], java.io.PrintWriter):boolean");
    }

    private boolean runPair(String[] strArr, PrintWriter printWriter) {
        if (strArr.length < 4) {
            printWriter.println("Error: two task ids should be provided as arguments");
            return false;
        }
        this.mAppPairsOptional.ifPresent(new ShellCommandHandlerImpl$$ExternalSyntheticLambda3(new Integer(strArr[2]).intValue(), new Integer(strArr[3]).intValue()));
        return true;
    }

    private boolean runUnpair(String[] strArr, PrintWriter printWriter) {
        if (strArr.length < 3) {
            printWriter.println("Error: task id should be provided as an argument");
            return false;
        }
        this.mAppPairsOptional.ifPresent(new ShellCommandHandlerImpl$$ExternalSyntheticLambda0(new Integer(strArr[2]).intValue()));
        return true;
    }

    private boolean runMoveToSideStage(String[] strArr, PrintWriter printWriter) {
        if (strArr.length < 3) {
            printWriter.println("Error: task id should be provided as arguments");
            return false;
        }
        this.mSplitScreenOptional.ifPresent(new ShellCommandHandlerImpl$$ExternalSyntheticLambda4(new Integer(strArr[2]).intValue(), strArr.length > 3 ? new Integer(strArr[3]).intValue() : 1));
        return true;
    }

    private boolean runRemoveFromSideStage(String[] strArr, PrintWriter printWriter) {
        if (strArr.length < 3) {
            printWriter.println("Error: task id should be provided as arguments");
            return false;
        }
        this.mSplitScreenOptional.ifPresent(new ShellCommandHandlerImpl$$ExternalSyntheticLambda1(new Integer(strArr[2]).intValue()));
        return true;
    }

    private boolean runSetSideStagePosition(String[] strArr, PrintWriter printWriter) {
        if (strArr.length < 3) {
            printWriter.println("Error: side stage position should be provided as arguments");
            return false;
        }
        this.mSplitScreenOptional.ifPresent(new ShellCommandHandlerImpl$$ExternalSyntheticLambda2(new Integer(strArr[2]).intValue()));
        return true;
    }

    private boolean runSetSideStageVisibility(String[] strArr, PrintWriter printWriter) {
        if (strArr.length < 3) {
            printWriter.println("Error: side stage position should be provided as arguments");
            return false;
        }
        this.mSplitScreenOptional.ifPresent(new ShellCommandHandlerImpl$$ExternalSyntheticLambda11(new Boolean(strArr[2])));
        return true;
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ void lambda$runSetSideStageVisibility$11(Boolean bool, SplitScreenController splitScreenController) {
        splitScreenController.setSideStageVisibility(bool.booleanValue());
    }

    private boolean runHelp(PrintWriter printWriter) {
        printWriter.println("Window Manager Shell commands:");
        printWriter.println("  help");
        printWriter.println("      Print this help text.");
        printWriter.println("  <no arguments provided>");
        printWriter.println("    Dump Window Manager Shell internal state");
        printWriter.println("  pair <taskId1> <taskId2>");
        printWriter.println("  unpair <taskId>");
        printWriter.println("    Pairs/unpairs tasks with given ids.");
        printWriter.println("  moveToSideStage <taskId> <SideStagePosition>");
        printWriter.println("    Move a task with given id in split-screen mode.");
        printWriter.println("  removeFromSideStage <taskId>");
        printWriter.println("    Remove a task with given id in split-screen mode.");
        printWriter.println("  setSideStagePosition <SideStagePosition>");
        printWriter.println("    Sets the position of the side-stage.");
        printWriter.println("  setSideStageVisibility <true/false>");
        printWriter.println("    Show/hide side-stage.");
        return true;
    }

    /* access modifiers changed from: private */
    public class HandlerImpl implements ShellCommandHandler {
        private HandlerImpl() {
        }

        /* access modifiers changed from: private */
        public /* synthetic */ void lambda$dump$0(PrintWriter printWriter) {
            ShellCommandHandlerImpl.this.dump(printWriter);
        }

        @Override // com.android.wm.shell.ShellCommandHandler
        public void dump(PrintWriter printWriter) {
            try {
                ShellCommandHandlerImpl.this.mMainExecutor.executeBlocking(new ShellCommandHandlerImpl$HandlerImpl$$ExternalSyntheticLambda0(this, printWriter));
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to dump the Shell in 2s", e);
            }
        }

        @Override // com.android.wm.shell.ShellCommandHandler
        public boolean handleCommand(String[] strArr, PrintWriter printWriter) {
            try {
                boolean[] zArr = new boolean[1];
                ShellCommandHandlerImpl.this.mMainExecutor.executeBlocking(new ShellCommandHandlerImpl$HandlerImpl$$ExternalSyntheticLambda1(this, zArr, strArr, printWriter));
                return zArr[0];
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to handle Shell command in 2s", e);
            }
        }

        /* access modifiers changed from: private */
        public /* synthetic */ void lambda$handleCommand$1(boolean[] zArr, String[] strArr, PrintWriter printWriter) {
            zArr[0] = ShellCommandHandlerImpl.this.handleCommand(strArr, printWriter);
        }
    }
}
