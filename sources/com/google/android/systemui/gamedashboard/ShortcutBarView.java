package com.google.android.systemui.gamedashboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.IntProperty;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.TouchDelegate;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import com.android.internal.util.ScreenshotHelper;
import com.android.systemui.R$color;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.shared.recents.utilities.BitmapUtil;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.wm.shell.animation.PhysicsAnimator;
import com.android.wm.shell.tasksurfacehelper.TaskSurfaceHelper;
import com.google.android.systemui.gamedashboard.GameDashboardUiEventLogger;
import java.util.Optional;
import java.util.function.Consumer;

public class ShortcutBarView extends FrameLayout implements ViewTreeObserver.OnComputeInternalInsetsListener, ConfigurationController.ConfigurationListener, RecordingController.RecordingStateChangeCallback {
    private static final int SHORTCUT_BAR_BACKGROUND_COLOR = Color.parseColor("#99000000");
    private ShortcutBarContainer mBar;
    private final int mBarMarginEnd = getResources().getDimensionPixelSize(R$dimen.game_dashboard_shortcut_bar_margin_end);
    private ConfigurationController mConfigurationController;
    private ShortcutBarButton mEntryPointButton;
    private Consumer<Rect> mExcludeBackRegionCallback;
    private TextView mFpsView;
    private Insets mInsets;
    private boolean mIsAttached;
    private boolean mIsDockingAnimationRunning = false;
    private boolean mIsDragging = false;
    private boolean mIsEntryPointVisible;
    private boolean mIsFpsVisible;
    private boolean mIsRecordVisible;
    private boolean mIsScreenshotVisible;
    private final View.OnLayoutChangeListener mOnLayoutChangeListener = new View.OnLayoutChangeListener() {
        /* class com.google.android.systemui.gamedashboard.ShortcutBarView.AnonymousClass3 */

        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            float f;
            float viewTranslationX = ShortcutBarView.this.getViewTranslationX();
            float viewTranslationY = ShortcutBarView.this.getViewTranslationY();
            float f2 = (float) (i3 - i);
            float f3 = (float) (i7 - i5);
            float f4 = (float) (i4 - i2);
            float f5 = (float) (i8 - i6);
            int barWidth = ShortcutBarView.this.getBarWidth();
            int barHeight = ShortcutBarView.this.getBarHeight();
            if (ShortcutBarView.this.mRevealButton.getVisibility() == 0) {
                f = ShortcutBarView.this.mRevealButton.isOnTheRight() ? f2 - ((float) ShortcutBarView.this.mRevealButtonIconWidth) : 0.0f;
            } else {
                float f6 = (float) barWidth;
                f = ((f2 - f6) * viewTranslationX) / (f3 - f6);
            }
            float f7 = (float) barHeight;
            ShortcutBarView.this.setTranslation(f, ((f4 - f7) * viewTranslationY) / (f5 - f7));
            ShortcutBarView.this.snapBarBackIfNecessary();
            ShortcutBarView shortcutBarView = ShortcutBarView.this;
            shortcutBarView.removeOnLayoutChangeListener(shortcutBarView.mOnLayoutChangeListener);
        }
    };
    private final ShortcutBarTouchController mOnTouchListener = new ShortcutBarTouchController() {
        /* class com.google.android.systemui.gamedashboard.ShortcutBarView.AnonymousClass1 */
        private final PhysicsAnimator.SpringConfig mSpringConfig = new PhysicsAnimator.SpringConfig(700.0f, 1.0f);

        @Override // com.google.android.systemui.gamedashboard.ShortcutBarView.ShortcutBarTouchController
        public boolean onTouch(View view, MotionEvent motionEvent) {
            super.onTouch(view, motionEvent);
            int action = motionEvent.getAction();
            if (action == 0) {
                ShortcutBarView.this.mIsDragging = true;
            } else if (action == 1 || action == 3) {
                ShortcutBarView.this.mIsDragging = false;
            }
            return true;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.android.systemui.gamedashboard.ShortcutBarView.ShortcutBarTouchController
        public View getView() {
            return ShortcutBarView.this.mBar;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.android.systemui.gamedashboard.ShortcutBarView.ShortcutBarTouchController
        public void onMove(float f, float f2) {
            getView().setTranslationX(f);
            getView().setTranslationY(f2);
            if (getView().getX() < 0.0f && ShortcutBarView.this.mInsets.left == 0) {
                ShortcutBarView.this.dock(false);
            } else if (getView().getX() + ((float) getView().getWidth()) > ((float) ShortcutBarView.this.getWidth()) && ShortcutBarView.this.mInsets.right == 0) {
                ShortcutBarView.this.dock(true);
            }
        }

        /* access modifiers changed from: protected */
        @Override // com.google.android.systemui.gamedashboard.ShortcutBarView.ShortcutBarTouchController
        public void onUp(float f, float f2) {
            if (!ShortcutBarView.this.mIsDockingAnimationRunning) {
                PhysicsAnimator.getInstance(getView()).flingThenSpring(DynamicAnimation.TRANSLATION_X, f, getFlingXConfig(), this.mSpringConfig, true).fling(DynamicAnimation.TRANSLATION_Y, f2, getFlingYConfig()).addEndListener(new PhysicsAnimator.EndListener<View>() {
                    /* class com.google.android.systemui.gamedashboard.ShortcutBarView.AnonymousClass1.AnonymousClass1 */

                    public void onAnimationEnd(View view, FloatPropertyCompat<? super View> floatPropertyCompat, boolean z, boolean z2, float f, float f2, boolean z3) {
                        if (!z2 && floatPropertyCompat == DynamicAnimation.TRANSLATION_Y) {
                            ShortcutBarView.this.snapBarBackVertically().start();
                        }
                    }
                }).start();
            }
        }

        private PhysicsAnimator.FlingConfig getFlingXConfig() {
            return new PhysicsAnimator.FlingConfig(1.9f, ShortcutBarView.this.getBarMovementBoundsLeft(), ShortcutBarView.this.getBarMovementBoundsRight());
        }

        private PhysicsAnimator.FlingConfig getFlingYConfig() {
            return new PhysicsAnimator.FlingConfig(1.9f, ShortcutBarView.this.getBarMovementBoundsTop(), ShortcutBarView.this.getBarMovementBoundsBottom());
        }
    };
    private int mOrientation = -1;
    private ShortcutBarButton mRecordButton;
    private RevealButton mRevealButton;
    private final int mRevealButtonIconHeight = getResources().getDimensionPixelSize(R$dimen.game_dashboard_shortcut_bar_reveal_button_height);
    private final int mRevealButtonIconWidth = (getResources().getDimensionPixelSize(R$dimen.game_dashboard_shortcut_bar_reveal_button_width) / 2);
    private final ShortcutBarTouchController mRevealButtonOnTouchListener = new ShortcutBarTouchController() {
        /* class com.google.android.systemui.gamedashboard.ShortcutBarView.AnonymousClass2 */

        /* access modifiers changed from: protected */
        @Override // com.google.android.systemui.gamedashboard.ShortcutBarView.ShortcutBarTouchController
        public View getView() {
            return ShortcutBarView.this.mRevealButton;
        }

        /* access modifiers changed from: protected */
        @Override // com.google.android.systemui.gamedashboard.ShortcutBarView.ShortcutBarTouchController
        public void onMove(float f, float f2) {
            getView().setTranslationY(f2);
        }

        /* access modifiers changed from: protected */
        @Override // com.google.android.systemui.gamedashboard.ShortcutBarView.ShortcutBarTouchController
        public void onUp(float f, float f2) {
            if (!ShortcutBarView.this.mIsDockingAnimationRunning) {
                if ((!ShortcutBarView.this.mRevealButton.isOnTheRight() || f >= -500.0f) && (ShortcutBarView.this.mRevealButton.isOnTheRight() || f <= 500.0f)) {
                    PhysicsAnimator.getInstance(getView()).fling(DynamicAnimation.TRANSLATION_Y, f2, getFlingYConfig()).start();
                } else {
                    ShortcutBarView.this.autoUndock();
                }
            }
        }

        private PhysicsAnimator.FlingConfig getFlingYConfig() {
            return new PhysicsAnimator.FlingConfig(1.9f, ShortcutBarView.this.getRevealButtonTop(), ShortcutBarView.this.getRevealButtonBottom());
        }
    };
    private ScreenRecordController mScreenRecordController;
    private ShortcutBarButton mScreenshotButton;
    private int mShiftForTransientBar;
    private final ViewTreeObserver.OnDrawListener mSystemGestureExcludeUpdater = new ShortcutBarView$$ExternalSyntheticLambda3(this);
    private final int[] mTempInts = new int[2];
    private final Rect mTmpRect = new Rect();
    private final int mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    private GameDashboardUiEventLogger mUiEventLogger;

    /* renamed from: $r8$lambda$dqYjxawPxSV5jMv-27VIg7K7nVQ  reason: not valid java name */
    public static /* synthetic */ void m683$r8$lambda$dqYjxawPxSV5jMv27VIg7K7nVQ(ShortcutBarView shortcutBarView, View view) {
        shortcutBarView.lambda$init$3(view);
    }

    /* renamed from: $r8$lambda$vhLt2XijpZu7JLMTbeBgVWV6G-4  reason: not valid java name */
    public static /* synthetic */ void m684$r8$lambda$vhLt2XijpZu7JLMTbeBgVWV6G4(ShortcutBarView shortcutBarView, View view) {
        shortcutBarView.lambda$init$4(view);
    }

    public static /* synthetic */ void $r8$lambda$z97dER_mVbNAS0z7LmQ5soxX0mw(ShortcutBarView shortcutBarView, Optional optional, ShortcutBarController shortcutBarController, ScreenshotHelper screenshotHelper, Handler handler, View view) {
        shortcutBarView.lambda$init$2(optional, shortcutBarController, screenshotHelper, handler, view);
    }

    /* access modifiers changed from: private */
    public abstract class ShortcutBarTouchController implements View.OnTouchListener {
        private boolean mCancelled;
        private boolean mHasMoved;
        private final PointF mTouchDown;
        private final VelocityTracker mVelocityTracker;
        private final PointF mViewPositionOnTouchDown;

        /* access modifiers changed from: protected */
        public abstract View getView();

        /* access modifiers changed from: protected */
        public abstract void onMove(float f, float f2);

        /* access modifiers changed from: protected */
        public abstract void onUp(float f, float f2);

        private ShortcutBarTouchController() {
            this.mVelocityTracker = VelocityTracker.obtain();
            this.mViewPositionOnTouchDown = new PointF();
            this.mTouchDown = new PointF();
            this.mHasMoved = false;
            this.mCancelled = false;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            addMovement(motionEvent);
            int action = motionEvent.getAction();
            if (action == 0) {
                this.mTouchDown.set(motionEvent.getRawX(), motionEvent.getRawY());
                this.mViewPositionOnTouchDown.set(getView().getTranslationX(), getView().getTranslationY());
                this.mHasMoved = false;
                this.mCancelled = false;
            } else if (action != 1) {
                if (action != 2) {
                    if (action != 3 || this.mCancelled) {
                        return true;
                    }
                    this.mVelocityTracker.computeCurrentVelocity(1000);
                    onUp(this.mVelocityTracker.getXVelocity(), this.mVelocityTracker.getYVelocity());
                    this.mHasMoved = false;
                    this.mVelocityTracker.clear();
                } else if (this.mCancelled) {
                    return true;
                } else {
                    onMove((motionEvent.getRawX() - this.mTouchDown.x) + this.mViewPositionOnTouchDown.x, (motionEvent.getRawY() - this.mTouchDown.y) + this.mViewPositionOnTouchDown.y);
                    if (Math.hypot((double) (motionEvent.getRawX() - this.mTouchDown.x), (double) (motionEvent.getRawY() - this.mTouchDown.y)) > ((double) ShortcutBarView.this.mTouchSlop)) {
                        this.mHasMoved = true;
                    }
                }
            } else if (this.mCancelled) {
                return true;
            } else {
                if (!this.mHasMoved) {
                    view.performClick();
                } else {
                    this.mVelocityTracker.computeCurrentVelocity(1000);
                    onUp(this.mVelocityTracker.getXVelocity(), this.mVelocityTracker.getYVelocity());
                }
                this.mHasMoved = false;
                this.mVelocityTracker.clear();
            }
            return true;
        }

        public void cancelAnimation() {
            PhysicsAnimator.getInstance(getView()).cancel();
            this.mCancelled = true;
            this.mHasMoved = false;
            this.mVelocityTracker.clear();
        }

        private void addMovement(MotionEvent motionEvent) {
            float rawX = motionEvent.getRawX() - motionEvent.getX();
            float rawY = motionEvent.getRawY() - motionEvent.getY();
            motionEvent.offsetLocation(rawX, rawY);
            this.mVelocityTracker.addMovement(motionEvent);
            motionEvent.offsetLocation(-rawX, -rawY);
        }
    }

    public static ShortcutBarView create(Context context, ConfigurationController configurationController, ShortcutBarController shortcutBarController, Handler handler, Optional<TaskSurfaceHelper> optional, ScreenRecordController screenRecordController, GameDashboardUiEventLogger gameDashboardUiEventLogger) {
        ShortcutBarView shortcutBarView = (ShortcutBarView) LayoutInflater.from(context).inflate(R$layout.game_dashboard_shortcut_bar, (ViewGroup) null);
        shortcutBarView.init(configurationController, shortcutBarController, handler, optional, screenRecordController, gameDashboardUiEventLogger);
        return shortcutBarView;
    }

    public ShortcutBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mBar = (ShortcutBarContainer) findViewById(R$id.expanded_bar);
        this.mRevealButton = (RevealButton) findViewById(R$id.reveal);
        this.mScreenshotButton = (ShortcutBarButton) this.mBar.findViewById(R$id.screenshot);
        this.mRecordButton = (ShortcutBarButton) this.mBar.findViewById(R$id.record);
        this.mFpsView = (TextView) this.mBar.findViewById(R$id.fps);
        this.mEntryPointButton = (ShortcutBarButton) this.mBar.findViewById(R$id.entry_point);
    }

    private void init(ConfigurationController configurationController, ShortcutBarController shortcutBarController, Handler handler, Optional<TaskSurfaceHelper> optional, ScreenRecordController screenRecordController, GameDashboardUiEventLogger gameDashboardUiEventLogger) {
        this.mScreenRecordController = screenRecordController;
        this.mConfigurationController = configurationController;
        this.mUiEventLogger = gameDashboardUiEventLogger;
        ScreenshotHelper screenshotHelper = new ScreenshotHelper(getContext());
        this.mScreenshotButton.setOnTouchListener(this.mOnTouchListener);
        this.mScreenshotButton.setOnClickListener(new ShortcutBarView$$ExternalSyntheticLambda2(this, optional, shortcutBarController, screenshotHelper, handler));
        this.mRecordButton.setOnTouchListener(this.mOnTouchListener);
        this.mRecordButton.setOnClickListener(new ShortcutBarView$$ExternalSyntheticLambda0(this));
        setOnTouchListener(this.mOnTouchListener);
        this.mRevealButton.setOnClickListener(new ShortcutBarView$$ExternalSyntheticLambda1(this));
        this.mRevealButton.setOnTouchListener(this.mRevealButtonOnTouchListener);
        this.mRevealButton.setSide(true);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            /* class com.google.android.systemui.gamedashboard.ShortcutBarView.AnonymousClass4 */

            public void onGlobalLayout() {
                ShortcutBarView.this.mRevealButton.setTranslationY((((float) ShortcutBarView.this.getHeight()) * 1.0f) / 3.0f);
                ShortcutBarView.this.slideIn();
                int dimensionPixelSize = ShortcutBarView.this.getResources().getDimensionPixelSize(R$dimen.game_dashboard_shortcut_bar_stashed_horizontal_padding);
                int dimensionPixelSize2 = ShortcutBarView.this.getResources().getDimensionPixelSize(R$dimen.game_dashboard_shortcut_bar_stashed_vertical_padding);
                Rect rect = new Rect();
                ShortcutBarView.this.mRevealButton.getHitRect(rect);
                rect.top -= dimensionPixelSize2;
                rect.bottom += dimensionPixelSize2;
                rect.left -= dimensionPixelSize;
                rect.right += dimensionPixelSize;
                ((View) ShortcutBarView.this.mRevealButton.getParent()).setTouchDelegate(new TouchDelegate(rect, ShortcutBarView.this.mRevealButton));
                ShortcutBarView.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    private /* synthetic */ void lambda$init$2(Optional optional, ShortcutBarController shortcutBarController, ScreenshotHelper screenshotHelper, Handler handler, View view) {
        this.mUiEventLogger.log(GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_SCREENSHOT);
        optional.ifPresent(new ShortcutBarView$$ExternalSyntheticLambda5(this, shortcutBarController, screenshotHelper, handler));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$init$1(ShortcutBarController shortcutBarController, ScreenshotHelper screenshotHelper, Handler handler, TaskSurfaceHelper taskSurfaceHelper) {
        ActivityManager.RunningTaskInfo gameTaskInfo = shortcutBarController.getGameTaskInfo();
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        Rect rect = new Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        taskSurfaceHelper.screenshotTask(gameTaskInfo, rect, getContext().getMainExecutor(), new ShortcutBarView$$ExternalSyntheticLambda4(this, screenshotHelper, rect, gameTaskInfo, handler));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$init$0(ScreenshotHelper screenshotHelper, Rect rect, ActivityManager.RunningTaskInfo runningTaskInfo, Handler handler, SurfaceControl.ScreenshotHardwareBuffer screenshotHardwareBuffer) {
        screenshotHelper.provideScreenshot(BitmapUtil.hardwareBitmapToBundle(screenshotHardwareBuffer.asBitmap()), rect, Insets.NONE, runningTaskInfo.taskId, 1, new ComponentName(getClass().getPackageName(), getClass().getSimpleName()), 5, handler, (Consumer) null);
    }

    private /* synthetic */ void lambda$init$3(View view) {
        handleScreenRecord();
    }

    private /* synthetic */ void lambda$init$4(View view) {
        autoUndock();
    }

    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        this.mShiftForTransientBar = 0;
        if (this.mOrientation == 1) {
            this.mInsets = windowInsets.getStableInsets();
        } else {
            Insets insetsIgnoringVisibility = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars());
            if (insetsIgnoringVisibility.right > 0) {
                this.mInsets = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.statusBars() | WindowInsets.Type.displayCutout());
                this.mShiftForTransientBar = insetsIgnoringVisibility.right;
            } else {
                this.mInsets = windowInsets.getStableInsets();
            }
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onConfigChanged(Configuration configuration) {
        int i = configuration.orientation;
        if (i != this.mOrientation) {
            onOrientationChanged(i);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private float getBarMovementBoundsLeft() {
        return (float) (this.mInsets.left + this.mBarMarginEnd);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private float getBarMovementBoundsTop() {
        return (float) this.mInsets.top;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private float getBarMovementBoundsRight() {
        return (float) (((getWidth() - this.mBarMarginEnd) - this.mInsets.right) - this.mBar.getWidth());
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private float getBarMovementBoundsBottom() {
        return (float) ((getHeight() - this.mInsets.bottom) - this.mBar.getHeight());
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private float getRevealButtonTop() {
        return (float) this.mInsets.top;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private float getRevealButtonBottom() {
        return (float) ((getHeight() - this.mRevealButtonIconHeight) - this.mInsets.bottom);
    }

    private void onOrientationChanged(int i) {
        addOnLayoutChangeListener(this.mOnLayoutChangeListener);
        this.mOrientation = i;
    }

    public void setExcludeBackRegionCallback(Consumer<Rect> consumer) {
        this.mExcludeBackRegionCallback = consumer;
    }

    public void slideIn() {
        this.mRevealButton.slideIn();
    }

    public void setNavBarButtonState(boolean z) {
        if (this.mIsAttached && this.mShiftForTransientBar != 0) {
            if (z) {
                if (this.mRevealButton.getVisibility() != 0) {
                    float width = (float) (((getWidth() - this.mBar.getWidth()) - this.mBarMarginEnd) - this.mShiftForTransientBar);
                    if (!this.mIsDragging && this.mBar.getTranslationX() >= width) {
                        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mBar, FrameLayout.TRANSLATION_X, width);
                        ofFloat.setDuration(100L);
                        ofFloat.start();
                    }
                } else if (this.mRevealButton.isOnTheRight()) {
                    autoUndock((float) this.mShiftForTransientBar);
                }
            } else if (this.mRevealButton.getVisibility() != 0 && !this.mIsDragging) {
                snapBarBackIfNecessary();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateSystemGestureExcludeRects() {
        if (this.mExcludeBackRegionCallback != null) {
            if (this.mIsAttached) {
                getTouchableRegion();
            } else {
                this.mTmpRect.setEmpty();
            }
            this.mExcludeBackRegionCallback.accept(this.mTmpRect);
        }
    }

    public void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        getTouchableRegion();
        internalInsetsInfo.setTouchableInsets(3);
        internalInsetsInfo.touchableRegion.set(this.mTmpRect);
    }

    private void getTouchableRegion() {
        if (this.mRevealButton.getVisibility() == 0) {
            this.mRevealButton.getLocationInWindow(this.mTempInts);
        } else {
            this.mBar.getLocationInWindow(this.mTempInts);
        }
        Rect rect = this.mTmpRect;
        int[] iArr = this.mTempInts;
        rect.set(iArr[0], iArr[1], iArr[0] + getBarWidth(), this.mTempInts[1] + getBarHeight());
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIsAttached = true;
        getViewTreeObserver().addOnComputeInternalInsetsListener(this);
        getViewTreeObserver().addOnDrawListener(this.mSystemGestureExcludeUpdater);
        this.mConfigurationController.addCallback(this);
        int i = ((FrameLayout) this).mContext.getResources().getConfiguration().orientation;
        int i2 = this.mOrientation;
        if (i2 == -1) {
            this.mOrientation = i;
            return;
        }
        if (i2 != i) {
            onOrientationChanged(i);
        }
        if (this.mIsRecordVisible) {
            onScreenRecordButtonVisible();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mIsAttached = false;
        getViewTreeObserver().removeOnComputeInternalInsetsListener(this);
        getViewTreeObserver().removeOnDrawListener(this.mSystemGestureExcludeUpdater);
        this.mConfigurationController.removeCallback(this);
    }

    @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
    public void onCountdown(long j) {
        int floorDiv = (int) Math.floorDiv(j + 500, 1000);
        int i = R$drawable.ic_screen_record;
        if (floorDiv == 1) {
            i = R$drawable.game_dashboard_screen_record_countdown_1;
        } else if (floorDiv == 2) {
            i = R$drawable.game_dashboard_screen_record_countdown_2;
        } else if (floorDiv == 3) {
            i = R$drawable.game_dashboard_screen_record_countdown_3;
        }
        setScreenRecordButtonBackground(true);
        this.mRecordButton.setImageResource(i);
        this.mRecordButton.setContentDescription(((FrameLayout) this).mContext.getString(R$string.accessibility_game_dashboard_shortcut_bar_stop_recording));
    }

    @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
    public void onCountdownEnd() {
        setScreenRecordButtonDrawable();
        if (!this.mScreenRecordController.isRecording() && !this.mScreenRecordController.isStarting()) {
            this.mScreenshotButton.announceForAccessibility(((FrameLayout) this).mContext.getString(R$string.accessibility_game_dashboard_screen_record_cancelled));
            onScreenRecordStop();
        }
    }

    @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
    public void onRecordingEnd() {
        this.mScreenshotButton.announceForAccessibility(((FrameLayout) this).mContext.getString(R$string.accessibility_game_dashboard_screen_record_stopped));
        onScreenRecordStop();
    }

    private void handleScreenRecord() {
        if (!this.mScreenRecordController.isRecording() && !this.mScreenRecordController.isStarting()) {
            this.mScreenRecordController.getController().addCallback((RecordingController.RecordingStateChangeCallback) this);
            this.mUiEventLogger.log(GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_SCREEN_RECORD);
        }
        this.mScreenRecordController.handleClick();
    }

    private void onScreenRecordStop() {
        this.mScreenRecordController.getController().removeCallback((RecordingController.RecordingStateChangeCallback) this);
        setScreenRecordButtonBackground(false);
        setScreenRecordButtonDrawable();
        this.mRevealButton.setIsRecording(false);
        this.mRecordButton.setContentDescription(((FrameLayout) this).mContext.getString(R$string.accessibility_game_dashboard_shortcut_bar_start_recording));
    }

    private void setScreenRecordButtonBackground(boolean z) {
        int i;
        Drawable background = this.mRecordButton.getBackground();
        if (z) {
            i = ((FrameLayout) this).mContext.getColor(R$color.game_dashboard_screen_record_countdown);
        } else {
            i = SHORTCUT_BAR_BACKGROUND_COLOR;
        }
        background.setColorFilter(new PorterDuffColorFilter(i, PorterDuff.Mode.SRC));
        this.mRecordButton.setBackground(background);
    }

    private void setScreenRecordButtonDrawable() {
        Drawable mutate = ((FrameLayout) this).mContext.getDrawable(R$drawable.ic_screen_record).mutate();
        mutate.setColorFilter(new PorterDuffColorFilter(-1, PorterDuff.Mode.SRC_ATOP));
        this.mRecordButton.setImageDrawable(mutate);
    }

    public void setScreenshotVisibility(boolean z) {
        GameDashboardUiEventLogger.GameDashboardEvent gameDashboardEvent;
        GameDashboardUiEventLogger gameDashboardUiEventLogger = this.mUiEventLogger;
        if (z) {
            gameDashboardEvent = GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_MENU_ENABLE_SCREENSHOT;
        } else {
            gameDashboardEvent = GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_MENU_DISABLE_SCREENSHOT;
        }
        gameDashboardUiEventLogger.log(gameDashboardEvent);
        this.mIsScreenshotVisible = z;
        this.mScreenshotButton.setVisibility(z ? 0 : 8);
        if (this.mRevealButton.getVisibility() == 0 && this.mIsAttached) {
            this.mRevealButton.bounce(z);
        }
    }

    public boolean isScreenshotVisible() {
        return this.mIsScreenshotVisible;
    }

    private void onScreenRecordButtonVisible() {
        this.mScreenRecordController.getController().addCallback((RecordingController.RecordingStateChangeCallback) this);
        setScreenRecordButtonDrawable();
        boolean isRecording = this.mScreenRecordController.isRecording();
        setScreenRecordButtonBackground(isRecording);
        this.mRevealButton.setIsRecording(isRecording);
    }

    public void setRecordVisibility(boolean z) {
        GameDashboardUiEventLogger.GameDashboardEvent gameDashboardEvent;
        GameDashboardUiEventLogger gameDashboardUiEventLogger = this.mUiEventLogger;
        if (z) {
            gameDashboardEvent = GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_MENU_ENABLE_SCREEN_RECORD;
        } else {
            gameDashboardEvent = GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_MENU_DISABLE_SCREEN_RECORD;
        }
        gameDashboardUiEventLogger.log(gameDashboardEvent);
        this.mIsRecordVisible = z;
        this.mRecordButton.setVisibility(z ? 0 : 8);
        if (this.mRevealButton.getVisibility() == 0 && this.mIsAttached) {
            this.mRevealButton.bounce(z);
        }
        if (this.mIsRecordVisible) {
            onScreenRecordButtonVisible();
        }
    }

    public boolean isRecordVisible() {
        return this.mIsRecordVisible;
    }

    public void setFpsVisibility(boolean z) {
        GameDashboardUiEventLogger.GameDashboardEvent gameDashboardEvent;
        GameDashboardUiEventLogger gameDashboardUiEventLogger = this.mUiEventLogger;
        if (z) {
            gameDashboardEvent = GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_MENU_ENABLE_FPS;
        } else {
            gameDashboardEvent = GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_MENU_DISABLE_FPS;
        }
        gameDashboardUiEventLogger.log(gameDashboardEvent);
        this.mIsFpsVisible = z;
        this.mFpsView.setVisibility(z ? 0 : 8);
        if (this.mRevealButton.getVisibility() == 0 && this.mIsAttached) {
            this.mRevealButton.bounce(z);
        }
    }

    public boolean isFpsVisible() {
        return this.mIsFpsVisible;
    }

    public void setFps(float f) {
        this.mFpsView.setText(String.valueOf(Math.round(f)));
    }

    public void setEntryPointVisibility(boolean z) {
        this.mIsEntryPointVisible = z;
        this.mEntryPointButton.setVisibility(z ? 0 : 8);
    }

    public void setEntryPointOnTouchListener(View.OnClickListener onClickListener) {
        this.mEntryPointButton.setOnTouchListener(this.mOnTouchListener);
        this.mEntryPointButton.setOnClickListener(onClickListener);
    }

    public boolean shouldBeVisible() {
        return this.mIsScreenshotVisible || this.mIsRecordVisible || this.mIsFpsVisible || this.mIsEntryPointVisible;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void snapBarBackIfNecessary() {
        if (this.mRevealButton.getVisibility() != 0) {
            snapBarBack(this.mBar.getTranslationX() + (((float) this.mBar.getWidth()) / 2.0f) > ((float) getWidth()) / 2.0f);
        }
    }

    private void snapBarBack(boolean z) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mBar, FrameLayout.TRANSLATION_X, z ? getBarMovementBoundsRight() : getBarMovementBoundsLeft());
        Animator snapBarBackVertically = snapBarBackVertically();
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, snapBarBackVertically);
        animatorSet.setInterpolator(new FastOutSlowInInterpolator());
        animatorSet.setDuration(100L);
        animatorSet.start();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private Animator snapBarBackVertically() {
        float min = Math.min(getBarMovementBoundsBottom(), Math.max(getBarMovementBoundsTop(), this.mBar.getTranslationY()));
        return ObjectAnimator.ofFloat(this.mBar, FrameLayout.TRANSLATION_Y, min);
    }

    public void autoUndock() {
        autoUndock(0.0f);
    }

    private void autoUndock(float f) {
        if (this.mRevealButton.getVisibility() == 0) {
            unDock(this.mRevealButton.getTranslationX() > 0.0f, f);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void dock(final boolean z) {
        int i;
        if (!this.mIsDockingAnimationRunning) {
            this.mOnTouchListener.cancelAnimation();
            this.mRevealButtonOnTouchListener.cancelAnimation();
            this.mRevealButton.setTranslationX(this.mBar.getTranslationX());
            float translationY = this.mBar.getTranslationY() + ((float) (this.mBar.getHeight() / 2));
            RevealButton revealButton = this.mRevealButton;
            revealButton.setTranslationY(translationY - ((float) (revealButton.getHeight() / 2)));
            IntProperty<RevealButton> intProperty = RevealButton.BACKGROUND_WIDTH;
            intProperty.set(this.mRevealButton, Integer.valueOf(this.mBar.getWidth()));
            IntProperty<RevealButton> intProperty2 = RevealButton.BACKGROUND_HEIGHT;
            intProperty2.set(this.mRevealButton, Integer.valueOf(this.mBar.getHeight()));
            if (z) {
                i = getWidth() - this.mRevealButtonIconWidth;
            } else {
                i = -this.mRevealButtonIconWidth;
            }
            final float f = (float) i;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mBar, FrameLayout.TRANSLATION_X, f);
            ObjectAnimator ofInt = ObjectAnimator.ofInt(this.mRevealButton, intProperty, this.mRevealButtonIconWidth * 2);
            ObjectAnimator ofInt2 = ObjectAnimator.ofInt(this.mRevealButton, intProperty2, this.mRevealButtonIconHeight);
            ObjectAnimator ofInt3 = ObjectAnimator.ofInt(this.mRevealButton, RevealButton.ICON_ALPHA, 0, 255);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mRevealButton, FrameLayout.TRANSLATION_X, f);
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.mBar, FrameLayout.ALPHA, 1.0f, 0.0f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(ofFloat, ofInt, ofInt2, ofInt3, ofFloat2, ofFloat3);
            animatorSet.setInterpolator(new FastOutSlowInInterpolator());
            animatorSet.setDuration(200L);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                /* class com.google.android.systemui.gamedashboard.ShortcutBarView.AnonymousClass5 */

                public void onAnimationStart(Animator animator) {
                    ShortcutBarView.this.mIsDockingAnimationRunning = true;
                    ShortcutBarView.this.mRevealButton.setVisibility(0);
                    ShortcutBarView.this.mBar.setUseClearBackground(true);
                }

                public void onAnimationEnd(Animator animator) {
                    ShortcutBarView.this.mRevealButton.setSide(z);
                    ShortcutBarView.this.mRevealButton.setTranslationX(f);
                    RevealButton.ICON_ALPHA.set(ShortcutBarView.this.mRevealButton, (Integer) 255);
                    ShortcutBarView.this.mBar.setVisibility(4);
                    ShortcutBarView.this.mBar.setUseClearBackground(false);
                    ShortcutBarView.this.mIsDockingAnimationRunning = false;
                }
            });
            animatorSet.start();
        }
    }

    private void unDock(boolean z, float f) {
        final float f2;
        if (!this.mIsDockingAnimationRunning) {
            this.mOnTouchListener.cancelAnimation();
            this.mRevealButtonOnTouchListener.cancelAnimation();
            this.mBar.setTranslationX(this.mRevealButton.getTranslationX());
            float translationY = this.mRevealButton.getTranslationY() + ((float) (this.mRevealButton.getHeight() / 2));
            ShortcutBarContainer shortcutBarContainer = this.mBar;
            shortcutBarContainer.setTranslationY(translationY - ((float) (shortcutBarContainer.getHeight() / 2)));
            if (z) {
                f2 = ((float) ((getWidth() - this.mBar.getWidth()) - this.mBarMarginEnd)) - f;
            } else {
                f2 = ((float) this.mBarMarginEnd) + f;
            }
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mRevealButton, FrameLayout.TRANSLATION_X, f2);
            ObjectAnimator ofInt = ObjectAnimator.ofInt(this.mRevealButton, RevealButton.BACKGROUND_WIDTH, this.mBar.getWidth());
            ObjectAnimator ofInt2 = ObjectAnimator.ofInt(this.mRevealButton, RevealButton.BACKGROUND_HEIGHT, this.mBar.getHeight());
            ObjectAnimator ofInt3 = ObjectAnimator.ofInt(this.mRevealButton, RevealButton.ICON_ALPHA, 255, 0);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mBar, FrameLayout.TRANSLATION_X, f2);
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.mBar, FrameLayout.ALPHA, 0.0f, 1.0f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(ofFloat, ofInt, ofInt2, ofInt3, ofFloat2, ofFloat3);
            animatorSet.setInterpolator(new FastOutSlowInInterpolator());
            animatorSet.setDuration(200L);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                /* class com.google.android.systemui.gamedashboard.ShortcutBarView.AnonymousClass6 */

                public void onAnimationStart(Animator animator) {
                    ShortcutBarView.this.mIsDockingAnimationRunning = true;
                    ShortcutBarView.this.mBar.setVisibility(0);
                    ShortcutBarView.this.mBar.setUseClearBackground(true);
                }

                public void onAnimationEnd(Animator animator) {
                    ShortcutBarView.this.mRevealButton.setVisibility(4);
                    RevealButton.BACKGROUND_WIDTH.set(ShortcutBarView.this.mRevealButton, Integer.valueOf(ShortcutBarView.this.mRevealButtonIconWidth * 2));
                    RevealButton.BACKGROUND_HEIGHT.set(ShortcutBarView.this.mRevealButton, Integer.valueOf(ShortcutBarView.this.mRevealButtonIconHeight));
                    RevealButton.ICON_ALPHA.set(ShortcutBarView.this.mRevealButton, (Integer) 255);
                    ShortcutBarView.this.mBar.setUseClearBackground(false);
                    ShortcutBarView.this.mBar.setTranslationX(f2);
                    ShortcutBarView.this.snapBarBackVertically().start();
                    ShortcutBarView.this.mIsDockingAnimationRunning = false;
                }
            });
            animatorSet.start();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private float getViewTranslationX() {
        if (this.mRevealButton.getVisibility() == 0) {
            return this.mRevealButton.getTranslationX();
        }
        return this.mBar.getTranslationX();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private float getViewTranslationY() {
        if (this.mRevealButton.getVisibility() == 0) {
            return this.mRevealButton.getTranslationY();
        }
        return this.mBar.getTranslationY();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getBarWidth() {
        if (this.mRevealButton.getVisibility() == 0) {
            return this.mRevealButton.getWidth();
        }
        return this.mBar.getWidth();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getBarHeight() {
        if (this.mRevealButton.getVisibility() == 0) {
            return this.mRevealButton.getHeight();
        }
        return this.mBar.getHeight();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setTranslation(float f, float f2) {
        if (this.mRevealButton.getVisibility() == 0) {
            this.mRevealButton.setTranslationX(f);
            this.mRevealButton.setTranslationY(f2);
            return;
        }
        this.mBar.setTranslationX(f);
        this.mBar.setTranslationY(f2);
    }
}
