package com.android.systemui;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.core.app.AppComponentFactory;
import com.android.systemui.dagger.ContextComponentHelper;
import com.android.systemui.dagger.SysUIComponent;
import java.lang.reflect.InvocationTargetException;

public class SystemUIAppComponentFactory extends AppComponentFactory {
    public ContextComponentHelper mComponentHelper;

    public interface ContextAvailableCallback {
        void onContextAvailable(Context context);
    }

    public interface ContextInitializer {
        void setContextAvailableCallback(ContextAvailableCallback contextAvailableCallback);
    }

    public static /* synthetic */ void $r8$lambda$zKKm1nezMgurYfpE42JHdUJUlZ0(SystemUIAppComponentFactory systemUIAppComponentFactory, Context context) {
        systemUIAppComponentFactory.lambda$instantiateApplicationCompat$0(context);
    }

    @Override // androidx.core.app.AppComponentFactory
    public Application instantiateApplicationCompat(ClassLoader classLoader, String str) {
        Application instantiateApplicationCompat = super.instantiateApplicationCompat(classLoader, str);
        if (instantiateApplicationCompat instanceof ContextInitializer) {
            ((ContextInitializer) instantiateApplicationCompat).setContextAvailableCallback(new SystemUIAppComponentFactory$$ExternalSyntheticLambda1(this));
        }
        return instantiateApplicationCompat;
    }

    private /* synthetic */ void lambda$instantiateApplicationCompat$0(Context context) {
        SystemUIFactory.createFromConfig(context);
        SystemUIFactory.getInstance().getSysUIComponent().inject(this);
    }

    @Override // androidx.core.app.AppComponentFactory
    public ContentProvider instantiateProviderCompat(ClassLoader classLoader, String str) {
        ContentProvider instantiateProviderCompat = super.instantiateProviderCompat(classLoader, str);
        if (instantiateProviderCompat instanceof ContextInitializer) {
            ((ContextInitializer) instantiateProviderCompat).setContextAvailableCallback(new SystemUIAppComponentFactory$$ExternalSyntheticLambda0(instantiateProviderCompat));
        }
        return instantiateProviderCompat;
    }

    public static /* synthetic */ void lambda$instantiateProviderCompat$1(ContentProvider contentProvider, Context context) {
        SystemUIFactory.createFromConfig(context);
        SysUIComponent sysUIComponent = SystemUIFactory.getInstance().getSysUIComponent();
        try {
            sysUIComponent.getClass().getMethod("inject", contentProvider.getClass()).invoke(sysUIComponent, contentProvider);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.w("AppComponentFactory", "No injector for class: " + contentProvider.getClass(), e);
        }
    }

    @Override // androidx.core.app.AppComponentFactory
    public Activity instantiateActivityCompat(ClassLoader classLoader, String str, Intent intent) {
        if (this.mComponentHelper == null) {
            SystemUIFactory.getInstance().getSysUIComponent().inject(this);
        }
        Activity resolveActivity = this.mComponentHelper.resolveActivity(str);
        if (resolveActivity != null) {
            return resolveActivity;
        }
        return super.instantiateActivityCompat(classLoader, str, intent);
    }

    @Override // androidx.core.app.AppComponentFactory
    public Service instantiateServiceCompat(ClassLoader classLoader, String str, Intent intent) {
        if (this.mComponentHelper == null) {
            SystemUIFactory.getInstance().getSysUIComponent().inject(this);
        }
        Service resolveService = this.mComponentHelper.resolveService(str);
        if (resolveService != null) {
            return resolveService;
        }
        return super.instantiateServiceCompat(classLoader, str, intent);
    }

    @Override // androidx.core.app.AppComponentFactory
    public BroadcastReceiver instantiateReceiverCompat(ClassLoader classLoader, String str, Intent intent) {
        if (this.mComponentHelper == null) {
            SystemUIFactory.getInstance().getSysUIComponent().inject(this);
        }
        BroadcastReceiver resolveBroadcastReceiver = this.mComponentHelper.resolveBroadcastReceiver(str);
        if (resolveBroadcastReceiver != null) {
            return resolveBroadcastReceiver;
        }
        return super.instantiateReceiverCompat(classLoader, str, intent);
    }
}
