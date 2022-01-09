package com.google.android.systemui.columbus.sensors;

import android.content.Context;
import android.hardware.google.pixel.vendor.PixelAtoms$DoubleTapNanoappEventReported$Type;
import android.hardware.location.ContextHubClient;
import android.hardware.location.ContextHubInfo;
import android.hardware.location.ContextHubManager;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.util.StatsEvent;
import android.util.StatsLog;
import androidx.constraintlayout.widget.R$styleable;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.RingBuffer;
import com.android.systemui.Dumpable;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.google.android.systemui.columbus.proto.nano.ColumbusProto$GestureDetected;
import com.google.android.systemui.columbus.proto.nano.ColumbusProto$NanoappEvent;
import com.google.android.systemui.columbus.proto.nano.ColumbusProto$NanoappEvents;
import com.google.android.systemui.columbus.proto.nano.ColumbusProto$RecognizerStart;
import com.google.android.systemui.columbus.proto.nano.ColumbusProto$ScreenStateUpdate;
import com.google.android.systemui.columbus.proto.nano.ColumbusProto$SensitivityUpdate;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import com.google.android.systemui.columbus.sensors.config.GestureConfiguration;
import com.google.protobuf.nano.MessageNano;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CHREGestureSensor.kt */
public final class CHREGestureSensor extends GestureSensor implements Dumpable {
    public static final Companion Companion = new Companion(null);
    private final Handler bgHandler;
    private final Context context;
    private ContextHubClient contextHubClient;
    private final CHREGestureSensor$contextHubClientCallback$1 contextHubClientCallback = new CHREGestureSensor$contextHubClientCallback$1(this);
    private final FeatureVectorDumper featureVectorDumper = new FeatureVectorDumper();
    private final GestureConfiguration gestureConfiguration;
    private boolean isAwake;
    private boolean isDozing;
    private boolean isListening;
    private boolean screenOn;
    private boolean screenStateUpdated;
    private final CHREGestureSensor$statusBarStateListener$1 statusBarStateListener;
    private final UiEventLogger uiEventLogger;
    private final CHREGestureSensor$wakefulnessLifecycleObserver$1 wakefulnessLifecycleObserver;

    private final int protoGestureTypeToGesture(int i) {
        if (i != 1) {
            return i != 2 ? 0 : 2;
        }
        return 1;
    }

    public CHREGestureSensor(Context context2, UiEventLogger uiEventLogger2, GestureConfiguration gestureConfiguration2, StatusBarStateController statusBarStateController, WakefulnessLifecycle wakefulnessLifecycle, Handler handler) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(uiEventLogger2, "uiEventLogger");
        Intrinsics.checkNotNullParameter(gestureConfiguration2, "gestureConfiguration");
        Intrinsics.checkNotNullParameter(statusBarStateController, "statusBarStateController");
        Intrinsics.checkNotNullParameter(wakefulnessLifecycle, "wakefulnessLifecycle");
        Intrinsics.checkNotNullParameter(handler, "bgHandler");
        this.context = context2;
        this.uiEventLogger = uiEventLogger2;
        this.gestureConfiguration = gestureConfiguration2;
        this.bgHandler = handler;
        CHREGestureSensor$statusBarStateListener$1 cHREGestureSensor$statusBarStateListener$1 = new CHREGestureSensor$statusBarStateListener$1(this);
        this.statusBarStateListener = cHREGestureSensor$statusBarStateListener$1;
        CHREGestureSensor$wakefulnessLifecycleObserver$1 cHREGestureSensor$wakefulnessLifecycleObserver$1 = new CHREGestureSensor$wakefulnessLifecycleObserver$1(this);
        this.wakefulnessLifecycleObserver = cHREGestureSensor$wakefulnessLifecycleObserver$1;
        this.isDozing = statusBarStateController.isDozing();
        boolean z = false;
        boolean z2 = wakefulnessLifecycle.getWakefulness() == 2;
        this.isAwake = z2;
        if (z2 && !this.isDozing) {
            z = true;
        }
        this.screenOn = z;
        this.screenStateUpdated = true;
        gestureConfiguration2.setListener(new GestureConfiguration.Listener(this) {
            /* class com.google.android.systemui.columbus.sensors.CHREGestureSensor.AnonymousClass1 */
            final /* synthetic */ CHREGestureSensor this$0;

            {
                this.this$0 = r1;
            }

            @Override // com.google.android.systemui.columbus.sensors.config.GestureConfiguration.Listener
            public void onGestureConfigurationChanged(GestureConfiguration gestureConfiguration) {
                Intrinsics.checkNotNullParameter(gestureConfiguration, "configuration");
                this.this$0.updateSensitivity(gestureConfiguration);
            }
        });
        statusBarStateController.addCallback(cHREGestureSensor$statusBarStateListener$1);
        wakefulnessLifecycle.addObserver(cHREGestureSensor$wakefulnessLifecycleObserver$1);
        initializeContextHubClientIfNull();
    }

    @Override // com.google.android.systemui.columbus.sensors.Sensor
    public boolean isListening() {
        return this.isListening;
    }

    public void setListening(boolean z) {
        this.isListening = z;
    }

    @Override // com.google.android.systemui.columbus.sensors.Sensor
    public void startListening() {
        setListening(true);
        startRecognizer();
        sendScreenState();
    }

    @Override // com.google.android.systemui.columbus.sensors.Sensor
    public void stopListening() {
        sendMessageToNanoApp$default(this, R$styleable.Constraint_layout_goneMarginRight, new byte[0], new CHREGestureSensor$stopListening$1(this), null, 8, null);
        setListening(false);
    }

    /* access modifiers changed from: private */
    public final void startRecognizer() {
        ColumbusProto$RecognizerStart columbusProto$RecognizerStart = new ColumbusProto$RecognizerStart();
        columbusProto$RecognizerStart.sensitivity = this.gestureConfiguration.getSensitivity();
        byte[] byteArray = MessageNano.toByteArray(columbusProto$RecognizerStart);
        Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(recognizerStart)");
        sendMessageToNanoApp$default(this, 100, byteArray, new CHREGestureSensor$startRecognizer$1(this), null, 8, null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: com.google.android.systemui.columbus.sensors.CHREGestureSensor */
    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void sendMessageToNanoApp$default(CHREGestureSensor cHREGestureSensor, int i, byte[] bArr, Function0 function0, Function0 function02, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            function0 = null;
        }
        if ((i2 & 8) != 0) {
            function02 = null;
        }
        cHREGestureSensor.sendMessageToNanoApp(i, bArr, function0, function02);
    }

    private final void sendMessageToNanoApp(int i, byte[] bArr, Function0<Unit> function0, Function0<Unit> function02) {
        initializeContextHubClientIfNull();
        if (this.contextHubClient == null) {
            Log.e("Columbus/GestureSensor", "ContextHubClient null");
        } else {
            this.bgHandler.post(new CHREGestureSensor$sendMessageToNanoApp$1(i, bArr, this, function02, function0));
        }
    }

    private final void initializeContextHubClientIfNull() {
        List list;
        int i;
        if (this.contextHubClient == null) {
            ContextHubManager contextHubManager = (ContextHubManager) this.context.getSystemService("contexthub");
            if (contextHubManager == null) {
                list = null;
            } else {
                list = contextHubManager.getContextHubs();
            }
            if (list == null) {
                i = 0;
            } else {
                i = list.size();
            }
            if (i == 0) {
                Log.e("Columbus/GestureSensor", "No context hubs found");
            } else if (list != null) {
                this.contextHubClient = contextHubManager.createClient((ContextHubInfo) list.get(0), this.contextHubClientCallback);
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final void updateSensitivity(GestureConfiguration gestureConfiguration2) {
        ColumbusProto$SensitivityUpdate columbusProto$SensitivityUpdate = new ColumbusProto$SensitivityUpdate();
        columbusProto$SensitivityUpdate.sensitivity = gestureConfiguration2.getSensitivity();
        byte[] byteArray = MessageNano.toByteArray(columbusProto$SensitivityUpdate);
        Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(sensitivityUpdate)");
        sendMessageToNanoApp$default(this, 200, byteArray, null, null, 12, null);
    }

    /* access modifiers changed from: private */
    public final void handleGestureDetection(ColumbusProto$GestureDetected columbusProto$GestureDetected) {
        int i = columbusProto$GestureDetected.gestureType;
        reportGestureDetected(protoGestureTypeToGesture(i), new GestureSensor.DetectionProperties(i == 2));
        this.featureVectorDumper.onGestureDetected(columbusProto$GestureDetected);
    }

    /* access modifiers changed from: private */
    public final void handleNanoappEvents(ColumbusProto$NanoappEvents columbusProto$NanoappEvents) {
        ColumbusProto$NanoappEvent[] columbusProto$NanoappEventArr = columbusProto$NanoappEvents.batchedEvents;
        Intrinsics.checkNotNullExpressionValue(columbusProto$NanoappEventArr, "nanoappEvents.batchedEvents");
        for (ColumbusProto$NanoappEvent columbusProto$NanoappEvent : columbusProto$NanoappEventArr) {
            StatsLog.write(StatsEvent.newBuilder().setAtomId(100051).writeLong(columbusProto$NanoappEvent.timestamp).writeInt(toWestWorldEventType(columbusProto$NanoappEvent.type)).build());
        }
    }

    private final int toWestWorldEventType(int i) {
        switch (i) {
            case 1:
                return PixelAtoms$DoubleTapNanoappEventReported$Type.GATE_START.getNumber();
            case 2:
                return PixelAtoms$DoubleTapNanoappEventReported$Type.GATE_STOP.getNumber();
            case 3:
                return PixelAtoms$DoubleTapNanoappEventReported$Type.HIGH_IMU_ODR_START.getNumber();
            case 4:
                return PixelAtoms$DoubleTapNanoappEventReported$Type.HIGH_IMU_ODR_STOP.getNumber();
            case 5:
                return PixelAtoms$DoubleTapNanoappEventReported$Type.ML_PREDICTION_START.getNumber();
            case 6:
                return PixelAtoms$DoubleTapNanoappEventReported$Type.ML_PREDICTION_STOP.getNumber();
            case 7:
                return PixelAtoms$DoubleTapNanoappEventReported$Type.SINGLE_TAP.getNumber();
            case 8:
                return PixelAtoms$DoubleTapNanoappEventReported$Type.DOUBLE_TAP.getNumber();
            default:
                return PixelAtoms$DoubleTapNanoappEventReported$Type.UNKNOWN.getNumber();
        }
    }

    /* access modifiers changed from: private */
    public final void handleDozingChanged(boolean z) {
        if (this.isDozing != z) {
            this.isDozing = z;
            updateScreenState();
        }
    }

    /* access modifiers changed from: private */
    public final void handleWakefullnessChanged(boolean z) {
        if (this.isAwake != z) {
            this.isAwake = z;
            updateScreenState();
        }
    }

    /* access modifiers changed from: private */
    public final void updateScreenState() {
        boolean z = this.isAwake && !this.isDozing;
        if (this.screenOn != z || !this.screenStateUpdated) {
            this.screenOn = z;
            if (isListening()) {
                sendScreenState();
            }
        }
    }

    private final void sendScreenState() {
        ColumbusProto$ScreenStateUpdate columbusProto$ScreenStateUpdate = new ColumbusProto$ScreenStateUpdate();
        columbusProto$ScreenStateUpdate.screenState = this.screenOn ? 1 : 2;
        byte[] byteArray = MessageNano.toByteArray(columbusProto$ScreenStateUpdate);
        Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(screenStateUpdate)");
        sendMessageToNanoApp(400, byteArray, new CHREGestureSensor$sendScreenState$1(this), new CHREGestureSensor$sendScreenState$2(this));
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Intrinsics.checkNotNullParameter(fileDescriptor, "fd");
        Intrinsics.checkNotNullParameter(printWriter, "pw");
        Intrinsics.checkNotNullParameter(strArr, "args");
        this.featureVectorDumper.dump(fileDescriptor, printWriter, strArr);
    }

    /* compiled from: CHREGestureSensor.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* access modifiers changed from: private */
    /* compiled from: CHREGestureSensor.kt */
    public static final class FeatureVector implements Dumpable {
        private final int gesture;
        private final long timestamp = SystemClock.elapsedRealtime();
        private final float[] vector;

        public FeatureVector(ColumbusProto$GestureDetected columbusProto$GestureDetected) {
            Intrinsics.checkNotNullParameter(columbusProto$GestureDetected, "gestureDetected");
            this.vector = columbusProto$GestureDetected.featureVector;
            this.gesture = columbusProto$GestureDetected.gestureType;
        }

        @Override // com.android.systemui.Dumpable
        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            Intrinsics.checkNotNullParameter(fileDescriptor, "fd");
            Intrinsics.checkNotNullParameter(printWriter, "pw");
            Intrinsics.checkNotNullParameter(strArr, "args");
            printWriter.println("      Gesture: " + this.gesture + " Time: " + (this.timestamp - SystemClock.elapsedRealtime()));
            float[] fArr = this.vector;
            Intrinsics.checkNotNullExpressionValue(fArr, "vector");
            printWriter.println(Intrinsics.stringPlus("      ", ArraysKt___ArraysKt.joinToString$default(fArr, ", ", "[ ", " ]", 0, null, null, 56, null)));
        }
    }

    /* access modifiers changed from: private */
    /* compiled from: CHREGestureSensor.kt */
    public static final class FeatureVectorDumper implements Dumpable {
        private final RingBuffer<FeatureVector> featureVectors = new RingBuffer<>(FeatureVector.class, 10);
        private FeatureVector lastSingleTapFeatureVector;

        public final void onGestureDetected(ColumbusProto$GestureDetected columbusProto$GestureDetected) {
            Intrinsics.checkNotNullParameter(columbusProto$GestureDetected, "gestureDetected");
            int i = columbusProto$GestureDetected.gestureType;
            if (i == 1) {
                FeatureVector featureVector = this.lastSingleTapFeatureVector;
                this.lastSingleTapFeatureVector = null;
                if (featureVector == null) {
                    Log.w("Columbus/GestureSensor", "Received double tap without single taps, event will not appear in sysdump");
                    return;
                }
                this.featureVectors.append(featureVector);
                this.featureVectors.append(new FeatureVector(columbusProto$GestureDetected));
            } else if (i == 2) {
                this.lastSingleTapFeatureVector = new FeatureVector(columbusProto$GestureDetected);
            }
        }

        @Override // com.android.systemui.Dumpable
        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            Intrinsics.checkNotNullParameter(fileDescriptor, "fd");
            Intrinsics.checkNotNullParameter(printWriter, "pw");
            Intrinsics.checkNotNullParameter(strArr, "args");
            printWriter.println("    Feature Vectors:");
            Object[] array = this.featureVectors.toArray();
            Intrinsics.checkNotNullExpressionValue(array, "featureVectors.toArray()");
            for (Object obj : array) {
                ((FeatureVector) obj).dump(fileDescriptor, printWriter, strArr);
            }
        }
    }
}
