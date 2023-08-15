package jpabook.jpashop.service;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

@RunWith(SpringRunner.class) //It is used when it is started with Spring.
@SpringBootTest //It make spring environment before test
@Transactional
public class MemberServiceTest {
	@Autowired
	MemberService memberService;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	EntityManager em;	
	
	@Test
	public void singUp() throws Exception {
		//given
		Member member = new Member();
		member.setName("kim");
		
		//when
		Long savedId = memberService.join(member);
		
		//then
		em.flush(); //before rollback, it performs all queries.
		assertEquals(member, memberRepository.findOne(savedId));
	}
	
	@Test
	public void signUpByDuplicatedId() throws Exception {
		//given
		Member member1 = new Member();
		member1.setName("kim");
		Member member2 = new Member();
		member2.setName("kim");
		
		//when
		memberService.join(member1);
		try {
			memberService.join(member2);
		} catch(IllegalStateException e) {
			return; 
		}
		
		//then
		Assertions.fail("예외가 발생해야한다.");
	}
}






















