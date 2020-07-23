package is.db.rw.file;

import java.io.IOException;
import java.nio.channels.CompletionHandler;

public class WriterHandlerFile implements CompletionHandler<Integer, AttachmentFile> {

    @Override
    public void completed(Integer result, AttachmentFile attachmentFile) {
        try {
            attachmentFile.asynchronousFileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void failed(Throwable exc, AttachmentFile attachmentFile) {
        try {
            attachmentFile.asynchronousFileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
