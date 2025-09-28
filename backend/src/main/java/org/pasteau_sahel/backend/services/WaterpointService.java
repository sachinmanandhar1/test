package org.pasteau_sahel.backend.services;

import org.pasteau_sahel.backend.entities.Waterpoint;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class WaterpointService {

    @PersistenceContext(unitName = "pasteau-sahel-pu")
    private EntityManager em;

    public List<Waterpoint> findAll() {
        return em.createQuery("SELECT w FROM Waterpoint w", Waterpoint.class).getResultList();
    }

    public Waterpoint findById(Long id) {
        return em.find(Waterpoint.class, id);
    }

    public void save(Waterpoint waterpoint) {
        em.persist(waterpoint);
    }

    public void update(Waterpoint waterpoint) {
        em.merge(waterpoint);
    }

    public void delete(Long id) {
        Waterpoint waterpoint = findById(id);
        if (waterpoint != null) {
            em.remove(waterpoint);
        }
    }
}