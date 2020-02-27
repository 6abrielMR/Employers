package vortex.solutions.employers.Presenter.Interface;

import vortex.solutions.employers.Model.User;
import vortex.solutions.employers.View.Activity.Dashboard;

public interface UpdateUserImpl {

    void loadEmployer(User user);
    void stateFields(int field);

    Dashboard getMainContext();

}
