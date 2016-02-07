package ConexaoPU;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager {

    public static final boolean DEBUG = true;

    private static final PersistenceManager singleton = new PersistenceManager();

    protected EntityManagerFactory emf;

    public static PersistenceManager getInstance() {
        return singleton;
    }

    private PersistenceManager() {
    }

    public EntityManagerFactory getEntityManagerFactory() {

        if (emf == null) {
            createEntityManagerFactory();
        }
        return emf;
    }

    public void closeEntityManagerFactory() {

        if (emf != null) {
            emf.close();
            emf = null;
            if (DEBUG) {
                System.out.println("\n n*** Persistence finished at " + new java.util.Date() + "\n");
            }
        }
    }

    protected void createEntityManagerFactory() {

        this.emf = Persistence.createEntityManagerFactory("FinancePU", configProperty());
        if (DEBUG) {
            System.out.println("n*** Persistence started at " + new java.util.Date());
        }
    }

    public HashMap<String, Object> configProperty() {        
        Map<String, Object> prop = new HashMap<>();
        prop.put("javax.persistence.jdbc.user", "root");
        prop.put("javax.persistence.jdbc.password", "skyline@123");        
        return (HashMap<String, Object>) prop;
    }
}
