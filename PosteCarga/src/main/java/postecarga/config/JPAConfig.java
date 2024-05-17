package postecarga.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * JPA Configuration class.
 */
public class JPAConfig {
    private static final EntityManagerFactory emFactory;

    static {
        emFactory = Persistence.createEntityManagerFactory("PosteCargaPU");
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emFactory;
    }

    public static void close() {
        emFactory.close();
    }
}
