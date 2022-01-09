package com.android.systemui.statusbar.phone;

import android.os.UserManager;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.DejankUtils;
import com.android.systemui.R$bool;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.qs.QSDetailDisplayer;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.ViewController;

public class MultiUserSwitchController extends ViewController<MultiUserSwitch> {
    private final FalsingManager mFalsingManager;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        /* class com.android.systemui.statusbar.phone.MultiUserSwitchController.AnonymousClass1 */

        public void onClick(View view) {
            if (!MultiUserSwitchController.this.mFalsingManager.isFalseTap(1)) {
                View childAt = ((MultiUserSwitch) ((ViewController) MultiUserSwitchController.this).mView).getChildCount() > 0 ? ((MultiUserSwitch) ((ViewController) MultiUserSwitchController.this).mView).getChildAt(0) : ((ViewController) MultiUserSwitchController.this).mView;
                int[] iArr = new int[2];
                childAt.getLocationInWindow(iArr);
                iArr[0] = iArr[0] + (childAt.getWidth() / 2);
                iArr[1] = iArr[1] + (childAt.getHeight() / 2);
                MultiUserSwitchController.this.mQsDetailDisplayer.showDetailAdapter(MultiUserSwitchController.this.getUserDetailAdapter(), iArr[0], iArr[1]);
            }
        }
    };
    private final QSDetailDisplayer mQsDetailDisplayer;
    private UserSwitcherController.BaseUserAdapter mUserListener;
    private final UserManager mUserManager;
    private final UserSwitcherController mUserSwitcherController;

    public MultiUserSwitchController(MultiUserSwitch multiUserSwitch, UserManager userManager, UserSwitcherController userSwitcherController, QSDetailDisplayer qSDetailDisplayer, FalsingManager falsingManager) {
        super(multiUserSwitch);
        this.mUserManager = userManager;
        this.mUserSwitcherController = userSwitcherController;
        this.mQsDetailDisplayer = qSDetailDisplayer;
        this.mFalsingManager = falsingManager;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.ViewController
    public void onInit() {
        registerListener();
        ((MultiUserSwitch) this.mView).refreshContentDescription(getCurrentUser());
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.ViewController
    public void onViewAttached() {
        ((MultiUserSwitch) this.mView).setOnClickListener(this.mOnClickListener);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
        ((MultiUserSwitch) this.mView).setOnClickListener(null);
    }

    /* access modifiers changed from: protected */
    public DetailAdapter getUserDetailAdapter() {
        return this.mUserSwitcherController.mUserDetailAdapter;
    }

    private void registerListener() {
        UserSwitcherController userSwitcherController;
        if (this.mUserManager.isUserSwitcherEnabled() && this.mUserListener == null && (userSwitcherController = this.mUserSwitcherController) != null) {
            this.mUserListener = new UserSwitcherController.BaseUserAdapter(userSwitcherController) {
                /* class com.android.systemui.statusbar.phone.MultiUserSwitchController.AnonymousClass2 */

                public View getView(int i, View view, ViewGroup viewGroup) {
                    return null;
                }

                public void notifyDataSetChanged() {
                    ((MultiUserSwitch) ((ViewController) MultiUserSwitchController.this).mView).refreshContentDescription(MultiUserSwitchController.this.getCurrentUser());
                }
            };
            ((MultiUserSwitch) this.mView).refreshContentDescription(getCurrentUser());
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private String getCurrentUser() {
        if (((Boolean) DejankUtils.whitelistIpcs(new MultiUserSwitchController$$ExternalSyntheticLambda0(this))).booleanValue()) {
            return this.mUserSwitcherController.getCurrentUserName();
        }
        return null;
    }

    /* access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$getCurrentUser$0() {
        return Boolean.valueOf(this.mUserManager.isUserSwitcherEnabled());
    }

    /* access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$isMultiUserEnabled$1() {
        return Boolean.valueOf(this.mUserManager.isUserSwitcherEnabled(getResources().getBoolean(R$bool.qs_show_user_switcher_for_single_user)));
    }

    public boolean isMultiUserEnabled() {
        return ((Boolean) DejankUtils.whitelistIpcs(new MultiUserSwitchController$$ExternalSyntheticLambda1(this))).booleanValue();
    }
}
