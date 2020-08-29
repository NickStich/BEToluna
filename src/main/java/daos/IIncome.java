package daos;


import model.Income;

import java.util.List;

public interface IIncome {

    void createIncome(Income income);

    void removeIncome(Income income);

    void updateIncome(Income income);

    Income getById(long id);

    List<Income> getAll();
}
