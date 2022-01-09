package com.google.android.systemui.autorotate.proto.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public final class AutorotateProto$DeviceRotatedSensorData extends MessageNano {
    public AutorotateProto$DeviceRotatedSensorHeader header;
    public AutorotateProto$DeviceRotatedSensorSample[] sample;

    public AutorotateProto$DeviceRotatedSensorData() {
        clear();
    }

    public AutorotateProto$DeviceRotatedSensorData clear() {
        this.header = null;
        this.sample = AutorotateProto$DeviceRotatedSensorSample.emptyArray();
        this.cachedSize = -1;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        AutorotateProto$DeviceRotatedSensorHeader autorotateProto$DeviceRotatedSensorHeader = this.header;
        if (autorotateProto$DeviceRotatedSensorHeader != null) {
            codedOutputByteBufferNano.writeMessage(1, autorotateProto$DeviceRotatedSensorHeader);
        }
        AutorotateProto$DeviceRotatedSensorSample[] autorotateProto$DeviceRotatedSensorSampleArr = this.sample;
        if (autorotateProto$DeviceRotatedSensorSampleArr != null && autorotateProto$DeviceRotatedSensorSampleArr.length > 0) {
            int i = 0;
            while (true) {
                AutorotateProto$DeviceRotatedSensorSample[] autorotateProto$DeviceRotatedSensorSampleArr2 = this.sample;
                if (i >= autorotateProto$DeviceRotatedSensorSampleArr2.length) {
                    break;
                }
                AutorotateProto$DeviceRotatedSensorSample autorotateProto$DeviceRotatedSensorSample = autorotateProto$DeviceRotatedSensorSampleArr2[i];
                if (autorotateProto$DeviceRotatedSensorSample != null) {
                    codedOutputByteBufferNano.writeMessage(2, autorotateProto$DeviceRotatedSensorSample);
                }
                i++;
            }
        }
        super.writeTo(codedOutputByteBufferNano);
    }

    /* access modifiers changed from: protected */
    @Override // com.google.protobuf.nano.MessageNano
    public int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        AutorotateProto$DeviceRotatedSensorHeader autorotateProto$DeviceRotatedSensorHeader = this.header;
        if (autorotateProto$DeviceRotatedSensorHeader != null) {
            computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(1, autorotateProto$DeviceRotatedSensorHeader);
        }
        AutorotateProto$DeviceRotatedSensorSample[] autorotateProto$DeviceRotatedSensorSampleArr = this.sample;
        if (autorotateProto$DeviceRotatedSensorSampleArr != null && autorotateProto$DeviceRotatedSensorSampleArr.length > 0) {
            int i = 0;
            while (true) {
                AutorotateProto$DeviceRotatedSensorSample[] autorotateProto$DeviceRotatedSensorSampleArr2 = this.sample;
                if (i >= autorotateProto$DeviceRotatedSensorSampleArr2.length) {
                    break;
                }
                AutorotateProto$DeviceRotatedSensorSample autorotateProto$DeviceRotatedSensorSample = autorotateProto$DeviceRotatedSensorSampleArr2[i];
                if (autorotateProto$DeviceRotatedSensorSample != null) {
                    computeSerializedSize += CodedOutputByteBufferNano.computeMessageSize(2, autorotateProto$DeviceRotatedSensorSample);
                }
                i++;
            }
        }
        return computeSerializedSize;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public AutorotateProto$DeviceRotatedSensorData mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                return this;
            }
            if (readTag == 10) {
                if (this.header == null) {
                    this.header = new AutorotateProto$DeviceRotatedSensorHeader();
                }
                codedInputByteBufferNano.readMessage(this.header);
            } else if (readTag == 18) {
                int repeatedFieldArrayLength = WireFormatNano.getRepeatedFieldArrayLength(codedInputByteBufferNano, 18);
                AutorotateProto$DeviceRotatedSensorSample[] autorotateProto$DeviceRotatedSensorSampleArr = this.sample;
                int length = autorotateProto$DeviceRotatedSensorSampleArr == null ? 0 : autorotateProto$DeviceRotatedSensorSampleArr.length;
                int i = repeatedFieldArrayLength + length;
                AutorotateProto$DeviceRotatedSensorSample[] autorotateProto$DeviceRotatedSensorSampleArr2 = new AutorotateProto$DeviceRotatedSensorSample[i];
                if (length != 0) {
                    System.arraycopy(autorotateProto$DeviceRotatedSensorSampleArr, 0, autorotateProto$DeviceRotatedSensorSampleArr2, 0, length);
                }
                while (length < i - 1) {
                    autorotateProto$DeviceRotatedSensorSampleArr2[length] = new AutorotateProto$DeviceRotatedSensorSample();
                    codedInputByteBufferNano.readMessage(autorotateProto$DeviceRotatedSensorSampleArr2[length]);
                    codedInputByteBufferNano.readTag();
                    length++;
                }
                autorotateProto$DeviceRotatedSensorSampleArr2[length] = new AutorotateProto$DeviceRotatedSensorSample();
                codedInputByteBufferNano.readMessage(autorotateProto$DeviceRotatedSensorSampleArr2[length]);
                this.sample = autorotateProto$DeviceRotatedSensorSampleArr2;
            } else if (!WireFormatNano.parseUnknownField(codedInputByteBufferNano, readTag)) {
                return this;
            }
        }
    }
}
