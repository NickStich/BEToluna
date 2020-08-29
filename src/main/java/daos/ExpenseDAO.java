package daos;

import config.HibernateConfig;
import model.Expense;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO implements IExpense {


    @Override
    public void createExpense(Expense expense) {
        Transaction transaction = null;
        try {
            Session session = HibernateConfig.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(expense);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction!=null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    @Override
    public void removeExpense(Expense expense) {
        Transaction transaction = null;
        try {
            Session session = HibernateConfig.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.remove(expense);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction!=null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    @Override
    public void updateExpense(Expense expense) {
        Transaction transaction = null;
        try {
            Session session = HibernateConfig.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(expense);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction!=null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    @Override
    public Expense getById(long id) {
        Expense expense = null;
        try {
            Session session = HibernateConfig.getSessionFactory().openSession();
            expense = session.find(Expense.class,id);
            session.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return expense;
    }

    @Override
    public List<Expense> getAll() {
        List<Expense> expenses = new ArrayList<Expense>();
        try {
            Session session = HibernateConfig.getSessionFactory().openSession();
            expenses = session.createQuery("from Expense", Expense.class).list();
            session.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return expenses;
    }
}
