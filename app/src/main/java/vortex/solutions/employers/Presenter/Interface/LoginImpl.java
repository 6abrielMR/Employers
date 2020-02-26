package vortex.solutions.employers.Presenter.Interface;

public interface LoginImpl {

    void goToNextScreen(boolean state);

    void FailureInput(int field, String messagge);

    void SuccesMessagge(String messagge);
    void FailureMessagge(String messagge);

}
