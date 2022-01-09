package com.google.android.apps.miphone.aiai.matchmaker.overview.ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.EntitiesData;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotOp;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$ScreenshotOpStatus;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.InteractionContextParcelables$InteractionContext;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.ParserParcelables$ParsedViewHierarchy;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$Action;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$ActionGroup;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$Entities;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$Entity;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$IntentInfo;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$IntentType;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$InteractionType;
import com.google.android.apps.miphone.aiai.matchmaker.overview.common.BundleUtils;
import com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceWrapper;
import com.google.android.apps.miphone.aiai.matchmaker.overview.ui.utils.LogUtils;
import com.google.android.apps.miphone.aiai.matchmaker.overview.ui.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;

public class ContentSuggestionsServiceClient {
    private static final Random random = new Random();
    private final BundleUtils bundleUtils = BundleUtils.createWithBackwardsCompatVersion();
    private final Context context;
    private final boolean isAiAiVersionSupported;
    private final ContentSuggestionsServiceWrapper serviceWrapper;
    private final UserManager userManager;

    public ContentSuggestionsServiceClient(Context context2, Executor executor, Handler handler) {
        this.context = context2;
        this.serviceWrapper = SuggestController.create(context2, context2, executor, handler).getWrapper();
        this.isAiAiVersionSupported = isVersionCodeSupported(context2);
        this.userManager = (UserManager) context2.getSystemService(UserManager.class);
    }

    private static boolean isVersionCodeSupported(Context context2) {
        try {
            if (context2.getPackageManager().getPackageInfo("com.google.android.as", 0).getLongVersionCode() >= 660780) {
                return true;
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e("Error obtaining package info: ", e);
            return false;
        }
    }

    public void provideScreenshotActions(Bitmap bitmap, Uri uri, String str, String str2, UserHandle userHandle, SuggestParcelables$InteractionType suggestParcelables$InteractionType, ContentSuggestionsServiceWrapper.BundleCallback bundleCallback) {
        if (!this.isAiAiVersionSupported) {
            bundleCallback.onResult(Bundle.EMPTY);
            return;
        }
        int nextInt = random.nextInt();
        long currentTimeMillis = System.currentTimeMillis();
        Bundle obtainScreenshotContextImageBundle = this.bundleUtils.obtainScreenshotContextImageBundle(true, uri, str, str2, currentTimeMillis);
        obtainScreenshotContextImageBundle.putParcelable("android.contentsuggestions.extra.BITMAP", bitmap);
        InteractionContextParcelables$InteractionContext create = InteractionContextParcelables$InteractionContext.create();
        create.setInteractionType(suggestParcelables$InteractionType);
        create.setDisallowCopyPaste(false);
        create.setVersionCode(1);
        create.setIsPrimaryTask(true);
        this.serviceWrapper.connectAndRunAsync(new ContentSuggestionsServiceClient$$ExternalSyntheticLambda0(this, nextInt, obtainScreenshotContextImageBundle, str, str2, currentTimeMillis, create, userHandle, uri, bundleCallback));
    }

    public /* synthetic */ void lambda$provideScreenshotActions$0$ContentSuggestionsServiceClient(final int i, Bundle bundle, final String str, final String str2, final long j, final InteractionContextParcelables$InteractionContext interactionContextParcelables$InteractionContext, UserHandle userHandle, final Uri uri, final ContentSuggestionsServiceWrapper.BundleCallback bundleCallback) {
        this.serviceWrapper.processContextImage(i, null, bundle);
        Bundle createSelectionsRequest = this.bundleUtils.createSelectionsRequest(str, str2, i, j, interactionContextParcelables$InteractionContext, new Bundle(), ParserParcelables$ParsedViewHierarchy.create());
        createSelectionsRequest.putBoolean("IsManagedProfile", this.userManager.isManagedProfile(userHandle.getIdentifier()));
        createSelectionsRequest.putParcelable("UserHandle", userHandle);
        this.serviceWrapper.suggestContentSelections(i, createSelectionsRequest, new ContentSuggestionsServiceWrapper.BundleCallback() {
            /* class com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceClient.AnonymousClass1 */

            @Override // com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceWrapper.BundleCallback
            public void onResult(Bundle bundle) {
                try {
                    ContentSuggestionsServiceClient.this.serviceWrapper.classifyContentSelections(ContentSuggestionsServiceClient.this.bundleUtils.createClassificationsRequest(str, str2, i, j, new Bundle(), interactionContextParcelables$InteractionContext, ContentSuggestionsServiceClient.this.bundleUtils.extractContents(bundle)), new ContentSuggestionsServiceWrapper.BundleCallback() {
                        /* class com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceClient.AnonymousClass1.AnonymousClass1 */

                        @Override // com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceWrapper.BundleCallback
                        public void onResult(Bundle bundle) {
                            try {
                                EntitiesData extractEntitiesParcelable = ContentSuggestionsServiceClient.this.bundleUtils.extractEntitiesParcelable(bundle);
                                SuggestParcelables$Entities create = extractEntitiesParcelable.entities() == null ? SuggestParcelables$Entities.create() : extractEntitiesParcelable.entities();
                                ArrayList<Notification.Action> arrayList = new ArrayList<>();
                                if (create.getEntities() != null) {
                                    for (SuggestParcelables$Entity suggestParcelables$Entity : (List) Utils.checkNotNull(create.getEntities())) {
                                        Notification.Action generateNotificationAction = ContentSuggestionsServiceClient.generateNotificationAction(ContentSuggestionsServiceClient.this.context, suggestParcelables$Entity, extractEntitiesParcelable, uri);
                                        if (generateNotificationAction != null) {
                                            arrayList.add(generateNotificationAction);
                                        }
                                    }
                                }
                                AnonymousClass1 r6 = AnonymousClass1.this;
                                bundleCallback.onResult(ContentSuggestionsServiceClient.this.bundleUtils.createScreenshotActionsResponse(arrayList));
                            } catch (Throwable th) {
                                LogUtils.e("Failed to handle classification result while generating smart actions for screenshot notification", th);
                                bundleCallback.onResult(Bundle.EMPTY);
                            }
                        }
                    });
                } catch (Throwable th) {
                    LogUtils.e("Failed to handle selections response while generating smart actions for screenshot notification", th);
                    bundleCallback.onResult(Bundle.EMPTY);
                }
            }
        });
    }

    public void notifyOp(String str, FeedbackParcelables$ScreenshotOp feedbackParcelables$ScreenshotOp, FeedbackParcelables$ScreenshotOpStatus feedbackParcelables$ScreenshotOpStatus, long j) {
        this.serviceWrapper.notifyInteractionAsync(str, new ContentSuggestionsServiceClient$$ExternalSyntheticLambda1(this, str, feedbackParcelables$ScreenshotOp, feedbackParcelables$ScreenshotOpStatus, j), null, null);
    }

    public /* synthetic */ Bundle lambda$notifyOp$1$ContentSuggestionsServiceClient(String str, FeedbackParcelables$ScreenshotOp feedbackParcelables$ScreenshotOp, FeedbackParcelables$ScreenshotOpStatus feedbackParcelables$ScreenshotOpStatus, long j) {
        return this.bundleUtils.createFeedbackRequest(FeedbackDataBuilder.newBuilder(str).addScreenshotOpFeedback(str, feedbackParcelables$ScreenshotOp, feedbackParcelables$ScreenshotOpStatus, j).build());
    }

    public void notifyAction(String str, String str2, boolean z, Intent intent) {
        this.serviceWrapper.notifyInteractionAsync(str, new ContentSuggestionsServiceClient$$ExternalSyntheticLambda2(this, str, str2, z, intent), null, null);
    }

    public /* synthetic */ Bundle lambda$notifyAction$2$ContentSuggestionsServiceClient(String str, String str2, boolean z, Intent intent) {
        return this.bundleUtils.createFeedbackRequest(FeedbackDataBuilder.newBuilder(str).addScreenshotActionFeedback(str, str2, z, intent).build());
    }

    /* access modifiers changed from: private */
    @Nullable
    public static Notification.Action generateNotificationAction(Context context2, SuggestParcelables$Entity suggestParcelables$Entity, EntitiesData entitiesData, @Nullable Uri uri) {
        String str;
        if (suggestParcelables$Entity.getActions() != null && !((List) Utils.checkNotNull(suggestParcelables$Entity.getActions())).isEmpty()) {
            SuggestParcelables$Action mainAction = ((SuggestParcelables$ActionGroup) ((List) Utils.checkNotNull(suggestParcelables$Entity.getActions())).get(0)).getMainAction();
            if (mainAction == null || mainAction.getId() == null) {
                LogUtils.d("Malformed mainAction: Expected id");
            } else {
                if (uri != null && mainAction.getHasProxiedIntentInfo()) {
                    grantReadUriPermissionIfLensAction(context2, ((SuggestParcelables$IntentInfo) Utils.checkNotNull(mainAction.getProxiedIntentInfo())).getIntentType(), (Uri) Utils.checkNotNull(uri));
                }
                Bitmap bitmap = entitiesData.getBitmap((String) Utils.checkNotNull(mainAction.getId()));
                PendingIntent pendingIntent = ((EntitiesData) Utils.checkNotNull(entitiesData)).getPendingIntent((String) Utils.checkNotNull(mainAction.getId()));
                if (pendingIntent == null || bitmap == null) {
                    LogUtils.d("Malformed EntitiesData: Expected icon bitmap and intent");
                    return null;
                }
                String firstNonEmptyString = getFirstNonEmptyString((String) Utils.checkNotNull(mainAction.getDisplayName()), (String) Utils.checkNotNull(mainAction.getFullDisplayName()), (String) Utils.checkNotNull(suggestParcelables$Entity.getSearchQueryHint()));
                if (firstNonEmptyString == null) {
                    LogUtils.d("Title expected.");
                    return null;
                }
                RemoteAction remoteAction = new RemoteAction(Icon.createWithBitmap(bitmap), firstNonEmptyString, firstNonEmptyString, pendingIntent);
                if (TextUtils.isEmpty(suggestParcelables$Entity.getSearchQueryHint())) {
                    str = "Smart Action";
                } else {
                    str = suggestParcelables$Entity.getSearchQueryHint();
                }
                return createNotificationActionFromRemoteAction(remoteAction, (String) Utils.checkNotNull(str), 1.0f);
            }
        }
        return null;
    }

    private static void grantReadUriPermissionIfLensAction(Context context2, SuggestParcelables$IntentType suggestParcelables$IntentType, Uri uri) {
        if (suggestParcelables$IntentType == SuggestParcelables$IntentType.LENS) {
            context2.grantUriPermission("com.google.android.googlequicksearchbox", uri, 1);
        }
    }

    @Nullable
    private static String getFirstNonEmptyString(@Nullable String... strArr) {
        if (strArr == null) {
            return null;
        }
        for (String str : strArr) {
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
        }
        return null;
    }

    private static Notification.Action createNotificationActionFromRemoteAction(RemoteAction remoteAction, String str, float f) {
        Icon icon = remoteAction.shouldShowIcon() ? remoteAction.getIcon() : null;
        Bundle bundle = new Bundle();
        bundle.putString("action_type", str);
        bundle.putFloat("action_score", f);
        return new Notification.Action.Builder(icon, remoteAction.getTitle(), remoteAction.getActionIntent()).setContextual(true).addExtras(bundle).build();
    }
}
