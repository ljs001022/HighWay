package hanium.highwayspring.board.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hanium.highwayspring.board.Board;
import hanium.highwayspring.board.QBoard;
import hanium.highwayspring.board.dto.BoardResponseDto;
import hanium.highwayspring.board.dto.QBoardResponseDto;
import hanium.highwayspring.user.QUser;
import org.hibernate.criterion.Projection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public BoardRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Slice<BoardResponseDto> getBoardSlice(Pageable pageable) {
        QBoard board = QBoard.board;
        QUser user = QUser.user;
        List<BoardResponseDto> dtoList = jpaQueryFactory.select(new QBoardResponseDto(board, user))
                .from(board)
                .innerJoin(user)
                .on(board.user.eq(user))
                .where(board.state.eq(1L))
                .orderBy(board.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .fetch();

        boolean hasNext = false;
        if(dtoList.size() > pageable.getPageSize()) {
            dtoList.remove(pageable.getPageSize());
            hasNext =true;
        }

        return new SliceImpl<>(dtoList, pageable, hasNext);
    }
}
