package com.google.android.systemui.gesture;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.Log;
import com.android.systemui.navigationbar.gestural.BackGestureTfClassifierProvider;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import org.tensorflow.lite.Interpreter;

public class BackGestureTfClassifierProviderGoogle extends BackGestureTfClassifierProvider {
    private Interpreter mInterpreter = null;
    private AssetFileDescriptor mModelFileDescriptor = null;
    private float[][] mOutput = ((float[][]) Array.newInstance(float.class, 1, 1));
    private Map<Integer, Object> mOutputMap = new HashMap();
    private final String mVocabFile;

    @Override // com.android.systemui.navigationbar.gestural.BackGestureTfClassifierProvider
    public boolean isActive() {
        return true;
    }

    public BackGestureTfClassifierProviderGoogle(AssetManager assetManager, String str) {
        this.mVocabFile = str + ".vocab";
        this.mOutputMap.put(0, this.mOutput);
        try {
            AssetFileDescriptor openFd = assetManager.openFd(str + ".tflite");
            this.mModelFileDescriptor = openFd;
            this.mInterpreter = new Interpreter(openFd.createInputStream().getChannel().map(FileChannel.MapMode.READ_ONLY, this.mModelFileDescriptor.getStartOffset(), this.mModelFileDescriptor.getDeclaredLength()));
        } catch (Exception e) {
            Log.e("BackGestureTfClassifier", "Load TFLite file error:", e);
        }
    }

    @Override // com.android.systemui.navigationbar.gestural.BackGestureTfClassifierProvider
    public Map<String, Integer> loadVocab(AssetManager assetManager) {
        HashMap hashMap = new HashMap();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open(this.mVocabFile)));
            int i = 0;
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    hashMap.put(readLine, Integer.valueOf(i));
                    i++;
                } catch (Throwable th) {
                    th.addSuppressed(th);
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            Log.e("BackGestureTfClassifier", "Load vocab file error: ", e);
        }
        return hashMap;
        throw th;
    }

    @Override // com.android.systemui.navigationbar.gestural.BackGestureTfClassifierProvider
    public float predict(Object[] objArr) {
        Interpreter interpreter = this.mInterpreter;
        if (interpreter == null) {
            return -1.0f;
        }
        interpreter.runForMultipleInputsOutputs(objArr, this.mOutputMap);
        return this.mOutput[0][0];
    }

    @Override // com.android.systemui.navigationbar.gestural.BackGestureTfClassifierProvider
    public void release() {
        Interpreter interpreter = this.mInterpreter;
        if (interpreter != null) {
            interpreter.close();
        }
        AssetFileDescriptor assetFileDescriptor = this.mModelFileDescriptor;
        if (assetFileDescriptor != null) {
            try {
                assetFileDescriptor.close();
            } catch (Exception e) {
                Log.e("BackGestureTfClassifier", "Failed to close model file descriptor: ", e);
            }
        }
    }
}
