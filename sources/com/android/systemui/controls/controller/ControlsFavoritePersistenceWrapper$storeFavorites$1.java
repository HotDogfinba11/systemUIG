package com.android.systemui.controls.controller;

import android.app.backup.BackupManager;
import android.util.AtomicFile;
import android.util.Log;
import android.util.Xml;
import com.android.systemui.backup.BackupHelper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlSerializer;

/* access modifiers changed from: package-private */
/* compiled from: ControlsFavoritePersistenceWrapper.kt */
public final class ControlsFavoritePersistenceWrapper$storeFavorites$1 implements Runnable {
    final /* synthetic */ List<StructureInfo> $structures;
    final /* synthetic */ ControlsFavoritePersistenceWrapper this$0;

    ControlsFavoritePersistenceWrapper$storeFavorites$1(ControlsFavoritePersistenceWrapper controlsFavoritePersistenceWrapper, List<StructureInfo> list) {
        this.this$0 = controlsFavoritePersistenceWrapper;
        this.$structures = list;
    }

    public final void run() {
        boolean z;
        BackupManager backupManager;
        Log.d("ControlsFavoritePersistenceWrapper", Intrinsics.stringPlus("Saving data to file: ", this.this$0.file));
        AtomicFile atomicFile = new AtomicFile(this.this$0.file);
        Object controlsDataLock = BackupHelper.Companion.getControlsDataLock();
        List<StructureInfo> list = this.$structures;
        synchronized (controlsDataLock) {
            try {
                FileOutputStream startWrite = atomicFile.startWrite();
                z = true;
                try {
                    XmlSerializer newSerializer = Xml.newSerializer();
                    newSerializer.setOutput(startWrite, "utf-8");
                    newSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                    newSerializer.startDocument(null, Boolean.TRUE);
                    newSerializer.startTag(null, "version");
                    newSerializer.text("1");
                    newSerializer.endTag(null, "version");
                    newSerializer.startTag(null, "structures");
                    for (T t : list) {
                        newSerializer.startTag(null, "structure");
                        newSerializer.attribute(null, "component", t.getComponentName().flattenToString());
                        newSerializer.attribute(null, "structure", t.getStructure().toString());
                        newSerializer.startTag(null, "controls");
                        for (T t2 : t.getControls()) {
                            newSerializer.startTag(null, "control");
                            newSerializer.attribute(null, "id", t2.getControlId());
                            newSerializer.attribute(null, "title", t2.getControlTitle().toString());
                            newSerializer.attribute(null, "subtitle", t2.getControlSubtitle().toString());
                            newSerializer.attribute(null, "type", String.valueOf(t2.getDeviceType()));
                            newSerializer.endTag(null, "control");
                        }
                        newSerializer.endTag(null, "controls");
                        newSerializer.endTag(null, "structure");
                    }
                    newSerializer.endTag(null, "structures");
                    newSerializer.endDocument();
                    atomicFile.finishWrite(startWrite);
                } catch (Throwable th) {
                    IoUtils.closeQuietly(startWrite);
                    throw th;
                }
                IoUtils.closeQuietly(startWrite);
            } catch (IOException e) {
                Log.e("ControlsFavoritePersistenceWrapper", "Failed to start write file", e);
                return;
            }
        }
        if (z && (backupManager = this.this$0.backupManager) != null) {
            backupManager.dataChanged();
        }
    }
}
