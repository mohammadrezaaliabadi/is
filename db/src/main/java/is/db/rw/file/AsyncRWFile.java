package is.db.rw.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.util.concurrent.Future;

import static java.nio.file.StandardOpenOption.*;

@NoArgsConstructor
public class AsyncRWFile<T extends Serializable> {
    private ObjectMapper mapper = new ObjectMapper();

    public void writeDB(Path path, T t) {
        try (AsynchronousFileChannel afc = AsynchronousFileChannel.open(path, CREATE, WRITE)) {
            WriterHandlerFile writerHandler = new WriterHandlerFile();
            AttachmentFile attach = new AttachmentFile();
            attach.asynchronousFileChannel = afc;
            attach.buffer = ByteBuffer.wrap(mapper.writeValueAsBytes(t));
            attach.path = path;
            afc.write(attach.buffer, 0, attach, writerHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public T readDB(Path path, Class<T> tClass) {
        try (AsynchronousFileChannel afc = AsynchronousFileChannel.open(path, READ)) {
            int fileSize = (int) afc.size();
            ByteBuffer byteBuffer = ByteBuffer.allocate(fileSize);
            Future<Integer> result = afc.read(byteBuffer, 0);
            return mapper.readValue(byteBuffer.array(), tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        return baos.toByteArray();
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
    }


}
