package com.revature.ims_backend.entities;

import java.util.Calendar;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProductDaoTest {

	private static SessionFactory sf;
	private Session session;
	
	@BeforeClass
	public static void setUp() throws Exception {
		sf = new Configuration().configure().buildSessionFactory();
	}
	
	@Before
	public void setupSession() {
		session = sf.openSession();
	}
	
	@After
	public void cleanupSession() {
		if ( session != null )
			session.close();
	}
	
	@AfterClass
	public static void tearDown() {
		sf.close();
	}
	
	///////////// Test Content //////////////
	@Test
	public void testStuff() {
		
		/*
		 * Previously ran:
		 * 
			insert into IMS_STATE_ABBRV VALUES (1, 'Minnesota', 'MN');
			insert into IMS_ADDRESS VALUES (1, 'street house', ' ', 'Plymouth', 1,
			  '55447');
			insert into IMS_CLIENT_TYPE VALUES (1, 'resturant');
			insert into IMS_CLIENT VALUES (1, 'Restrurant', 'rest@gmail.com', 'John Doe',
			  '763-323-2355', '355-232-2399', 1, 1);
			commit;
			
		 */
		
		Query query = session.createQuery("FROM Client WHERE id=1");
		Client client = (Client) query.uniqueResult();
		
		Category category = new Category(2, "Foodstuffs");
		ProductImage image = new ProductImage(1, "http://i.imgur.com/8ezc6vO.jpg");
		Product product = new Product(1, "Food", "Some food", "fd", 2.00, 10, 45, 3.45, 30.5, image);
		PurchaseOrder order = new PurchaseOrder(1, 77.77, Calendar.getInstance().getTime(), .05, 88.88, client);
		OrderLine line = new OrderLine(1, 1, 20.00, 5, product);
		
		Transaction tx = (Transaction) session.beginTransaction();
		try {
			session.save(category);
			session.save(image);
			session.save(product);
			session.save(order);
			session.save(line);
			tx.commit();
		} catch ( Throwable t ) {
			try {
				tx.rollback();
			} catch ( Throwable th ) {}
		}
	}
}
