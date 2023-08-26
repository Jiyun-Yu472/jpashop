package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {
	@Autowired
	MemberRepository memberRepository;
	
	@Test
	@Transactional //default value is rollback = true, if you want to watch result data, set value to '@Rollback(false)'.
	public void testMember() throws Exception {
		//given
		Member member = new Member();
		member.setName("mamberA");
		
		//when
		memberRepository.save(member);
		
		//then
		Assertions.assertThat(member.getName());
	}
}
