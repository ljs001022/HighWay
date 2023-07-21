package hanium.highwayspring.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Optional;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO{
    private Long userNo;
    private String userId;
    private String userName;
    private Long userGrade;
    private Long userNumber;
    private Long userRole;

    public UserDTO(final User entity) {
        this.userNo = entity.getId();
        this.userId = entity.getUid();
        this.userName = entity.getName();
        this.userGrade = entity.getGrade();
        this.userNumber = entity.getNumber();
        this.userRole = entity.getRole();
    }

    public static UserDTO toEntity(final Optional<User> entity) {
        return UserDTO.builder()
                .userNo(entity.get().getId())
                .userId(entity.get().getUid())
                .userName(entity.get().getName())
                .userGrade(entity.get().getGrade())
                .userNumber(entity.get().getNumber())
                .userRole(entity.get().getRole())
                .build();
    }
}

