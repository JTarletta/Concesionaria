
package Hibernate;

import java.awt.Component;
import java.util.*;
import javax.swing.JOptionPane;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.*;


/**
 *
 * @author Juan
 */
public class GestorHibernate extends HibernateUtil {

    private Transaction tx;
    
    public void eliminarObjeto(Object objeto){
        try{   
            Session s = HibernateUtil.getSession();
            Transaction tx = s.beginTransaction();
            s.delete(objeto); 
            tx.commit();
        } catch(HibernateException ex){
            System.out.println("Repositorio.eliminarObjeto(Object objeto)"+ex);
            JOptionPane.showMessageDialog((Component)null,"El elemento no se puede eliminar:"+ex.getMessage(),"Error",0);
            getTx().rollback();
        }
    }

    public void guardarObjeto(Object objeto){
        try{
        Session s = HibernateUtil.getSession();
        Transaction tx = s.beginTransaction();
        s.save(objeto);        
        tx.commit();
        System.out.println(" guardaractualizarObjeto() " +objeto.getClass()+": "+objeto.toString());  
        } catch(HibernateException ex){
            System.out.println("error "+ex);
            System.out.println("Repositorio.guardarObjeto(Object objeto)"+objeto.getClass()+": "+objeto.toString()+ex);
            getTx().rollback();
        }
    }

    public boolean actualizarObjeto(Object objeto){
        Session s = HibernateUtil.getSession();
        Transaction tx = s.beginTransaction();
        try{
            s.update(objeto);
            tx.commit();
            System.out.println(" actualizarObjeto() " +objeto.getClass()+": "+objeto.toString());
            return true;
        }catch(HibernateException e){
            JOptionPane.showMessageDialog(null, e);
            tx.rollback();
            JOptionPane.showMessageDialog(null, "No se pueden guardar los datos. \nLos mismos han sido modificados por otra persona.");
            return false;
        }
    }
    
    public Transaction getTx() {
        return tx;
    }

    public void setTx(Transaction tx) {
        this.tx = tx;
    }

    public List listarClase(Class clase){
        Criteria crit = getSession().createCriteria(clase);
        return crit.list();
    }

    public List listarClase(Class clase,String atributoOrden, boolean estado,int max){
        Criteria crit = getSession().createCriteria(clase).addOrder(Order.asc(atributoOrden))
                        .add (Restrictions.eq("estado",estado)).setMaxResults(max);
        return crit.list();
    }
    
    public List listarClase(Class clase, boolean estado,int max){
        Criteria crit = getSession().createCriteria(clase).add (Restrictions.eq("estado",estado)).setMaxResults(max);
        return crit.list();
    }

    public List listarClase(Class clase,String atributoOrden){
        Criteria crit = getSession().createCriteria(clase).addOrder(Order.asc(atributoOrden));
        return crit.list();
    }

    public List listarUltimo(Class clase) {
           Criteria crit = getSession().createCriteria(clase) 
                        .addOrder(Order.desc("codigo"));
        return crit.list();
    }

    public List listarClaseCodigo(Class clase, int valor){        
        Criteria crit = getSession().createCriteria(clase)
            .add( Restrictions.eq("codigo", valor))
             .add( Restrictions.eq("estado", 0));
        return crit.list();
    }  
    
    public Object listarClaseCodigoUnique(Class clase, int valor){
        Criteria crit = getSession().createCriteria(clase)
            .add( Restrictions.eq("codigo", valor))
            .add( Restrictions.eq("estado", 0));
        return crit.uniqueResult();
    }
}
