package hanium.highwayspring.board;
import hanium.highwayspring.school.School;
import hanium.highwayspring.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardDTO {
	private Long id;
	private String title;
	private String content;
	private Boolean accept;

	public static Board toEntity(final BoardDTO dto, final User user) {
		return Board.builder()
				.id(dto.getId())
				.title(dto.getTitle())
				.content(dto.getContent())
				.user(user)
				.school(user.getSchool())
				.accept(dto.getAccept())
				.build();
	}
}
