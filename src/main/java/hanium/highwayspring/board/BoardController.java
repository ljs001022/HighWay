package hanium.highwayspring.board;

import java.util.List;
import java.util.stream.Collectors;

import hanium.highwayspring.res.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("todo")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@PostMapping
	public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, BoardDTO dto){
		try {
			
			// (1) TodoEntity로 변환한다.
			BoardEntity entity = BoardDTO.toEntity(dto);
			
			// (2) id를 null로 초기화한다. 생성 당시에는 id가 없어야하기 때문이다.
			entity.setId(null);

			// (3) 임시 사용자 아이디를 설정해 준다. @AuthenticaionPrincipal에서 넘어온 userId로 설정
			entity.setUserId(userId);
			
			// (4) 서비스를 이용해 Todo 엔티티를 생성한다.
			List<BoardEntity> entities = service.create(entity);
			
			// (5) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
			List<BoardDTO> dtos = entities.stream().map(BoardDTO::new).collect(Collectors.toList());
			
			// (6) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
			ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder().data(dtos).build();
			
			// (7) ResponseDTO를 리턴한다.
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			// (8) ,혹시 예외가 있는 경우 dto 대신 error에 메시지를 넣어 리턴한다.
			String error = e.getMessage();
			ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId){
		
		// (1) 서비스 메서드 retrieve() 메서드를 사용해 Todo 리스트를 가져온다.
		List<BoardEntity> entities = service.retrieve(userId);
		
		// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
		List<BoardDTO> dtos = entities.stream().map(BoardDTO::new).collect(Collectors.toList());
		
		// (3) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
		ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder().data(dtos).build();
		
		// (4) ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping
	public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId, BoardDTO dto){
		
		// (1) dto를 entity로 변환한다.
		BoardEntity entity = BoardDTO.toEntity(dto);
		
		// (2) id를 userId로 초기화한다. 여기서 인증 추가
		entity.setUserId(userId);
		
		// (3) 서비스를 이용해 entity를 업데이트한다.
		List<BoardEntity> entities = service.update(entity);
		
		// (4) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
		List<BoardDTO> dtos = entities.stream().map(BoardDTO::new).collect(Collectors.toList());
		
		// (5) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
		ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder().data(dtos).build();
		
		// (6) ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId, BoardDTO dto){
		try {
			
			// (1) TodoEntity로 변환한다.
			BoardEntity entity = BoardDTO.toEntity(dto);
			
			// (2) 임시 사용자 아이디를 설정해 준다.
			entity.setUserId(userId);
			
			// (3) 서비스를 이용해 entity를 삭제한다.
			List<BoardEntity> entities = service.delete(entity);
			
			// (4) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환한다.
			List<BoardDTO> dtos = entities.stream().map(BoardDTO::new).collect(Collectors.toList());
			
			// (5) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
			ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder().data(dtos).build();
			
			// (6) ResponseDTO를 리턴한다.
			return ResponseEntity.ok().body(response);
		} catch(Exception e) {
			// (7) 혹시 예외가 있는 경우 dto 대신 error에 메시지를 넣어 리턴한다.
			String error = e.getMessage();
			ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
		
	}
}
