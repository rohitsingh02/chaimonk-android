package www.chaayos.com.chaimonkbluetoothapp.domain.model;

/**
 * Created by rohitsingh on 10/07/16.
 */
public class Employee {
    protected int id;
    protected String name;
    protected String gender;


    public int getId() {
        return id;
    }

    public void setId(int value) {
        this.id = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }






    @Override
    public String toString() {
        String employee = "";
        employee = employee + this.getName() + " " + " "
                + " " + " " ;
        return employee;
    }
}
