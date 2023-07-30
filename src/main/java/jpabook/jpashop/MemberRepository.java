package jpabook.jpashop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
	@PersistenceContext
	private EntityManager em;
	
	public Member find(Long id) {
		return em.find(Member.class, id);
	}
	
	public Long Save(Member member) {
		em.persist(member);
		return member.getId();
	}
}
