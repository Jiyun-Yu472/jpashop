package jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor //it can be possible to use @Autowired instead of @PersistenceContext. Becuase of JPA library.
public class MemberRepository {
//	@PersistenceContext
//	private EntityManager em;
	
	private final EntityManager em;
	
	public  void save(Member member) {
		em.persist(member); //member 객체 저장
	}
	
	public Member findOne(Long id) {
		return em.find(Member.class, id); // 단건 조회
	}
	
	public List<Member> findAll() {
		List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList(); //jpql을 이용한 리스트 조회
		return result;
	}
	
	public List<Member> findByName(String name) {
		List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class).setParameter("name", name).getResultList(); //
		return result;
	}
}
