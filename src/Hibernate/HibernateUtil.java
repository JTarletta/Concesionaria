
package Hibernate;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import org.hibernate.*;
import org.hibernate.cfg.*;

/**
 *
 * @author Juan
 */


public class HibernateUtil  {
    public static SessionFactory sessionFactory;
    public static Session session;

    public static void inicializar() {
        try {
           Configuration conf = new Configuration(); 
           try{
                conf.setProperty("hibernate.connection.driver_class","org.postgresql.Driver");
                conf.setProperty("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");                 
                conf.setProperty("hibernate.connection.url","jdbc:postgresql://localhost:5432/avanzada");
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error de Base de Datos Nº 2001");
            }
            
            conf.setProperty("hibernate.connection.username","postgres");
            conf.setProperty("hibernate.connection.password","123");

            conf.setProperty("hibernate.connection.pool_size","10");                          
            conf.setProperty("hibernate.hbm2ddl.auto","update");

            conf.addPackage("Models");
         

            //clases del proyecto
            conf.addAnnotatedClass(Models.Pais.class);
            conf.addAnnotatedClass(Models.Modelo.class);
            conf.addAnnotatedClass(Models.Marca.class);
            
            conf.addAnnotatedClass(Models.Auto.class);
            
            conf.addAnnotatedClass(Models.Cliente.class);
            conf.addAnnotatedClass(Models.Empleado.class);

            conf.addAnnotatedClass(Models.Venta.class);
            conf.addAnnotatedClass(Models.Estado.class);
          
           
            try {
                    sessionFactory = conf.buildSessionFactory();
                    session=sessionFactory.openSession();
                }
                catch(HibernateException e){
                    JOptionPane.showMessageDialog(null, e);
                }
        } catch (HeadlessException ex) {
            throw new ExceptionInInitializerError(ex);
        } catch (MappingException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static Session getSession()
    throws HibernateException {
        return session;
    }
}