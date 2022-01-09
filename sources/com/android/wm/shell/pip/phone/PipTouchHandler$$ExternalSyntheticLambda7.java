package com.android.wm.shell.pip.phone;

import android.graphics.Rect;
import java.util.function.Function;

public final /* synthetic */ class PipTouchHandler$$ExternalSyntheticLambda7 implements Function {
    public final /* synthetic */ PipTouchHandler f$0;

    public /* synthetic */ PipTouchHandler$$ExternalSyntheticLambda7(PipTouchHandler pipTouchHandler) {
        this.f$0 = pipTouchHandler;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return this.f$0.getMovementBounds((Rect) obj);
    }
}
