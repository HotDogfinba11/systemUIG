package com.google.android.systemui.gamedashboard;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import java.util.function.Consumer;

public class FloatingEntryButton {
    private final int mButtonHeight;
    private boolean mCanShow = true;
    private final Context mContext;
    private final ImageView mEntryPointButton;
    private EntryPointController mEntryPointController;
    private final View mFloatingView;
    private boolean mIsShowing = false;
    private final int mMargin;
    private Consumer<Boolean> mVisibilityChangedCallback;
    private final WindowManager mWindowManager;

    public FloatingEntryButton(Context context) {
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        View inflate = LayoutInflater.from(context).inflate(R$layout.game_dashboard_floating_entry_point, (ViewGroup) null);
        this.mFloatingView = inflate;
        inflate.setVisibility(0);
        ImageView imageView = (ImageView) inflate.findViewById(R$id.game_dashboard_entry_button);
        this.mEntryPointButton = imageView;
        imageView.setVisibility(0);
        Resources resources = context.getResources();
        this.mMargin = Math.max(resources.getDimensionPixelSize(R$dimen.game_dashboard_floating_entry_point_margin), resources.getDimensionPixelSize(R$dimen.rounded_corner_content_padding));
        this.mButtonHeight = resources.getDimensionPixelSize(R$dimen.game_dashboard_floating_entry_point_height);
    }

    public void setEntryPointController(EntryPointController entryPointController) {
        this.mEntryPointController = entryPointController;
    }

    public View getCurrentView() {
        return this.mFloatingView;
    }

    public boolean show(boolean z) {
        int i;
        if (!this.mCanShow || this.mIsShowing) {
            return false;
        }
        this.mIsShowing = true;
        int i2 = this.mContext.getResources().getConfiguration().orientation;
        int i3 = this.mMargin;
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R$dimen.navigation_bar_height);
        if (i2 == 2) {
            if (!z) {
                i = dimensionPixelSize + i3;
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, this.mButtonHeight + i3, i, 0, 2024, 8, -3);
                layoutParams.privateFlags |= 16;
                layoutParams.setTitle("FloatingEntryButton");
                layoutParams.setFitInsetsTypes(0);
                layoutParams.gravity = 8388693;
                this.mWindowManager.addView(this.mFloatingView, layoutParams);
                this.mFloatingView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    /* class com.google.android.systemui.gamedashboard.FloatingEntryButton.AnonymousClass1 */

                    public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                        if (FloatingEntryButton.this.mIsShowing && FloatingEntryButton.this.mVisibilityChangedCallback != null) {
                            FloatingEntryButton.this.mVisibilityChangedCallback.accept(Boolean.TRUE);
                        }
                        FloatingEntryButton.this.mFloatingView.removeOnLayoutChangeListener(this);
                    }
                });
                return true;
            }
        } else if (i2 != 1) {
            i = i3;
            WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams(-2, this.mButtonHeight + i3, i, 0, 2024, 8, -3);
            layoutParams2.privateFlags |= 16;
            layoutParams2.setTitle("FloatingEntryButton");
            layoutParams2.setFitInsetsTypes(0);
            layoutParams2.gravity = 8388693;
            this.mWindowManager.addView(this.mFloatingView, layoutParams2);
            this.mFloatingView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                /* class com.google.android.systemui.gamedashboard.FloatingEntryButton.AnonymousClass1 */

                public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    if (FloatingEntryButton.this.mIsShowing && FloatingEntryButton.this.mVisibilityChangedCallback != null) {
                        FloatingEntryButton.this.mVisibilityChangedCallback.accept(Boolean.TRUE);
                    }
                    FloatingEntryButton.this.mFloatingView.removeOnLayoutChangeListener(this);
                }
            });
            return true;
        }
        i = i3;
        i3 = dimensionPixelSize + i3;
        WindowManager.LayoutParams layoutParams22 = new WindowManager.LayoutParams(-2, this.mButtonHeight + i3, i, 0, 2024, 8, -3);
        layoutParams22.privateFlags |= 16;
        layoutParams22.setTitle("FloatingEntryButton");
        layoutParams22.setFitInsetsTypes(0);
        layoutParams22.gravity = 8388693;
        this.mWindowManager.addView(this.mFloatingView, layoutParams22);
        this.mFloatingView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            /* class com.google.android.systemui.gamedashboard.FloatingEntryButton.AnonymousClass1 */

            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                if (FloatingEntryButton.this.mIsShowing && FloatingEntryButton.this.mVisibilityChangedCallback != null) {
                    FloatingEntryButton.this.mVisibilityChangedCallback.accept(Boolean.TRUE);
                }
                FloatingEntryButton.this.mFloatingView.removeOnLayoutChangeListener(this);
            }
        });
        return true;
    }

    public boolean hide() {
        if (!this.mIsShowing) {
            return false;
        }
        this.mWindowManager.removeViewImmediate(this.mFloatingView);
        this.mIsShowing = false;
        Consumer<Boolean> consumer = this.mVisibilityChangedCallback;
        if (consumer == null) {
            return true;
        }
        consumer.accept(Boolean.FALSE);
        return true;
    }

    public boolean isVisible() {
        return this.mIsShowing;
    }

    public void setEntryPointOnClickListener(View.OnClickListener onClickListener) {
        this.mEntryPointButton.setOnClickListener(onClickListener);
    }

    public void setCanShow(boolean z) {
        this.mCanShow = z;
        if (!z) {
            hide();
        }
    }

    public void setVisibilityChangedCallback(Consumer<Boolean> consumer) {
        this.mVisibilityChangedCallback = consumer;
    }
}
