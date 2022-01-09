package com.google.android.systemui.columbus.sensors;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.Log;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import org.tensorflow.lite.Interpreter;

public class TfClassifier {
    private Interpreter mInterpreter;

    public TfClassifier(AssetManager assetManager, String str) {
        try {
            AssetFileDescriptor openFd = assetManager.openFd(str);
            this.mInterpreter = new Interpreter((ByteBuffer) new FileInputStream(openFd.getFileDescriptor()).getChannel().map(FileChannel.MapMode.READ_ONLY, openFd.getStartOffset(), openFd.getDeclaredLength()));
            Log.d("Columbus", "tflite file loaded: " + str);
        } catch (Exception e) {
            Log.d("Columbus", "load tflite file error: " + str);
            Log.e("Columbus", "tflite file:" + e.toString());
        }
    }

    public ArrayList<ArrayList<Float>> predict(ArrayList<Float> arrayList, int i) {
        if (this.mInterpreter == null) {
            return new ArrayList<>();
        }
        int size = arrayList.size();
        int[] iArr = new int[4];
        iArr[3] = 1;
        iArr[2] = 1;
        iArr[1] = size;
        iArr[0] = 1;
        float[][][][] fArr = (float[][][][]) Array.newInstance(float.class, iArr);
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            fArr[0][i2][0][0] = arrayList.get(i2).floatValue();
        }
        Object[] objArr = {fArr};
        HashMap hashMap = new HashMap();
        int[] iArr2 = new int[2];
        iArr2[1] = i;
        iArr2[0] = 1;
        hashMap.put(0, (float[][]) Array.newInstance(float.class, iArr2));
        this.mInterpreter.runForMultipleInputsOutputs(objArr, hashMap);
        float[][] fArr2 = (float[][]) hashMap.get(0);
        ArrayList<ArrayList<Float>> arrayList2 = new ArrayList<>();
        ArrayList<Float> arrayList3 = new ArrayList<>();
        for (int i3 = 0; i3 < i; i3++) {
            arrayList3.add(Float.valueOf(fArr2[0][i3]));
        }
        arrayList2.add(arrayList3);
        return arrayList2;
    }
}
