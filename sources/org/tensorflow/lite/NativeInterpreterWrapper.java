package org.tensorflow.lite;

import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.nnapi.NnApiDelegate;

/* access modifiers changed from: package-private */
public final class NativeInterpreterWrapper implements AutoCloseable {
    private long cancellationFlagHandle = 0;
    private final List<Delegate> delegates = new ArrayList();
    private long errorHandle;
    private long inferenceDurationNanoseconds = -1;
    private Tensor[] inputTensors;
    private Map<String, Integer> inputsIndexes;
    private long interpreterHandle;
    private boolean isMemoryAllocated = false;
    private ByteBuffer modelByteBuffer;
    private long modelHandle;
    private Tensor[] outputTensors;
    private Map<String, Integer> outputsIndexes;
    private final List<AutoCloseable> ownedDelegates = new ArrayList();

    private static native long allocateTensors(long j, long j2);

    private static native void allowBufferHandleOutput(long j, boolean z);

    private static native void allowFp16PrecisionForFp32(long j, boolean z);

    private static native void applyDelegate(long j, long j2, long j3);

    private static native long createCancellationFlag(long j);

    private static native long createErrorReporter(int i);

    private static native long createInterpreter(long j, long j2, int i);

    private static native long createModelWithBuffer(ByteBuffer byteBuffer, long j);

    private static native void delete(long j, long j2, long j3);

    private static native long deleteCancellationFlag(long j);

    private static native int getInputCount(long j);

    private static native int getInputTensorIndex(long j, int i);

    private static native int getOutputCount(long j);

    private static native int getOutputTensorIndex(long j, int i);

    private static native String[] getSignatureDefNames(long j);

    private static native boolean hasUnresolvedFlexOp(long j);

    private static native boolean resizeInput(long j, long j2, int i, int[] iArr, boolean z);

    private static native void run(long j, long j2);

    private static native void useXNNPACK(long j, long j2, int i, int i2);

    NativeInterpreterWrapper(ByteBuffer byteBuffer, Interpreter.Options options) {
        TensorFlowLite.init();
        if (byteBuffer == null || (!(byteBuffer instanceof MappedByteBuffer) && (!byteBuffer.isDirect() || byteBuffer.order() != ByteOrder.nativeOrder()))) {
            throw new IllegalArgumentException("Model ByteBuffer should be either a MappedByteBuffer of the model file, or a direct ByteBuffer using ByteOrder.nativeOrder() which contains bytes of model content.");
        }
        this.modelByteBuffer = byteBuffer;
        long createErrorReporter = createErrorReporter(512);
        init(createErrorReporter, createModelWithBuffer(this.modelByteBuffer, createErrorReporter), options);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v11 */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v21 */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void init(long r7, long r9, org.tensorflow.lite.Interpreter.Options r11) {
        /*
        // Method dump skipped, instructions count: 115
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tensorflow.lite.NativeInterpreterWrapper.init(long, long, org.tensorflow.lite.Interpreter$Options):void");
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        int i = 0;
        while (true) {
            Tensor[] tensorArr = this.inputTensors;
            if (i >= tensorArr.length) {
                break;
            }
            if (tensorArr[i] != null) {
                tensorArr[i].close();
                this.inputTensors[i] = null;
            }
            i++;
        }
        int i2 = 0;
        while (true) {
            Tensor[] tensorArr2 = this.outputTensors;
            if (i2 >= tensorArr2.length) {
                break;
            }
            if (tensorArr2[i2] != null) {
                tensorArr2[i2].close();
                this.outputTensors[i2] = null;
            }
            i2++;
        }
        delete(this.errorHandle, this.modelHandle, this.interpreterHandle);
        deleteCancellationFlag(this.cancellationFlagHandle);
        this.errorHandle = 0;
        this.modelHandle = 0;
        this.interpreterHandle = 0;
        this.cancellationFlagHandle = 0;
        this.modelByteBuffer = null;
        this.inputsIndexes = null;
        this.outputsIndexes = null;
        this.isMemoryAllocated = false;
        this.delegates.clear();
        for (AutoCloseable autoCloseable : this.ownedDelegates) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                System.err.println("Failed to close flex delegate: " + e);
            }
        }
        this.ownedDelegates.clear();
    }

    /* access modifiers changed from: package-private */
    public void run(Object[] objArr, Map<Integer, Object> map) {
        this.inferenceDurationNanoseconds = -1;
        if (objArr == null || objArr.length == 0) {
            throw new IllegalArgumentException("Input error: Inputs should not be null or empty.");
        } else if (map == null || map.isEmpty()) {
            throw new IllegalArgumentException("Input error: Outputs should not be null or empty.");
        } else {
            int i = 0;
            for (int i2 = 0; i2 < objArr.length; i2++) {
                int[] inputShapeIfDifferent = getInputTensor(i2).getInputShapeIfDifferent(objArr[i2]);
                if (inputShapeIfDifferent != null) {
                    resizeInput(i2, inputShapeIfDifferent);
                }
            }
            boolean z = !this.isMemoryAllocated;
            if (z) {
                allocateTensors(this.interpreterHandle, this.errorHandle);
                this.isMemoryAllocated = true;
            }
            for (int i3 = 0; i3 < objArr.length; i3++) {
                getInputTensor(i3).setTo(objArr[i3]);
            }
            long nanoTime = System.nanoTime();
            run(this.interpreterHandle, this.errorHandle);
            long nanoTime2 = System.nanoTime() - nanoTime;
            if (z) {
                while (true) {
                    Tensor[] tensorArr = this.outputTensors;
                    if (i >= tensorArr.length) {
                        break;
                    }
                    if (tensorArr[i] != null) {
                        tensorArr[i].refreshShape();
                    }
                    i++;
                }
            }
            for (Map.Entry<Integer, Object> entry : map.entrySet()) {
                getOutputTensor(entry.getKey().intValue()).copyTo(entry.getValue());
            }
            this.inferenceDurationNanoseconds = nanoTime2;
        }
    }

    /* access modifiers changed from: package-private */
    public void resizeInput(int i, int[] iArr) {
        resizeInput(i, iArr, false);
    }

    /* access modifiers changed from: package-private */
    public void resizeInput(int i, int[] iArr, boolean z) {
        if (resizeInput(this.interpreterHandle, this.errorHandle, i, iArr, z)) {
            this.isMemoryAllocated = false;
            Tensor[] tensorArr = this.inputTensors;
            if (tensorArr[i] != null) {
                tensorArr[i].refreshShape();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Tensor getInputTensor(int i) {
        if (i >= 0) {
            Tensor[] tensorArr = this.inputTensors;
            if (i < tensorArr.length) {
                Tensor tensor = tensorArr[i];
                if (tensor != null) {
                    return tensor;
                }
                long j = this.interpreterHandle;
                Tensor fromIndex = Tensor.fromIndex(j, getInputTensorIndex(j, i));
                tensorArr[i] = fromIndex;
                return fromIndex;
            }
        }
        throw new IllegalArgumentException("Invalid input Tensor index: " + i);
    }

    public String[] getSignatureDefNames() {
        return getSignatureDefNames(this.interpreterHandle);
    }

    /* access modifiers changed from: package-private */
    public Tensor getOutputTensor(int i) {
        if (i >= 0) {
            Tensor[] tensorArr = this.outputTensors;
            if (i < tensorArr.length) {
                Tensor tensor = tensorArr[i];
                if (tensor != null) {
                    return tensor;
                }
                long j = this.interpreterHandle;
                Tensor fromIndex = Tensor.fromIndex(j, getOutputTensorIndex(j, i));
                tensorArr[i] = fromIndex;
                return fromIndex;
            }
        }
        throw new IllegalArgumentException("Invalid output Tensor index: " + i);
    }

    private void applyDelegates(Interpreter.Options options) {
        Delegate maybeCreateFlexDelegate;
        boolean hasUnresolvedFlexOp = hasUnresolvedFlexOp(this.interpreterHandle);
        if (hasUnresolvedFlexOp && (maybeCreateFlexDelegate = maybeCreateFlexDelegate(options.delegates)) != null) {
            this.ownedDelegates.add((AutoCloseable) maybeCreateFlexDelegate);
            applyDelegate(this.interpreterHandle, this.errorHandle, maybeCreateFlexDelegate.getNativeHandle());
        }
        try {
            for (Delegate delegate : options.delegates) {
                applyDelegate(this.interpreterHandle, this.errorHandle, delegate.getNativeHandle());
                this.delegates.add(delegate);
            }
            Boolean bool = options.useNNAPI;
            if (bool != null && bool.booleanValue()) {
                NnApiDelegate nnApiDelegate = new NnApiDelegate();
                this.ownedDelegates.add(nnApiDelegate);
                applyDelegate(this.interpreterHandle, this.errorHandle, nnApiDelegate.getNativeHandle());
            }
        } catch (IllegalArgumentException e) {
            if (hasUnresolvedFlexOp && !hasUnresolvedFlexOp(this.interpreterHandle)) {
                PrintStream printStream = System.err;
                printStream.println("Ignoring failed delegate application: " + e);
                return;
            }
            throw e;
        }
    }

    private static Delegate maybeCreateFlexDelegate(List<Delegate> list) {
        try {
            Class<?> cls = Class.forName("org.tensorflow.lite.flex.FlexDelegate");
            for (Delegate delegate : list) {
                if (cls.isInstance(delegate)) {
                    return null;
                }
            }
            return (Delegate) cls.getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }
}
