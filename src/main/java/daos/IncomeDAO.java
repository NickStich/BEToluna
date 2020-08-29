package daos;

import config.HibernateConfig;
import model.Income;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class IncomeDAO implements IIncome {
    @Override
    public void createIncome(Income income) {
        Transaction transaction = null;
        try {
            Session session = HibernateConfig.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(income);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction!=null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    @Override
    public void removeIncome(Income income) {
        Transaction transaction = null;
        try {
            Session session = HibernateConfig.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.remove(income);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction!=null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    @Override
    public void updateIncome(Income income) {
        Transaction transaction = null;
        try {
            Session session = HibernateConfig.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(income);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction!=null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    @Override
    public Income getById(long id) {
        Income income = null;
        try {
            Session session = HibernateConfig.getSessionFactory().openSession();
            income = session.find(Income.class,id);
            session.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return income;
    }

    @Override
    public List<Income> getAll() {
        List<Income> incomes = new ArrayList<Income>();
        try {
            Session session = HibernateConfig.getSessionFactory().openSession();
            incomes = session.createQuery("from Income", Income.class).list();
            session.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return incomes;
    }

}
