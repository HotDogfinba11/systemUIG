package com.android.systemui.statusbar.notification.stack;

import android.view.ViewGroup;
import com.android.systemui.statusbar.notification.stack.NotificationSectionsManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NotificationSectionsManager.kt */
public final class NotificationSectionsManager$expandableViewHeaderState$1 implements NotificationSectionsManager.SectionUpdateState<T> {
    final /* synthetic */ T $header;
    private Integer currentPosition;
    private final T header;
    private Integer targetPosition;
    final /* synthetic */ NotificationSectionsManager this$0;

    NotificationSectionsManager$expandableViewHeaderState$1(T t, NotificationSectionsManager notificationSectionsManager) {
        this.$header = t;
        this.this$0 = notificationSectionsManager;
        this.header = t;
    }

    @Override // com.android.systemui.statusbar.notification.stack.NotificationSectionsManager.SectionUpdateState
    public Integer getCurrentPosition() {
        return this.currentPosition;
    }

    @Override // com.android.systemui.statusbar.notification.stack.NotificationSectionsManager.SectionUpdateState
    public void setCurrentPosition(Integer num) {
        this.currentPosition = num;
    }

    @Override // com.android.systemui.statusbar.notification.stack.NotificationSectionsManager.SectionUpdateState
    public Integer getTargetPosition() {
        return this.targetPosition;
    }

    @Override // com.android.systemui.statusbar.notification.stack.NotificationSectionsManager.SectionUpdateState
    public void setTargetPosition(Integer num) {
        this.targetPosition = num;
    }

    @Override // com.android.systemui.statusbar.notification.stack.NotificationSectionsManager.SectionUpdateState
    public void adjustViewPosition() {
        Integer targetPosition2 = getTargetPosition();
        Integer currentPosition2 = getCurrentPosition();
        if (targetPosition2 == null) {
            if (currentPosition2 != null) {
                NotificationStackScrollLayout notificationStackScrollLayout = this.this$0.parent;
                if (notificationStackScrollLayout != null) {
                    notificationStackScrollLayout.removeView(this.$header);
                } else {
                    Intrinsics.throwUninitializedPropertyAccessException("parent");
                    throw null;
                }
            }
        } else if (currentPosition2 == null) {
            ViewGroup transientContainer = this.$header.getTransientContainer();
            if (transientContainer != null) {
                transientContainer.removeTransientView(this.$header);
            }
            this.$header.setTransientContainer(null);
            NotificationStackScrollLayout notificationStackScrollLayout2 = this.this$0.parent;
            if (notificationStackScrollLayout2 != null) {
                notificationStackScrollLayout2.addView(this.$header, targetPosition2.intValue());
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("parent");
                throw null;
            }
        } else {
            NotificationStackScrollLayout notificationStackScrollLayout3 = this.this$0.parent;
            if (notificationStackScrollLayout3 != null) {
                notificationStackScrollLayout3.changeViewPosition(this.$header, targetPosition2.intValue());
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("parent");
                throw null;
            }
        }
    }
}
