package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.stack.NotificationSectionsManager;

/* compiled from: NotificationSectionsManager.kt */
public final class NotificationSectionsManager$decorViewHeaderState$1 implements NotificationSectionsManager.SectionUpdateState<T> {
    private final /* synthetic */ NotificationSectionsManager.SectionUpdateState<T> $$delegate_0;
    final /* synthetic */ T $header;
    final /* synthetic */ NotificationSectionsManager.SectionUpdateState<T> $inner;

    @Override // com.android.systemui.statusbar.notification.stack.NotificationSectionsManager.SectionUpdateState
    public Integer getCurrentPosition() {
        return this.$$delegate_0.getCurrentPosition();
    }

    @Override // com.android.systemui.statusbar.notification.stack.NotificationSectionsManager.SectionUpdateState
    public Integer getTargetPosition() {
        return this.$$delegate_0.getTargetPosition();
    }

    @Override // com.android.systemui.statusbar.notification.stack.NotificationSectionsManager.SectionUpdateState
    public void setCurrentPosition(Integer num) {
        this.$$delegate_0.setCurrentPosition(num);
    }

    @Override // com.android.systemui.statusbar.notification.stack.NotificationSectionsManager.SectionUpdateState
    public void setTargetPosition(Integer num) {
        this.$$delegate_0.setTargetPosition(num);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$SectionUpdateState<? extends T> */
    /* JADX WARN: Multi-variable type inference failed */
    NotificationSectionsManager$decorViewHeaderState$1(NotificationSectionsManager.SectionUpdateState<? extends T> sectionUpdateState, T t) {
        this.$inner = sectionUpdateState;
        this.$header = t;
        this.$$delegate_0 = sectionUpdateState;
    }

    @Override // com.android.systemui.statusbar.notification.stack.NotificationSectionsManager.SectionUpdateState
    public void adjustViewPosition() {
        this.$inner.adjustViewPosition();
        if (getTargetPosition() != null && getCurrentPosition() == null) {
            this.$header.setContentVisible(true);
        }
    }
}
