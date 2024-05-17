package postecarga.repository;

import postecarga.PosteCarga;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

public class PosteCargaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public PosteCarga findById(Long id) {
        return entityManager.find(PosteCarga.class, id);
    }

    @Transactional
    public PosteCarga save(PosteCarga posteCarga) {
        if (posteCarga.getId() == null) {
            entityManager.persist(posteCarga);
            return posteCarga;
        } else {
            return entityManager.merge(posteCarga);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        PosteCarga posteCarga = findById(id);
        if (posteCarga != null) {
            entityManager.remove(posteCarga);
        }
    }

    public List<PosteCarga> findAll() {
        return entityManager.createQuery("SELECT p FROM PosteCarga p", PosteCarga.class).getResultList();
    }
}
