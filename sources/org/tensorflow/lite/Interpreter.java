package org.tensorflow.lite;

import android.support.annotation.NonNull;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Interpreter implements AutoCloseable {
    String[] signatureNameList;
    NativeInterpreterWrapper wrapper;

    public static class Options {
        Boolean allowBufferHandleOutput;
        Boolean allowCancellation;
        Boolean allowFp16PrecisionForFp32;
        final List<Delegate> delegates = new ArrayList();
        int numThreads = -1;
        Boolean useNNAPI;
        Boolean useXNNPACK;
    }

    public Interpreter(@NonNull ByteBuffer byteBuffer) {
        this(byteBuffer, null);
    }

    @Deprecated
    public Interpreter(@NonNull MappedByteBuffer mappedByteBuffer) {
        this(mappedByteBuffer, null);
    }

    public Interpreter(@NonNull ByteBuffer byteBuffer, Options options) {
        this.wrapper = new NativeInterpreterWrapper(byteBuffer, options);
        this.signatureNameList = getSignatureDefNames();
    }

    public void runForMultipleInputsOutputs(@NonNull Object[] objArr, @NonNull Map<Integer, Object> map) {
        checkNotClosed();
        this.wrapper.run(objArr, map);
    }

    public String[] getSignatureDefNames() {
        checkNotClosed();
        return this.wrapper.getSignatureDefNames();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        NativeInterpreterWrapper nativeInterpreterWrapper = this.wrapper;
        if (nativeInterpreterWrapper != null) {
            nativeInterpreterWrapper.close();
            this.wrapper = null;
        }
    }

    /* access modifiers changed from: protected */
    @Override // java.lang.Object
    public void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    private void checkNotClosed() {
        if (this.wrapper == null) {
            throw new IllegalStateException("Internal error: The Interpreter has already been closed.");
        }
    }
}
