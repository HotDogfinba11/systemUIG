package com.google.android.systemui.keyguard;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.builders.ListBuilder;
import com.android.systemui.R$dimen;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.google.android.systemui.smartspace.SmartSpaceCard;
import com.google.android.systemui.smartspace.SmartSpaceController;
import com.google.android.systemui.smartspace.SmartSpaceData;
import com.google.android.systemui.smartspace.SmartSpaceUpdateListener;
import java.lang.ref.WeakReference;

public class KeyguardSliceProviderGoogle extends KeyguardSliceProvider implements SmartSpaceUpdateListener {
    private static final boolean DEBUG = Log.isLoggable("KeyguardSliceProvider", 3);
    private final Uri mCalendarUri = Uri.parse("content://com.android.systemui.keyguard/smartSpace/calendar");
    private boolean mHideSensitiveContent;
    private boolean mHideWorkContent = true;
    public SmartSpaceController mSmartSpaceController;
    private SmartSpaceData mSmartSpaceData;
    private final Uri mWeatherUri = Uri.parse("content://com.android.systemui.keyguard/smartSpace/weather");

    @Override // androidx.slice.SliceProvider, com.android.systemui.keyguard.KeyguardSliceProvider
    public boolean onCreateSliceProvider() {
        boolean onCreateSliceProvider = super.onCreateSliceProvider();
        this.mSmartSpaceData = new SmartSpaceData();
        this.mSmartSpaceController.addListener(this);
        return onCreateSliceProvider;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.keyguard.KeyguardSliceProvider
    public void onDestroy() {
        super.onDestroy();
        this.mSmartSpaceController.removeListener(this);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00e3, code lost:
        r6 = r7.build();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00e9, code lost:
        if (com.google.android.systemui.keyguard.KeyguardSliceProviderGoogle.DEBUG == false) goto L_0x0101;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00eb, code lost:
        android.util.Log.d("KeyguardSliceProvider", "Binding slice: " + r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0101, code lost:
        android.os.Trace.endSection();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0104, code lost:
        return r6;
     */
    @Override // androidx.slice.SliceProvider, com.android.systemui.keyguard.KeyguardSliceProvider
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public androidx.slice.Slice onBindSlice(android.net.Uri r7) {
        /*
        // Method dump skipped, instructions count: 264
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.keyguard.KeyguardSliceProviderGoogle.onBindSlice(android.net.Uri):androidx.slice.Slice");
    }

    private void addWeather(ListBuilder listBuilder) {
        SmartSpaceCard weatherCard = this.mSmartSpaceData.getWeatherCard();
        if (weatherCard != null && !weatherCard.isExpired()) {
            ListBuilder.RowBuilder title = new ListBuilder.RowBuilder(this.mWeatherUri).setTitle(weatherCard.getTitle());
            Bitmap icon = weatherCard.getIcon();
            if (icon != null) {
                IconCompat createWithBitmap = IconCompat.createWithBitmap(icon);
                createWithBitmap.setTintMode(PorterDuff.Mode.DST);
                title.addEndItem(createWithBitmap, 1);
            }
            listBuilder.addRow(title);
        }
    }

    @Override // com.google.android.systemui.smartspace.SmartSpaceUpdateListener
    public void onSensitiveModeChanged(boolean z, boolean z2) {
        boolean z3;
        boolean z4;
        synchronized (this) {
            z3 = true;
            if (this.mHideSensitiveContent != z) {
                this.mHideSensitiveContent = z;
                if (DEBUG) {
                    Log.d("KeyguardSliceProvider", "Public mode changed, hide data: " + z);
                }
                z4 = true;
            } else {
                z4 = false;
            }
            if (this.mHideWorkContent != z2) {
                this.mHideWorkContent = z2;
                if (DEBUG) {
                    Log.d("KeyguardSliceProvider", "Public work mode changed, hide data: " + z2);
                }
            } else {
                z3 = z4;
            }
        }
        if (z3) {
            notifyChange();
        }
    }

    @Override // com.google.android.systemui.smartspace.SmartSpaceUpdateListener
    public void onSmartSpaceUpdated(SmartSpaceData smartSpaceData) {
        synchronized (this) {
            this.mSmartSpaceData = smartSpaceData;
        }
        SmartSpaceCard weatherCard = smartSpaceData.getWeatherCard();
        if (weatherCard == null || weatherCard.getIcon() == null || weatherCard.isIconProcessed()) {
            notifyChange();
            return;
        }
        weatherCard.setIconProcessed(true);
        new AddShadowTask(this, weatherCard).execute(weatherCard.getIcon());
    }

    /* access modifiers changed from: protected */
    @Override // com.android.systemui.keyguard.KeyguardSliceProvider
    public void updateClockLocked() {
        notifyChange();
    }

    private static class AddShadowTask extends AsyncTask<Bitmap, Void, Bitmap> {
        private final float mBlurRadius;
        private final WeakReference<KeyguardSliceProviderGoogle> mProviderReference;
        private final SmartSpaceCard mWeatherCard;

        AddShadowTask(KeyguardSliceProviderGoogle keyguardSliceProviderGoogle, SmartSpaceCard smartSpaceCard) {
            this.mProviderReference = new WeakReference<>(keyguardSliceProviderGoogle);
            this.mWeatherCard = smartSpaceCard;
            this.mBlurRadius = keyguardSliceProviderGoogle.getContext().getResources().getDimension(R$dimen.smartspace_icon_shadow);
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(Bitmap... bitmapArr) {
            return applyShadow(bitmapArr[0]);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap bitmap) {
            KeyguardSliceProviderGoogle keyguardSliceProviderGoogle;
            synchronized (this) {
                this.mWeatherCard.setIcon(bitmap);
                keyguardSliceProviderGoogle = this.mProviderReference.get();
            }
            if (keyguardSliceProviderGoogle != null) {
                keyguardSliceProviderGoogle.notifyChange();
            }
        }

        private Bitmap applyShadow(Bitmap bitmap) {
            BlurMaskFilter blurMaskFilter = new BlurMaskFilter(this.mBlurRadius, BlurMaskFilter.Blur.NORMAL);
            Paint paint = new Paint();
            paint.setMaskFilter(blurMaskFilter);
            int[] iArr = new int[2];
            Bitmap extractAlpha = bitmap.extractAlpha(paint, iArr);
            Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Paint paint2 = new Paint();
            paint2.setAlpha(70);
            canvas.drawBitmap(extractAlpha, (float) iArr[0], ((float) iArr[1]) + (this.mBlurRadius / 2.0f), paint2);
            extractAlpha.recycle();
            paint2.setAlpha(255);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint2);
            return createBitmap;
        }
    }
}
