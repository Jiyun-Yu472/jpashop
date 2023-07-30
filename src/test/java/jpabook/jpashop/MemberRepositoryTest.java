package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {
	@Autowired
	MemberRepository memberRepository;
	
	@Test
	@Transactional //�⺻ �ɼ��� rollback = true�� �Ǿ�����. '@Rollback(false)'�ϸ� �ѹ� ����.
	public void testMember() throws Exception {
		//given
		Member member = new Member();
		member.setUserName("mamberA");
		
		//when
		Long savedId = memberRepository.Save(member);
		Member findMember = memberRepository.find(savedId);
		
		//then
		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
		Assertions.assertThat(findMember.getUserName()).isEqualTo(member.getUserName());
	}
}
