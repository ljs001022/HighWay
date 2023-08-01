package hanium.highwayspring.board.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardRequestDTO {
	private Long id;
	private String content;
	private Long state;
	private String uname;
}
