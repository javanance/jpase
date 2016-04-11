package fetchKrx;

import javax.persistence.EntityManager;

import org.greyhawk.logger.Logger;
import org.greyhawk.logger.LoggerFactory;

import com.eugenefe.enums.PersistenceManager;

public class $1001EntityManagerTest {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger($6001KsdScrapUtilTest.class);
	private final static Logger _logger = LoggerFactory.getLogger($6001KsdScrapUtilTest.class);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		em.getTransaction().begin();
		
//		List<Ksd200T3> zzz= KsdScrapUtil.convertTo(EKsdMenu.Ksd200T3, rawData);
//		for(Ksd200T3 aa : zzz){
//			logger.info("list : {}, {}", aa.getClass(), aa.getIsin());
//		}
		
//		em.persist(aa);

		// logger.info("test : {}, {}", logger.getName(), xx.getIsin());
		// OdsIsinMaster yy = zz.findById("ee");

		// zz.remove(xx);
		// log.info( );

		// em.getTransaction().commit();
		//
		em.close();
		PersistenceManager.INSTANCE.close();

	}

}
