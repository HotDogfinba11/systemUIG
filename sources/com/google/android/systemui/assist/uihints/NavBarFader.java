package com.google.android.systemui.assist.uihints;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.view.View;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarView;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NavBarFader.kt */
public final class NavBarFader implements NgaMessageHandler.NavBarVisibilityListener {
    public static final Companion Companion = new Companion(null);
    private ObjectAnimator animator = new ObjectAnimator();
    private final Handler handler;
    private final Lazy<NavigationBarController> navigationBarController;
    private final Runnable onTimeout = new NavBarFader$onTimeout$1(this);
    private float targetAlpha = -1.0f;

    public NavBarFader(Lazy<NavigationBarController> lazy, Handler handler2) {
        Intrinsics.checkNotNullParameter(lazy, "navigationBarController");
        Intrinsics.checkNotNullParameter(handler2, "handler");
        this.navigationBarController = lazy;
        this.handler = handler2;
    }

    @Override // com.google.android.systemui.assist.uihints.NgaMessageHandler.NavBarVisibilityListener
    public void onVisibleRequest(boolean z) {
        NavigationBarView defaultNavigationBarView = this.navigationBarController.get().getDefaultNavigationBarView();
        if (defaultNavigationBarView != null) {
            this.handler.removeCallbacks(this.onTimeout);
            if (!z) {
                this.handler.postDelayed(this.onTimeout, 10000);
            }
            float alpha = defaultNavigationBarView.getAlpha();
            float f = z ? 1.0f : 0.0f;
            if (!(f == alpha)) {
                if (!(f == this.targetAlpha)) {
                    this.animator.cancel();
                    this.targetAlpha = f;
                    ObjectAnimator duration = ObjectAnimator.ofFloat(defaultNavigationBarView, View.ALPHA, alpha, f).setDuration((long) (((float) 80) * Math.abs(f - alpha)));
                    Intrinsics.checkNotNullExpressionValue(duration, "ofFloat(navBarView, View.ALPHA, initialAlpha, finalAlpha)\n                .setDuration((FADE_DURATION_MS * abs(finalAlpha - initialAlpha)).toLong())");
                    this.animator = duration;
                    if (z) {
                        duration.setStartDelay(80);
                    }
                    this.animator.start();
                }
            }
        }
    }

    /* compiled from: NavBarFader.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
