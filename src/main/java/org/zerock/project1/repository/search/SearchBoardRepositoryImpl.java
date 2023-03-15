package org.zerock.project1.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.project1.entity.Board;
import org.zerock.project1.entity.QBoard;
import org.zerock.project1.entity.QMember;
import org.zerock.project1.entity.QReply;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
// QuerydslRepositorySupport은 Querydsl 라이브러리를 이용해서 쿼리메서드, 쿼리@보다
// 더 다양한 처리를 하기 위해서 사용
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport
    implements SearchBoardRepository {
  public SearchBoardRepositoryImpl() {
    super(Board.class);
  }

  @Override
  public Board search1() {
    log.info("search1...");
    QBoard board = QBoard.board;
    QReply reply = QReply.reply;
    QMember member = QMember.member;

    JPQLQuery<Board> jpqlQuery = from(board);
    jpqlQuery.leftJoin(member).on(board.writer.eq(member));
    jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

//    jpqlQuery.select(board).where(board.id.eq(1L));
    JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count()).groupBy(board);

    log.info("==================");
    log.info("jpqlQuery: " + jpqlQuery);
    log.info(tuple);
    log.info("==================");
//    List<Board> result = jpqlQuery.fetch();
    List<Tuple> result = tuple.fetch();
    log.info(result);
    return null;
  }

  @Override
  public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
    log.info("searchPage...");
    QBoard board = QBoard.board;
    QReply reply = QReply.reply;
    QMember member = QMember.member;

    JPQLQuery<Board> jpqlQuery = from(board);
    jpqlQuery.leftJoin(member).on(board.writer.eq(member));
    jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

//    jpqlQuery.select(board).where(board.id.eq(1L));
    JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());

    BooleanBuilder booleanBuilder = new BooleanBuilder();
    BooleanExpression expression = board.id.gt(0L);
    booleanBuilder.and(expression);

    if (type != null) {
      String[] typeArr = type.split("");
      BooleanBuilder conditioBuilder = new BooleanBuilder();
      for (String t : typeArr) {
        switch (t) {
          case "t":
            conditioBuilder.or(board.title.contains(keyword)); break;
          case "w":
            conditioBuilder.or(member.email.contains(keyword)); break;
          case "c":
            conditioBuilder.or(board.content.contains(keyword)); break;
        }
      }
      booleanBuilder.and(conditioBuilder);
    }
    tuple.where(booleanBuilder);

    // JPQL에서 Sort객체를 지원하지 않기 때문에 JPQL의 orderBy()에는
    // new OrderSpecifier<>(direction, orderByExpression.get(prop))를 파라미터로 처리
    Sort sort = pageable.getSort();
    sort.stream().forEach(order -> {
      Order direction = order.isAscending() ? Order.ASC : Order.DESC;
      String prop = order.getProperty(); //id, title
      System.out.println("prop>>"+prop);
      PathBuilder orderByExpression = new PathBuilder(Board.class, "board");
      tuple.orderBy(new OrderSpecifier<>(direction, orderByExpression.get(prop)));
    });
    tuple.groupBy(board);
    tuple.offset(pageable.getOffset());
    tuple.limit(pageable.getPageSize());

    List<Tuple> result = tuple.fetch();
    log.info(result);
    long count = tuple.fetchCount();
    log.info("Count: " + count);

    return new PageImpl<Object[]>(
        result.stream().map(t -> t.toArray()).collect(Collectors.toList()),
        pageable,
        count
    );
  }
}
