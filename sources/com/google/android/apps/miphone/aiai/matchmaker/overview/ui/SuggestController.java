package com.google.android.apps.miphone.aiai.matchmaker.overview.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.ContentParcelables$Contents;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$FeedbackBatch;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.InteractionContextParcelables$InteractionContext;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$ErrorCode;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$InteractionType;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$SetupInfo;
import com.google.android.apps.miphone.aiai.matchmaker.overview.common.BundleUtils;
import com.google.android.apps.miphone.aiai.matchmaker.overview.ui.utils.LogUtils;
import com.google.android.apps.miphone.aiai.matchmaker.overview.ui.utils.Utils;
import java.util.List;
import java.util.concurrent.Executor;

public class SuggestController {
    public static Factory defaultFactory = SuggestController$$ExternalSyntheticLambda1.INSTANCE;
    private final BundleUtils bundleUtils;
    private String overviewSessionId = "null_session_id";
    private SuggestParcelables$SetupInfo setupInfo;
    private final Context uiContext;
    private final Handler uiHandler;
    private final Context userProfileDataContext;
    private final ContentSuggestionsServiceWrapper wrapper;

    public interface Factory {
        ContentSuggestionsServiceWrapper create(Context context, Executor executor, Executor executor2, Executor executor3);
    }

    public static SuggestController create(Context context, Context context2, Executor executor, Handler handler) {
        handler.getClass();
        return create(context, context2, handler, new SuggestController$$ExternalSyntheticLambda4(handler), executor, executor);
    }

    public static SuggestController create(Context context, Context context2, Handler handler, Executor executor, Executor executor2, Executor executor3) {
        return new SuggestController(context, context2, handler, executor, executor2, executor3).startIfNeeded();
    }

    /* access modifiers changed from: package-private */
    public ContentSuggestionsServiceWrapper getWrapper() {
        return this.wrapper;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void reportMetricsToService(String str, FeedbackParcelables$FeedbackBatch feedbackParcelables$FeedbackBatch, @Nullable SuggestListener suggestListener) {
        if (!((List) Utils.checkNotNull(feedbackParcelables$FeedbackBatch.getFeedback())).isEmpty()) {
            this.wrapper.notifyInteractionAsync(str, new SuggestController$$ExternalSyntheticLambda5(this, feedbackParcelables$FeedbackBatch), suggestListener, feedbackParcelables$FeedbackBatch);
        }
    }

    public /* synthetic */ Bundle lambda$reportMetricsToService$1$SuggestController(FeedbackParcelables$FeedbackBatch feedbackParcelables$FeedbackBatch) {
        return this.bundleUtils.createFeedbackRequest(feedbackParcelables$FeedbackBatch);
    }

    private SuggestController startIfNeeded() {
        this.wrapper.runOnServiceConnection(new SuggestController$$ExternalSyntheticLambda2(this));
        return this;
    }

    public /* synthetic */ void lambda$startIfNeeded$3$SuggestController() {
        try {
            LogUtils.i("Connecting to system intelligence module.");
            InteractionContextParcelables$InteractionContext create = InteractionContextParcelables$InteractionContext.create();
            create.setInteractionType(SuggestParcelables$InteractionType.SETUP);
            this.wrapper.suggestContentSelections(-1, this.bundleUtils.createSelectionsRequest(this.uiContext.getPackageName(), "", -1, -1, create, null, null), new SuggestController$$ExternalSyntheticLambda0(this));
        } catch (Exception e) {
            LogUtils.e("Error while connecting to system intelligence module.", e);
        }
    }

    public /* synthetic */ void lambda$startIfNeeded$2$SuggestController(Bundle bundle) {
        try {
            ContentParcelables$Contents extractContents = this.bundleUtils.extractContents(bundle);
            if (extractContents.getSetupInfo() == null) {
                LogUtils.e("System intelligence is unavailable.");
                return;
            }
            SuggestParcelables$SetupInfo suggestParcelables$SetupInfo = (SuggestParcelables$SetupInfo) Utils.checkNotNull(extractContents.getSetupInfo());
            this.setupInfo = suggestParcelables$SetupInfo;
            if (suggestParcelables$SetupInfo.getErrorCode() == SuggestParcelables$ErrorCode.ERROR_CODE_SUCCESS) {
                LogUtils.i("Successfully connected to system intelligence: ");
                return;
            }
            String valueOf = String.valueOf(suggestParcelables$SetupInfo.getErrorMesssage());
            LogUtils.e(valueOf.length() != 0 ? "Unable to connect to system intelligence: ".concat(valueOf) : new String("Unable to connect to system intelligence: "));
        } catch (Exception e) {
            LogUtils.e("Unable to connect to system intelligence module.", e);
        }
    }

    protected SuggestController(Context context, Context context2, Handler handler, Executor executor, Executor executor2, Executor executor3) {
        this.userProfileDataContext = context;
        this.uiContext = context2;
        this.uiHandler = handler;
        this.wrapper = defaultFactory.create(context, executor, executor2, executor3);
        this.bundleUtils = BundleUtils.createWithBackwardsCompatVersion();
    }
}
