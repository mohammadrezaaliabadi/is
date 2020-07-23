package is.db.rw.bytes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import is.db.meta.SlottedPageHeader;
import lombok.RequiredArgsConstructor;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.nio.file.StandardOpenOption.*;

@RequiredArgsConstructor
public class SeekByteRW<T extends Serializable,ID> implements Closeable {
    private ObjectMapper mapper = new ObjectMapper();
    private Path path;
    private SeekableByteChannel header;
    private SlottedPageHeader sph;
    private final byte[] END_FILE;
    private final byte[] COMMA;
    private Class<T> tClass;
    private TypeReference<List<T>> typeReference;
    private boolean firstFlag = false;
    private SeekableByteChannel sbc;

    public SeekByteRW(Path path,Class<T> tClass,TypeReference typeReference) throws IOException {
        this.typeReference = typeReference;
        this.tClass = tClass;
        this.path = path;
        this.END_FILE = "]".getBytes();
        this.COMMA = ",".getBytes();
        if (!Files.exists(path.resolve("data.json"))){
            this.firstFlag = true;
        }
        this.sbc = Files.newByteChannel(path.resolve("data.json"),CREATE,WRITE,READ);
        this.header = Files.newByteChannel(path.resolve("header.json"), CREATE, WRITE,READ);
        if (header.size()!=0){
            readHeader();
        }else {
            this.sph = SlottedPageHeader.builder().sizes(new ArrayList<Integer>()).locations(new ArrayList<Long>()).build();
            writeHeader();
        }
    }

    public void writeHeader() throws IOException {
        if (Files.exists(path.resolve("header.json"))){
            Files.delete(path.resolve("header.json"));
        }
        this.header = Files.newByteChannel(path.resolve("header.json"), CREATE, WRITE,READ);
        header.write(ByteBuffer.wrap(mapper.writeValueAsBytes(sph)));
    }

    public void readHeader() throws IOException {
        ByteBuffer allocate = ByteBuffer.allocate((int) header.size());
        header.read(allocate);
        sph = mapper.readValue(allocate.array(),SlottedPageHeader.class);
    }
    public void save(T t) {
        try {
            byte[] bytes = mapper.writeValueAsBytes(t);
            sph.getSizes().add(bytes.length);
            if (firstFlag){
                sph.getLocations().add(2l);
                firstFlag = false;
                ByteBuffer wrap = ByteBuffer.allocate(bytes.length+3);
                wrap.put("[ ".getBytes());
                wrap.put(bytes);
                wrap.put(END_FILE);
                wrap.compact();
                sbc.write(wrap);
            }else {
                sbc.position(sbc.size()-1);
                sph.getLocations().add(sbc.position()+1);
                ByteBuffer wrap = ByteBuffer.allocate(bytes.length+2);
                wrap.put(COMMA);
                wrap.put(bytes);
                wrap.put(END_FILE);
                wrap.compact();
                sbc.write(wrap);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<T> findTempAll() {
        try (SeekableByteChannel sbc = Files.newByteChannel(path.resolve("data.json"), READ)) {
            ByteBuffer allocate = ByteBuffer.allocate((int) sbc.size());
            sbc.read(allocate);
            return mapper.readValue(allocate.array(),typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<T> findAll() {
        List<T> tList = new LinkedList<>();
        try {
            for (int i = 0;i< sph.getSizes().size();i++){
                ByteBuffer byteBuffer = ByteBuffer.allocate(sph.getSizes().get(i));
                sbc.position(sph.getLocations().get(i));
                sbc.read(byteBuffer);
                tList.add(mapper.readValue(byteBuffer.array(),tClass));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tList;
    }

    @Override
    public void close() throws IOException {
        writeHeader();
    }
    
    public int findLoc(ID id,Field field){
        try {
            for (int i = 0;i< sph.getSizes().size();i++){
                ByteBuffer byteBuffer = ByteBuffer.allocate(sph.getSizes().get(i));
                sbc.position(sph.getLocations().get(i));
                sbc.read(byteBuffer);
                T t = mapper.readValue(byteBuffer.array(), tClass);
                field.setAccessible(true);
                if(field.get(t).equals(id)){
                    return i;
                };
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public T find(int loc){
        try {
            ByteBuffer allocate = ByteBuffer.allocate(sph.getSizes().get(loc));
            sbc.position(sph.getLocations().get(loc));
            sbc.read(allocate);
            return  mapper.readValue(allocate.array(),tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void delete(int loc){
        sph.getLocations().remove(loc);
        sph.getSizes().remove(loc);
    }


    public void wrap(){
        //todo impl
    }
}
