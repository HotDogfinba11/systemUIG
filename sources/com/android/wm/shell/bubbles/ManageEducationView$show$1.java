package com.android.wm.shell.bubbles;

import android.graphics.Rect;
import android.view.View;
import android.widget.Button;
import com.android.wm.shell.R;
import com.android.wm.shell.animation.Interpolators;

/* access modifiers changed from: package-private */
/* compiled from: ManageEducationView.kt */
public final class ManageEducationView$show$1 implements Runnable {
    final /* synthetic */ BubbleExpandedView $expandedView;
    final /* synthetic */ Rect $rect;
    final /* synthetic */ ManageEducationView this$0;

    ManageEducationView$show$1(BubbleExpandedView bubbleExpandedView, Rect rect, ManageEducationView manageEducationView) {
        this.$expandedView = bubbleExpandedView;
        this.$rect = rect;
        this.this$0 = manageEducationView;
    }

    public final void run() {
        this.$expandedView.getManageButtonBoundsOnScreen(this.$rect);
        Button manageButton = this.this$0.getManageButton();
        final BubbleExpandedView bubbleExpandedView = this.$expandedView;
        final ManageEducationView manageEducationView = this.this$0;
        manageButton.setOnClickListener(new View.OnClickListener() {
            /* class com.android.wm.shell.bubbles.ManageEducationView$show$1.AnonymousClass1 */

            public final void onClick(View view) {
                bubbleExpandedView.findViewById(R.id.settings_button).performClick();
                manageEducationView.hide(true);
            }
        });
        Button gotItButton = this.this$0.getGotItButton();
        final ManageEducationView manageEducationView2 = this.this$0;
        gotItButton.setOnClickListener(new View.OnClickListener() {
            /* class com.android.wm.shell.bubbles.ManageEducationView$show$1.AnonymousClass2 */

            public final void onClick(View view) {
                manageEducationView2.hide(true);
            }
        });
        final ManageEducationView manageEducationView3 = this.this$0;
        manageEducationView3.setOnClickListener(new View.OnClickListener() {
            /* class com.android.wm.shell.bubbles.ManageEducationView$show$1.AnonymousClass3 */

            public final void onClick(View view) {
                manageEducationView3.hide(true);
            }
        });
        View manageView = this.this$0.getManageView();
        Rect rect = this.$rect;
        ManageEducationView manageEducationView4 = this.this$0;
        manageView.setTranslationX(0.0f);
        manageView.setTranslationY((float) ((rect.top - manageEducationView4.getManageView().getHeight()) + manageView.getResources().getDimensionPixelSize(R.dimen.bubbles_manage_education_top_inset)));
        this.this$0.bringToFront();
        this.this$0.animate().setDuration(this.this$0.ANIMATE_DURATION).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).alpha(1.0f);
    }
}
