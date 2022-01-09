package com.android.systemui.statusbar.notification.collection.render;

import java.util.List;

/* compiled from: NodeController.kt */
public interface NodeSpec {
    List<NodeSpec> getChildren();

    NodeController getController();

    NodeSpec getParent();
}
