package hanium.highwayspring.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private Long userNo;
    private String userId;
    private String token;
    private String userName;
    private String userEmail;
    private String userGender;
    private Long userAge;
    private Long userRole;

    public UserDTO(final User entity) {
        this.userNo = entity.getId();
        this.userId = entity.getUid();
        this.userName = entity.getName();
        this.userEmail = entity.getEmail();
        this.userGender = entity.getGender();
        this.userAge = entity.getAge();
        this.userRole = entity.getRole();
    }

    public static UserDTO toEntity(final Optional<User> entity) {
        return UserDTO.builder()
                .userNo(entity.get().getId())
                .userId(entity.get().getUid())
                .userName(entity.get().getName())
                .userEmail(entity.get().getEmail())
                .userGender(entity.get().getGender())
                .userAge(entity.get().getAge())
                .userRole(entity.get().getRole())
                .build();
    }
}
