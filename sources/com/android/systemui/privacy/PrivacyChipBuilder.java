package com.android.systemui.privacy;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.appcompat.R$styleable;
import com.android.systemui.R$string;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt___MapsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PrivacyChipBuilder.kt */
public final class PrivacyChipBuilder {
    private final List<Pair<PrivacyApplication, List<PrivacyType>>> appsAndTypes;
    private final Context context;
    private final String lastSeparator;
    private final String separator;
    private final List<PrivacyType> types;

    public PrivacyChipBuilder(Context context2, List<PrivacyItem> list) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(list, "itemsList");
        this.context = context2;
        this.separator = context2.getString(R$string.ongoing_privacy_dialog_separator);
        this.lastSeparator = context2.getString(R$string.ongoing_privacy_dialog_last_separator);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (T t : list) {
            PrivacyApplication application = t.getApplication();
            Object obj = linkedHashMap.get(application);
            if (obj == null) {
                obj = new ArrayList();
                linkedHashMap.put(application, obj);
            }
            ((List) obj).add(t.getPrivacyType());
        }
        this.appsAndTypes = CollectionsKt___CollectionsKt.sortedWith(MapsKt___MapsKt.toList(linkedHashMap), ComparisonsKt__ComparisonsKt.compareBy(AnonymousClass3.INSTANCE, AnonymousClass4.INSTANCE));
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getPrivacyType());
        }
        this.types = CollectionsKt___CollectionsKt.sorted(CollectionsKt___CollectionsKt.distinct(arrayList));
    }

    public final List<Drawable> generateIcons() {
        List<PrivacyType> list = this.types;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getIcon(this.context));
        }
        return arrayList;
    }

    private final <T> StringBuilder joinWithAnd(List<? extends T> list) {
        List<? extends T> subList = list.subList(0, list.size() - 1);
        StringBuilder sb = new StringBuilder();
        String str = this.separator;
        Intrinsics.checkNotNullExpressionValue(str, "separator");
        StringBuilder sb2 = (StringBuilder) CollectionsKt___CollectionsKt.joinTo$default(subList, sb, str, null, null, 0, null, null, R$styleable.AppCompatTheme_windowMinWidthMajor, null);
        sb2.append(this.lastSeparator);
        sb2.append(CollectionsKt.last((List) list));
        return sb2;
    }

    public final String joinTypes() {
        int size = this.types.size();
        if (size == 0) {
            return "";
        }
        if (size != 1) {
            List<PrivacyType> list = this.types;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getName(this.context));
            }
            String sb = joinWithAnd(arrayList).toString();
            Intrinsics.checkNotNullExpressionValue(sb, "types.map { it.getName(context) }.joinWithAnd().toString()");
            return sb;
        }
        String name = this.types.get(0).getName(this.context);
        Intrinsics.checkNotNullExpressionValue(name, "types[0].getName(context)");
        return name;
    }
}
