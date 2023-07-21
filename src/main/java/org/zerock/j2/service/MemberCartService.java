package org.zerock.j2.service;

import jakarta.transaction.Transactional;
import org.zerock.j2.dto.MemberCartDTO;
import org.zerock.j2.entity.MemberCart;

import java.util.List;

@Transactional
public interface MemberCartService {
    // 장바구니에 상품을 추가하는 메서드
    List<MemberCartDTO> addCart(MemberCartDTO memberCartDTO);

    // 이메일을 기반으로 사용자의 장바구니를 조회하는 메서드
    List<MemberCartDTO> getCart(String email);

    // DTO 객체를 MemberCart 엔티티로 변환하는 메서드 (역매핑)
    default MemberCart dtoToEntity(MemberCartDTO dto) { // 인터페이스에서 구현 가능한 default 메소드가 존재함
        MemberCart entity = MemberCart.builder().cno(dto.getCno()).email(dto.getEmail()).pno(dto.getPno()).build();
        return entity;
    }

    // MemberCart 엔티티를 DTO 객체로 변환하는 메서드 (역매핑)
    default MemberCartDTO entityToDTO(MemberCart entity) { // 인터페이스에서 구현 가능한 default 메소드가 존재함
        MemberCartDTO dto = MemberCartDTO.builder().cno(entity.getCno()).email(entity.getEmail()).pno(entity.getPno()).build();
        return dto;
    }
}
