package vortex.solutions.employers.Presenter.Interface;

import java.util.List;

import vortex.solutions.employers.Model.User;
import vortex.solutions.employers.View.Activity.Dashboard;

public interface ListUsersImpl {

    void showList(List<User> list);

    Dashboard getMainContext();

}
