package com.google.android.apps.miphone.aiai.matchmaker.overview.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UrlIndicatorContainerView extends LinearLayout {
    private final Context context;
    @Nullable
    private ActionMode menuActionMode;
    private boolean readyToDrag;
    private final LinearLayout urlContainer;
    private final ImageButton urlIndicator;
    private final TextView urlText;

    public UrlIndicatorContainerView(Context context2, AttributeSet attributeSet) {
        this(context2, attributeSet, 0);
    }

    public UrlIndicatorContainerView(Context context2, AttributeSet attributeSet, int i) {
        this(context2, attributeSet, i, 0);
    }

    public UrlIndicatorContainerView(Context context2, AttributeSet attributeSet, int i, int i2) {
        super(context2, attributeSet, i, i2);
        this.readyToDrag = false;
        this.context = context2;
        LinearLayout linearLayout = (LinearLayout) LinearLayout.inflate(context2, R$layout.url_container, this);
        this.urlContainer = linearLayout;
        this.urlText = (TextView) linearLayout.findViewById(R$id.url_text);
        this.urlIndicator = (ImageButton) linearLayout.findViewById(R$id.url_indicator);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ActionMode actionMode = this.menuActionMode;
        if (actionMode != null) {
            actionMode.finish();
            this.menuActionMode = null;
        }
    }
}
