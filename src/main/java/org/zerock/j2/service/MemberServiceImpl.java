package org.zerock.j2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.j2.dto.MemberDTO;
import org.zerock.j2.entity.Member;
import org.zerock.j2.repository.MemberRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    // 로그인 시 예외 처리를 위한 내부 정적 클래스 생성
    public static final class MemberLoginException extends RuntimeException { //RUNTIME인 이유?
        public MemberLoginException(String msg) {
            super(msg);
        }
    }

    // 로그인 기능 구현
    @Override
    public MemberDTO login(String email, String pw) {
        MemberDTO memberDTO = null;

        try {
            Optional<Member> result = memberRepository.findById(email); // Optional에서 값이 없으면 예외 발생
            Member member = result.orElseThrow();

            // 입력된 비밀번호가 회원의 비밀번호와 일치하지 않으면 예외 발생
            if (!member.getPw().equals(pw)) {

                throw new MemberLoginException("Password incorrect");
            }

            // 로그인에 성공하면 회원의 정보를 MemberDTO로 변환하여 반환
            memberDTO = MemberDTO.builder().email(member.getEmail())
                    .pw("") // 보안을 위해 비밀번호를 빈 문자열로 설정 (클라이언트에게 비밀번호는 반환하지 않음)
                    .nickname(member.getNickname()).admin(member.isAdmin()).build();

        } catch (Exception e) {
            // 예외가 발생하면 로그인 실패 처리를 위해 MemberLoginException 예외를 다시 던짐
            throw new MemberLoginException(e.getMessage());
        }
        return memberDTO;
    }

    // 이메일로 회원 조회 기능 구현
    @Override
    public MemberDTO getMemberWithEmail(String email) {
        Optional<Member> result = memberRepository.findById(email);
        if (result.isPresent()) {
            // 회원이 존재하면 회원의 정보를 MemberDTO로 변환하여 반환
            Member member = result.get();
            MemberDTO dto = MemberDTO.builder().email(member.getEmail()).nickname(member.getNickname()).admin(member.isAdmin()).build();
            return dto;
        }
        // 회원 데이터베이스에 존재하지 않는 이메일인 경우, 임의의 비밀번호와 닉네임으로 새로운 소셜 회원을 생성하여 저장 후 반환
        Member socialMember = Member.builder()
                .email(email)
                .pw(UUID.randomUUID().toString())  // 임의의 비밀번호 생성
                .nickname("SOCIAL_MEMBER") // 닉네임 설정
                .build();
        memberRepository.save(socialMember);
        MemberDTO dto = MemberDTO.builder().email(socialMember.getEmail()).nickname(socialMember.getNickname()).admin(socialMember.isAdmin()).build();
        return dto;
    }
}
