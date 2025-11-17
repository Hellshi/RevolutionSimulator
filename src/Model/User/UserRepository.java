package Model.User;

import Model.Data.Storage;
import Model.Repository.BaseRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserRepository extends BaseRepository<UserEntity> {
    public UserRepository(Storage storage) {
        super(storage);
    }

    public List<UserEntity> getAllUsersAsObjects() {
        List<UserEntity> list = new ArrayList<>();
        // Pega o mapa cru do Storage (ID -> JSON String)
        Map<Integer, String> rawData = super.readAllRaw();

        for (Map.Entry<Integer, String> entry : rawData.entrySet()) {
            String json = entry.getValue();
            UserEntity user = UserEntity.fromJson(json);
            // Garante que o ID esteja setado (caso venha do storage)
            if (user.getId() == null || user.getId().equals("null")) {
                user.setId(String.valueOf(entry.getKey()));
            }
            list.add(user);
        }
        return list;
    }
}
