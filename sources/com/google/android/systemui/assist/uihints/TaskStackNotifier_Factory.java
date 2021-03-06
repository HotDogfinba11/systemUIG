package com.google.android.systemui.assist.uihints;

import dagger.internal.Factory;

public final class TaskStackNotifier_Factory implements Factory<TaskStackNotifier> {

    private static final class InstanceHolder {
        private static final TaskStackNotifier_Factory INSTANCE = new TaskStackNotifier_Factory();
    }

    @Override // javax.inject.Provider
    public TaskStackNotifier get() {
        return newInstance();
    }

    public static TaskStackNotifier_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static TaskStackNotifier newInstance() {
        return new TaskStackNotifier();
    }
}
