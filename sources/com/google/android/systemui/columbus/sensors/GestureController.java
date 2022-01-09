package com.google.android.systemui.columbus.sensors;

import android.os.SystemClock;
import android.util.SparseLongArray;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Dumpable;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.google.android.systemui.columbus.gates.Gate;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GestureController.kt */
public class GestureController implements Dumpable {
    public static final Companion Companion = new Companion(null);
    private GestureListener gestureListener;
    private final GestureSensor gestureSensor;
    private final GestureController$gestureSensorListener$1 gestureSensorListener;
    private final SparseLongArray lastTimestampMap = new SparseLongArray();
    private long softGateBlockCount;
    private final GestureController$softGateListener$1 softGateListener;
    private final Set<Gate> softGates;
    private final UiEventLogger uiEventLogger;

    /* compiled from: GestureController.kt */
    public interface GestureListener {
        void onGestureDetected(GestureSensor gestureSensor, int i, GestureSensor.DetectionProperties detectionProperties);
    }

    public GestureController(GestureSensor gestureSensor2, Set<Gate> set, CommandRegistry commandRegistry, UiEventLogger uiEventLogger2) {
        Intrinsics.checkNotNullParameter(gestureSensor2, "gestureSensor");
        Intrinsics.checkNotNullParameter(set, "softGates");
        Intrinsics.checkNotNullParameter(commandRegistry, "commandRegistry");
        Intrinsics.checkNotNullParameter(uiEventLogger2, "uiEventLogger");
        this.gestureSensor = gestureSensor2;
        this.softGates = set;
        this.uiEventLogger = uiEventLogger2;
        GestureController$gestureSensorListener$1 gestureController$gestureSensorListener$1 = new GestureController$gestureSensorListener$1(this);
        this.gestureSensorListener = gestureController$gestureSensorListener$1;
        this.softGateListener = new GestureController$softGateListener$1();
        gestureSensor2.setGestureListener(gestureController$gestureSensorListener$1);
        commandRegistry.registerCommand("quick-tap", new Function0<Command>(this) {
            /* class com.google.android.systemui.columbus.sensors.GestureController.AnonymousClass1 */
            final /* synthetic */ GestureController this$0;

            {
                this.this$0 = r1;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Command invoke() {
                return new ColumbusCommand(this.this$0);
            }
        });
    }

    public void setGestureListener(GestureListener gestureListener2) {
        this.gestureListener = gestureListener2;
    }

    public boolean startListening() {
        if (this.gestureSensor.isListening()) {
            return false;
        }
        Iterator<T> it = this.softGates.iterator();
        while (it.hasNext()) {
            it.next().registerListener(this.softGateListener);
        }
        this.gestureSensor.startListening();
        return true;
    }

    public boolean stopListening() {
        if (!this.gestureSensor.isListening()) {
            return false;
        }
        this.gestureSensor.stopListening();
        Iterator<T> it = this.softGates.iterator();
        while (it.hasNext()) {
            it.next().unregisterListener(this.softGateListener);
        }
        return true;
    }

    /* access modifiers changed from: private */
    public final boolean isThrottled(int i) {
        long uptimeMillis = SystemClock.uptimeMillis();
        long j = this.lastTimestampMap.get(i);
        this.lastTimestampMap.put(i, uptimeMillis);
        return uptimeMillis - j <= 500;
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Intrinsics.checkNotNullParameter(fileDescriptor, "fd");
        Intrinsics.checkNotNullParameter(printWriter, "pw");
        Intrinsics.checkNotNullParameter(strArr, "args");
        printWriter.println(Intrinsics.stringPlus("  Soft Blocks: ", Long.valueOf(this.softGateBlockCount)));
        printWriter.println(Intrinsics.stringPlus("  Gesture Sensor: ", this.gestureSensor));
        GestureSensor gestureSensor2 = this.gestureSensor;
        if (gestureSensor2 instanceof Dumpable) {
            ((Dumpable) gestureSensor2).dump(fileDescriptor, printWriter, strArr);
        }
    }

    /* compiled from: GestureController.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* access modifiers changed from: private */
    /* compiled from: GestureController.kt */
    public final class ColumbusCommand implements Command {
        final /* synthetic */ GestureController this$0;

        /* JADX WARN: Incorrect args count in method signature: ()V */
        public ColumbusCommand(GestureController gestureController) {
            Intrinsics.checkNotNullParameter(gestureController, "this$0");
            this.this$0 = gestureController;
        }

        @Override // com.android.systemui.statusbar.commandline.Command
        public void execute(PrintWriter printWriter, List<String> list) {
            Intrinsics.checkNotNullParameter(printWriter, "pw");
            Intrinsics.checkNotNullParameter(list, "args");
            if (list.isEmpty()) {
                help(printWriter);
            } else if (Intrinsics.areEqual(list.get(0), "trigger")) {
                performTrigger();
            } else {
                help(printWriter);
            }
        }

        public void help(PrintWriter printWriter) {
            Intrinsics.checkNotNullParameter(printWriter, "pw");
            printWriter.println("usage: quick-tap <command>");
            printWriter.println("Available commands:");
            printWriter.println("  trigger");
        }

        private final void performTrigger() {
            this.this$0.gestureSensorListener.onGestureDetected(this.this$0.gestureSensor, 1, null);
        }
    }
}
