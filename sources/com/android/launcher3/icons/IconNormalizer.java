package com.android.launcher3.icons;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import java.nio.ByteBuffer;

public class IconNormalizer {
    private final RectF mAdaptiveIconBounds = new RectF();
    private float mAdaptiveIconScale;
    private final Bitmap mBitmap;
    private final Rect mBounds = new Rect();
    private final Canvas mCanvas;
    private boolean mEnableShapeDetection;
    private final float[] mLeftBorder;
    private final Matrix mMatrix;
    private final int mMaxSize;
    private final Paint mPaintMaskShape;
    private final Paint mPaintMaskShapeOutline;
    private final byte[] mPixels;
    private final float[] mRightBorder;
    private final Path mShapePath;

    IconNormalizer(Context context, int i, boolean z) {
        int i2 = i * 2;
        this.mMaxSize = i2;
        Bitmap createBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ALPHA_8);
        this.mBitmap = createBitmap;
        this.mCanvas = new Canvas(createBitmap);
        this.mPixels = new byte[(i2 * i2)];
        this.mLeftBorder = new float[i2];
        this.mRightBorder = new float[i2];
        Paint paint = new Paint();
        this.mPaintMaskShape = paint;
        paint.setColor(-65536);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        Paint paint2 = new Paint();
        this.mPaintMaskShapeOutline = paint2;
        paint2.setStrokeWidth(context.getResources().getDisplayMetrics().density * 2.0f);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(-16777216);
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.mShapePath = new Path();
        this.mMatrix = new Matrix();
        this.mAdaptiveIconScale = 0.0f;
        this.mEnableShapeDetection = z;
    }

    private static float getScale(float f, float f2, float f3) {
        float f4 = f / f2;
        float f5 = f4 < 0.7853982f ? 0.6597222f : ((1.0f - f4) * 0.040449437f) + 0.6510417f;
        float f6 = f / f3;
        if (f6 > f5) {
            return (float) Math.sqrt((double) (f5 / f6));
        }
        return 1.0f;
    }

    @TargetApi(26)
    public static float normalizeAdaptiveIcon(Drawable drawable, int i, RectF rectF) {
        Rect rect = new Rect(drawable.getBounds());
        drawable.setBounds(0, 0, i, i);
        Path iconMask = ((AdaptiveIconDrawable) drawable).getIconMask();
        Region region = new Region();
        region.setPath(iconMask, new Region(0, 0, i, i));
        Rect bounds = region.getBounds();
        int area = GraphicsUtils.getArea(region);
        if (rectF != null) {
            float f = (float) i;
            rectF.set(((float) bounds.left) / f, ((float) bounds.top) / f, 1.0f - (((float) bounds.right) / f), 1.0f - (((float) bounds.bottom) / f));
        }
        drawable.setBounds(rect);
        float f2 = (float) area;
        return getScale(f2, f2, (float) (i * i));
    }

    private boolean isShape(Path path) {
        if (Math.abs((((float) this.mBounds.width()) / ((float) this.mBounds.height())) - 1.0f) > 0.05f) {
            return false;
        }
        this.mMatrix.reset();
        this.mMatrix.setScale((float) this.mBounds.width(), (float) this.mBounds.height());
        Matrix matrix = this.mMatrix;
        Rect rect = this.mBounds;
        matrix.postTranslate((float) rect.left, (float) rect.top);
        path.transform(this.mMatrix, this.mShapePath);
        this.mCanvas.drawPath(this.mShapePath, this.mPaintMaskShape);
        this.mCanvas.drawPath(this.mShapePath, this.mPaintMaskShapeOutline);
        return isTransparentBitmap();
    }

    private boolean isTransparentBitmap() {
        Rect rect;
        ByteBuffer wrap = ByteBuffer.wrap(this.mPixels);
        wrap.rewind();
        this.mBitmap.copyPixelsToBuffer(wrap);
        Rect rect2 = this.mBounds;
        int i = rect2.top;
        int i2 = this.mMaxSize;
        int i3 = i * i2;
        int i4 = i2 - rect2.right;
        int i5 = 0;
        while (true) {
            rect = this.mBounds;
            if (i >= rect.bottom) {
                break;
            }
            int i6 = rect.left;
            int i7 = i3 + i6;
            while (i6 < this.mBounds.right) {
                if ((this.mPixels[i7] & 255) > 40) {
                    i5++;
                }
                i7++;
                i6++;
            }
            i3 = i7 + i4;
            i++;
        }
        if (((float) i5) / ((float) (rect.width() * this.mBounds.height())) < 0.005f) {
            return true;
        }
        return false;
    }

    public synchronized float getScale(Drawable drawable, RectF rectF, Path path, boolean[] zArr) {
        if (!BaseIconFactory.ATLEAST_OREO || !(drawable instanceof AdaptiveIconDrawable)) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
                if (intrinsicWidth <= 0 || intrinsicWidth > this.mMaxSize) {
                    intrinsicWidth = this.mMaxSize;
                }
                if (intrinsicHeight <= 0 || intrinsicHeight > this.mMaxSize) {
                    intrinsicHeight = this.mMaxSize;
                }
            } else {
                int i = this.mMaxSize;
                if (intrinsicWidth > i || intrinsicHeight > i) {
                    int max = Math.max(intrinsicWidth, intrinsicHeight);
                    int i2 = this.mMaxSize;
                    intrinsicWidth = (intrinsicWidth * i2) / max;
                    intrinsicHeight = (i2 * intrinsicHeight) / max;
                }
            }
            int i3 = 0;
            this.mBitmap.eraseColor(0);
            drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
            drawable.draw(this.mCanvas);
            ByteBuffer wrap = ByteBuffer.wrap(this.mPixels);
            wrap.rewind();
            this.mBitmap.copyPixelsToBuffer(wrap);
            int i4 = this.mMaxSize;
            int i5 = i4 + 1;
            int i6 = i4 - intrinsicWidth;
            int i7 = 0;
            int i8 = 0;
            int i9 = -1;
            int i10 = -1;
            int i11 = -1;
            while (i7 < intrinsicHeight) {
                int i12 = -1;
                int i13 = -1;
                for (int i14 = i3; i14 < intrinsicWidth; i14++) {
                    if ((this.mPixels[i8] & 255) > 40) {
                        if (i12 == -1) {
                            i12 = i14;
                        }
                        i13 = i14;
                    }
                    i8++;
                }
                i8 += i6;
                this.mLeftBorder[i7] = (float) i12;
                this.mRightBorder[i7] = (float) i13;
                if (i12 != -1) {
                    if (i9 == -1) {
                        i9 = i7;
                    }
                    int min = Math.min(i5, i12);
                    i10 = Math.max(i10, i13);
                    i5 = min;
                    i11 = i7;
                }
                i7++;
                i3 = 0;
            }
            if (i9 == -1 || i10 == -1) {
                return 1.0f;
            }
            convertToConvexArray(this.mLeftBorder, 1, i9, i11);
            convertToConvexArray(this.mRightBorder, -1, i9, i11);
            float f = 0.0f;
            for (int i15 = 0; i15 < intrinsicHeight; i15++) {
                float[] fArr = this.mLeftBorder;
                if (fArr[i15] > -1.0f) {
                    f += (this.mRightBorder[i15] - fArr[i15]) + 1.0f;
                }
            }
            Rect rect = this.mBounds;
            rect.left = i5;
            rect.right = i10;
            rect.top = i9;
            rect.bottom = i11;
            if (rectF != null) {
                float f2 = (float) intrinsicWidth;
                float f3 = (float) intrinsicHeight;
                rectF.set(((float) i5) / f2, ((float) i9) / f3, 1.0f - (((float) i10) / f2), 1.0f - (((float) i11) / f3));
            }
            if (zArr != null && this.mEnableShapeDetection && zArr.length > 0) {
                zArr[0] = isShape(path);
            }
            return getScale(f, (float) (((i11 + 1) - i9) * ((i10 + 1) - i5)), (float) (intrinsicWidth * intrinsicHeight));
        }
        if (this.mAdaptiveIconScale == 0.0f) {
            this.mAdaptiveIconScale = normalizeAdaptiveIcon(drawable, this.mMaxSize, this.mAdaptiveIconBounds);
        }
        if (rectF != null) {
            rectF.set(this.mAdaptiveIconBounds);
        }
        return this.mAdaptiveIconScale;
    }

    private static void convertToConvexArray(float[] fArr, int i, int i2, int i3) {
        float[] fArr2 = new float[(fArr.length - 1)];
        int i4 = -1;
        float f = Float.MAX_VALUE;
        for (int i5 = i2 + 1; i5 <= i3; i5++) {
            if (fArr[i5] > -1.0f) {
                if (f == Float.MAX_VALUE) {
                    i4 = i2;
                } else {
                    float f2 = ((fArr[i5] - fArr[i4]) / ((float) (i5 - i4))) - f;
                    float f3 = (float) i;
                    if (f2 * f3 < 0.0f) {
                        while (i4 > i2) {
                            i4--;
                            if ((((fArr[i5] - fArr[i4]) / ((float) (i5 - i4))) - fArr2[i4]) * f3 >= 0.0f) {
                                break;
                            }
                        }
                    }
                }
                f = (fArr[i5] - fArr[i4]) / ((float) (i5 - i4));
                for (int i6 = i4; i6 < i5; i6++) {
                    fArr2[i6] = f;
                    fArr[i6] = fArr[i4] + (((float) (i6 - i4)) * f);
                }
                i4 = i5;
            }
        }
    }

    public static int getNormalizedCircleSize(int i) {
        return (int) Math.round(Math.sqrt(((double) ((((float) (i * i)) * 0.6597222f) * 4.0f)) / 3.141592653589793d));
    }
}
