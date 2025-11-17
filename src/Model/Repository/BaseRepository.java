package Model.Repository;

import Model.Data.Storage;
import java.lang.reflect.Field;
import java.util.Map;

public class BaseRepository<T> {
    private final Storage storage;
    private int nextId = 1;

    public BaseRepository(Storage storage) {
        this.storage = storage;
        // Atualiza o nextId baseado no último ID salvo no arquivo
        this.nextId = storage.getAll().keySet().stream()
                .max(Integer::compare)
                .orElse(0) + 1;
    }

    public String create(BaseEntity<T> record) {
        int id = nextId++;

        setIdViaReflection(record, String.valueOf(id));

        String jsonRecord = record.toString();
        storage.put(id, jsonRecord);

        return jsonRecord;
    }

    public String read(int id) {
        return storage.get(id);
    }

    public boolean update(int id, String newJsonRecord) {
        if (!storage.contains(id)) return false;
        storage.put(id, newJsonRecord);
        return true;
    }

    public boolean delete(int id) {
        return storage.remove(id);
    }

    protected Map<Integer, String> readAllRaw() {
        return storage.getAll();
    }

    private void setIdViaReflection(Object entity, String idValue) {
        try {
            Field field = null;
            Class<?> clazz = entity.getClass();
            while (clazz != null && field == null) {
                try {
                    field = clazz.getDeclaredField("id");
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            }

            if (field != null) {
                field.setAccessible(true);
                field.set(entity, idValue);
            }
        } catch (Exception e) {
            System.out.println("Aviso: Não foi possível setar o ID via reflection: " + e.getMessage());
        }
    }

    public String listAll() {
        return storage.getAll().toString();
    }
}