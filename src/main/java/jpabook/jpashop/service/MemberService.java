package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //It makes constructor only for objects whose variables are specified as final
public class MemberService {
	private final MemberRepository memberRespository;
	
	//sign up
	@Transactional
	public Long join(Member member) {
		validateDuplicateMember(member); //It validate duplication
		memberRespository.save(member); 
		return member.getId();
	}
	
	private void validateDuplicateMember(Member member) {
		 List<Member> findMembers = memberRespository.findByName(member.getName());
		 if(!findMembers.isEmpty()) {
			 throw new IllegalStateException("이미 존재하는 회원입니다.");
		 }
	}
	
	//List of members
	public List<Member> findMembers() {
		return memberRespository.findAll();
	}
	
	//find one if members
	public Member findOne(Long memberId) {
		return memberRespository.findOne(memberId);
	}
}
