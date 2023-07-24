package hanium.highwayspring.user;

import hanium.highwayspring.school.School;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity(name = "USER_TB")
@Data
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uid;
    private String pass;
    private String name;
    @Column(columnDefinition = "integer default 0")
    private Long role;
    @Column(columnDefinition = "integer default 1")
    private Long school;
}
