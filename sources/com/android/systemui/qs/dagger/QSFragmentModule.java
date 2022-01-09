package com.android.systemui.qs.dagger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.qs.QSContainerImpl;
import com.android.systemui.qs.QSFooter;
import com.android.systemui.qs.QSFooterView;
import com.android.systemui.qs.QSFooterViewController;
import com.android.systemui.qs.QSFragment;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.qs.QuickQSPanel;
import com.android.systemui.qs.QuickStatusBarHeader;
import com.android.systemui.qs.customize.QSCustomizer;
import com.android.systemui.statusbar.phone.MultiUserSwitch;
import com.android.systemui.util.Utils;

public interface QSFragmentModule {
    static default Context provideThemedContext(View view) {
        return view.getContext();
    }

    static default LayoutInflater provideThemedLayoutInflater(Context context) {
        return LayoutInflater.from(context);
    }

    static default View provideRootView(QSFragment qSFragment) {
        return qSFragment.getView();
    }

    static default MultiUserSwitch providesMultiUserSWitch(QSFooterView qSFooterView) {
        return (MultiUserSwitch) qSFooterView.findViewById(R$id.multi_user_switch);
    }

    static default QSPanel provideQSPanel(View view) {
        return (QSPanel) view.findViewById(R$id.quick_settings_panel);
    }

    static default QSContainerImpl providesQSContainerImpl(View view) {
        return (QSContainerImpl) view.findViewById(R$id.quick_settings_container);
    }

    static default QuickStatusBarHeader providesQuickStatusBarHeader(View view) {
        return (QuickStatusBarHeader) view.findViewById(R$id.header);
    }

    static default QuickQSPanel providesQuickQSPanel(QuickStatusBarHeader quickStatusBarHeader) {
        return (QuickQSPanel) quickStatusBarHeader.findViewById(R$id.quick_qs_panel);
    }

    static default QSFooterView providesQSFooterView(View view) {
        return (QSFooterView) view.findViewById(R$id.qs_footer);
    }

    static default QSFooter providesQSFooter(QSFooterViewController qSFooterViewController) {
        qSFooterViewController.init();
        return qSFooterViewController;
    }

    static default QSCustomizer providesQSCutomizer(View view) {
        return (QSCustomizer) view.findViewById(R$id.qs_customize);
    }

    static default View providesQSSecurityFooterView(LayoutInflater layoutInflater, QSPanel qSPanel) {
        return layoutInflater.inflate(R$layout.quick_settings_security_footer, (ViewGroup) qSPanel, false);
    }

    static default boolean providesQSUsingMediaPlayer(Context context) {
        return Utils.useQsMediaPlayer(context);
    }
}
