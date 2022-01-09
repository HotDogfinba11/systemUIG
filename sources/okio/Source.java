package okio;

import java.io.Closeable;
import java.io.IOException;

/* compiled from: Source.kt */
public interface Source extends Closeable {
    @Override // java.io.Closeable, java.lang.AutoCloseable
    void close() throws IOException;

    long read(Buffer buffer, long j) throws IOException;
}
