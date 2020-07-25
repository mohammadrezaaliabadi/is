package is.db.meta;


import is.db.annotation.ForeignKey;
import is.db.rw.file.AsyncRWFile;
import lombok.Getter;
import lombok.Setter;
import org.reflections.Reflections;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import java.io.Closeable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Setter
@Getter
public class MetaDB implements Closeable {
    private DB db;
    private AsyncRWFile<DB> dbaAsyncRWFile = new AsyncRWFile<>();
    private AsyncRWFile<Table> tableAsyncRWFile = new AsyncRWFile<>();
    private Path pathDB;
    private String dBName;

    public MetaDB(Path path, String dBName) {
        this.pathDB = path;
        this.dBName = dBName;
        this.pathDB = pathDB.resolve(dBName);

    }

    public void writeMetaDb() throws IOException {
        Files.createDirectories(pathDB);
        dbaAsyncRWFile.writeDB(pathDB.resolve(dBName + ".json"), db);
        db.getTables().forEach((name, table) -> {
            try {
                if (!Files.exists(pathDB.resolve(table.getName()))) {
                    Files.createDirectory(pathDB.resolve(table.getName()));
                }
                tableAsyncRWFile.writeDB(pathDB.resolve(Paths.get(table.getName(), table.getName() + ".json")), table);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void readMetaDb() {
        this.db = dbaAsyncRWFile.readDB(pathDB.resolve(dBName + ".json"), DB.class);
    }

    public void loadTableInJavaClass(String prefixPackage) {
        Map<String, Table> tables = loadTablesInPackage(prefixPackage);
        db = DB.builder().name(dBName).tables(tables).build();
    }

    private Map<String, Table> loadTablesInPackage(String prefixPackage) {
        Reflections reflections = new Reflections(prefixPackage);
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(javax.persistence.Table.class);
        Map<String, Table> tables = new HashMap<>();
        typesAnnotatedWith.forEach(aClass -> {
            try {
                String nameTable = "";
                Field[] fields = aClass.getDeclaredFields();
                List<Field> keys = new ArrayList<>();
                Index[] indices = null;
                for (Annotation annotation : aClass.getAnnotations()) {
                    if (annotation instanceof javax.persistence.Table) {
                        var table =(javax.persistence.Table) annotation;
                        nameTable = table.name();
                        indices = table.indexes();
                        break;
                    }
                }
                for (Field field : fields) {
                    if (field.getDeclaredAnnotations().length != 0) {
                        for (Annotation annotation : field.getDeclaredAnnotations()) {
                            if (annotation instanceof Id || annotation instanceof ForeignKey) {
                                keys.add(field);
                                break;
                            }
                        }
                    } else {
                    }
                }
                tables.put(nameTable, Table.builder().name(nameTable).keys(keys).fields(fields).indices(indices).build());

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        return tables;
    }

    @Override
    public void close() throws IOException {
        writeMetaDb();
    }
}
