package com.ssafy.soyu.item.Service;

import com.ssafy.soyu.chat.Chat;
import com.ssafy.soyu.chat.ChatRepository;
import com.ssafy.soyu.history.domain.History;
import com.ssafy.soyu.history.repository.HistoryRepository;
import com.ssafy.soyu.item.domain.Item;
import com.ssafy.soyu.item.domain.request.ItemCreateRequest;
import com.ssafy.soyu.item.domain.ItemStatus;
import com.ssafy.soyu.item.domain.request.ItemStatusRequest;
import com.ssafy.soyu.item.domain.request.ItemUpdateRequest;
import com.ssafy.soyu.item.repository.ItemRepository;
import com.ssafy.soyu.member.domain.Member;
import com.ssafy.soyu.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
  private final ItemRepository itemRepository;
  private final MemberRepository memberRepository;
  private final ChatRepository chatRepository;
  private final HistoryRepository historyRepository;

  public void save(Long memberId, ItemCreateRequest itemRequest) {

    Member member = memberRepository.getReferenceById(memberId);

    Item item = new Item(member, itemRequest.getTitle(), itemRequest.getContent(), itemRequest.getPrice(), itemRequest.getItemCategories());

    itemRepository.save(item);
  }


  public void update(ItemUpdateRequest itemUpdateRequest) {
    // 바꾸려는 아이템 객체를 가져온다
    Item item = itemRepository.getReferenceById(itemUpdateRequest.getItemId());

    // item 의 값을 변경해서 더티체킹을 통한 업데이트를 진행한다
    item.updateItem(itemUpdateRequest.getTitle(), itemUpdateRequest.getContent(), itemUpdateRequest.getPrice(), itemUpdateRequest.getItemCategories());
  }

  public void updateStatus(ItemStatusRequest itemStatusRequest) {
    Item item = itemRepository.getReferenceById(itemStatusRequest.getItemId());

    // 더티 체킹을 통한 upaate
    item.updateItemStatus(itemStatusRequest.getItemStatus());
  }

  @Transactional
  public void makeReserve(Long chatId){
    Chat chat = chatRepository.findById(chatId).get();
    Item item = chat.getItem();
    ItemStatus status = ItemStatus.from("reserve");
    itemRepository.updateStatus(item.getId(), status); //아이템 상태 변경
    History history = new History(item, chat.getBuyer());

    //구매내역에 추가
    historyRepository.save(history);

    //payAction API

  }
}