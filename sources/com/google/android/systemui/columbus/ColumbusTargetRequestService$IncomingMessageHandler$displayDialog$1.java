package com.google.android.systemui.columbus;

import android.content.DialogInterface;
import android.content.pm.LauncherActivityInfo;
import android.os.Messenger;
import android.provider.Settings;
import android.util.Log;
import com.google.android.systemui.columbus.ColumbusTargetRequestService;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ColumbusTargetRequestService.kt */
final class ColumbusTargetRequestService$IncomingMessageHandler$displayDialog$1 implements Runnable {
    final /* synthetic */ LauncherActivityInfo $appInfo;
    final /* synthetic */ Messenger $replyTo;
    final /* synthetic */ int $requestCode;
    final /* synthetic */ ColumbusTargetRequestService this$0;
    final /* synthetic */ ColumbusTargetRequestService.IncomingMessageHandler this$1;

    ColumbusTargetRequestService$IncomingMessageHandler$displayDialog$1(ColumbusTargetRequestService columbusTargetRequestService, LauncherActivityInfo launcherActivityInfo, ColumbusTargetRequestService.IncomingMessageHandler incomingMessageHandler, Messenger messenger, int i) {
        this.this$0 = columbusTargetRequestService;
        this.$appInfo = launcherActivityInfo;
        this.this$1 = incomingMessageHandler;
        this.$replyTo = messenger;
        this.$requestCode = i;
    }

    public final void run() {
        ColumbusStructuredDataManager access$getColumbusStructuredDataManager$p = ColumbusTargetRequestService.access$getColumbusStructuredDataManager$p(this.this$0);
        String packageName = this.$appInfo.getComponentName().getPackageName();
        Intrinsics.checkNotNullExpressionValue(packageName, "appInfo.componentName.packageName");
        final int packageShownCount = access$getColumbusStructuredDataManager$p.getPackageShownCount(packageName);
        ColumbusTargetRequestService.access$getUiEventLogger$p(this.this$0).log(ColumbusEvent.COLUMBUS_RETARGET_DIALOG_SHOWN, 0, this.$appInfo.getComponentName().getPackageName());
        ColumbusTargetRequestDialog columbusTargetRequestDialog = new ColumbusTargetRequestDialog(ColumbusTargetRequestService.access$getSysUIContext$p(this.this$0));
        columbusTargetRequestDialog.show();
        final LauncherActivityInfo launcherActivityInfo = this.$appInfo;
        final ColumbusTargetRequestService columbusTargetRequestService = this.this$0;
        final ColumbusTargetRequestService.IncomingMessageHandler incomingMessageHandler = this.this$1;
        final Messenger messenger = this.$replyTo;
        final int i = this.$requestCode;
        AnonymousClass1 r11 = new DialogInterface.OnClickListener() {
            /* class com.google.android.systemui.columbus.ColumbusTargetRequestService$IncomingMessageHandler$displayDialog$1.AnonymousClass1 */

            public final void onClick(DialogInterface dialogInterface, int i) {
                ColumbusEvent columbusEvent;
                ColumbusEvent columbusEvent2;
                if (i == -2) {
                    ColumbusStructuredDataManager access$getColumbusStructuredDataManager$p = ColumbusTargetRequestService.access$getColumbusStructuredDataManager$p(columbusTargetRequestService);
                    String packageName = launcherActivityInfo.getComponentName().getPackageName();
                    Intrinsics.checkNotNullExpressionValue(packageName, "appInfo.componentName.packageName");
                    access$getColumbusStructuredDataManager$p.setLastDenyTimeToNow(packageName);
                    incomingMessageHandler.replyToMessenger(messenger, i, 5);
                    Log.d("Columbus/TargetRequest", Intrinsics.stringPlus("Target change denied by user: ", launcherActivityInfo.getComponentName().flattenToString()));
                    if (packageShownCount == 0) {
                        columbusEvent = ColumbusEvent.COLUMBUS_RETARGET_NOT_APPROVED;
                    } else {
                        columbusEvent = ColumbusEvent.COLUMBUS_RETARGET_FOLLOW_ON_NOT_APPROVED;
                    }
                    ColumbusTargetRequestService.access$getUiEventLogger$p(columbusTargetRequestService).log(columbusEvent, 0, launcherActivityInfo.getComponentName().flattenToString());
                } else if (i != -1) {
                    Log.e("Columbus/TargetRequest", Intrinsics.stringPlus("Invalid dialog option: ", Integer.valueOf(i)));
                } else {
                    Settings.Secure.putIntForUser(columbusTargetRequestService.getContentResolver(), "columbus_enabled", 1, ColumbusTargetRequestService.access$getUserTracker$p(columbusTargetRequestService).getUserId());
                    Settings.Secure.putStringForUser(columbusTargetRequestService.getContentResolver(), "columbus_action", "launch", ColumbusTargetRequestService.access$getUserTracker$p(columbusTargetRequestService).getUserId());
                    Settings.Secure.putStringForUser(columbusTargetRequestService.getContentResolver(), "columbus_launch_app", launcherActivityInfo.getComponentName().flattenToString(), ColumbusTargetRequestService.access$getUserTracker$p(columbusTargetRequestService).getUserId());
                    Settings.Secure.putStringForUser(columbusTargetRequestService.getContentResolver(), "columbus_launch_app_shortcut", launcherActivityInfo.getComponentName().flattenToString(), ColumbusTargetRequestService.access$getUserTracker$p(columbusTargetRequestService).getUserId());
                    incomingMessageHandler.replyToMessenger(messenger, i, 0);
                    Log.d("Columbus/TargetRequest", Intrinsics.stringPlus("Target changed to ", launcherActivityInfo.getComponentName().flattenToString()));
                    if (packageShownCount == 0) {
                        columbusEvent2 = ColumbusEvent.COLUMBUS_RETARGET_APPROVED;
                    } else {
                        columbusEvent2 = ColumbusEvent.COLUMBUS_RETARGET_FOLLOW_ON_APPROVED;
                    }
                    ColumbusTargetRequestService.access$getUiEventLogger$p(columbusTargetRequestService).log(columbusEvent2, 0, launcherActivityInfo.getComponentName().flattenToString());
                }
            }
        };
        final ColumbusTargetRequestService.IncomingMessageHandler incomingMessageHandler2 = this.this$1;
        final Messenger messenger2 = this.$replyTo;
        final int i2 = this.$requestCode;
        final LauncherActivityInfo launcherActivityInfo2 = this.$appInfo;
        final ColumbusTargetRequestService columbusTargetRequestService2 = this.this$0;
        columbusTargetRequestDialog.bind(launcherActivityInfo, r11, new DialogInterface.OnCancelListener() {
            /* class com.google.android.systemui.columbus.ColumbusTargetRequestService$IncomingMessageHandler$displayDialog$1.AnonymousClass2 */

            public final void onCancel(DialogInterface dialogInterface) {
                ColumbusEvent columbusEvent;
                incomingMessageHandler2.replyToMessenger(messenger2, i2, 6);
                Log.d("Columbus/TargetRequest", Intrinsics.stringPlus("Target change dismissed by user: ", launcherActivityInfo2.getComponentName().flattenToString()));
                if (packageShownCount == 0) {
                    columbusEvent = ColumbusEvent.COLUMBUS_RETARGET_NOT_APPROVED;
                } else {
                    columbusEvent = ColumbusEvent.COLUMBUS_RETARGET_FOLLOW_ON_NOT_APPROVED;
                }
                ColumbusTargetRequestService.access$getUiEventLogger$p(columbusTargetRequestService2).log(columbusEvent, 0, launcherActivityInfo2.getComponentName().flattenToString());
            }
        });
        ColumbusTargetRequestService.IncomingMessageHandler incomingMessageHandler3 = this.this$1;
        String packageName2 = this.$appInfo.getComponentName().getPackageName();
        Intrinsics.checkNotNullExpressionValue(packageName2, "appInfo.componentName.packageName");
        incomingMessageHandler3.incrementPackageShowCount(packageName2);
    }
}
