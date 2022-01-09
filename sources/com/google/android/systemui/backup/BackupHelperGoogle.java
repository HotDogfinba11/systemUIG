package com.google.android.systemui.backup;

import android.app.backup.BlobBackupHelper;
import android.content.ContentResolver;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.backup.BackupHelper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BackupHelperGoogle.kt */
public final class BackupHelperGoogle extends BackupHelper {
    public static final Companion Companion = new Companion(null);
    private static final List<String> SECURE_SETTINGS_INT_KEYS = CollectionsKt__CollectionsKt.listOf((Object[]) new String[]{"columbus_enabled", "columbus_low_sensitivity"});
    private static final List<String> SECURE_SETTINGS_STRING_KEYS = CollectionsKt__CollectionsKt.listOf((Object[]) new String[]{"columbus_action", "columbus_launch_app", "columbus_launch_app_shortcut"});

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.google.android.systemui.backup.BackupHelperGoogle */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v1, types: [android.app.backup.BackupHelper, com.google.android.systemui.backup.BackupHelperGoogle$SecureSettingsBackupHelper] */
    /* JADX WARNING: Unknown variable types count: 1 */
    @Override // com.android.systemui.backup.BackupHelper
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCreate(android.os.UserHandle r3, int r4) {
        /*
            r2 = this;
            java.lang.String r0 = "userHandle"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r3, r0)
            super.onCreate(r3, r4)
            com.google.android.systemui.backup.BackupHelperGoogle$SecureSettingsBackupHelper r4 = new com.google.android.systemui.backup.BackupHelperGoogle$SecureSettingsBackupHelper
            android.content.ContentResolver r0 = r2.getContentResolver()
            java.lang.String r1 = "contentResolver"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            r4.<init>(r0, r3)
            java.lang.String r3 = "systemui.google.secure_settings_backup"
            r2.addHelper(r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.backup.BackupHelperGoogle.onCreate(android.os.UserHandle, int):void");
    }

    /* compiled from: BackupHelperGoogle.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: BackupHelperGoogle.kt */
    private static final class SecureSettingsBackupHelper extends BlobBackupHelper {
        private final ContentResolver contentResolver;
        private final UserHandle userHandle;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public SecureSettingsBackupHelper(android.content.ContentResolver r5, android.os.UserHandle r6) {
            /*
                r4 = this;
                java.lang.String r0 = "contentResolver"
                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
                java.lang.String r0 = "userHandle"
                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
                kotlin.jvm.internal.SpreadBuilder r0 = new kotlin.jvm.internal.SpreadBuilder
                r1 = 2
                r0.<init>(r1)
                java.util.List r1 = com.google.android.systemui.backup.BackupHelperGoogle.access$getSECURE_SETTINGS_INT_KEYS$cp()
                r2 = 0
                java.lang.String[] r3 = new java.lang.String[r2]
                java.lang.Object[] r1 = r1.toArray(r3)
                java.lang.String r3 = "null cannot be cast to non-null type kotlin.Array<T>"
                java.util.Objects.requireNonNull(r1, r3)
                r0.addSpread(r1)
                java.util.List r1 = com.google.android.systemui.backup.BackupHelperGoogle.access$getSECURE_SETTINGS_STRING_KEYS$cp()
                java.lang.String[] r2 = new java.lang.String[r2]
                java.lang.Object[] r1 = r1.toArray(r2)
                java.util.Objects.requireNonNull(r1, r3)
                r0.addSpread(r1)
                int r1 = r0.size()
                java.lang.String[] r1 = new java.lang.String[r1]
                java.lang.Object[] r0 = r0.toArray(r1)
                java.lang.String[] r0 = (java.lang.String[]) r0
                r1 = 1
                r4.<init>(r1, r0)
                r4.contentResolver = r5
                r4.userHandle = r6
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.backup.BackupHelperGoogle.SecureSettingsBackupHelper.<init>(android.content.ContentResolver, android.os.UserHandle):void");
        }

        /* access modifiers changed from: protected */
        public byte[] getBackupPayload(String str) {
            if (CollectionsKt___CollectionsKt.contains(BackupHelperGoogle.SECURE_SETTINGS_INT_KEYS, str)) {
                return getSecureSettingsIntPayload(str);
            }
            if (CollectionsKt___CollectionsKt.contains(BackupHelperGoogle.SECURE_SETTINGS_STRING_KEYS, str)) {
                return getSecureSettingsStringPayload(str);
            }
            return null;
        }

        /* JADX INFO: finally extract failed */
        /* JADX WARNING: Can't wrap try/catch for region: R(2:8|9) */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0034, code lost:
            r2.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0037, code lost:
            throw r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:7:0x0022, code lost:
            r3 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:?, code lost:
            r2.close();
            android.util.Log.e("BackupHelper", kotlin.jvm.internal.Intrinsics.stringPlus("Failed to backup ", r4));
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x0024 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private final byte[] getSecureSettingsIntPayload(java.lang.String r4) {
            /*
                r3 = this;
                r0 = 0
                android.content.ContentResolver r1 = r3.contentResolver     // Catch:{ SettingNotFoundException -> 0x0038 }
                android.os.UserHandle r3 = r3.userHandle     // Catch:{ SettingNotFoundException -> 0x0038 }
                int r3 = r3.getIdentifier()     // Catch:{ SettingNotFoundException -> 0x0038 }
                int r3 = android.provider.Settings.Secure.getIntForUser(r1, r4, r3)     // Catch:{ SettingNotFoundException -> 0x0038 }
                java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream
                r1.<init>()
                java.io.DataOutputStream r2 = new java.io.DataOutputStream
                r2.<init>(r1)
                r2.writeInt(r3)     // Catch:{ IOException -> 0x0024 }
                byte[] r0 = r1.toByteArray()     // Catch:{ IOException -> 0x0024 }
            L_0x001e:
                r2.close()
                goto L_0x0033
            L_0x0022:
                r3 = move-exception
                goto L_0x0034
            L_0x0024:
                r2.close()     // Catch:{ all -> 0x0022 }
                java.lang.String r3 = "BackupHelper"
                java.lang.String r1 = "Failed to backup "
                java.lang.String r4 = kotlin.jvm.internal.Intrinsics.stringPlus(r1, r4)     // Catch:{ all -> 0x0022 }
                android.util.Log.e(r3, r4)     // Catch:{ all -> 0x0022 }
                goto L_0x001e
            L_0x0033:
                return r0
            L_0x0034:
                r2.close()
                throw r3
            L_0x0038:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.backup.BackupHelperGoogle.SecureSettingsBackupHelper.getSecureSettingsIntPayload(java.lang.String):byte[]");
        }

        private final byte[] getSecureSettingsStringPayload(String str) {
            String stringForUser = Settings.Secure.getStringForUser(this.contentResolver, str, this.userHandle.getIdentifier());
            byte[] bArr = null;
            if (stringForUser == null) {
                return null;
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeUTF(stringForUser);
                bArr = byteArrayOutputStream.toByteArray();
            } catch (IOException unused) {
                Log.e("BackupHelper", Intrinsics.stringPlus("Failed to backup ", str));
            } catch (Throwable th) {
                dataOutputStream.close();
                throw th;
            }
            dataOutputStream.close();
            return bArr;
        }

        /* access modifiers changed from: protected */
        public void applyRestoredPayload(String str, byte[] bArr) {
            if (CollectionsKt___CollectionsKt.contains(BackupHelperGoogle.SECURE_SETTINGS_INT_KEYS, str)) {
                applySecureSettingsIntPayload(str, bArr);
            } else if (CollectionsKt___CollectionsKt.contains(BackupHelperGoogle.SECURE_SETTINGS_STRING_KEYS, str)) {
                applySecureSettingsStringPayload(str, bArr);
            }
        }

        private final void applySecureSettingsIntPayload(String str, byte[] bArr) {
            if (str != null && bArr != null) {
                boolean z = true;
                if (!(str.length() == 0)) {
                    if (bArr.length != 0) {
                        z = false;
                    }
                    if (!z) {
                        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
                        try {
                            int readInt = dataInputStream.readInt();
                            dataInputStream.close();
                            Settings.Secure.putIntForUser(this.contentResolver, str, readInt, this.userHandle.getIdentifier());
                        } catch (IOException unused) {
                            Log.e("BackupHelper", Intrinsics.stringPlus("Failed to restore ", str));
                            dataInputStream.close();
                        } catch (Throwable th) {
                            dataInputStream.close();
                            throw th;
                        }
                    }
                }
            }
        }

        private final void applySecureSettingsStringPayload(String str, byte[] bArr) {
            if (str != null && bArr != null) {
                boolean z = true;
                if (!(str.length() == 0)) {
                    if (bArr.length != 0) {
                        z = false;
                    }
                    if (!z) {
                        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
                        try {
                            String readUTF = dataInputStream.readUTF();
                            Intrinsics.checkNotNullExpressionValue(readUTF, "input.readUTF()");
                            dataInputStream.close();
                            Settings.Secure.putStringForUser(this.contentResolver, str, readUTF, this.userHandle.getIdentifier());
                        } catch (IOException unused) {
                            Log.e("BackupHelper", Intrinsics.stringPlus("Failed to restore ", str));
                            dataInputStream.close();
                        } catch (Throwable th) {
                            dataInputStream.close();
                            throw th;
                        }
                    }
                }
            }
        }
    }
}
