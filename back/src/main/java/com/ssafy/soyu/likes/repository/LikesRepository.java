package com.ssafy.soyu.likes.repository;

import com.ssafy.soyu.item.entity.Item;
import com.ssafy.soyu.likes.entity.Likes;
import com.ssafy.soyu.member.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes, Long> {
  @Query("select l from Likes l join fetch l.item i where l.member.id = :memberId and l.status = true and i.itemStatus != 'DELETED' and i.itemStatus != 'SOLD'")
  List<Likes> findLikesByMemberId(@Param("memberId") Long memberId);

  Likes findLikesByItemAndMember(Item item, Member member);

  @Query("SELECT COUNT(l) FROM Likes l WHERE l.item.id = :itemId AND l.status = true")
  Integer countLikeByItemId(@Param("itemId") Long itemId);

  @Query("SELECT COUNT(l) FROM Likes l WHERE l.member.id = :memberId AND l.item.id = :itemId")
  Integer getLikeByMemberWithItem(Long memberId, Long itemId);
}
