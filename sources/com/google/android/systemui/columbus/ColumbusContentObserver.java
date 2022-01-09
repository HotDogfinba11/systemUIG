package com.google.android.systemui.columbus;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import com.android.systemui.settings.UserTracker;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class ColumbusContentObserver extends ContentObserver {
    private final Function1<Uri, Unit> callback;
    private final ContentResolverWrapper contentResolver;
    private final Executor executor;
    private final Uri settingsUri;
    private final UserTracker userTracker;
    private final ColumbusContentObserver$userTrackerCallback$1 userTrackerCallback;

    public /* synthetic */ ColumbusContentObserver(ContentResolverWrapper contentResolverWrapper, Uri uri, Function1 function1, UserTracker userTracker2, Executor executor2, Handler handler, DefaultConstructorMarker defaultConstructorMarker) {
        this(contentResolverWrapper, uri, function1, userTracker2, executor2, handler);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: kotlin.jvm.functions.Function1<? super android.net.Uri, kotlin.Unit> */
    /* JADX WARN: Multi-variable type inference failed */
    private ColumbusContentObserver(ContentResolverWrapper contentResolverWrapper, Uri uri, Function1<? super Uri, Unit> function1, UserTracker userTracker2, Executor executor2, Handler handler) {
        super(handler);
        this.contentResolver = contentResolverWrapper;
        this.settingsUri = uri;
        this.callback = function1;
        this.userTracker = userTracker2;
        this.executor = executor2;
        this.userTrackerCallback = new ColumbusContentObserver$userTrackerCallback$1(this);
    }

    public static final class Factory {
        private final ContentResolverWrapper contentResolver;
        private final Executor executor;
        private final Handler handler;
        private final UserTracker userTracker;

        public Factory(ContentResolverWrapper contentResolverWrapper, UserTracker userTracker2, Handler handler2, Executor executor2) {
            Intrinsics.checkNotNullParameter(contentResolverWrapper, "contentResolver");
            Intrinsics.checkNotNullParameter(userTracker2, "userTracker");
            Intrinsics.checkNotNullParameter(handler2, "handler");
            Intrinsics.checkNotNullParameter(executor2, "executor");
            this.contentResolver = contentResolverWrapper;
            this.userTracker = userTracker2;
            this.handler = handler2;
            this.executor = executor2;
        }

        public final ColumbusContentObserver create(Uri uri, Function1<? super Uri, Unit> function1) {
            Intrinsics.checkNotNullParameter(uri, "settingsUri");
            Intrinsics.checkNotNullParameter(function1, "callback");
            return new ColumbusContentObserver(this.contentResolver, uri, function1, this.userTracker, this.executor, this.handler, null);
        }
    }

    public final void activate() {
        this.userTracker.addCallback(this.userTrackerCallback, this.executor);
        updateContentObserver();
    }

    /* access modifiers changed from: public */
    private final void updateContentObserver() {
        this.contentResolver.unregisterContentObserver(this);
        this.contentResolver.registerContentObserver(this.settingsUri, false, this, this.userTracker.getUserId());
    }

    public void onChange(boolean z, Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        this.callback.invoke(uri);
    }
}
