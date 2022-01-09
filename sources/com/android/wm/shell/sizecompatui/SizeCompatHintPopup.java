package com.android.wm.shell.sizecompatui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import com.android.wm.shell.R;

public class SizeCompatHintPopup extends FrameLayout implements View.OnClickListener {
    private SizeCompatUILayout mLayout;

    public SizeCompatHintPopup(Context context) {
        super(context);
    }

    public SizeCompatHintPopup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SizeCompatHintPopup(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public SizeCompatHintPopup(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    /* access modifiers changed from: package-private */
    public void inject(SizeCompatUILayout sizeCompatUILayout) {
        this.mLayout = sizeCompatUILayout;
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        Button button = (Button) findViewById(R.id.got_it);
        button.setBackground(new RippleDrawable(ColorStateList.valueOf(-3355444), null, null));
        button.setOnClickListener(this);
    }

    public void onClick(View view) {
        this.mLayout.dismissHint();
    }
}
