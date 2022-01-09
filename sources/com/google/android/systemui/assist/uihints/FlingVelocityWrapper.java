package com.google.android.systemui.assist.uihints;

import java.util.Optional;

/* access modifiers changed from: package-private */
public class FlingVelocityWrapper {
    private boolean mGuarded = true;
    private float mVelocity;

    FlingVelocityWrapper() {
    }

    /* access modifiers changed from: package-private */
    public void setVelocity(float f) {
        this.mVelocity = f;
        this.mGuarded = false;
    }

    /* access modifiers changed from: package-private */
    public float getVelocity() {
        return this.mVelocity;
    }

    /* access modifiers changed from: package-private */
    public Optional<Float> consumeVelocity() {
        if (this.mGuarded) {
            return Optional.empty();
        }
        this.mGuarded = true;
        return Optional.of(Float.valueOf(this.mVelocity));
    }
}
