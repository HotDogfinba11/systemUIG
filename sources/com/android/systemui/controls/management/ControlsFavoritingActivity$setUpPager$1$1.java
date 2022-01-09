package com.android.systemui.controls.management;

import android.text.TextUtils;
import android.widget.TextView;
import androidx.viewpager2.widget.ViewPager2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsFavoritingActivity.kt */
public final class ControlsFavoritingActivity$setUpPager$1$1 extends ViewPager2.OnPageChangeCallback {
    final /* synthetic */ ControlsFavoritingActivity this$0;

    ControlsFavoritingActivity$setUpPager$1$1(ControlsFavoritingActivity controlsFavoritingActivity) {
        this.this$0 = controlsFavoritingActivity;
    }

    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
    public void onPageSelected(int i) {
        super.onPageSelected(i);
        CharSequence structureName = ((StructureContainer) this.this$0.listOfStructures.get(i)).getStructureName();
        if (TextUtils.isEmpty(structureName)) {
            structureName = this.this$0.appName;
        }
        TextView textView = this.this$0.titleView;
        if (textView != null) {
            textView.setText(structureName);
            TextView textView2 = this.this$0.titleView;
            if (textView2 != null) {
                textView2.requestFocus();
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("titleView");
                throw null;
            }
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("titleView");
            throw null;
        }
    }

    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
    public void onPageScrolled(int i, float f, int i2) {
        super.onPageScrolled(i, f, i2);
        ManagementPageIndicator managementPageIndicator = this.this$0.pageIndicator;
        if (managementPageIndicator != null) {
            managementPageIndicator.setLocation(((float) i) + f);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("pageIndicator");
            throw null;
        }
    }
}
