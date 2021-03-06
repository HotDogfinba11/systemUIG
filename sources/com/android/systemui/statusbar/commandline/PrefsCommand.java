package com.android.systemui.statusbar.commandline;

import android.content.Context;
import com.android.systemui.Prefs;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* access modifiers changed from: package-private */
/* compiled from: CommandRegistry.kt */
public final class PrefsCommand implements Command {
    private final Context context;

    public PrefsCommand(Context context2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        this.context = context2;
    }

    public void help(PrintWriter printWriter) {
        Intrinsics.checkNotNullParameter(printWriter, "pw");
        printWriter.println("usage: prefs <command> [args]");
        printWriter.println("Available commands:");
        printWriter.println("  list-prefs");
        printWriter.println("  set-pref <pref name> <value>");
    }

    @Override // com.android.systemui.statusbar.commandline.Command
    public void execute(PrintWriter printWriter, List<String> list) {
        Intrinsics.checkNotNullParameter(printWriter, "pw");
        Intrinsics.checkNotNullParameter(list, "args");
        if (list.isEmpty()) {
            help(printWriter);
        } else if (Intrinsics.areEqual(list.get(0), "list-prefs")) {
            listPrefs(printWriter);
        } else {
            help(printWriter);
        }
    }

    private final void listPrefs(PrintWriter printWriter) {
        printWriter.println("Available keys:");
        Field[] declaredFields = Prefs.Key.class.getDeclaredFields();
        Intrinsics.checkNotNullExpressionValue(declaredFields, "Prefs.Key::class.java.declaredFields");
        int length = declaredFields.length;
        int i = 0;
        while (i < length) {
            Field field = declaredFields[i];
            i++;
            printWriter.print("  ");
            printWriter.println(field.get(Prefs.Key.class));
        }
    }
}
