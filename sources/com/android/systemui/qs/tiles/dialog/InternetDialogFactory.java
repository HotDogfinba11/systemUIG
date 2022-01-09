package com.android.systemui.qs.tiles.dialog;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.android.internal.logging.UiEventLogger;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: InternetDialogFactory.kt */
public final class InternetDialogFactory {
    public static final Companion Companion = new Companion(null);
    private static InternetDialog internetDialog;
    private final Context context;
    private final Executor executor;
    private final Handler handler;
    private final InternetDialogController internetDialogController;
    private final UiEventLogger uiEventLogger;

    public InternetDialogFactory(Handler handler2, Executor executor2, InternetDialogController internetDialogController2, Context context2, UiEventLogger uiEventLogger2) {
        Intrinsics.checkNotNullParameter(handler2, "handler");
        Intrinsics.checkNotNullParameter(executor2, "executor");
        Intrinsics.checkNotNullParameter(internetDialogController2, "internetDialogController");
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(uiEventLogger2, "uiEventLogger");
        this.handler = handler2;
        this.executor = executor2;
        this.internetDialogController = internetDialogController2;
        this.context = context2;
        this.uiEventLogger = uiEventLogger2;
    }

    /* compiled from: InternetDialogFactory.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final InternetDialog getInternetDialog() {
            return InternetDialogFactory.internetDialog;
        }

        public final void setInternetDialog(InternetDialog internetDialog) {
            InternetDialogFactory.internetDialog = internetDialog;
        }
    }

    public final void create(boolean z, boolean z2, boolean z3) {
        Companion companion = Companion;
        if (companion.getInternetDialog() == null) {
            companion.setInternetDialog(new InternetDialog(this.context, this, this.internetDialogController, z2, z3, z, this.uiEventLogger, this.handler, this.executor));
            InternetDialog internetDialog2 = companion.getInternetDialog();
            if (internetDialog2 != null) {
                internetDialog2.show();
            }
        } else if (InternetDialogFactoryKt.access$getDEBUG$p()) {
            Log.d("InternetDialogFactory", "InternetDialog is showing, do not create it twice.");
        }
    }

    public final void destroyDialog() {
        if (InternetDialogFactoryKt.access$getDEBUG$p()) {
            Log.d("InternetDialogFactory", "destroyDialog");
        }
        Companion.setInternetDialog(null);
    }
}
