package okio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: Options.kt */
public final class Options extends AbstractList<ByteString> implements RandomAccess {
    public static final Companion Companion = new Companion(null);
    private final ByteString[] byteStrings;
    private final int[] trie;

    public /* synthetic */ Options(ByteString[] byteStringArr, int[] iArr, DefaultConstructorMarker defaultConstructorMarker) {
        this(byteStringArr, iArr);
    }

    public static final Options of(ByteString... byteStringArr) {
        return Companion.of(byteStringArr);
    }

    @Override // kotlin.collections.AbstractCollection
    public final /* bridge */ boolean contains(Object obj) {
        if (!(obj instanceof ByteString)) {
            return false;
        }
        return contains((ByteString) obj);
    }

    public /* bridge */ boolean contains(ByteString byteString) {
        return super.contains((Object) byteString);
    }

    @Override // kotlin.collections.AbstractList
    public final /* bridge */ int indexOf(Object obj) {
        if (!(obj instanceof ByteString)) {
            return -1;
        }
        return indexOf((ByteString) obj);
    }

    public /* bridge */ int indexOf(ByteString byteString) {
        return super.indexOf((Object) byteString);
    }

    @Override // kotlin.collections.AbstractList
    public final /* bridge */ int lastIndexOf(Object obj) {
        if (!(obj instanceof ByteString)) {
            return -1;
        }
        return lastIndexOf((ByteString) obj);
    }

    public /* bridge */ int lastIndexOf(ByteString byteString) {
        return super.lastIndexOf((Object) byteString);
    }

    public final ByteString[] getByteStrings$external__okio__android_common__okio_lib() {
        return this.byteStrings;
    }

    public final int[] getTrie$external__okio__android_common__okio_lib() {
        return this.trie;
    }

    private Options(ByteString[] byteStringArr, int[] iArr) {
        this.byteStrings = byteStringArr;
        this.trie = iArr;
    }

    @Override // kotlin.collections.AbstractCollection
    public int getSize() {
        return this.byteStrings.length;
    }

    @Override // java.util.List, kotlin.collections.AbstractList
    public ByteString get(int i) {
        return this.byteStrings[i];
    }

    /* compiled from: Options.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Options of(ByteString... byteStringArr) {
            Intrinsics.checkNotNullParameter(byteStringArr, "byteStrings");
            int i = 0;
            if (byteStringArr.length == 0) {
                return new Options(new ByteString[0], new int[]{0, -1}, null);
            }
            List mutableList = ArraysKt.toMutableList(byteStringArr);
            CollectionsKt.sort(mutableList);
            ArrayList arrayList = new ArrayList(byteStringArr.length);
            for (ByteString byteString : byteStringArr) {
                arrayList.add(-1);
            }
            Object[] array = arrayList.toArray(new Integer[0]);
            Objects.requireNonNull(array, "null cannot be cast to non-null type kotlin.Array<T>");
            Integer[] numArr = (Integer[]) array;
            Integer[] numArr2 = new Integer[numArr.length];
            System.arraycopy(numArr, 0, numArr2, 0, numArr.length);
            List mutableListOf = CollectionsKt.mutableListOf(numArr2);
            int length = byteStringArr.length;
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                mutableListOf.set(CollectionsKt.binarySearch$default(mutableList, byteStringArr[i2], 0, 0, 6, null), Integer.valueOf(i3));
                i2++;
                i3++;
            }
            if (((ByteString) mutableList.get(0)).size() > 0) {
                int i4 = 0;
                while (i4 < mutableList.size()) {
                    ByteString byteString2 = (ByteString) mutableList.get(i4);
                    int i5 = i4 + 1;
                    int i6 = i5;
                    while (i6 < mutableList.size()) {
                        ByteString byteString3 = (ByteString) mutableList.get(i6);
                        if (!byteString3.startsWith(byteString2)) {
                            continue;
                            break;
                        }
                        if (!(byteString3.size() != byteString2.size())) {
                            throw new IllegalArgumentException(Intrinsics.stringPlus("duplicate option: ", byteString3).toString());
                        } else if (((Number) mutableListOf.get(i6)).intValue() > ((Number) mutableListOf.get(i4)).intValue()) {
                            mutableList.remove(i6);
                            mutableListOf.remove(i6);
                        } else {
                            i6++;
                        }
                    }
                    i4 = i5;
                }
                Buffer buffer = new Buffer();
                buildTrieRecursive$default(this, 0, buffer, 0, mutableList, 0, 0, mutableListOf, 53, null);
                int[] iArr = new int[((int) getIntCount(buffer))];
                while (!buffer.exhausted()) {
                    iArr[i] = buffer.readInt();
                    i++;
                }
                Object[] copyOf = Arrays.copyOf(byteStringArr, byteStringArr.length);
                Intrinsics.checkNotNullExpressionValue(copyOf, "java.util.Arrays.copyOf(this, size)");
                return new Options((ByteString[]) copyOf, iArr, null);
            }
            throw new IllegalArgumentException("the empty byte string is not a supported option".toString());
        }

        static /* synthetic */ void buildTrieRecursive$default(Companion companion, long j, Buffer buffer, int i, List list, int i2, int i3, List list2, int i4, Object obj) {
            int i5;
            long j2 = (i4 & 1) != 0 ? 0 : j;
            int i6 = (i4 & 4) != 0 ? 0 : i;
            int i7 = (i4 & 16) != 0 ? 0 : i2;
            if ((i4 & 32) != 0) {
                i5 = list.size();
            } else {
                i5 = i3;
            }
            companion.buildTrieRecursive(j2, buffer, i6, list, i7, i5, list2);
        }

        private final void buildTrieRecursive(long j, Buffer buffer, int i, List<? extends ByteString> list, int i2, int i3, List<Integer> list2) {
            int i4;
            int i5;
            int i6;
            int i7;
            int i8;
            Buffer buffer2;
            int i9 = i;
            if (i2 < i3) {
                if (i2 < i3) {
                    int i10 = i2;
                    while (true) {
                        int i11 = i10 + 1;
                        if (!(((ByteString) list.get(i10)).size() >= i9)) {
                            throw new IllegalArgumentException("Failed requirement.".toString());
                        } else if (i11 >= i3) {
                            break;
                        } else {
                            i10 = i11;
                        }
                    }
                }
                ByteString byteString = (ByteString) list.get(i2);
                ByteString byteString2 = (ByteString) list.get(i3 - 1);
                int i12 = -1;
                if (i9 == byteString.size()) {
                    int i13 = i2 + 1;
                    i4 = i13;
                    i5 = list2.get(i2).intValue();
                    byteString = (ByteString) list.get(i13);
                } else {
                    i4 = i2;
                    i5 = -1;
                }
                if (byteString.getByte(i9) != byteString2.getByte(i9)) {
                    int i14 = i4 + 1;
                    int i15 = 1;
                    if (i14 < i3) {
                        while (true) {
                            int i16 = i14 + 1;
                            if (((ByteString) list.get(i14 - 1)).getByte(i9) != ((ByteString) list.get(i14)).getByte(i9)) {
                                i15++;
                            }
                            if (i16 >= i3) {
                                break;
                            }
                            i14 = i16;
                        }
                    }
                    long intCount = j + getIntCount(buffer) + ((long) 2) + ((long) (i15 * 2));
                    buffer.writeInt(i15);
                    buffer.writeInt(i5);
                    if (i4 < i3) {
                        int i17 = i4;
                        while (true) {
                            int i18 = i17 + 1;
                            byte b = ((ByteString) list.get(i17)).getByte(i9);
                            if (i17 == i4 || b != ((ByteString) list.get(i17 - 1)).getByte(i9)) {
                                buffer.writeInt(b & 255);
                            }
                            if (i18 >= i3) {
                                break;
                            }
                            i17 = i18;
                        }
                    }
                    Buffer buffer3 = new Buffer();
                    while (i4 < i3) {
                        byte b2 = ((ByteString) list.get(i4)).getByte(i9);
                        int i19 = i4 + 1;
                        if (i19 < i3) {
                            int i20 = i19;
                            while (true) {
                                int i21 = i20 + 1;
                                if (b2 != ((ByteString) list.get(i20)).getByte(i9)) {
                                    i7 = i20;
                                    break;
                                } else if (i21 >= i3) {
                                    break;
                                } else {
                                    i20 = i21;
                                }
                            }
                        }
                        i7 = i3;
                        if (i19 == i7 && i9 + 1 == ((ByteString) list.get(i4)).size()) {
                            buffer.writeInt(list2.get(i4).intValue());
                            i8 = i7;
                            buffer2 = buffer3;
                        } else {
                            buffer.writeInt(((int) (intCount + getIntCount(buffer3))) * i12);
                            i8 = i7;
                            buffer2 = buffer3;
                            buildTrieRecursive(intCount, buffer3, i9 + 1, list, i4, i7, list2);
                        }
                        buffer3 = buffer2;
                        i4 = i8;
                        i12 = -1;
                    }
                    buffer.writeAll(buffer3);
                    return;
                }
                int min = Math.min(byteString.size(), byteString2.size());
                if (i9 < min) {
                    int i22 = i9;
                    i6 = 0;
                    while (true) {
                        int i23 = i22 + 1;
                        if (byteString.getByte(i22) != byteString2.getByte(i22)) {
                            break;
                        }
                        i6++;
                        if (i23 >= min) {
                            break;
                        }
                        i22 = i23;
                    }
                } else {
                    i6 = 0;
                }
                long intCount2 = j + getIntCount(buffer) + ((long) 2) + ((long) i6) + 1;
                buffer.writeInt(-i6);
                buffer.writeInt(i5);
                int i24 = i9 + i6;
                if (i9 < i24) {
                    while (true) {
                        int i25 = i9 + 1;
                        buffer.writeInt(byteString.getByte(i9) & 255);
                        if (i25 >= i24) {
                            break;
                        }
                        i9 = i25;
                    }
                }
                if (i4 + 1 == i3) {
                    if (i24 == ((ByteString) list.get(i4)).size()) {
                        buffer.writeInt(list2.get(i4).intValue());
                        return;
                    }
                    throw new IllegalStateException("Check failed.".toString());
                }
                Buffer buffer4 = new Buffer();
                buffer.writeInt(((int) (getIntCount(buffer4) + intCount2)) * -1);
                buildTrieRecursive(intCount2, buffer4, i24, list, i4, i3, list2);
                buffer.writeAll(buffer4);
                return;
            }
            throw new IllegalArgumentException("Failed requirement.".toString());
        }

        private final long getIntCount(Buffer buffer) {
            return buffer.size() / ((long) 4);
        }
    }
}
