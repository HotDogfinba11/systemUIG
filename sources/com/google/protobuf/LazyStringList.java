package com.google.protobuf;

import java.util.List;

public interface LazyStringList extends List {
    void add(ByteString byteString);

    List<?> getUnderlyingElements();

    LazyStringList getUnmodifiableView();
}
