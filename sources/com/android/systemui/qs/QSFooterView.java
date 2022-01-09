package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserManager;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.settingslib.Utils;
import com.android.settingslib.development.DevelopmentSettingsEnabler;
import com.android.settingslib.drawable.UserIconDrawable;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.statusbar.phone.MultiUserSwitch;
import com.android.systemui.statusbar.phone.SettingsButton;

public class QSFooterView extends FrameLayout {
    private View mActionsContainer;
    private TextView mBuildText;
    private final ContentObserver mDeveloperSettingsObserver = new ContentObserver(new Handler(((FrameLayout) this).mContext.getMainLooper())) {
        /* class com.android.systemui.qs.QSFooterView.AnonymousClass1 */

        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            QSFooterView.this.setBuildText();
        }
    };
    protected View mEdit;
    private View.OnClickListener mExpandClickListener;
    private boolean mExpanded;
    private float mExpansionAmount;
    protected TouchAnimator mFooterAnimator;
    private ImageView mMultiUserAvatar;
    protected MultiUserSwitch mMultiUserSwitch;
    private PageIndicator mPageIndicator;
    private boolean mQsDisabled;
    private SettingsButton mSettingsButton;
    private TouchAnimator mSettingsCogAnimator;
    protected View mSettingsContainer;
    private boolean mShouldShowBuildText;
    private View mTunerIcon;
    private int mTunerIconTranslation;

    public QSFooterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mEdit = requireViewById(16908291);
        this.mPageIndicator = (PageIndicator) findViewById(R$id.footer_page_indicator);
        this.mSettingsButton = (SettingsButton) findViewById(R$id.settings_button);
        this.mSettingsContainer = findViewById(R$id.settings_button_container);
        MultiUserSwitch multiUserSwitch = (MultiUserSwitch) findViewById(R$id.multi_user_switch);
        this.mMultiUserSwitch = multiUserSwitch;
        this.mMultiUserAvatar = (ImageView) multiUserSwitch.findViewById(R$id.multi_user_avatar);
        this.mActionsContainer = requireViewById(R$id.qs_footer_actions_container);
        this.mBuildText = (TextView) findViewById(R$id.build);
        this.mTunerIcon = requireViewById(R$id.tuner_icon);
        if (this.mSettingsButton.getBackground() instanceof RippleDrawable) {
            ((RippleDrawable) this.mSettingsButton.getBackground()).setForceSoftware(true);
        }
        updateResources();
        setImportantForAccessibility(1);
        setBuildText();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setBuildText() {
        if (this.mBuildText != null) {
            if (DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(((FrameLayout) this).mContext)) {
                this.mBuildText.setText(((FrameLayout) this).mContext.getString(17039799, Build.VERSION.RELEASE_OR_CODENAME, Build.ID));
                this.mBuildText.setSelected(true);
                this.mShouldShowBuildText = true;
                return;
            }
            this.mBuildText.setText((CharSequence) null);
            this.mShouldShowBuildText = false;
            this.mBuildText.setSelected(false);
        }
    }

    /* access modifiers changed from: package-private */
    public void updateAnimator(int i, int i2) {
        int dimensionPixelSize = (i - ((((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R$dimen.qs_quick_tile_size) - ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R$dimen.qs_tile_padding)) * i2)) / (i2 - 1);
        int dimensionPixelOffset = ((FrameLayout) this).mContext.getResources().getDimensionPixelOffset(R$dimen.default_gear_space);
        TouchAnimator.Builder builder = new TouchAnimator.Builder();
        SettingsButton settingsButton = this.mSettingsButton;
        float[] fArr = new float[2];
        int i3 = dimensionPixelSize - dimensionPixelOffset;
        if (!isLayoutRtl()) {
            i3 = -i3;
        }
        fArr[0] = (float) i3;
        fArr[1] = 0.0f;
        this.mSettingsCogAnimator = builder.addFloat(settingsButton, "translationX", fArr).addFloat(this.mSettingsButton, "rotation", -120.0f, 0.0f).build();
        setExpansion(this.mExpansionAmount);
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateResources();
    }

    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        updateResources();
    }

    private void updateResources() {
        updateFooterAnimator();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        marginLayoutParams.bottomMargin = getResources().getDimensionPixelSize(R$dimen.qs_footers_margin_bottom);
        setLayoutParams(marginLayoutParams);
        this.mTunerIconTranslation = ((FrameLayout) this).mContext.getResources().getDimensionPixelOffset(R$dimen.qs_footer_tuner_icon_translation);
        View view = this.mTunerIcon;
        boolean isLayoutRtl = isLayoutRtl();
        int i = this.mTunerIconTranslation;
        if (isLayoutRtl) {
            i = -i;
        }
        view.setTranslationX((float) i);
    }

    private void updateFooterAnimator() {
        this.mFooterAnimator = createFooterAnimator();
    }

    private TouchAnimator createFooterAnimator() {
        return new TouchAnimator.Builder().addFloat(this.mActionsContainer, "alpha", 0.0f, 1.0f).addFloat(this.mPageIndicator, "alpha", 0.0f, 1.0f).addFloat(this.mBuildText, "alpha", 0.0f, 1.0f).setStartDelay(0.9f).build();
    }

    public void setKeyguardShowing() {
        setExpansion(this.mExpansionAmount);
    }

    public void setExpandClickListener(View.OnClickListener onClickListener) {
        this.mExpandClickListener = onClickListener;
    }

    /* access modifiers changed from: package-private */
    public void setExpanded(boolean z, boolean z2, boolean z3) {
        if (this.mExpanded != z) {
            this.mExpanded = z;
            updateEverything(z2, z3);
        }
    }

    public void setExpansion(float f) {
        this.mExpansionAmount = f;
        TouchAnimator touchAnimator = this.mSettingsCogAnimator;
        if (touchAnimator != null) {
            touchAnimator.setPosition(f);
        }
        TouchAnimator touchAnimator2 = this.mFooterAnimator;
        if (touchAnimator2 != null) {
            touchAnimator2.setPosition(f);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((FrameLayout) this).mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("development_settings_enabled"), false, this.mDeveloperSettingsObserver, -1);
    }

    public void onDetachedFromWindow() {
        ((FrameLayout) this).mContext.getContentResolver().unregisterContentObserver(this.mDeveloperSettingsObserver);
        super.onDetachedFromWindow();
    }

    public boolean performAccessibilityAction(int i, Bundle bundle) {
        View.OnClickListener onClickListener;
        if (i != 262144 || (onClickListener = this.mExpandClickListener) == null) {
            return super.performAccessibilityAction(i, bundle);
        }
        onClickListener.onClick(null);
        return true;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
    }

    /* access modifiers changed from: package-private */
    public void disable(int i, boolean z, boolean z2) {
        boolean z3 = true;
        if ((i & 1) == 0) {
            z3 = false;
        }
        if (z3 != this.mQsDisabled) {
            this.mQsDisabled = z3;
            updateEverything(z, z2);
        }
    }

    /* access modifiers changed from: package-private */
    public void updateEverything(boolean z, boolean z2) {
        post(new QSFooterView$$ExternalSyntheticLambda0(this, z, z2));
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$updateEverything$0(boolean z, boolean z2) {
        updateVisibilities(z, z2);
        updateClickabilities();
        setClickable(false);
    }

    private void updateClickabilities() {
        MultiUserSwitch multiUserSwitch = this.mMultiUserSwitch;
        boolean z = true;
        multiUserSwitch.setClickable(multiUserSwitch.getVisibility() == 0);
        View view = this.mEdit;
        view.setClickable(view.getVisibility() == 0);
        SettingsButton settingsButton = this.mSettingsButton;
        settingsButton.setClickable(settingsButton.getVisibility() == 0);
        TextView textView = this.mBuildText;
        if (textView.getVisibility() != 0) {
            z = false;
        }
        textView.setLongClickable(z);
    }

    private void updateVisibilities(boolean z, boolean z2) {
        int i = 8;
        int i2 = 0;
        this.mSettingsContainer.setVisibility(this.mQsDisabled ? 8 : 0);
        this.mTunerIcon.setVisibility(z ? 0 : 4);
        boolean isDeviceInDemoMode = UserManager.isDeviceInDemoMode(((FrameLayout) this).mContext);
        MultiUserSwitch multiUserSwitch = this.mMultiUserSwitch;
        if (showUserSwitcher(z2)) {
            i = 0;
        }
        multiUserSwitch.setVisibility(i);
        this.mSettingsButton.setVisibility((isDeviceInDemoMode || !this.mExpanded) ? 4 : 0);
        TextView textView = this.mBuildText;
        if (!this.mExpanded || !this.mShouldShowBuildText) {
            i2 = 4;
        }
        textView.setVisibility(i2);
    }

    private boolean showUserSwitcher(boolean z) {
        return this.mExpanded && z;
    }

    /* access modifiers changed from: package-private */
    public void onUserInfoChanged(Drawable drawable, boolean z) {
        if (drawable != null && z && !(drawable instanceof UserIconDrawable)) {
            drawable = drawable.getConstantState().newDrawable(getResources()).mutate();
            drawable.setColorFilter(Utils.getColorAttrDefaultColor(((FrameLayout) this).mContext, 16842800), PorterDuff.Mode.SRC_IN);
        }
        this.mMultiUserAvatar.setImageDrawable(drawable);
    }
}
