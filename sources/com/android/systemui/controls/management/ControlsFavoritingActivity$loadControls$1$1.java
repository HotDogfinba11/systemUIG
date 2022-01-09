package com.android.systemui.controls.management;

import android.animation.Animator;
import android.content.res.Resources;
import android.widget.TextView;
import androidx.viewpager2.widget.ViewPager2;
import com.android.systemui.R$string;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.controller.ControlsController;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: ControlsFavoritingActivity.kt */
public final class ControlsFavoritingActivity$loadControls$1$1 implements Consumer<ControlsController.LoadData> {
    final /* synthetic */ CharSequence $emptyZoneString;
    final /* synthetic */ ControlsFavoritingActivity this$0;

    ControlsFavoritingActivity$loadControls$1$1(ControlsFavoritingActivity controlsFavoritingActivity, CharSequence charSequence) {
        this.this$0 = controlsFavoritingActivity;
        this.$emptyZoneString = charSequence;
    }

    public final void accept(ControlsController.LoadData loadData) {
        Intrinsics.checkNotNullParameter(loadData, "data");
        List<ControlStatus> allControls = loadData.getAllControls();
        List<String> favoritesIds = loadData.getFavoritesIds();
        final boolean errorOnLoad = loadData.getErrorOnLoad();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (T t : allControls) {
            Object structure = t.getControl().getStructure();
            if (structure == null) {
                structure = "";
            }
            Object obj = linkedHashMap.get(structure);
            if (obj == null) {
                obj = new ArrayList();
                linkedHashMap.put(structure, obj);
            }
            ((List) obj).add(t);
        }
        ControlsFavoritingActivity controlsFavoritingActivity = this.this$0;
        CharSequence charSequence = this.$emptyZoneString;
        ArrayList arrayList = new ArrayList(linkedHashMap.size());
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            Intrinsics.checkNotNullExpressionValue(charSequence, "emptyZoneString");
            arrayList.add(new StructureContainer((CharSequence) entry.getKey(), new AllModel((List) entry.getValue(), favoritesIds, charSequence, controlsFavoritingActivity.controlsModelCallback)));
        }
        Comparator comparator = this.this$0.comparator;
        if (comparator != null) {
            controlsFavoritingActivity.listOfStructures = CollectionsKt___CollectionsKt.sortedWith(arrayList, comparator);
            List list = this.this$0.listOfStructures;
            ControlsFavoritingActivity controlsFavoritingActivity2 = this.this$0;
            Iterator it = list.iterator();
            final int i = 0;
            while (true) {
                if (!it.hasNext()) {
                    i = -1;
                    break;
                } else if (Intrinsics.areEqual(((StructureContainer) it.next()).getStructureName(), controlsFavoritingActivity2.structureExtra)) {
                    break;
                } else {
                    i++;
                }
            }
            if (i == -1) {
                i = 0;
            }
            if (this.this$0.getIntent().getBooleanExtra("extra_single_structure", false)) {
                ControlsFavoritingActivity controlsFavoritingActivity3 = this.this$0;
                controlsFavoritingActivity3.listOfStructures = CollectionsKt__CollectionsJVMKt.listOf(controlsFavoritingActivity3.listOfStructures.get(i));
            }
            Executor executor = this.this$0.executor;
            final ControlsFavoritingActivity controlsFavoritingActivity4 = this.this$0;
            executor.execute(new Runnable() {
                /* class com.android.systemui.controls.management.ControlsFavoritingActivity$loadControls$1$1.AnonymousClass2 */

                public final void run() {
                    ViewPager2 viewPager2 = controlsFavoritingActivity4.structurePager;
                    if (viewPager2 != null) {
                        viewPager2.setAdapter(new StructureAdapter(controlsFavoritingActivity4.listOfStructures));
                        ViewPager2 viewPager22 = controlsFavoritingActivity4.structurePager;
                        if (viewPager22 != null) {
                            viewPager22.setCurrentItem(i);
                            int i = 0;
                            if (errorOnLoad) {
                                TextView textView = controlsFavoritingActivity4.statusText;
                                if (textView != null) {
                                    Resources resources = controlsFavoritingActivity4.getResources();
                                    int i2 = R$string.controls_favorite_load_error;
                                    Object[] objArr = new Object[1];
                                    Object obj = controlsFavoritingActivity4.appName;
                                    if (obj == null) {
                                        obj = "";
                                    }
                                    objArr[0] = obj;
                                    textView.setText(resources.getString(i2, objArr));
                                    TextView textView2 = controlsFavoritingActivity4.subtitleView;
                                    if (textView2 != null) {
                                        textView2.setVisibility(8);
                                    } else {
                                        Intrinsics.throwUninitializedPropertyAccessException("subtitleView");
                                        throw null;
                                    }
                                } else {
                                    Intrinsics.throwUninitializedPropertyAccessException("statusText");
                                    throw null;
                                }
                            } else if (controlsFavoritingActivity4.listOfStructures.isEmpty()) {
                                TextView textView3 = controlsFavoritingActivity4.statusText;
                                if (textView3 != null) {
                                    textView3.setText(controlsFavoritingActivity4.getResources().getString(R$string.controls_favorite_load_none));
                                    TextView textView4 = controlsFavoritingActivity4.subtitleView;
                                    if (textView4 != null) {
                                        textView4.setVisibility(8);
                                    } else {
                                        Intrinsics.throwUninitializedPropertyAccessException("subtitleView");
                                        throw null;
                                    }
                                } else {
                                    Intrinsics.throwUninitializedPropertyAccessException("statusText");
                                    throw null;
                                }
                            } else {
                                TextView textView5 = controlsFavoritingActivity4.statusText;
                                if (textView5 != null) {
                                    textView5.setVisibility(8);
                                    ManagementPageIndicator managementPageIndicator = controlsFavoritingActivity4.pageIndicator;
                                    if (managementPageIndicator != null) {
                                        managementPageIndicator.setNumPages(controlsFavoritingActivity4.listOfStructures.size());
                                        ManagementPageIndicator managementPageIndicator2 = controlsFavoritingActivity4.pageIndicator;
                                        if (managementPageIndicator2 != null) {
                                            managementPageIndicator2.setLocation(0.0f);
                                            ManagementPageIndicator managementPageIndicator3 = controlsFavoritingActivity4.pageIndicator;
                                            if (managementPageIndicator3 != null) {
                                                if (controlsFavoritingActivity4.listOfStructures.size() <= 1) {
                                                    i = 4;
                                                }
                                                managementPageIndicator3.setVisibility(i);
                                                ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
                                                ManagementPageIndicator managementPageIndicator4 = controlsFavoritingActivity4.pageIndicator;
                                                if (managementPageIndicator4 != null) {
                                                    Animator enterAnimation = controlsAnimations.enterAnimation(managementPageIndicator4);
                                                    enterAnimation.addListener(new ControlsFavoritingActivity$loadControls$1$1$2$1$1(controlsFavoritingActivity4));
                                                    enterAnimation.start();
                                                    ViewPager2 viewPager23 = controlsFavoritingActivity4.structurePager;
                                                    if (viewPager23 != null) {
                                                        controlsAnimations.enterAnimation(viewPager23).start();
                                                    } else {
                                                        Intrinsics.throwUninitializedPropertyAccessException("structurePager");
                                                        throw null;
                                                    }
                                                } else {
                                                    Intrinsics.throwUninitializedPropertyAccessException("pageIndicator");
                                                    throw null;
                                                }
                                            } else {
                                                Intrinsics.throwUninitializedPropertyAccessException("pageIndicator");
                                                throw null;
                                            }
                                        } else {
                                            Intrinsics.throwUninitializedPropertyAccessException("pageIndicator");
                                            throw null;
                                        }
                                    } else {
                                        Intrinsics.throwUninitializedPropertyAccessException("pageIndicator");
                                        throw null;
                                    }
                                } else {
                                    Intrinsics.throwUninitializedPropertyAccessException("statusText");
                                    throw null;
                                }
                            }
                        } else {
                            Intrinsics.throwUninitializedPropertyAccessException("structurePager");
                            throw null;
                        }
                    } else {
                        Intrinsics.throwUninitializedPropertyAccessException("structurePager");
                        throw null;
                    }
                }
            });
            return;
        }
        Intrinsics.throwUninitializedPropertyAccessException("comparator");
        throw null;
    }
}
