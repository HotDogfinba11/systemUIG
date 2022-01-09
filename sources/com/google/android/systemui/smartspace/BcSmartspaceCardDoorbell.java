package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Size;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import com.android.launcher3.icons.RoundDrawableWrapper;
import com.android.systemui.bcsmartspace.R$dimen;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BcSmartspaceCardDoorbell extends BcSmartspaceCardGenericImage {
    private int mGifFrameDurationInMs = 200;
    private final Map<Uri, DrawableWithUri> mUriToDrawable = new HashMap();

    public BcSmartspaceCardDoorbell(Context context) {
        super(context);
    }

    public BcSmartspaceCardDoorbell(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardGenericImage, com.google.android.systemui.smartspace.BcSmartspaceCardSecondary
    public boolean setSmartspaceActions(SmartspaceTarget smartspaceTarget, BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier, BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo) {
        Bundle bundle;
        if (!isSysUiContext()) {
            return false;
        }
        SmartspaceAction baseAction = smartspaceTarget.getBaseAction();
        if (baseAction == null) {
            bundle = null;
        } else {
            bundle = baseAction.getExtras();
        }
        List<Uri> imageUris = getImageUris(smartspaceTarget);
        if (!imageUris.isEmpty()) {
            if (bundle != null && bundle.containsKey("frameDurationMs")) {
                this.mGifFrameDurationInMs = bundle.getInt("frameDurationMs");
            }
            loadImageUris(imageUris);
            Log.d("BcSmartspaceCardBell", "imageUri is set");
            return true;
        } else if (bundle == null || !bundle.containsKey("imageBitmap")) {
            return false;
        } else {
            setRoundedBitmapDrawable((Bitmap) bundle.get("imageBitmap"));
            Log.d("BcSmartspaceCardBell", "imageBitmap is set");
            return true;
        }
    }

    private void setRoundedBitmapDrawable(Bitmap bitmap) {
        if (bitmap.getHeight() != 0) {
            int dimension = (int) getResources().getDimension(R$dimen.enhanced_smartspace_height);
            bitmap = Bitmap.createScaledBitmap(bitmap, (int) (((float) dimension) * (((float) bitmap.getWidth()) / ((float) bitmap.getHeight()))), dimension, true);
        }
        RoundedBitmapDrawable create = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        create.setCornerRadius(getResources().getDimension(R$dimen.enhanced_smartspace_secondary_card_corner_radius));
        this.mImageView.setImageDrawable(create);
    }

    private void loadImageUris(List<Uri> list) {
        addFramesToAnimatedDrawable((List) list.stream().map(new BcSmartspaceCardDoorbell$$ExternalSyntheticLambda2(this, getContext().getApplicationContext().getContentResolver(), getResources().getDimensionPixelOffset(R$dimen.enhanced_smartspace_height), getResources().getDimension(R$dimen.enhanced_smartspace_secondary_card_corner_radius))).filter(BcSmartspaceCardDoorbell$$ExternalSyntheticLambda6.INSTANCE).collect(Collectors.toList()));
    }

    /* access modifiers changed from: public */
    private /* synthetic */ DrawableWithUri lambda$loadImageUris$1(ContentResolver contentResolver, int i, float f, Uri uri) {
        return this.mUriToDrawable.computeIfAbsent(uri, new BcSmartspaceCardDoorbell$$ExternalSyntheticLambda1(contentResolver, i, f));
    }

    public static /* synthetic */ DrawableWithUri lambda$loadImageUris$0(ContentResolver contentResolver, int i, float f, Uri uri) {
        DrawableWithUri drawableWithUri = new DrawableWithUri(uri, contentResolver, i, f);
        new LoadUriTask().execute(drawableWithUri);
        return drawableWithUri;
    }

    private void addFramesToAnimatedDrawable(List<Drawable> list) {
        AnimationDrawable animationDrawable = new AnimationDrawable();
        for (Drawable drawable : list) {
            animationDrawable.addFrame(drawable, this.mGifFrameDurationInMs);
        }
        this.mImageView.setImageDrawable(animationDrawable);
        animationDrawable.start();
    }

    private List<Uri> getImageUris(SmartspaceTarget smartspaceTarget) {
        return (List) smartspaceTarget.getIconGrid().stream().filter(BcSmartspaceCardDoorbell$$ExternalSyntheticLambda5.INSTANCE).map(BcSmartspaceCardDoorbell$$ExternalSyntheticLambda3.INSTANCE).map(BcSmartspaceCardDoorbell$$ExternalSyntheticLambda4.INSTANCE).collect(Collectors.toList());
    }

    public static /* synthetic */ boolean lambda$getImageUris$2(SmartspaceAction smartspaceAction) {
        return smartspaceAction.getExtras().containsKey("imageUri");
    }

    public static /* synthetic */ String lambda$getImageUris$3(SmartspaceAction smartspaceAction) {
        return smartspaceAction.getExtras().getString("imageUri");
    }

    private boolean isSysUiContext() {
        return getContext().getPackageName().equals("com.android.systemui");
    }

    public static class DrawableWithUri extends RoundDrawableWrapper {
        ContentResolver mContentResolver;
        Drawable mDrawable;
        int mHeightInPx;
        Uri mUri;

        DrawableWithUri(Uri uri, ContentResolver contentResolver, int i, float f) {
            super(new ColorDrawable(-16777216), f);
            this.mUri = uri;
            this.mHeightInPx = i;
            this.mContentResolver = contentResolver;
        }
    }

    @Override // com.google.android.systemui.smartspace.BcSmartspaceCardGenericImage
    public void onFinishInflate() {
        super.onFinishInflate();
    }

    public static Drawable getSampleBitmapDrawable(InputStream inputStream, int i) {
        try {
            return ImageDecoder.decodeDrawable(ImageDecoder.createSource((Resources) null, inputStream), new BcSmartspaceCardDoorbell$$ExternalSyntheticLambda0(i));
        } catch (IOException e) {
            Log.e("BcSmartspaceCardBell", "Unable to decode stream: " + e);
            return null;
        }
    }

    public static /* synthetic */ void lambda$getSampleBitmapDrawable$4(int i, ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        imageDecoder.setAllocator(3);
        imageDecoder.setTargetSize((int) (((float) i) * getTargetRatio(imageInfo)), i);
    }

    private static float getTargetRatio(ImageDecoder.ImageInfo imageInfo) {
        Size size = imageInfo.getSize();
        if (size.getHeight() != 0) {
            return ((float) size.getWidth()) / ((float) size.getHeight());
        }
        return 0.0f;
    }

    public static class LoadUriTask extends AsyncTask<DrawableWithUri, Void, DrawableWithUri> {
        private LoadUriTask() {
        }

        public DrawableWithUri doInBackground(DrawableWithUri... drawableWithUriArr) {
            if (drawableWithUriArr.length <= 0) {
                return null;
            }
            DrawableWithUri drawableWithUri = drawableWithUriArr[0];
            try {
                drawableWithUri.mDrawable = BcSmartspaceCardDoorbell.getSampleBitmapDrawable(drawableWithUri.mContentResolver.openInputStream(drawableWithUri.mUri), drawableWithUri.mHeightInPx);
            } catch (Exception e) {
                Log.w("BcSmartspaceCardBell", "open uri:" + drawableWithUri.mUri + " got exception:" + e);
            }
            return drawableWithUri;
        }

        public void onPostExecute(DrawableWithUri drawableWithUri) {
            Drawable drawable;
            if (drawableWithUri != null && (drawable = drawableWithUri.mDrawable) != null) {
                drawableWithUri.setDrawable(drawable);
            }
        }
    }
}
