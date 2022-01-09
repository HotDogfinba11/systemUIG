package com.google.android.systemui.columbus;

import android.util.Log;
import com.android.systemui.Dumpable;
import com.google.android.systemui.columbus.PowerManagerWrapper;
import com.google.android.systemui.columbus.actions.Action;
import com.google.android.systemui.columbus.feedback.FeedbackEffect;
import com.google.android.systemui.columbus.gates.Gate;
import com.google.android.systemui.columbus.sensors.GestureController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public class ColumbusService implements Dumpable {
    public static final Companion Companion = new Companion(null);
    private final ColumbusService$actionListener$1 actionListener = new ColumbusService$actionListener$1(this);
    private final List<Action> actions;
    private final Set<FeedbackEffect> effects;
    private final ColumbusService$gateListener$1 gateListener = new ColumbusService$gateListener$1(this);
    private final Set<Gate> gates;
    private final GestureController gestureController;
    private final ColumbusService$gestureListener$1 gestureListener = new ColumbusService$gestureListener$1(this);
    private Action lastActiveAction;
    private final PowerManagerWrapper.WakeLockWrapper wakeLock;

    public ColumbusService(List<Action> list, Set<FeedbackEffect> set, Set<Gate> set2, GestureController gestureController2, PowerManagerWrapper powerManagerWrapper) {
        Intrinsics.checkNotNullParameter(list, "actions");
        Intrinsics.checkNotNullParameter(set, "effects");
        Intrinsics.checkNotNullParameter(set2, "gates");
        Intrinsics.checkNotNullParameter(gestureController2, "gestureController");
        Intrinsics.checkNotNullParameter(powerManagerWrapper, "powerManager");
        this.actions = list;
        this.effects = set;
        this.gates = set2;
        this.gestureController = gestureController2;
        this.wakeLock = powerManagerWrapper.newWakeLock(1, "Columbus/Service");
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            it.next().registerListener(this.actionListener);
        }
        this.gestureController.setGestureListener(this.gestureListener);
        updateSensorListener();
    }

    private final void activateGates() {
        Iterator<T> it = this.gates.iterator();
        while (it.hasNext()) {
            it.next().registerListener(this.gateListener);
        }
    }

    private final void deactivateGates() {
        Iterator<T> it = this.gates.iterator();
        while (it.hasNext()) {
            it.next().unregisterListener(this.gateListener);
        }
    }

    private final Gate blockingGate() {
        T t;
        Iterator<T> it = this.gates.iterator();
        while (true) {
            if (!it.hasNext()) {
                t = null;
                break;
            }
            t = it.next();
            if (t.isBlocking()) {
                break;
            }
        }
        return t;
    }

    private final Action firstAvailableAction() {
        T t;
        Iterator<T> it = this.actions.iterator();
        while (true) {
            if (!it.hasNext()) {
                t = null;
                break;
            }
            t = it.next();
            if (t.isAvailable()) {
                break;
            }
        }
        return t;
    }

    private final boolean startListening() {
        return this.gestureController.startListening();
    }

    private final void stopListening() {
        if (this.gestureController.stopListening()) {
            Iterator<T> it = this.effects.iterator();
            while (it.hasNext()) {
                it.next().onGestureDetected(0, null);
            }
            Action updateActiveAction = updateActiveAction();
            if (updateActiveAction != null) {
                updateActiveAction.onGestureDetected(0, null);
            }
        }
    }

    /* access modifiers changed from: public */
    private final Action updateActiveAction() {
        Action firstAvailableAction = firstAvailableAction();
        Action action = this.lastActiveAction;
        if (!(action == null || firstAvailableAction == action)) {
            Log.i("Columbus/Service", "Switching action from " + action + " to " + firstAvailableAction);
            action.onGestureDetected(0, null);
        }
        this.lastActiveAction = firstAvailableAction;
        return firstAvailableAction;
    }

    /* access modifiers changed from: public */
    private final void updateSensorListener() {
        Action updateActiveAction = updateActiveAction();
        if (updateActiveAction == null) {
            Log.i("Columbus/Service", "No available actions");
            deactivateGates();
            stopListening();
            return;
        }
        activateGates();
        Gate blockingGate = blockingGate();
        if (blockingGate != null) {
            Log.i("Columbus/Service", Intrinsics.stringPlus("Gated by ", blockingGate));
            stopListening();
            return;
        }
        Log.i("Columbus/Service", Intrinsics.stringPlus("Unblocked; current action: ", updateActiveAction));
        startListening();
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str;
        String str2;
        Intrinsics.checkNotNullParameter(fileDescriptor, "fd");
        Intrinsics.checkNotNullParameter(printWriter, "pw");
        Intrinsics.checkNotNullParameter(strArr, "args");
        printWriter.println(Intrinsics.stringPlus(ColumbusService.class.getSimpleName(), " state:"));
        printWriter.println("  Gates:");
        Iterator<T> it = this.gates.iterator();
        while (true) {
            str = "X ";
            if (!it.hasNext()) {
                break;
            }
            T next = it.next();
            printWriter.print("    ");
            if (next.getActive()) {
                if (!next.isBlocking()) {
                    str = "O ";
                }
                printWriter.print(str);
            } else {
                printWriter.print("- ");
            }
            printWriter.println(next.toString());
        }
        printWriter.println("  Actions:");
        for (T t : this.actions) {
            printWriter.print("    ");
            if (t.isAvailable()) {
                str2 = "O ";
            } else {
                str2 = str;
            }
            printWriter.print(str2);
            printWriter.println(t.toString());
        }
        printWriter.println(Intrinsics.stringPlus("  Active: ", this.lastActiveAction));
        printWriter.println("  Feedback Effects:");
        Iterator<T> it2 = this.effects.iterator();
        while (it2.hasNext()) {
            printWriter.print("    ");
            printWriter.println(it2.next().toString());
        }
        this.gestureController.dump(fileDescriptor, printWriter, strArr);
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
