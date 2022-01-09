package com.google.android.apps.miphone.aiai.matchmaker.overview.ui;

import android.app.contentsuggestions.ClassificationsRequest;
import android.app.contentsuggestions.ContentSuggestionsManager;
import android.app.contentsuggestions.SelectionsRequest;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.FeedbackParcelables$FeedbackBatch;
import com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceWrapper;
import com.google.android.apps.miphone.aiai.matchmaker.overview.ui.utils.LogUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class ContentSuggestionsServiceWrapperImpl extends ContentSuggestionsServiceWrapper {
    private final ContentSuggestionsManager contentSuggestionsManager;
    private final Map<Object, ContentSuggestionsServiceWrapper.BundleCallback> pendingCallbacks = Collections.synchronizedMap(new WeakHashMap());

    public ContentSuggestionsServiceWrapperImpl(Context context, Executor executor, Executor executor2, Executor executor3) {
        super(executor, executor2, executor3, ContentSuggestionsServiceWrapperImpl$$ExternalSyntheticLambda3.INSTANCE);
        this.contentSuggestionsManager = (ContentSuggestionsManager) context.getSystemService(ContentSuggestionsManager.class);
    }

    @Override // com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceWrapper
    public void suggestContentSelections(int i, Bundle bundle, ContentSuggestionsServiceWrapper.BundleCallback bundleCallback) {
        SelectionsRequest build = new SelectionsRequest.Builder(i).setExtras(bundle).build();
        try {
            this.contentSuggestionsManager.suggestContentSelections(build, this.callbackExecutor, new ContentSuggestionsServiceWrapperImpl$$ExternalSyntheticLambda1(new BundleCallbackWrapper(bundleCallback, this)));
        } catch (Throwable th) {
            LogUtils.e("Failed to suggestContentSelections", th);
        }
    }

    @Override // com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceWrapper
    public void classifyContentSelections(Bundle bundle, ContentSuggestionsServiceWrapper.BundleCallback bundleCallback) {
        try {
            this.contentSuggestionsManager.classifyContentSelections(new ClassificationsRequest.Builder(new ArrayList()).setExtras(bundle).build(), this.callbackExecutor, new ContentSuggestionsServiceWrapperImpl$$ExternalSyntheticLambda0(new BundleCallbackWrapper(bundleCallback, this)));
        } catch (Throwable th) {
            LogUtils.e("Failed to classifyContentSelections", th);
        }
    }

    @Override // com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceWrapper
    public void notifyInteractionAsync(String str, Supplier<Bundle> supplier, SuggestListener suggestListener, FeedbackParcelables$FeedbackBatch feedbackParcelables$FeedbackBatch) {
        this.loggingExecutor.execute(new ContentSuggestionsServiceWrapperImpl$$ExternalSyntheticLambda2(this, supplier, str, suggestListener, feedbackParcelables$FeedbackBatch));
    }

    public /* synthetic */ void lambda$notifyInteractionAsync$2$ContentSuggestionsServiceWrapperImpl(Supplier supplier, String str, SuggestListener suggestListener, FeedbackParcelables$FeedbackBatch feedbackParcelables$FeedbackBatch) {
        try {
            this.contentSuggestionsManager.notifyInteraction(str, (Bundle) supplier.get());
            if (suggestListener != null && feedbackParcelables$FeedbackBatch != null) {
                suggestListener.onFeedbackBatchSent(str, feedbackParcelables$FeedbackBatch);
            }
        } catch (Throwable th) {
            LogUtils.e("Failed to notifyInteraction", th);
        }
    }

    @Override // com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceWrapper
    public void processContextImage(int i, Bitmap bitmap, Bundle bundle) {
        bundle.putLong("CAPTURE_TIME_MS", System.currentTimeMillis());
        try {
            this.contentSuggestionsManager.provideContextImage(i, bundle);
        } catch (Throwable th) {
            LogUtils.e("Failed to provideContextImage", th);
        }
    }

    @Override // com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceWrapper
    public void runOnServiceConnection(Runnable runnable) {
        connectAndRunAsync(runnable);
    }

    public static class BundleCallbackWrapper implements ContentSuggestionsServiceWrapper.BundleCallback {
        private final Object key;
        private final WeakReference<ContentSuggestionsServiceWrapperImpl> parentRef;

        BundleCallbackWrapper(ContentSuggestionsServiceWrapper.BundleCallback bundleCallback, ContentSuggestionsServiceWrapperImpl contentSuggestionsServiceWrapperImpl) {
            Object obj = new Object();
            this.key = obj;
            contentSuggestionsServiceWrapperImpl.pendingCallbacks.put(obj, bundleCallback);
            this.parentRef = new WeakReference<>(contentSuggestionsServiceWrapperImpl);
        }

        @Override // com.google.android.apps.miphone.aiai.matchmaker.overview.ui.ContentSuggestionsServiceWrapper.BundleCallback
        public void onResult(Bundle bundle) {
            ContentSuggestionsServiceWrapperImpl contentSuggestionsServiceWrapperImpl = this.parentRef.get();
            if (contentSuggestionsServiceWrapperImpl != null) {
                ContentSuggestionsServiceWrapper.BundleCallback bundleCallback = (ContentSuggestionsServiceWrapper.BundleCallback) contentSuggestionsServiceWrapperImpl.pendingCallbacks.remove(this.key);
                if (bundleCallback != null) {
                    bundleCallback.onResult(bundle);
                } else {
                    LogUtils.d("Callback received after calling UI was recycled");
                }
            } else {
                LogUtils.d("Callback received after service wrapper was recycled");
            }
        }
    }
}
