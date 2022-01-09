package com.google.android.systemui.columbus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.LauncherActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.statusbar.phone.SystemUIDialog;

public class ColumbusTargetRequestDialog extends SystemUIDialog {
    private TextView mContent;
    private Button mNegativeButton;
    private Button mPositiveButton;
    private TextView mTitle;

    public ColumbusTargetRequestDialog(Context context) {
        super(context);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R$layout.columbus_target_request_dialog);
        this.mTitle = (TextView) requireViewById(R$id.title);
        this.mContent = (TextView) requireViewById(R$id.content);
        this.mPositiveButton = (Button) requireViewById(R$id.positive_button);
        this.mNegativeButton = (Button) requireViewById(R$id.negative_button);
        getWindow().setLayout(getContext().getResources().getDimensionPixelSize(R$dimen.columbus_target_request_dialog_width), -2);
    }

    public void bind(LauncherActivityInfo launcherActivityInfo, DialogInterface.OnClickListener onClickListener, DialogInterface.OnCancelListener onCancelListener) {
        setTitle(getContext().getString(R$string.columbus_target_request_dialog_title, launcherActivityInfo.getLabel()));
        setMessage(getContext().getString(R$string.columbus_target_request_dialog_summary, launcherActivityInfo.getLabel()));
        setPositiveButton(R$string.columbus_target_request_dialog_allow, onClickListener);
        setNegativeButton(R$string.columbus_target_request_dialog_deny, onClickListener);
        setOnCancelListener(onCancelListener);
        setCanceledOnTouchOutside(true);
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    public void setTitle(CharSequence charSequence) {
        getWindow().setTitle(charSequence);
        getWindow().getAttributes().setTitle(charSequence);
        this.mTitle.setText(charSequence);
    }

    public void setMessage(CharSequence charSequence) {
        this.mContent.setText(charSequence);
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public void setPositiveButton(int i, DialogInterface.OnClickListener onClickListener) {
        this.mPositiveButton.setText(i);
        this.mPositiveButton.setOnClickListener(new ColumbusTargetRequestDialog$$ExternalSyntheticLambda1(this, onClickListener));
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$setPositiveButton$0(DialogInterface.OnClickListener onClickListener, View view) {
        onClickListener.onClick(this, -1);
        dismiss();
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public void setNegativeButton(int i, DialogInterface.OnClickListener onClickListener) {
        this.mNegativeButton.setText(i);
        this.mNegativeButton.setOnClickListener(new ColumbusTargetRequestDialog$$ExternalSyntheticLambda0(this, onClickListener));
    }

    /* access modifiers changed from: public */
    private /* synthetic */ void lambda$setNegativeButton$1(DialogInterface.OnClickListener onClickListener, View view) {
        onClickListener.onClick(this, -2);
        dismiss();
    }
}
