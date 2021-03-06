package com.android.systemui.controls.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.service.controls.Control;
import android.service.controls.actions.ControlAction;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.RangeTemplate;
import android.service.controls.templates.StatelessTemplate;
import android.service.controls.templates.TemperatureControlTemplate;
import android.service.controls.templates.ThumbnailTemplate;
import android.service.controls.templates.ToggleRangeTemplate;
import android.service.controls.templates.ToggleTemplate;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.R$color;
import com.android.systemui.R$fraction;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;

/* compiled from: ControlViewHolder.kt */
public final class ControlViewHolder {
    private static final int[] ATTR_DISABLED = {-16842910};
    private static final int[] ATTR_ENABLED = {16842910};
    public static final Companion Companion = new Companion(null);
    private static final Set<Integer> FORCE_PANEL_DEVICES = SetsKt__SetsKt.setOf((Object[]) new Integer[]{49, 50});
    private final GradientDrawable baseLayer;
    private Behavior behavior;
    private final DelayableExecutor bgExecutor;
    private final ClipDrawable clipLayer;
    private final Context context;
    private final ControlActionCoordinator controlActionCoordinator;
    private final ControlsController controlsController;
    private final ControlsMetricsLogger controlsMetricsLogger;
    public ControlWithState cws;
    private final ImageView icon;
    private boolean isLoading;
    private ControlAction lastAction;
    private Dialog lastChallengeDialog;
    private final ViewGroup layout;
    private CharSequence nextStatusText = "";
    private final Function0<Unit> onDialogCancel;
    private ValueAnimator stateAnimator;
    private final TextView status;
    private Animator statusAnimator;
    private final TextView subtitle;
    private final TextView title;
    private final float toggleBackgroundIntensity;
    private final DelayableExecutor uiExecutor;
    private final int uid;
    private boolean userInteractionInProgress;
    private Dialog visibleDialog;

    public ControlViewHolder(ViewGroup viewGroup, ControlsController controlsController2, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, ControlActionCoordinator controlActionCoordinator2, ControlsMetricsLogger controlsMetricsLogger2, int i) {
        Intrinsics.checkNotNullParameter(viewGroup, "layout");
        Intrinsics.checkNotNullParameter(controlsController2, "controlsController");
        Intrinsics.checkNotNullParameter(delayableExecutor, "uiExecutor");
        Intrinsics.checkNotNullParameter(delayableExecutor2, "bgExecutor");
        Intrinsics.checkNotNullParameter(controlActionCoordinator2, "controlActionCoordinator");
        Intrinsics.checkNotNullParameter(controlsMetricsLogger2, "controlsMetricsLogger");
        this.layout = viewGroup;
        this.controlsController = controlsController2;
        this.uiExecutor = delayableExecutor;
        this.bgExecutor = delayableExecutor2;
        this.controlActionCoordinator = controlActionCoordinator2;
        this.controlsMetricsLogger = controlsMetricsLogger2;
        this.uid = i;
        this.toggleBackgroundIntensity = viewGroup.getContext().getResources().getFraction(R$fraction.controls_toggle_bg_intensity, 1, 1);
        View requireViewById = viewGroup.requireViewById(R$id.icon);
        Intrinsics.checkNotNullExpressionValue(requireViewById, "layout.requireViewById(R.id.icon)");
        this.icon = (ImageView) requireViewById;
        View requireViewById2 = viewGroup.requireViewById(R$id.status);
        Intrinsics.checkNotNullExpressionValue(requireViewById2, "layout.requireViewById(R.id.status)");
        TextView textView = (TextView) requireViewById2;
        this.status = textView;
        View requireViewById3 = viewGroup.requireViewById(R$id.title);
        Intrinsics.checkNotNullExpressionValue(requireViewById3, "layout.requireViewById(R.id.title)");
        this.title = (TextView) requireViewById3;
        View requireViewById4 = viewGroup.requireViewById(R$id.subtitle);
        Intrinsics.checkNotNullExpressionValue(requireViewById4, "layout.requireViewById(R.id.subtitle)");
        this.subtitle = (TextView) requireViewById4;
        Context context2 = viewGroup.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "layout.getContext()");
        this.context = context2;
        this.onDialogCancel = new ControlViewHolder$onDialogCancel$1(this);
        Drawable background = viewGroup.getBackground();
        Objects.requireNonNull(background, "null cannot be cast to non-null type android.graphics.drawable.LayerDrawable");
        LayerDrawable layerDrawable = (LayerDrawable) background;
        layerDrawable.mutate();
        Drawable findDrawableByLayerId = layerDrawable.findDrawableByLayerId(R$id.clip_layer);
        Objects.requireNonNull(findDrawableByLayerId, "null cannot be cast to non-null type android.graphics.drawable.ClipDrawable");
        this.clipLayer = (ClipDrawable) findDrawableByLayerId;
        Drawable findDrawableByLayerId2 = layerDrawable.findDrawableByLayerId(R$id.background);
        Objects.requireNonNull(findDrawableByLayerId2, "null cannot be cast to non-null type android.graphics.drawable.GradientDrawable");
        this.baseLayer = (GradientDrawable) findDrawableByLayerId2;
        textView.setSelected(true);
    }

    public final ViewGroup getLayout() {
        return this.layout;
    }

    public final DelayableExecutor getUiExecutor() {
        return this.uiExecutor;
    }

    public final DelayableExecutor getBgExecutor() {
        return this.bgExecutor;
    }

    public final ControlActionCoordinator getControlActionCoordinator() {
        return this.controlActionCoordinator;
    }

    public final int getUid() {
        return this.uid;
    }

    /* compiled from: ControlViewHolder.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final KClass<? extends Behavior> findBehaviorClass(int i, ControlTemplate controlTemplate, int i2) {
            Intrinsics.checkNotNullParameter(controlTemplate, "template");
            if (i != 1) {
                return Reflection.getOrCreateKotlinClass(StatusBehavior.class);
            }
            if (Intrinsics.areEqual(controlTemplate, ControlTemplate.NO_TEMPLATE)) {
                return Reflection.getOrCreateKotlinClass(TouchBehavior.class);
            }
            if (controlTemplate instanceof ThumbnailTemplate) {
                return Reflection.getOrCreateKotlinClass(ThumbnailBehavior.class);
            }
            if (i2 == 50) {
                return Reflection.getOrCreateKotlinClass(TouchBehavior.class);
            }
            if (controlTemplate instanceof ToggleTemplate) {
                return Reflection.getOrCreateKotlinClass(ToggleBehavior.class);
            }
            if (controlTemplate instanceof StatelessTemplate) {
                return Reflection.getOrCreateKotlinClass(TouchBehavior.class);
            }
            if (controlTemplate instanceof ToggleRangeTemplate) {
                return Reflection.getOrCreateKotlinClass(ToggleRangeBehavior.class);
            }
            if (controlTemplate instanceof RangeTemplate) {
                return Reflection.getOrCreateKotlinClass(ToggleRangeBehavior.class);
            }
            return Reflection.getOrCreateKotlinClass(controlTemplate instanceof TemperatureControlTemplate ? TemperatureControlBehavior.class : DefaultBehavior.class);
        }
    }

    public final ImageView getIcon() {
        return this.icon;
    }

    public final TextView getStatus() {
        return this.status;
    }

    public final TextView getTitle() {
        return this.title;
    }

    public final TextView getSubtitle() {
        return this.subtitle;
    }

    public final Context getContext() {
        return this.context;
    }

    public final ClipDrawable getClipLayer() {
        return this.clipLayer;
    }

    public final ControlWithState getCws() {
        ControlWithState controlWithState = this.cws;
        if (controlWithState != null) {
            return controlWithState;
        }
        Intrinsics.throwUninitializedPropertyAccessException("cws");
        throw null;
    }

    public final void setCws(ControlWithState controlWithState) {
        Intrinsics.checkNotNullParameter(controlWithState, "<set-?>");
        this.cws = controlWithState;
    }

    public final ControlAction getLastAction() {
        return this.lastAction;
    }

    public final void setLoading(boolean z) {
        this.isLoading = z;
    }

    public final void setVisibleDialog(Dialog dialog) {
        this.visibleDialog = dialog;
    }

    public final int getDeviceType() {
        Control control = getCws().getControl();
        Integer valueOf = control == null ? null : Integer.valueOf(control.getDeviceType());
        return valueOf == null ? getCws().getCi().getDeviceType() : valueOf.intValue();
    }

    public final int getControlStatus() {
        Control control = getCws().getControl();
        if (control == null) {
            return 0;
        }
        return control.getStatus();
    }

    public final ControlTemplate getControlTemplate() {
        Control control = getCws().getControl();
        ControlTemplate controlTemplate = control == null ? null : control.getControlTemplate();
        if (controlTemplate != null) {
            return controlTemplate;
        }
        ControlTemplate controlTemplate2 = ControlTemplate.NO_TEMPLATE;
        Intrinsics.checkNotNullExpressionValue(controlTemplate2, "NO_TEMPLATE");
        return controlTemplate2;
    }

    public final void setUserInteractionInProgress(boolean z) {
        this.userInteractionInProgress = z;
    }

    public final void bindData(ControlWithState controlWithState, boolean z) {
        Intrinsics.checkNotNullParameter(controlWithState, "cws");
        if (!this.userInteractionInProgress) {
            setCws(controlWithState);
            if (getControlStatus() == 0 || getControlStatus() == 2) {
                this.title.setText(controlWithState.getCi().getControlTitle());
                this.subtitle.setText(controlWithState.getCi().getControlSubtitle());
            } else {
                Control control = controlWithState.getControl();
                if (control != null) {
                    getTitle().setText(control.getTitle());
                    getSubtitle().setText(control.getSubtitle());
                }
            }
            boolean z2 = true;
            if (controlWithState.getControl() != null) {
                getLayout().setClickable(true);
                getLayout().setOnLongClickListener(new ControlViewHolder$bindData$2$1(this));
                getControlActionCoordinator().runPendingAction(controlWithState.getCi().getControlId());
            }
            boolean z3 = this.isLoading;
            this.isLoading = false;
            this.behavior = bindBehavior$default(this, this.behavior, Companion.findBehaviorClass(getControlStatus(), getControlTemplate(), getDeviceType()), 0, 4, null);
            updateContentDescription();
            if (!z3 || this.isLoading) {
                z2 = false;
            }
            if (z2) {
                this.controlsMetricsLogger.refreshEnd(this, z);
            }
        }
    }

    public final void actionResponse(int i) {
        this.controlActionCoordinator.enableActionOnTouch(getCws().getCi().getControlId());
        boolean z = this.lastChallengeDialog != null;
        if (i == 0) {
            this.lastChallengeDialog = null;
            setErrorStatus();
        } else if (i == 1) {
            this.lastChallengeDialog = null;
        } else if (i == 2) {
            this.lastChallengeDialog = null;
            setErrorStatus();
        } else if (i == 3) {
            Dialog createConfirmationDialog = ChallengeDialogs.INSTANCE.createConfirmationDialog(this, this.onDialogCancel);
            this.lastChallengeDialog = createConfirmationDialog;
            if (createConfirmationDialog != null) {
                createConfirmationDialog.show();
            }
        } else if (i == 4) {
            Dialog createPinDialog = ChallengeDialogs.INSTANCE.createPinDialog(this, false, z, this.onDialogCancel);
            this.lastChallengeDialog = createPinDialog;
            if (createPinDialog != null) {
                createPinDialog.show();
            }
        } else if (i == 5) {
            Dialog createPinDialog2 = ChallengeDialogs.INSTANCE.createPinDialog(this, true, z, this.onDialogCancel);
            this.lastChallengeDialog = createPinDialog2;
            if (createPinDialog2 != null) {
                createPinDialog2.show();
            }
        }
    }

    public final void dismiss() {
        Dialog dialog = this.lastChallengeDialog;
        if (dialog != null) {
            dialog.dismiss();
        }
        this.lastChallengeDialog = null;
        Dialog dialog2 = this.visibleDialog;
        if (dialog2 != null) {
            dialog2.dismiss();
        }
        this.visibleDialog = null;
    }

    public final void setErrorStatus() {
        animateStatusChange(true, new ControlViewHolder$setErrorStatus$1(this, this.context.getResources().getString(R$string.controls_error_failed)));
    }

    private final void updateContentDescription() {
        ViewGroup viewGroup = this.layout;
        StringBuilder sb = new StringBuilder();
        sb.append((Object) this.title.getText());
        sb.append(' ');
        sb.append((Object) this.subtitle.getText());
        sb.append(' ');
        sb.append((Object) this.status.getText());
        viewGroup.setContentDescription(sb.toString());
    }

    public final void action(ControlAction controlAction) {
        Intrinsics.checkNotNullParameter(controlAction, "action");
        this.lastAction = controlAction;
        this.controlsController.action(getCws().getComponentName(), getCws().getCi(), controlAction);
    }

    public final boolean usePanel() {
        return FORCE_PANEL_DEVICES.contains(Integer.valueOf(getDeviceType())) || Intrinsics.areEqual(getControlTemplate(), ControlTemplate.NO_TEMPLATE);
    }

    public static /* synthetic */ Behavior bindBehavior$default(ControlViewHolder controlViewHolder, Behavior behavior2, KClass kClass, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return controlViewHolder.bindBehavior(behavior2, kClass, i);
    }

    public final Behavior bindBehavior(Behavior behavior2, KClass<? extends Behavior> kClass, int i) {
        Intrinsics.checkNotNullParameter(kClass, "clazz");
        if (behavior2 == null || !Intrinsics.areEqual(Reflection.getOrCreateKotlinClass(behavior2.getClass()), kClass)) {
            behavior2 = (Behavior) JvmClassMappingKt.getJavaClass(kClass).newInstance();
            behavior2.initialize(this);
            this.layout.setAccessibilityDelegate(null);
        }
        behavior2.bind(getCws(), i);
        Intrinsics.checkNotNullExpressionValue(behavior2, "behavior.also {\n            it.bind(cws, offset)\n        }");
        return behavior2;
    }

    public static /* synthetic */ void applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core$default(ControlViewHolder controlViewHolder, boolean z, int i, boolean z2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z2 = true;
        }
        controlViewHolder.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core(z, i, z2);
    }

    public final void applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core(boolean z, int i, boolean z2) {
        int i2;
        if (getControlStatus() == 1 || getControlStatus() == 0) {
            i2 = getDeviceType();
        } else {
            i2 = -1000;
        }
        RenderInfo lookup = RenderInfo.Companion.lookup(this.context, getCws().getComponentName(), i2, i);
        ColorStateList colorStateList = this.context.getResources().getColorStateList(lookup.getForeground(), this.context.getTheme());
        CharSequence charSequence = this.nextStatusText;
        Control control = getCws().getControl();
        if (Intrinsics.areEqual(charSequence, this.status.getText())) {
            z2 = false;
        }
        animateStatusChange(z2, new ControlViewHolder$applyRenderInfo$1(this, z, charSequence, lookup, colorStateList, control));
        animateBackgroundChange(z2, z, lookup.getEnabledBackground());
    }

    public final void setStatusTextSize(float f) {
        this.status.setTextSize(0, f);
    }

    public static /* synthetic */ void setStatusText$default(ControlViewHolder controlViewHolder, CharSequence charSequence, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        controlViewHolder.setStatusText(charSequence, z);
    }

    public final void setStatusText(CharSequence charSequence, boolean z) {
        Intrinsics.checkNotNullParameter(charSequence, "text");
        if (z) {
            this.status.setAlpha(1.0f);
            this.status.setText(charSequence);
            updateContentDescription();
        }
        this.nextStatusText = charSequence;
    }

    private final void animateBackgroundChange(boolean z, boolean z2, int i) {
        List list;
        int i2;
        ColorStateList customColor;
        Resources resources = this.context.getResources();
        int i3 = R$color.control_default_background;
        int color = resources.getColor(i3, this.context.getTheme());
        if (z2) {
            Control control = getCws().getControl();
            Integer num = null;
            if (!(control == null || (customColor = control.getCustomColor()) == null)) {
                num = Integer.valueOf(customColor.getColorForState(new int[]{16842910}, customColor.getDefaultColor()));
            }
            if (num == null) {
                i2 = this.context.getResources().getColor(i, this.context.getTheme());
            } else {
                i2 = num.intValue();
            }
            list = CollectionsKt__CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(i2), 255});
        } else {
            list = CollectionsKt__CollectionsKt.listOf((Object[]) new Integer[]{Integer.valueOf(this.context.getResources().getColor(i3, this.context.getTheme())), 0});
        }
        int intValue = ((Number) list.get(0)).intValue();
        int intValue2 = ((Number) list.get(1)).intValue();
        if (this.behavior instanceof ToggleRangeBehavior) {
            color = ColorUtils.blendARGB(color, intValue, this.toggleBackgroundIntensity);
        }
        Drawable drawable = this.clipLayer.getDrawable();
        if (drawable != null) {
            getClipLayer().setAlpha(0);
            ValueAnimator valueAnimator = this.stateAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            if (z) {
                startBackgroundAnimation(drawable, intValue2, intValue, color);
            } else {
                applyBackgroundChange(drawable, intValue2, intValue, color, 1.0f);
            }
        }
    }

    private final void startBackgroundAnimation(Drawable drawable, int i, int i2, int i3) {
        ColorStateList color;
        int defaultColor = (!(drawable instanceof GradientDrawable) || (color = ((GradientDrawable) drawable).getColor()) == null) ? i2 : color.getDefaultColor();
        ColorStateList color2 = this.baseLayer.getColor();
        int defaultColor2 = color2 == null ? i3 : color2.getDefaultColor();
        float alpha = this.layout.getAlpha();
        ValueAnimator ofInt = ValueAnimator.ofInt(this.clipLayer.getAlpha(), i);
        ofInt.addUpdateListener(new ControlViewHolder$startBackgroundAnimation$1$1(defaultColor, i2, defaultColor2, i3, alpha, this, drawable));
        ofInt.addListener(new ControlViewHolder$startBackgroundAnimation$1$2(this));
        ofInt.setDuration(700L);
        ofInt.setInterpolator(Interpolators.CONTROL_STATE);
        ofInt.start();
        Unit unit = Unit.INSTANCE;
        this.stateAnimator = ofInt;
    }

    /* access modifiers changed from: private */
    public final void applyBackgroundChange(Drawable drawable, int i, int i2, int i3, float f) {
        drawable.setAlpha(i);
        if (drawable instanceof GradientDrawable) {
            ((GradientDrawable) drawable).setColor(i2);
        }
        this.baseLayer.setColor(i3);
        this.layout.setAlpha(f);
    }

    private final void animateStatusChange(boolean z, Function0<Unit> function0) {
        Animator animator = this.statusAnimator;
        if (animator != null) {
            animator.cancel();
        }
        if (!z) {
            function0.invoke();
        } else if (this.isLoading) {
            function0.invoke();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.status, "alpha", 0.45f);
            ofFloat.setRepeatMode(2);
            ofFloat.setRepeatCount(-1);
            ofFloat.setDuration(500L);
            ofFloat.setInterpolator(Interpolators.LINEAR);
            ofFloat.setStartDelay(900);
            ofFloat.start();
            Unit unit = Unit.INSTANCE;
            this.statusAnimator = ofFloat;
        } else {
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.status, "alpha", 0.0f);
            ofFloat2.setDuration(200L);
            Interpolator interpolator = Interpolators.LINEAR;
            ofFloat2.setInterpolator(interpolator);
            ofFloat2.addListener(new ControlViewHolder$animateStatusChange$fadeOut$1$1(function0));
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.status, "alpha", 1.0f);
            ofFloat3.setDuration(200L);
            ofFloat3.setInterpolator(interpolator);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(ofFloat2, ofFloat3);
            animatorSet.addListener(new ControlViewHolder$animateStatusChange$2$1(this));
            animatorSet.start();
            Unit unit2 = Unit.INSTANCE;
            this.statusAnimator = animatorSet;
        }
    }

    public final void updateStatusRow$frameworks__base__packages__SystemUI__android_common__SystemUI_core(boolean z, CharSequence charSequence, Drawable drawable, ColorStateList colorStateList, Control control) {
        Icon customIcon;
        Intrinsics.checkNotNullParameter(charSequence, "text");
        Intrinsics.checkNotNullParameter(drawable, "drawable");
        Intrinsics.checkNotNullParameter(colorStateList, "color");
        setEnabled(z);
        this.status.setText(charSequence);
        updateContentDescription();
        this.status.setTextColor(colorStateList);
        Unit unit = null;
        if (!(control == null || (customIcon = control.getCustomIcon()) == null)) {
            getIcon().setImageIcon(customIcon);
            getIcon().setImageTintList(customIcon.getTintList());
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            if (drawable instanceof StateListDrawable) {
                if (getIcon().getDrawable() == null || !(getIcon().getDrawable() instanceof StateListDrawable)) {
                    getIcon().setImageDrawable(drawable);
                }
                getIcon().setImageState(z ? ATTR_ENABLED : ATTR_DISABLED, true);
            } else {
                getIcon().setImageDrawable(drawable);
            }
            if (getDeviceType() != 52) {
                getIcon().setImageTintList(colorStateList);
            }
        }
    }

    private final void setEnabled(boolean z) {
        this.status.setEnabled(z);
        this.icon.setEnabled(z);
    }
}
