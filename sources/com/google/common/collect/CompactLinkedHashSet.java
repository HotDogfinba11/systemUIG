package com.google.common.collect;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import java.util.Set;

class CompactLinkedHashSet<E> extends CompactHashSet<E> {
    private transient int firstEntry;
    private transient int lastEntry;
    private transient int[] predecessor;
    private transient int[] successor;

    public static <E> CompactLinkedHashSet<E> createWithExpectedSize(int i) {
        return new CompactLinkedHashSet<>(i);
    }

    CompactLinkedHashSet() {
    }

    CompactLinkedHashSet(int i) {
        super(i);
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.common.collect.CompactHashSet
    public void init(int i) {
        super.init(i);
        this.firstEntry = -2;
        this.lastEntry = -2;
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.common.collect.CompactHashSet
    public int allocArrays() {
        int allocArrays = super.allocArrays();
        this.predecessor = new int[allocArrays];
        this.successor = new int[allocArrays];
        return allocArrays;
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.common.collect.CompactHashSet
    @CanIgnoreReturnValue
    public Set<E> convertToHashFloodingResistantImplementation() {
        Set<E> convertToHashFloodingResistantImplementation = super.convertToHashFloodingResistantImplementation();
        this.predecessor = null;
        this.successor = null;
        return convertToHashFloodingResistantImplementation;
    }

    private int getPredecessor(int i) {
        return this.predecessor[i] - 1;
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.common.collect.CompactHashSet
    public int getSuccessor(int i) {
        return this.successor[i] - 1;
    }

    private void setSuccessor(int i, int i2) {
        this.successor[i] = i2 + 1;
    }

    private void setPredecessor(int i, int i2) {
        this.predecessor[i] = i2 + 1;
    }

    private void setSucceeds(int i, int i2) {
        if (i == -2) {
            this.firstEntry = i2;
        } else {
            setSuccessor(i, i2);
        }
        if (i2 == -2) {
            this.lastEntry = i;
        } else {
            setPredecessor(i2, i);
        }
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.common.collect.CompactHashSet
    public void insertEntry(int i, E e, int i2, int i3) {
        super.insertEntry(i, e, i2, i3);
        setSucceeds(this.lastEntry, i);
        setSucceeds(i, -2);
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.common.collect.CompactHashSet
    public void moveLastEntry(int i, int i2) {
        int size = size() - 1;
        super.moveLastEntry(i, i2);
        setSucceeds(getPredecessor(i), getSuccessor(i));
        if (i < size) {
            setSucceeds(getPredecessor(size), i);
            setSucceeds(i, getSuccessor(size));
        }
        this.predecessor[size] = 0;
        this.successor[size] = 0;
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.common.collect.CompactHashSet
    public void resizeEntries(int i) {
        super.resizeEntries(i);
        this.predecessor = Arrays.copyOf(this.predecessor, i);
        this.successor = Arrays.copyOf(this.successor, i);
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.common.collect.CompactHashSet
    public int firstEntryIndex() {
        return this.firstEntry;
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.common.collect.CompactHashSet
    public int adjustAfterRemove(int i, int i2) {
        return i >= size() ? i2 : i;
    }

    @Override // com.google.common.collect.CompactHashSet
    public Object[] toArray() {
        return ObjectArrays.toArrayImpl(this);
    }

    @Override // java.util.AbstractCollection, com.google.common.collect.CompactHashSet, java.util.Collection, java.util.Set
    public <T> T[] toArray(T[] tArr) {
        return (T[]) ObjectArrays.toArrayImpl(this, tArr);
    }

    @Override // com.google.common.collect.CompactHashSet
    public void clear() {
        if (!needsAllocArrays()) {
            this.firstEntry = -2;
            this.lastEntry = -2;
            int[] iArr = this.predecessor;
            if (iArr != null) {
                Arrays.fill(iArr, 0, size(), 0);
                Arrays.fill(this.successor, 0, size(), 0);
            }
            super.clear();
        }
    }
}
