package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;

/* access modifiers changed from: package-private */
public class Chain {
    static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, int i) {
        ChainHead[] chainHeadArr;
        int i2;
        int i3;
        if (i == 0) {
            int i4 = constraintWidgetContainer.mHorizontalChainsSize;
            chainHeadArr = constraintWidgetContainer.mHorizontalChainsArray;
            i2 = i4;
            i3 = 0;
        } else {
            i3 = 2;
            i2 = constraintWidgetContainer.mVerticalChainsSize;
            chainHeadArr = constraintWidgetContainer.mVerticalChainsArray;
        }
        for (int i5 = 0; i5 < i2; i5++) {
            ChainHead chainHead = chainHeadArr[i5];
            chainHead.define();
            applyChainConstraints(constraintWidgetContainer, linearSystem, i, i3, chainHead);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v56, resolved type: androidx.constraintlayout.solver.widgets.ConstraintWidget */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002d, code lost:
        if (r8 == 2) goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003c, code lost:
        if (r8 == 2) goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0040, code lost:
        r5 = false;
     */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0195  */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x01bc  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x01ce  */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x025b A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x02b4 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:209:0x03a5  */
    /* JADX WARNING: Removed duplicated region for block: B:216:0x03b8  */
    /* JADX WARNING: Removed duplicated region for block: B:222:0x03c5  */
    /* JADX WARNING: Removed duplicated region for block: B:267:0x048c  */
    /* JADX WARNING: Removed duplicated region for block: B:272:0x04c1  */
    /* JADX WARNING: Removed duplicated region for block: B:277:0x04d4 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:281:0x04e6  */
    /* JADX WARNING: Removed duplicated region for block: B:282:0x04e9  */
    /* JADX WARNING: Removed duplicated region for block: B:285:0x04ef  */
    /* JADX WARNING: Removed duplicated region for block: B:286:0x04f2  */
    /* JADX WARNING: Removed duplicated region for block: B:288:0x04f6  */
    /* JADX WARNING: Removed duplicated region for block: B:293:0x0506  */
    /* JADX WARNING: Removed duplicated region for block: B:295:0x050c A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:305:0x03a6 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:316:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void applyChainConstraints(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r39, androidx.constraintlayout.solver.LinearSystem r40, int r41, int r42, androidx.constraintlayout.solver.widgets.ChainHead r43) {
        /*
        // Method dump skipped, instructions count: 1325
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.Chain.applyChainConstraints(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer, androidx.constraintlayout.solver.LinearSystem, int, int, androidx.constraintlayout.solver.widgets.ChainHead):void");
    }
}
