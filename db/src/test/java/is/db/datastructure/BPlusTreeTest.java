package is.db.datastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import is.db.datastructure.BPlusTree.RangePolicy;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BPlusTreeTest {
    @Test
    public void test() throws IOException, ClassNotFoundException {
        BPlusTree<Integer, String> bpt = new BPlusTree<Integer, String>(4);
        bpt.insert(0, "a");
        bpt.insert(1, "b");
        bpt.insert(2, "c");
        bpt.insert(3, "d");
        bpt.insert(4, "e");
        bpt.insert(5, "f");
        bpt.insert(6, "g");
        bpt.insert(7, "h");
        bpt.insert(8, "i");
        bpt.insert(9, "j");
        bpt.delete(1);
        bpt.delete(3);
        bpt.delete(5);
        bpt.delete(7);
        bpt.delete(9);
        assertEquals(bpt.search(0), "a");
        assertEquals(bpt.search(1), null);
        assertEquals(bpt.search(2), "c");
        assertEquals(bpt.search(3), null);
        assertEquals(bpt.search(4), "e");
        assertEquals(bpt.search(5), null);
        assertEquals(bpt.search(6), "g");
        assertEquals(bpt.search(7), null);
        assertEquals(bpt.search(8), "i");
        assertEquals(bpt.search(9), null);
        SeekableByteChannel sbc = Files.newByteChannel(Paths.get("data.ser"), CREATE, WRITE, READ);
        byte[] serialize = serialize(bpt);

        sbc.write(ByteBuffer.wrap(serialize));
        sbc.position(0);
        ByteBuffer allocate = ByteBuffer.allocate((int) sbc.size());
        sbc.read(allocate);

        bpt = (BPlusTree<Integer, String>) deserialize(allocate.array());
        System.out.println(bpt);
        assertEquals(bpt.search(0), "a");
        assertEquals(bpt.search(1), null);
        assertEquals(bpt.search(2), "c");
        assertEquals(bpt.search(3), null);
        assertEquals(bpt.search(4), "e");
        assertEquals(bpt.search(5), null);
        assertEquals(bpt.search(6), "g");
        assertEquals(bpt.search(7), null);
        assertEquals(bpt.search(8), "i");
        assertEquals(bpt.search(9), null);

    }

    @Test
    public void test2(){
    }


    @Test
    public void testSearchRange() {
        BPlusTree<Integer, String> bpt = new BPlusTree<Integer, String>(4);
        bpt.insert(0, "a");
        bpt.insert(1, "b");
        bpt.insert(2, "c");
        bpt.insert(3, "d");
        bpt.insert(4, "e");
        bpt.insert(5, "f");
        bpt.insert(6, "g");
        bpt.insert(7, "h");
        bpt.insert(8, "i");
        bpt.insert(9, "j");
        assertArrayEquals(
                bpt.searchRange(3, RangePolicy.EXCLUSIVE, 7,
                        RangePolicy.EXCLUSIVE).toArray(), new String[] { "e",
                        "f", "g" });
        assertArrayEquals(
                bpt.searchRange(3, RangePolicy.INCLUSIVE, 7,
                        RangePolicy.EXCLUSIVE).toArray(), new String[] { "d",
                        "e", "f", "g" });
        assertArrayEquals(
                bpt.searchRange(3, RangePolicy.EXCLUSIVE, 7,
                        RangePolicy.INCLUSIVE).toArray(), new String[] { "e",
                        "f", "g", "h" });
        assertArrayEquals(
                bpt.searchRange(3, RangePolicy.INCLUSIVE, 7,
                        RangePolicy.INCLUSIVE).toArray(), new String[] { "d",
                        "e", "f", "g", "h" });
    }

    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        return baos.toByteArray();
    }

    public Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
    }

}