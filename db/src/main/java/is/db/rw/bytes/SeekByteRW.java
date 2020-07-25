package is.db.rw.bytes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import is.db.datastructure.BPlusTree;
import is.db.meta.SlottedPageHeader;
import is.db.meta.Table;
import lombok.RequiredArgsConstructor;

import javax.persistence.Index;
import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.nio.file.StandardOpenOption.*;

@RequiredArgsConstructor
public class SeekByteRW<T extends Serializable, ID extends Comparable<? super ID>> implements Closeable {
    private ObjectMapper mapper = new ObjectMapper();
    private ObjectMapperJava mapperJava = new ObjectMapperJava();
    private Path path;
    private SeekableByteChannel header;
    private SlottedPageHeader sph;
    private final byte[] END_FILE;
    private final byte[] COMMA;
    private Class<T> tClass;
    private TypeReference<List<T>> typeReference;
    private boolean firstFlag = false;
    private SeekableByteChannel sbc;
    private Table table;
    private Map<String,BPlusTree> indexes = new HashMap<>();
    private Map<String,SeekableByteChannel> fileIndexes = new HashMap<>();

    public SeekByteRW(Path path,Table table, Class<T> tClass, TypeReference typeReference) throws IOException {
        this.table = table;
        this.fileIndexes.put(table.getKeys().get(0).getName(),Files.newByteChannel(path.resolve(table.getKeys().get(0).getName()+".ser"), CREATE, WRITE, READ));
        for (Index index:table.getIndices()) {
            fileIndexes.put(index.name(),Files.newByteChannel(path.resolve(index.name()+".ser"), CREATE, WRITE, READ));
        }
        fileIndexes.forEach((s, fileIndex) -> {
            try {
                System.out.println(s);
                indexes.put(s,new BPlusTree(10));
                if (fileIndex.size()==0){
                }else {
                    readIndex(s,fileIndex,indexes.get(s));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.typeReference = typeReference;
        this.tClass = tClass;
        this.path = path;
        this.END_FILE = "]".getBytes();
        this.COMMA = ",".getBytes();
        this.sbc = Files.newByteChannel(path.resolve("data.json"), CREATE, WRITE, READ);
        this.header = Files.newByteChannel(path.resolve("header.json"), CREATE, WRITE, READ);
        if (sbc.size() ==0) {
            this.firstFlag = true;
        }
        if (header.size() != 0) {
            readHeader();
        } else {
            this.sph = SlottedPageHeader.builder().sizes(new ArrayList<Integer>()).locations(new ArrayList<Long>()).build();
        }
    }
    public void writeIndex(String name,SeekableByteChannel fileIndex,BPlusTree index) throws IOException{
        if (Files.exists(path.resolve(name + ".ser"))) {
            Files.delete(path.resolve(name + ".ser"));
        }
        fileIndex = Files.newByteChannel(path.resolve(name + ".ser"), CREATE, WRITE, READ);
        fileIndex.write(ByteBuffer.wrap(mapperJava.serialize(index)));
    }
    public void readIndex(String name,SeekableByteChannel fileIndex,BPlusTree index) throws IOException{
        ByteBuffer allocate = ByteBuffer.allocate((int) fileIndex.size());
        fileIndex.read(allocate);
        try {
            index = (BPlusTree<ID, Integer>) mapperJava.deserialize(allocate.array());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeHeader() throws IOException {
        if (Files.exists(path.resolve("header.json"))) {
            Files.delete(path.resolve("header.json"));
        }
        this.header = Files.newByteChannel(path.resolve("header.json"), CREATE, WRITE, READ);
        header.write(ByteBuffer.wrap(mapper.writeValueAsBytes(sph)));
    }

    public void readHeader() throws IOException {
        ByteBuffer allocate = ByteBuffer.allocate((int) header.size());
        header.read(allocate);
        sph = mapper.readValue(allocate.array(), SlottedPageHeader.class);
    }
    public T save(T t) {
        try {
            Field field = table.getKeys().get(0);
            field.setAccessible(true);
            ID id = (ID) field.get(t);
            if (findById(id)!=null){
                return null;
            }
            byte[] bytes = mapper.writeValueAsBytes(t);
            sph.getSizes().add(bytes.length);
            if (firstFlag) {
                sph.getLocations().add(2l);
                ByteBuffer wrap = ByteBuffer.allocate(bytes.length + 3);
                wrap.put("[ ".getBytes());
                wrap.put(bytes);
                wrap.put(END_FILE);
                wrap.compact();
                sbc.write(wrap);
                firstFlag = false;
            } else {

                sbc.position(sbc.size() - 1);
                sph.getLocations().add(sbc.position() + 1);
                ByteBuffer wrap = ByteBuffer.allocate(bytes.length + 2);
                wrap.put(COMMA);
                wrap.put(bytes);
                wrap.put(END_FILE);
                wrap.compact();
                sbc.write(wrap);
            }
            int loc = sph.getSizes().size()-1;
            insertToIndexes(t,loc);
            return findById(id);
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void insertToIndexes(T t,int loc){
        indexes.forEach((s, bPlusTree) -> {
            for (Field f:table.getFields()){
                f.setAccessible(true);
                if (s.equals(f.getName())){
                    try {
                        Object o = f.get(t);
                        if (o instanceof Integer){
                            bPlusTree.insert((int)o,loc);
                        }else if (o instanceof Long){
                            bPlusTree.insert((long)o,loc);
                        }else if (o instanceof Double){
                            bPlusTree.insert((double)o,loc);
                        } else if (o instanceof String){
                            bPlusTree.insert((String)o,loc);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void removeToIndexes(T t){
        indexes.forEach((s, bPlusTree) -> {
            for (Field f:table.getFields()){
                if (s.equals(f.getName())){
                    try {
                        Object o = f.get(t);
                        if (o instanceof Integer){
                            bPlusTree.delete((int)o);
                        }else if (o instanceof Long){
                            bPlusTree.delete((long)o);
                        }else if (o instanceof Double){
                            bPlusTree.delete((double)o);
                        }
                        else if (o instanceof String){
                            bPlusTree.delete((String)o);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public List<T> findTempAll() {
        try (SeekableByteChannel sbc = Files.newByteChannel(path.resolve("data.json"), READ)) {
            ByteBuffer allocate = ByteBuffer.allocate((int) sbc.size());
            sbc.read(allocate);
            return mapper.readValue(allocate.array(), typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<T> findAll() {
        List<T> tList = new LinkedList<>();
        try {
            for (int i = 0; i < sph.getSizes().size(); i++) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(sph.getSizes().get(i));
                sbc.position(sph.getLocations().get(i));
                sbc.read(byteBuffer);
                tList.add(mapper.readValue(byteBuffer.array(), tClass));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tList;
    }

    @Override
    public void close() throws IOException {
        writeHeader();
        fileIndexes.forEach((s, seekableByteChannel) -> {
            try {
                writeIndex(s,seekableByteChannel,indexes.get(s));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public T find(int loc) {
        try {
            ByteBuffer allocate = ByteBuffer.allocate(sph.getSizes().get(loc));
            sbc.position(sph.getLocations().get(loc));
            sbc.read(allocate);
            return mapper.readValue(allocate.array(), tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public T findById(ID id){
        Integer loc = (Integer) indexes.get(table.getKeys().get(0).getName()).search(id);
        return loc!=null? find(loc):null;
    }
    public void delete(ID id){
        Integer loc = (Integer) indexes.get(table.getKeys().get(0).getName()).search(id);
        removeToIndexes(find(loc));
        sph.getLocations().remove(loc);
        sph.getSizes().remove(loc);
    }

    public void wrap() {
        //todo impl

    }
}
