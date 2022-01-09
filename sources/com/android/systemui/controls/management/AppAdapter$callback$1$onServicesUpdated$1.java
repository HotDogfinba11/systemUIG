package com.android.systemui.controls.management;

import com.android.systemui.controls.ControlsServiceInfo;
import java.text.Collator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AppAdapter.kt */
final class AppAdapter$callback$1$onServicesUpdated$1 implements Runnable {
    final /* synthetic */ List<ControlsServiceInfo> $serviceInfos;
    final /* synthetic */ Executor $uiExecutor;
    final /* synthetic */ AppAdapter this$0;

    AppAdapter$callback$1$onServicesUpdated$1(AppAdapter appAdapter, List<ControlsServiceInfo> list, Executor executor) {
        this.this$0 = appAdapter;
        this.$serviceInfos = list;
        this.$uiExecutor = executor;
    }

    public final void run() {
        Collator instance = Collator.getInstance(this.this$0.resources.getConfiguration().getLocales().get(0));
        Intrinsics.checkNotNullExpressionValue(instance, "collator");
        AppAdapter$callback$1$onServicesUpdated$1$run$$inlined$compareBy$1 appAdapter$callback$1$onServicesUpdated$1$run$$inlined$compareBy$1 = new AppAdapter$callback$1$onServicesUpdated$1$run$$inlined$compareBy$1(instance);
        this.this$0.listOfServices = CollectionsKt___CollectionsKt.sortedWith(this.$serviceInfos, appAdapter$callback$1$onServicesUpdated$1$run$$inlined$compareBy$1);
        Executor executor = this.$uiExecutor;
        final AppAdapter appAdapter = this.this$0;
        executor.execute(new Runnable() {
            /* class com.android.systemui.controls.management.AppAdapter$callback$1$onServicesUpdated$1.AnonymousClass1 */

            public final void run() {
                AppAdapter.this.notifyDataSetChanged();
            }
        });
    }
}
