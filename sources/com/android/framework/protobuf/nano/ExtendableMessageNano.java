package com.android.framework.protobuf.nano;

import com.android.framework.protobuf.nano.ExtendableMessageNano;
import java.io.IOException;

public abstract class ExtendableMessageNano<M extends ExtendableMessageNano<M>> extends MessageNano {
    protected FieldArray unknownFieldData;

    /* access modifiers changed from: protected */
    @Override // com.android.framework.protobuf.nano.MessageNano
    public int computeSerializedSize() {
        return 0;
    }

    @Override // com.android.framework.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
    }

    @Override // com.android.framework.protobuf.nano.MessageNano, com.android.framework.protobuf.nano.MessageNano
    public M clone() throws CloneNotSupportedException {
        M m = (M) ((ExtendableMessageNano) super.clone());
        InternalNano.cloneUnknownFieldData(this, m);
        return m;
    }
}
