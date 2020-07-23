package is.db.rw.file;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;

public class AttachmentFile {
    public Path path;
    public ByteBuffer buffer;
    public AsynchronousFileChannel asynchronousFileChannel;
}
