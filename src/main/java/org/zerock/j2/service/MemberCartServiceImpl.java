package org.zerock.j2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.j2.dto.MemberCartDTO;
import org.zerock.j2.entity.MemberCart;
import org.zerock.j2.repository.MemberCartRepository;
import org.zerock.j2.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberCartServiceImpl implements MemberCartService {

    private final MemberCartRepository cartRepository;

    @Override
    public List<MemberCartDTO> addCart(MemberCartDTO memberCartDTO) { // 회원 장바구니에 상품을 추가하는 메서드

        MemberCart cart = dtoToEntity(memberCartDTO); // DTO를 MemberCart엔티티로 변환
        cartRepository.save(cart); // MemberCart 엔티티를 데이터베이스에 저장
        /** JpaRepository는 Spring Data JPA에서 제공하는 인터페이스로, CRUD(Create, Read, Update, Delete) 작업을 위한 기본적인 메서드들을 이미 구현해놓은 인터페이스입니다.
         이를 상속한 인터페이스나 클래스는 save, findById, findAll, deleteById 등의 메서드를 사용할 수 있게 됩니다. */

        // 장바구니에 있는 모든 상품 조회
        List<MemberCart> cartList = cartRepository.selectCart(memberCartDTO.getEmail());
        // 조회된 MemberCart 엔티티들을 MemberCartDTO로 변환하여 리스트로 반환
        return cartList.stream().map(entity -> entityToDTO(entity)).collect(Collectors.toList()); //group by 처리 안해놓음
    }

    // 회원의 장바구니에 담긴 상품 리스트 조회하는 메서드
    @Override
    public List<MemberCartDTO> getCart(String email) {

        // 회원의 장바구니에 담긴 모든 상품 조회
        List<MemberCart> cartList = cartRepository.selectCart(email);

        // 조회된 MemberCart 엔티티들을 MemberCartDTO로 변환하여 리스트로 반환
        return cartList.stream().map(entity -> entityToDTO(entity)).collect(Collectors.toList());
    }
}
