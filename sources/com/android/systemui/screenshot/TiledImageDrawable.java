package com.android.systemui.screenshot;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class TiledImageDrawable extends Drawable {
    private RenderNode mNode;
    private final ImageTileSet mTiles;

    public int getOpacity() {
        return -1;
    }

    public TiledImageDrawable(ImageTileSet imageTileSet) {
        this.mTiles = imageTileSet;
        imageTileSet.addOnContentChangedListener(new TiledImageDrawable$$ExternalSyntheticLambda0(this));
    }

    /* access modifiers changed from: private */
    public void onContentChanged() {
        RenderNode renderNode = this.mNode;
        if (renderNode != null && renderNode.hasDisplayList()) {
            this.mNode.discardDisplayList();
        }
        invalidateSelf();
    }

    private void rebuildDisplayListIfNeeded() {
        RenderNode renderNode = this.mNode;
        if (renderNode == null || !renderNode.hasDisplayList()) {
            if (this.mNode == null) {
                this.mNode = new RenderNode("TiledImageDrawable");
            }
            this.mNode.setPosition(0, 0, this.mTiles.getWidth(), this.mTiles.getHeight());
            RecordingCanvas beginRecording = this.mNode.beginRecording();
            beginRecording.translate((float) (-this.mTiles.getLeft()), (float) (-this.mTiles.getTop()));
            for (int i = 0; i < this.mTiles.size(); i++) {
                ImageTile imageTile = this.mTiles.get(i);
                beginRecording.save();
                beginRecording.translate((float) imageTile.getLeft(), (float) imageTile.getTop());
                beginRecording.drawRenderNode(imageTile.getDisplayList());
                beginRecording.restore();
            }
            this.mNode.endRecording();
        }
    }

    public void draw(Canvas canvas) {
        rebuildDisplayListIfNeeded();
        if (canvas.isHardwareAccelerated()) {
            Rect bounds = getBounds();
            canvas.save();
            canvas.clipRect(0, 0, bounds.width(), bounds.height());
            canvas.translate((float) (-bounds.left), (float) (-bounds.top));
            canvas.drawRenderNode(this.mNode);
            canvas.restore();
            return;
        }
        Log.d("TiledImageDrawable", "Canvas is not hardware accelerated. Skipping draw!");
    }

    public int getIntrinsicWidth() {
        return this.mTiles.getWidth();
    }

    public int getIntrinsicHeight() {
        return this.mTiles.getHeight();
    }

    public void setAlpha(int i) {
        if (this.mNode.setAlpha(((float) i) / 255.0f)) {
            invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        throw new IllegalArgumentException("not implemented");
    }
}
