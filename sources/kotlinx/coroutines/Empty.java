package kotlinx.coroutines;

/* access modifiers changed from: package-private */
/* compiled from: JobSupport.kt */
public final class Empty implements Incomplete {
    private final boolean isActive;

    @Override // kotlinx.coroutines.Incomplete
    public NodeList getList() {
        return null;
    }

    public Empty(boolean z) {
        this.isActive = z;
    }

    @Override // kotlinx.coroutines.Incomplete
    public boolean isActive() {
        return this.isActive;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Empty{");
        sb.append(isActive() ? "Active" : "New");
        sb.append('}');
        return sb.toString();
    }
}
