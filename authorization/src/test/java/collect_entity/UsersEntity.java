package collect_entity;

import com.bank.authorization.entity.UserEntity;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum UsersEntity {
    USER_1(1L, new UserEntity(1L, "USER", 1L, "user1")),
    USER_2(2L, new UserEntity(2L, "USER", 2L, "user2"));

    private final UserEntity user;
    private final Long id;

    UsersEntity(Long id, UserEntity user) {
        this.user = user;
        this.id = id;
    }

    public static UserEntity findUser(Long id) {
        return Arrays.stream(UsersEntity.values())
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseGet(() -> UsersEntity.USER_1)
                .getUser();
    }
}
