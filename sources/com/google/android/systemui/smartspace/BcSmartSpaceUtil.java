package com.google.android.systemui.smartspace;

import android.app.PendingIntent;
import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.SmartspaceTargetEvent;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import com.android.systemui.bcsmartspace.R$dimen;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.FalsingManager;

public final class BcSmartSpaceUtil {
    private static FalsingManager sFalsingManager;
    private static BcSmartspaceDataPlugin.IntentStarter sIntentStarter;

    public static void setOnClickListener(View view, SmartspaceTarget smartspaceTarget, SmartspaceAction smartspaceAction, String str, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        setOnClickListener(view, smartspaceTarget, smartspaceAction, null, smartspaceEventNotifier, str, bcSmartspaceCardLoggingInfo);
    }

    public static void setOnClickListener(View view, SmartspaceTarget smartspaceTarget, SmartspaceAction smartspaceAction, View.OnClickListener onClickListener, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, String str, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        if (view == null || smartspaceAction == null || (smartspaceAction.getIntent() == null && smartspaceAction.getPendingIntent() == null)) {
            Log.e(str, "No tap action can be set up");
            return;
        }
        BcSmartspaceDataPlugin.IntentStarter intentStarter = sIntentStarter;
        if (intentStarter == null) {
            intentStarter = defaultIntentStarter(str);
        }
        view.setOnClickListener(new BcSmartSpaceUtil$$ExternalSyntheticLambda0(bcSmartspaceCardLoggingInfo, intentStarter, smartspaceAction, onClickListener, smartspaceEventNotifier, str, smartspaceTarget));
    }

    public static /* synthetic */ void lambda$setOnClickListener$0(BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo, BcSmartspaceDataPlugin.IntentStarter intentStarter, SmartspaceAction smartspaceAction, View.OnClickListener onClickListener, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, String str, SmartspaceTarget smartspaceTarget, View view) {
        BcSmartspaceLogger.log(BcSmartspaceEvent.SMARTSPACE_CARD_CLICK, bcSmartspaceCardLoggingInfo);
        FalsingManager falsingManager = sFalsingManager;
        if (falsingManager == null || !falsingManager.isFalseTap(1)) {
            intentStarter.startFromAction(smartspaceAction, view);
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
            if (smartspaceEventNotifier == null) {
                Log.w(str, "Cannot notify target interaction smartspace event: event notifier null.");
            } else {
                smartspaceEventNotifier.notifySmartspaceEvent(new SmartspaceTargetEvent.Builder(1).setSmartspaceTarget(smartspaceTarget).setSmartspaceActionId(smartspaceAction.getId()).build());
            }
        }
    }

    public static Drawable getIconDrawable(Icon icon, Context context) {
        Drawable drawable;
        if (icon == null) {
            return null;
        }
        if (icon.getType() == 1 || icon.getType() == 5) {
            drawable = new BitmapDrawable(context.getResources(), icon.getBitmap());
        } else {
            drawable = icon.loadDrawable(context);
        }
        if (drawable != null) {
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R$dimen.enhanced_smartspace_icon_size);
            drawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
        }
        return drawable;
    }

    public static void setFalsingManager(FalsingManager falsingManager) {
        sFalsingManager = falsingManager;
    }

    public static void setIntentStarter(BcSmartspaceDataPlugin.IntentStarter intentStarter) {
        sIntentStarter = intentStarter;
    }

    private static BcSmartspaceDataPlugin.IntentStarter defaultIntentStarter(final String str) {
        return new BcSmartspaceDataPlugin.IntentStarter() {
            /* class com.google.android.systemui.smartspace.BcSmartSpaceUtil.AnonymousClass1 */

            @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.IntentStarter
            public void startIntent(View view, Intent intent) {
                try {
                    view.getContext().startActivity(intent);
                } catch (ActivityNotFoundException | NullPointerException | SecurityException e) {
                    Log.e(str, "Cannot invoke smartspace intent", e);
                }
            }

            @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.IntentStarter
            public void startPendingIntent(PendingIntent pendingIntent) {
                try {
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    Log.e(str, "Cannot invoke canceled smartspace intent", e);
                }
            }
        };
    }

    public static boolean isLoggable(String str) {
        return Log.isLoggable(str, 2);
    }

    public static int getLoggingDisplaySurface(String str, float f) {
        str.hashCode();
        if (str.equals("com.google.android.apps.nexuslauncher")) {
            return 1;
        }
        if (!str.equals("com.android.systemui")) {
            return 0;
        }
        if (f == 1.0f) {
            return 3;
        }
        return f == 0.0f ? 2 : -1;
    }

    public static SmartspaceTarget createUpcomingAlarmTarget(ComponentName componentName, UserHandle userHandle) {
        return new SmartspaceTarget.Builder("upcoming_alarm_card_94510_12684", componentName, userHandle).setFeatureType(23).build();
    }
}
