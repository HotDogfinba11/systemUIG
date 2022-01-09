package com.google.protobuf;

import com.google.protobuf.FieldSet;
import com.google.protobuf.FieldSet.FieldDescriptorLite;
import java.io.IOException;

abstract class ExtensionSchema<T extends FieldSet.FieldDescriptorLite<T>> {
    /* access modifiers changed from: package-private */
    public abstract Object findExtensionByNumber(ExtensionRegistryLite extensionRegistryLite, MessageLite messageLite, int i);

    /* access modifiers changed from: package-private */
    public abstract FieldSet<T> getExtensions(Object obj);

    /* access modifiers changed from: package-private */
    public abstract FieldSet<T> getMutableExtensions(Object obj);

    /* access modifiers changed from: package-private */
    public abstract boolean hasExtensions(MessageLite messageLite);

    /* access modifiers changed from: package-private */
    public abstract void makeImmutable(Object obj);

    /* access modifiers changed from: package-private */
    public abstract <UT, UB> UB parseExtension(Reader reader, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet<T> fieldSet, UB ub, UnknownFieldSchema<UT, UB> unknownFieldSchema) throws IOException;

    /* access modifiers changed from: package-private */
    public abstract void parseLengthPrefixedMessageSetItem(Reader reader, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet<T> fieldSet) throws IOException;

    /* access modifiers changed from: package-private */
    public abstract void parseMessageSetItem(ByteString byteString, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet<T> fieldSet) throws IOException;

    ExtensionSchema() {
    }
}