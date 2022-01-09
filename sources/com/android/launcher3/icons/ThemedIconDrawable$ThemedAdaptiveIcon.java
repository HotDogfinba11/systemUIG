package com.android.launcher3.icons;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.os.Process;
import android.os.UserHandle;
import com.android.launcher3.icons.BitmapInfo;

/* access modifiers changed from: package-private */
public class ThemedIconDrawable$ThemedAdaptiveIcon extends AdaptiveIconDrawable implements BitmapInfo.Extender {
    protected final ThemedIconDrawable$ThemeData mThemeData;

    public ThemedIconDrawable$ThemedAdaptiveIcon(AdaptiveIconDrawable adaptiveIconDrawable, ThemedIconDrawable$ThemeData themedIconDrawable$ThemeData) {
        super(adaptiveIconDrawable.getBackground(), adaptiveIconDrawable.getForeground());
        this.mThemeData = themedIconDrawable$ThemeData;
    }

    @Override // com.android.launcher3.icons.BitmapInfo.Extender
    public BitmapInfo getExtendedInfo(Bitmap bitmap, int i, BaseIconFactory baseIconFactory, float f, UserHandle userHandle) {
        Bitmap bitmap2;
        if (Process.myUserHandle().equals(userHandle)) {
            bitmap2 = null;
        } else {
            bitmap2 = baseIconFactory.getUserBadgeBitmap(userHandle);
        }
        return new ThemedIconDrawable$ThemedBitmapInfo(bitmap, i, this.mThemeData, f, bitmap2);
    }

    @Override // com.android.launcher3.icons.BitmapInfo.Extender
    public void drawForPersistence(Canvas canvas) {
        draw(canvas);
    }
}
