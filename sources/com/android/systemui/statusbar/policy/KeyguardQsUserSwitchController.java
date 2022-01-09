package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import com.android.keyguard.KeyguardVisibilityHelper;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.systemui.R$color;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.tiles.UserDetailView;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.phone.UserAvatarView;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.ViewController;
import javax.inject.Provider;

public class KeyguardQsUserSwitchController extends ViewController<FrameLayout> {
    private static final AnimationProperties ANIMATION_PROPERTIES = new AnimationProperties().setDuration(360);
    private UserSwitcherController.BaseUserAdapter mAdapter;
    private int mBarState;
    private final ConfigurationController mConfigurationController;
    private ConfigurationController.ConfigurationListener mConfigurationListener = new ConfigurationController.ConfigurationListener() {
        /* class com.android.systemui.statusbar.policy.KeyguardQsUserSwitchController.AnonymousClass2 */

        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public void onUiModeChanged() {
            KeyguardQsUserSwitchController.this.updateView(true);
        }
    };
    private final Context mContext;
    UserSwitcherController.UserRecord mCurrentUser;
    public final DataSetObserver mDataSetObserver = new DataSetObserver() {
        /* class com.android.systemui.statusbar.policy.KeyguardQsUserSwitchController.AnonymousClass5 */

        public void onChanged() {
            KeyguardQsUserSwitchController.this.updateView(false);
        }
    };
    private final FalsingManager mFalsingManager;
    private final KeyguardStateController mKeyguardStateController;
    private final KeyguardVisibilityHelper mKeyguardVisibilityHelper;
    private NotificationPanelViewController mNotificationPanelViewController;
    private Resources mResources;
    private final ScreenLifecycle mScreenLifecycle;
    protected final SysuiStatusBarStateController mStatusBarStateController;
    private final StatusBarStateController.StateListener mStatusBarStateListener = new StatusBarStateController.StateListener() {
        /* class com.android.systemui.statusbar.policy.KeyguardQsUserSwitchController.AnonymousClass1 */

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public void onStateChanged(int i) {
            boolean goingToFullShade = KeyguardQsUserSwitchController.this.mStatusBarStateController.goingToFullShade();
            boolean isKeyguardFadingAway = KeyguardQsUserSwitchController.this.mKeyguardStateController.isKeyguardFadingAway();
            int i2 = KeyguardQsUserSwitchController.this.mBarState;
            KeyguardQsUserSwitchController.this.mBarState = i;
            KeyguardQsUserSwitchController.this.setKeyguardQsUserSwitchVisibility(i, isKeyguardFadingAway, goingToFullShade, i2);
        }
    };
    private UserAvatarView mUserAvatarView;
    private final KeyguardUserDetailAdapter mUserDetailAdapter;
    private final UserSwitcherController mUserSwitcherController;

    public KeyguardQsUserSwitchController(FrameLayout frameLayout, Context context, Resources resources, ScreenLifecycle screenLifecycle, UserSwitcherController userSwitcherController, KeyguardStateController keyguardStateController, FalsingManager falsingManager, ConfigurationController configurationController, SysuiStatusBarStateController sysuiStatusBarStateController, DozeParameters dozeParameters, Provider<UserDetailView.Adapter> provider, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController) {
        super(frameLayout);
        this.mContext = context;
        this.mResources = resources;
        this.mScreenLifecycle = screenLifecycle;
        this.mUserSwitcherController = userSwitcherController;
        this.mKeyguardStateController = keyguardStateController;
        this.mFalsingManager = falsingManager;
        this.mConfigurationController = configurationController;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mKeyguardVisibilityHelper = new KeyguardVisibilityHelper(this.mView, keyguardStateController, dozeParameters, unlockedScreenOffAnimationController, false);
        this.mUserDetailAdapter = new KeyguardUserDetailAdapter(context, provider);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.ViewController
    public void onInit() {
        super.onInit();
        this.mUserAvatarView = (UserAvatarView) ((FrameLayout) this.mView).findViewById(R$id.kg_multi_user_avatar);
        this.mAdapter = new UserSwitcherController.BaseUserAdapter(this.mUserSwitcherController) {
            /* class com.android.systemui.statusbar.policy.KeyguardQsUserSwitchController.AnonymousClass3 */

            public View getView(int i, View view, ViewGroup viewGroup) {
                return null;
            }
        };
        this.mUserAvatarView.setOnClickListener(new KeyguardQsUserSwitchController$$ExternalSyntheticLambda0(this));
        this.mUserAvatarView.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            /* class com.android.systemui.statusbar.policy.KeyguardQsUserSwitchController.AnonymousClass4 */

            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, KeyguardQsUserSwitchController.this.mContext.getString(R$string.accessibility_quick_settings_choose_user_action)));
            }
        });
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$onInit$0(View view) {
        if (!this.mFalsingManager.isFalseTap(1) && !isListAnimating()) {
            openQsUserPanel();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.ViewController
    public void onViewAttached() {
        this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
        this.mDataSetObserver.onChanged();
        this.mStatusBarStateController.addCallback(this.mStatusBarStateListener);
        this.mConfigurationController.addCallback(this.mConfigurationListener);
        updateView(true);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
        this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        this.mStatusBarStateController.removeCallback(this.mStatusBarStateListener);
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
    }

    private boolean updateCurrentUser() {
        UserSwitcherController.UserRecord userRecord = this.mCurrentUser;
        this.mCurrentUser = null;
        for (int i = 0; i < this.mAdapter.getCount(); i++) {
            UserSwitcherController.UserRecord item = this.mAdapter.getItem(i);
            if (item.isCurrent) {
                this.mCurrentUser = item;
                return !item.equals(userRecord);
            }
        }
        if (this.mCurrentUser != null || userRecord == null) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateView(boolean z) {
        String str;
        UserInfo userInfo;
        if (updateCurrentUser() || z) {
            UserSwitcherController.UserRecord userRecord = this.mCurrentUser;
            if (userRecord == null || (userInfo = userRecord.info) == null || TextUtils.isEmpty(userInfo.name)) {
                str = this.mContext.getString(R$string.accessibility_multi_user_switch_switcher);
            } else {
                str = this.mContext.getString(R$string.accessibility_quick_settings_user, this.mCurrentUser.info.name);
            }
            if (!TextUtils.equals(this.mUserAvatarView.getContentDescription(), str)) {
                this.mUserAvatarView.setContentDescription(str);
            }
            UserSwitcherController.UserRecord userRecord2 = this.mCurrentUser;
            this.mUserAvatarView.setDrawableWithBadge(getCurrentUserIcon().mutate(), userRecord2 != null ? userRecord2.resolveId() : -10000);
        }
    }

    /* access modifiers changed from: package-private */
    public Drawable getCurrentUserIcon() {
        Drawable drawable;
        Drawable drawable2;
        UserSwitcherController.UserRecord userRecord = this.mCurrentUser;
        if (userRecord == null || userRecord.picture == null) {
            if (userRecord == null || !userRecord.isGuest) {
                drawable2 = this.mContext.getDrawable(R$drawable.ic_avatar_user);
            } else {
                drawable2 = this.mContext.getDrawable(R$drawable.ic_avatar_guest_user);
            }
            drawable = drawable2;
            drawable.setTint(this.mResources.getColor(R$color.kg_user_switcher_avatar_icon_color, this.mContext.getTheme()));
        } else {
            drawable = new CircleFramedDrawable(this.mCurrentUser.picture, (int) this.mResources.getDimension(R$dimen.kg_framed_avatar_size));
        }
        return new LayerDrawable(new Drawable[]{this.mContext.getDrawable(R$drawable.kg_bg_avatar), drawable});
    }

    public int getUserIconHeight() {
        return this.mUserAvatarView.getHeight();
    }

    public void setKeyguardQsUserSwitchVisibility(int i, boolean z, boolean z2, int i2) {
        this.mKeyguardVisibilityHelper.setViewVisibility(i, z, z2, i2);
    }

    public void updatePosition(int i, int i2, boolean z) {
        AnimationProperties animationProperties = ANIMATION_PROPERTIES;
        PropertyAnimator.setProperty((FrameLayout) this.mView, AnimatableProperty.Y, (float) i2, animationProperties, z);
        PropertyAnimator.setProperty((FrameLayout) this.mView, AnimatableProperty.TRANSLATION_X, (float) (-Math.abs(i)), animationProperties, z);
    }

    public void setAlpha(float f) {
        if (!this.mKeyguardVisibilityHelper.isVisibilityAnimating()) {
            ((FrameLayout) this.mView).setAlpha(f);
        }
    }

    private boolean isListAnimating() {
        return this.mKeyguardVisibilityHelper.isVisibilityAnimating();
    }

    private void openQsUserPanel() {
        this.mNotificationPanelViewController.expandWithQsDetail(this.mUserDetailAdapter);
    }

    public void setNotificationPanelViewController(NotificationPanelViewController notificationPanelViewController) {
        this.mNotificationPanelViewController = notificationPanelViewController;
    }

    /* access modifiers changed from: package-private */
    public class KeyguardUserDetailAdapter extends UserSwitcherController.UserDetailAdapter {
        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public boolean shouldAnimate() {
            return false;
        }

        KeyguardUserDetailAdapter(Context context, Provider<UserDetailView.Adapter> provider) {
            super(context, provider);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public int getDoneText() {
            return R$string.quick_settings_close_user_panel;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public boolean onDoneButtonClicked() {
            if (KeyguardQsUserSwitchController.this.mNotificationPanelViewController == null) {
                return false;
            }
            KeyguardQsUserSwitchController.this.mNotificationPanelViewController.animateCloseQs(true);
            return true;
        }
    }
}
