package vortex.solutions.employers.Model;

public class User {

    private String names;
    private String lastnames;
    private String typeId;
    private String id;
    private String num1;
    private String num2;
    private String num3;
    private String email;
    private String salary;
    private String timesTamp;
    private String lastKnownChange;

    public User(String names, String lastnames, String typeId, String id, String num1, String num2,
                String num3, String email, String salary, String timesTamp, String lastKnownChange) {
        this.names = names;
        this.lastnames = lastnames;
        this.typeId = typeId;
        this.id = id;
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;
        this.email = email;
        this.salary = salary;
        this.timesTamp = timesTamp;
        this.lastKnownChange = lastKnownChange;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastnames() {
        return lastnames;
    }

    public void setLastnames(String lastnames) {
        this.lastnames = lastnames;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNum1() {
        return num1;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public String getNum2() {
        return num2;
    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }

    public String getNum3() {
        return num3;
    }

    public void setNum3(String num3) {
        this.num3 = num3;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getTimesTamp() {
        return timesTamp;
    }

    public void setTimesTamp(String timesTamp) {
        this.timesTamp = timesTamp;
    }

    public String getLastKnownChange() {
        return lastKnownChange;
    }

    public void setLastKnownChange(String lastKnownChange) {
        this.lastKnownChange = lastKnownChange;
    }
}
