package com.android.systemui.statusbar.events;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.internal.annotations.GuardedBy;
import com.android.systemui.R$id;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsChangedListener;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.leak.RotationUtils;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* compiled from: PrivacyDotViewController.kt */
public final class PrivacyDotViewController {
    private final SystemStatusAnimationScheduler animationScheduler;
    private View bl;
    private View br;
    private Runnable cancelRunnable;
    private final ConfigurationController configurationController;
    private final StatusBarContentInsetsProvider contentInsetsProvider;
    private ViewState currentViewState;
    private final Object lock = new Object();
    private final Executor mainExecutor;
    @GuardedBy({"lock"})
    private ViewState nextViewState;
    private int sbHeightLandscape;
    private int sbHeightPortrait;
    private final StatusBarStateController stateController;
    private final SystemStatusAnimationCallback systemStatusAnimationCallback;
    private View tl;
    private View tr;
    private DelayableExecutor uiExecutor;

    private final int rotatedCorner(int i, int i2) {
        int i3 = i - i2;
        return i3 < 0 ? i3 + 4 : i3;
    }

    public PrivacyDotViewController(Executor executor, StatusBarStateController statusBarStateController, ConfigurationController configurationController2, StatusBarContentInsetsProvider statusBarContentInsetsProvider, SystemStatusAnimationScheduler systemStatusAnimationScheduler) {
        Intrinsics.checkNotNullParameter(executor, "mainExecutor");
        Intrinsics.checkNotNullParameter(statusBarStateController, "stateController");
        Intrinsics.checkNotNullParameter(configurationController2, "configurationController");
        Intrinsics.checkNotNullParameter(statusBarContentInsetsProvider, "contentInsetsProvider");
        Intrinsics.checkNotNullParameter(systemStatusAnimationScheduler, "animationScheduler");
        this.mainExecutor = executor;
        this.stateController = statusBarStateController;
        this.configurationController = configurationController2;
        this.contentInsetsProvider = statusBarContentInsetsProvider;
        this.animationScheduler = systemStatusAnimationScheduler;
        ViewState viewState = new ViewState(false, false, false, false, null, null, null, null, false, 0, 0, 0, null, null, 16383, null);
        this.currentViewState = viewState;
        this.nextViewState = ViewState.copy$default(viewState, false, false, false, false, null, null, null, null, false, 0, 0, 0, null, null, 16383, null);
        statusBarContentInsetsProvider.addCallback((StatusBarContentInsetsChangedListener) new StatusBarContentInsetsChangedListener(this) {
            /* class com.android.systemui.statusbar.events.PrivacyDotViewController.AnonymousClass1 */
            final /* synthetic */ PrivacyDotViewController this$0;

            {
                this.this$0 = r1;
            }

            @Override // com.android.systemui.statusbar.phone.StatusBarContentInsetsChangedListener
            public void onStatusBarContentInsetsChanged() {
                PrivacyDotViewControllerKt.access$dlog("onStatusBarContentInsetsChanged: ");
                this.this$0.setNewLayoutRects();
            }
        });
        configurationController2.addCallback(new ConfigurationController.ConfigurationListener(this) {
            /* class com.android.systemui.statusbar.events.PrivacyDotViewController.AnonymousClass2 */
            final /* synthetic */ PrivacyDotViewController this$0;

            {
                this.this$0 = r1;
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public void onLayoutDirectionChanged(boolean z) {
                PrivacyDotViewController privacyDotViewController = this.this$0;
                synchronized (this) {
                    privacyDotViewController.setNextViewState(ViewState.copy$default(privacyDotViewController.nextViewState, false, false, false, false, null, null, null, null, z, 0, 0, 0, privacyDotViewController.selectDesignatedCorner(privacyDotViewController.nextViewState.getRotation(), z), null, 12031, null));
                    Unit unit = Unit.INSTANCE;
                }
            }
        });
        statusBarStateController.addCallback(new StatusBarStateController.StateListener(this) {
            /* class com.android.systemui.statusbar.events.PrivacyDotViewController.AnonymousClass3 */
            final /* synthetic */ PrivacyDotViewController this$0;

            {
                this.this$0 = r1;
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onExpandedChanged(boolean z) {
                this.this$0.updateStatusBarState();
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onStateChanged(int i) {
                this.this$0.updateStatusBarState();
            }
        });
        this.systemStatusAnimationCallback = new PrivacyDotViewController$systemStatusAnimationCallback$1(this);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final void setNextViewState(ViewState viewState) {
        this.nextViewState = viewState;
        scheduleUpdate();
    }

    private final Sequence<View> getViews() {
        View view = this.tl;
        if (view == null) {
            return SequencesKt.sequenceOf(new View[0]);
        }
        View[] viewArr = new View[4];
        if (view != null) {
            viewArr[0] = view;
            View view2 = this.tr;
            if (view2 != null) {
                viewArr[1] = view2;
                View view3 = this.br;
                if (view3 != null) {
                    viewArr[2] = view3;
                    View view4 = this.bl;
                    if (view4 != null) {
                        viewArr[3] = view4;
                        return SequencesKt.sequenceOf(viewArr);
                    }
                    Intrinsics.throwUninitializedPropertyAccessException("bl");
                    throw null;
                }
                Intrinsics.throwUninitializedPropertyAccessException("br");
                throw null;
            }
            Intrinsics.throwUninitializedPropertyAccessException("tr");
            throw null;
        }
        Intrinsics.throwUninitializedPropertyAccessException("tl");
        throw null;
    }

    public final void setUiExecutor(DelayableExecutor delayableExecutor) {
        Intrinsics.checkNotNullParameter(delayableExecutor, "e");
        this.uiExecutor = delayableExecutor;
    }

    public final void setQsExpanded(boolean z) {
        PrivacyDotViewControllerKt.access$dlog(Intrinsics.stringPlus("setQsExpanded ", Boolean.valueOf(z)));
        synchronized (this.lock) {
            setNextViewState(ViewState.copy$default(this.nextViewState, false, false, false, z, null, null, null, null, false, 0, 0, 0, null, null, 16375, null));
            Unit unit = Unit.INSTANCE;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0034, code lost:
        if (r21 == 0) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0037, code lost:
        if (r21 == 1) goto L_0x0041;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003a, code lost:
        if (r21 == 2) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003d, code lost:
        if (r21 == 3) goto L_0x0041;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003f, code lost:
        r1 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0041, code lost:
        r1 = r20.sbHeightLandscape;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0044, code lost:
        r1 = r20.sbHeightPortrait;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0046, code lost:
        r1 = r20.lock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0049, code lost:
        monitor-enter(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004c, code lost:
        r19 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        setNextViewState(com.android.systemui.statusbar.events.ViewState.copy$default(r20.nextViewState, false, false, false, false, null, null, null, null, false, r21, r1, r13, r14, null, 8703, null));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x006c, code lost:
        monitor-exit(r19);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x006d, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x006e, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0070, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0071, code lost:
        r19 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0073, code lost:
        monitor-exit(r19);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0074, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0028, code lost:
        setCornerVisibilities(4);
        r14 = selectDesignatedCorner(r21, r2);
        r13 = cornerIndex(r14);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setNewRotation(int r21) {
        /*
        // Method dump skipped, instructions count: 120
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.events.PrivacyDotViewController.setNewRotation(int):void");
    }

    private final void hideDotView(View view, boolean z) {
        view.clearAnimation();
        if (z) {
            view.animate().setDuration(160).setInterpolator(Interpolators.ALPHA_OUT).alpha(0.0f).withEndAction(new PrivacyDotViewController$hideDotView$1(view)).start();
        } else {
            view.setVisibility(4);
        }
    }

    private final void showDotView(View view, boolean z) {
        view.clearAnimation();
        if (z) {
            view.setVisibility(0);
            view.setAlpha(0.0f);
            view.animate().alpha(1.0f).setDuration(160).setInterpolator(Interpolators.ALPHA_IN).start();
            return;
        }
        view.setVisibility(0);
        view.setAlpha(1.0f);
    }

    private final void updateRotations(int i) {
        for (View view : getViews()) {
            int rotatedCorner = rotatedCorner(cornerForView(view), i);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            Objects.requireNonNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
            ((FrameLayout.LayoutParams) layoutParams).gravity = PrivacyDotViewControllerKt.access$toGravity(rotatedCorner);
            ViewGroup.LayoutParams layoutParams2 = view.findViewById(R$id.privacy_dot).getLayoutParams();
            Objects.requireNonNull(layoutParams2, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
            ((FrameLayout.LayoutParams) layoutParams2).gravity = PrivacyDotViewControllerKt.access$innerGravity(rotatedCorner);
        }
    }

    private final void setCornerSizes(ViewState viewState) {
        int i;
        int i2;
        boolean layoutRtl = viewState.getLayoutRtl();
        Point point = new Point();
        View view = this.tl;
        if (view != null) {
            view.getContext().getDisplay().getRealSize(point);
            View view2 = this.tl;
            if (view2 != null) {
                int exactRotation = RotationUtils.getExactRotation(view2.getContext());
                if (exactRotation == 1 || exactRotation == 3) {
                    i = point.y;
                    i2 = point.x;
                } else {
                    i = point.x;
                    i2 = point.y;
                }
                View view3 = this.tl;
                if (view3 != null) {
                    Rect contentRectForRotation = viewState.contentRectForRotation(activeRotationForCorner(view3, layoutRtl));
                    View view4 = this.tl;
                    if (view4 != null) {
                        ViewGroup.LayoutParams layoutParams = view4.getLayoutParams();
                        Objects.requireNonNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
                        layoutParams2.height = contentRectForRotation.height();
                        if (layoutRtl) {
                            layoutParams2.width = contentRectForRotation.left;
                        } else {
                            layoutParams2.width = i2 - contentRectForRotation.right;
                        }
                        View view5 = this.tr;
                        if (view5 != null) {
                            Rect contentRectForRotation2 = viewState.contentRectForRotation(activeRotationForCorner(view5, layoutRtl));
                            View view6 = this.tr;
                            if (view6 != null) {
                                ViewGroup.LayoutParams layoutParams3 = view6.getLayoutParams();
                                Objects.requireNonNull(layoutParams3, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                                FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) layoutParams3;
                                layoutParams4.height = contentRectForRotation2.height();
                                if (layoutRtl) {
                                    layoutParams4.width = contentRectForRotation2.left;
                                } else {
                                    layoutParams4.width = i - contentRectForRotation2.right;
                                }
                                View view7 = this.br;
                                if (view7 != null) {
                                    Rect contentRectForRotation3 = viewState.contentRectForRotation(activeRotationForCorner(view7, layoutRtl));
                                    View view8 = this.br;
                                    if (view8 != null) {
                                        ViewGroup.LayoutParams layoutParams5 = view8.getLayoutParams();
                                        Objects.requireNonNull(layoutParams5, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                                        FrameLayout.LayoutParams layoutParams6 = (FrameLayout.LayoutParams) layoutParams5;
                                        layoutParams6.height = contentRectForRotation3.height();
                                        if (layoutRtl) {
                                            layoutParams6.width = contentRectForRotation3.left;
                                        } else {
                                            layoutParams6.width = i2 - contentRectForRotation3.right;
                                        }
                                        View view9 = this.bl;
                                        if (view9 != null) {
                                            Rect contentRectForRotation4 = viewState.contentRectForRotation(activeRotationForCorner(view9, layoutRtl));
                                            View view10 = this.bl;
                                            if (view10 != null) {
                                                ViewGroup.LayoutParams layoutParams7 = view10.getLayoutParams();
                                                Objects.requireNonNull(layoutParams7, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
                                                FrameLayout.LayoutParams layoutParams8 = (FrameLayout.LayoutParams) layoutParams7;
                                                layoutParams8.height = contentRectForRotation4.height();
                                                if (layoutRtl) {
                                                    layoutParams8.width = contentRectForRotation4.left;
                                                } else {
                                                    layoutParams8.width = i - contentRectForRotation4.right;
                                                }
                                            } else {
                                                Intrinsics.throwUninitializedPropertyAccessException("bl");
                                                throw null;
                                            }
                                        } else {
                                            Intrinsics.throwUninitializedPropertyAccessException("bl");
                                            throw null;
                                        }
                                    } else {
                                        Intrinsics.throwUninitializedPropertyAccessException("br");
                                        throw null;
                                    }
                                } else {
                                    Intrinsics.throwUninitializedPropertyAccessException("br");
                                    throw null;
                                }
                            } else {
                                Intrinsics.throwUninitializedPropertyAccessException("tr");
                                throw null;
                            }
                        } else {
                            Intrinsics.throwUninitializedPropertyAccessException("tr");
                            throw null;
                        }
                    } else {
                        Intrinsics.throwUninitializedPropertyAccessException("tl");
                        throw null;
                    }
                } else {
                    Intrinsics.throwUninitializedPropertyAccessException("tl");
                    throw null;
                }
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("tl");
                throw null;
            }
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("tl");
            throw null;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final View selectDesignatedCorner(int i, boolean z) {
        View view = this.tl;
        if (view == null) {
            return null;
        }
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        throw new IllegalStateException("unknown rotation");
                    } else if (z) {
                        view = this.bl;
                        if (view == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("bl");
                            throw null;
                        }
                    } else if (view == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tl");
                        throw null;
                    }
                } else if (z) {
                    view = this.br;
                    if (view == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("br");
                        throw null;
                    }
                } else {
                    view = this.bl;
                    if (view == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("bl");
                        throw null;
                    }
                }
            } else if (z) {
                view = this.tr;
                if (view == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tr");
                    throw null;
                }
            } else {
                view = this.br;
                if (view == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("br");
                    throw null;
                }
            }
        } else if (!z) {
            view = this.tr;
            if (view == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tr");
                throw null;
            }
        } else if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tl");
            throw null;
        }
        return view;
    }

    private final void updateDesignatedCorner(View view, boolean z) {
        if (z && view != null) {
            view.clearAnimation();
            view.setVisibility(0);
            view.setAlpha(0.0f);
            view.animate().alpha(1.0f).setDuration(300).start();
        }
    }

    private final void setCornerVisibilities(int i) {
        for (View view : getViews()) {
            view.setVisibility(i);
        }
    }

    private final int cornerForView(View view) {
        View view2 = this.tl;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tl");
            throw null;
        } else if (Intrinsics.areEqual(view, view2)) {
            return 0;
        } else {
            View view3 = this.tr;
            if (view3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tr");
                throw null;
            } else if (Intrinsics.areEqual(view, view3)) {
                return 1;
            } else {
                View view4 = this.bl;
                if (view4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("bl");
                    throw null;
                } else if (Intrinsics.areEqual(view, view4)) {
                    return 3;
                } else {
                    View view5 = this.br;
                    if (view5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("br");
                        throw null;
                    } else if (Intrinsics.areEqual(view, view5)) {
                        return 2;
                    } else {
                        throw new IllegalArgumentException("not a corner view");
                    }
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001f, code lost:
        if (r8 != false) goto L_0x0013;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000f, code lost:
        if (r8 != false) goto L_0x0011;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int activeRotationForCorner(android.view.View r7, boolean r8) {
        /*
            r6 = this;
            android.view.View r0 = r6.tr
            r1 = 0
            if (r0 == 0) goto L_0x0042
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r0)
            r2 = 2
            r3 = 3
            r4 = 1
            r5 = 0
            if (r0 == 0) goto L_0x0015
            if (r8 == 0) goto L_0x0013
        L_0x0011:
            r2 = r4
            goto L_0x0034
        L_0x0013:
            r2 = r5
            goto L_0x0034
        L_0x0015:
            android.view.View r0 = r6.tl
            if (r0 == 0) goto L_0x003b
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r0)
            if (r0 == 0) goto L_0x0024
            if (r8 == 0) goto L_0x0022
            goto L_0x0013
        L_0x0022:
            r2 = r3
            goto L_0x0034
        L_0x0024:
            android.view.View r6 = r6.br
            if (r6 == 0) goto L_0x0035
            boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r6)
            if (r6 == 0) goto L_0x0031
            if (r8 == 0) goto L_0x0011
            goto L_0x0034
        L_0x0031:
            if (r8 == 0) goto L_0x0034
            goto L_0x0022
        L_0x0034:
            return r2
        L_0x0035:
            java.lang.String r6 = "br"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r6)
            throw r1
        L_0x003b:
            java.lang.String r6 = "tl"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r6)
            throw r1
        L_0x0042:
            java.lang.String r6 = "tr"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r6)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.events.PrivacyDotViewController.activeRotationForCorner(android.view.View, boolean):int");
    }

    public final void initialize(View view, View view2, View view3, View view4) {
        Intrinsics.checkNotNullParameter(view, "topLeft");
        Intrinsics.checkNotNullParameter(view2, "topRight");
        Intrinsics.checkNotNullParameter(view3, "bottomLeft");
        Intrinsics.checkNotNullParameter(view4, "bottomRight");
        View view5 = this.tl;
        if (!(view5 == null || this.tr == null || this.bl == null || this.br == null)) {
            if (view5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tl");
                throw null;
            } else if (Intrinsics.areEqual(view5, view)) {
                View view6 = this.tr;
                if (view6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tr");
                    throw null;
                } else if (Intrinsics.areEqual(view6, view2)) {
                    View view7 = this.bl;
                    if (view7 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("bl");
                        throw null;
                    } else if (Intrinsics.areEqual(view7, view3)) {
                        View view8 = this.br;
                        if (view8 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("br");
                            throw null;
                        } else if (Intrinsics.areEqual(view8, view4)) {
                            return;
                        }
                    }
                }
            }
        }
        this.tl = view;
        this.tr = view2;
        this.bl = view3;
        this.br = view4;
        boolean isLayoutRtl = this.configurationController.isLayoutRtl();
        View selectDesignatedCorner = selectDesignatedCorner(0, isLayoutRtl);
        int cornerIndex = cornerIndex(selectDesignatedCorner);
        this.mainExecutor.execute(new PrivacyDotViewController$initialize$5(this));
        Rect statusBarContentInsetsForRotation = this.contentInsetsProvider.getStatusBarContentInsetsForRotation(3);
        Rect statusBarContentInsetsForRotation2 = this.contentInsetsProvider.getStatusBarContentInsetsForRotation(0);
        Rect statusBarContentInsetsForRotation3 = this.contentInsetsProvider.getStatusBarContentInsetsForRotation(1);
        Rect statusBarContentInsetsForRotation4 = this.contentInsetsProvider.getStatusBarContentInsetsForRotation(2);
        synchronized (this.lock) {
            setNextViewState(ViewState.copy$default(this.nextViewState, true, false, false, false, statusBarContentInsetsForRotation2, statusBarContentInsetsForRotation3, statusBarContentInsetsForRotation4, statusBarContentInsetsForRotation, isLayoutRtl, 0, 0, cornerIndex, selectDesignatedCorner, null, 9742, null));
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setStatusBarHeights(int i, int i2) {
        this.sbHeightPortrait = i;
        this.sbHeightLandscape = i2;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final void updateStatusBarState() {
        synchronized (this.lock) {
            setNextViewState(ViewState.copy$default(this.nextViewState, false, false, isShadeInQs(), false, null, null, null, null, false, 0, 0, 0, null, null, 16379, null));
            Unit unit = Unit.INSTANCE;
        }
    }

    @GuardedBy({"lock"})
    private final boolean isShadeInQs() {
        return (this.stateController.isExpanded() && this.stateController.getState() == 0) || this.stateController.getState() == 2;
    }

    private final void scheduleUpdate() {
        PrivacyDotViewControllerKt.access$dlog("scheduleUpdate: ");
        Runnable runnable = this.cancelRunnable;
        if (runnable != null) {
            runnable.run();
        }
        DelayableExecutor delayableExecutor = this.uiExecutor;
        this.cancelRunnable = delayableExecutor == null ? null : delayableExecutor.executeDelayed(new PrivacyDotViewController$scheduleUpdate$1(this), 100);
    }

    /* access modifiers changed from: private */
    public final void processNextViewState() {
        ViewState copy$default;
        PrivacyDotViewControllerKt.access$dlog("processNextViewState: ");
        synchronized (this.lock) {
            copy$default = ViewState.copy$default(this.nextViewState, false, false, false, false, null, null, null, null, false, 0, 0, 0, null, null, 16383, null);
            Unit unit = Unit.INSTANCE;
        }
        resolveState(copy$default);
    }

    private final void resolveState(ViewState viewState) {
        View designatedCorner;
        PrivacyDotViewControllerKt.access$dlog(Intrinsics.stringPlus("resolveState ", viewState));
        if (!viewState.getViewInitialized()) {
            PrivacyDotViewControllerKt.access$dlog("resolveState: view is not initialized. skipping.");
        } else if (Intrinsics.areEqual(viewState, this.currentViewState)) {
            PrivacyDotViewControllerKt.access$dlog("resolveState: skipping");
        } else {
            if (viewState.getRotation() != this.currentViewState.getRotation()) {
                updateRotations(viewState.getRotation());
            }
            if (viewState.needsLayout(this.currentViewState)) {
                setCornerSizes(viewState);
                for (View view : getViews()) {
                    view.requestLayout();
                }
            }
            if (!Intrinsics.areEqual(viewState.getDesignatedCorner(), this.currentViewState.getDesignatedCorner())) {
                View designatedCorner2 = this.currentViewState.getDesignatedCorner();
                if (designatedCorner2 != null) {
                    designatedCorner2.setContentDescription(null);
                }
                View designatedCorner3 = viewState.getDesignatedCorner();
                if (designatedCorner3 != null) {
                    designatedCorner3.setContentDescription(viewState.getContentDescription());
                }
                updateDesignatedCorner(viewState.getDesignatedCorner(), viewState.shouldShowDot());
            } else if (!Intrinsics.areEqual(viewState.getContentDescription(), this.currentViewState.getContentDescription()) && (designatedCorner = viewState.getDesignatedCorner()) != null) {
                designatedCorner.setContentDescription(viewState.getContentDescription());
            }
            boolean shouldShowDot = viewState.shouldShowDot();
            if (shouldShowDot != this.currentViewState.shouldShowDot()) {
                if (shouldShowDot && viewState.getDesignatedCorner() != null) {
                    showDotView(viewState.getDesignatedCorner(), true);
                } else if (!shouldShowDot && viewState.getDesignatedCorner() != null) {
                    hideDotView(viewState.getDesignatedCorner(), true);
                }
            }
            this.currentViewState = viewState;
        }
    }

    private final int cornerIndex(View view) {
        if (view != null) {
            return cornerForView(view);
        }
        return -1;
    }

    private final List<Rect> getLayoutRects() {
        return CollectionsKt__CollectionsKt.listOf((Object[]) new Rect[]{this.contentInsetsProvider.getStatusBarContentInsetsForRotation(3), this.contentInsetsProvider.getStatusBarContentInsetsForRotation(0), this.contentInsetsProvider.getStatusBarContentInsetsForRotation(1), this.contentInsetsProvider.getStatusBarContentInsetsForRotation(2)});
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private final void setNewLayoutRects() {
        List<Rect> layoutRects = getLayoutRects();
        synchronized (this.lock) {
            Rect rect = layoutRects.get(0);
            setNextViewState(ViewState.copy$default(this.nextViewState, false, false, false, false, layoutRects.get(1), layoutRects.get(2), layoutRects.get(3), rect, false, 0, 0, 0, null, null, 16143, null));
            Unit unit = Unit.INSTANCE;
        }
    }
}
