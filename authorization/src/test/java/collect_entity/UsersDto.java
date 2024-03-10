package collect_entity;

import com.bank.authorization.dto.UserDto;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum UsersDto {
    USER_1(1L, new UserDto(1L, "USER", "user1", 1L)),
    USER_2(2L, new UserDto(2L, "USER", "user2", 2L));

    private final UserDto user;
    private final Long id;

    UsersDto(Long id, UserDto user) {
        this.user = user;
        this.id = id;
    }

    public static UserDto findUser(Long id) {
        return Arrays.stream(UsersDto.values())
                .filter(user -> user.getId() .equals(id))
                .findFirst()
                .orElseGet(() -> UsersDto.USER_1)
                .getUser();
    }
}
