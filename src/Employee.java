// this file manages the emplyees info and data
import java.util.ArrayList;

// the general class that all other employees will inherit from
public abstract class Employee
{
    protected static int id_generator = 0;
    protected String name;
    protected String jobtitle;
    protected int id;
    protected double salary;
    protected Account account;

    Employee(String n, double s, Account a)
    {
        name = n;
        id_generator++;
        id = this.id_generator;
        salary = s;
        account = a;
    }

    public abstract void GetEmployeeInfo();
}

// admin class for system developer/management
class Admin extends Employee
{
    Admin(String n, double s, Account a)
    {
        super(n, s, a);
        this.jobtitle = "Admin: ";
    }

    @Override public void GetEmployeeInfo()
    {
        String info = String.format(
            """

Admin: %s
ID: %d

""", this.name, this.id);

        System.out.print(info);
    }
}

// doctor class (INERITENCE AND OVERLOADING IS USED HERE) inherit from employee
class Doctor extends Employee
{
    Doctor(String n, double s, Account a, String sp)
    {
        super(n, s, a);
        this.jobtitle = "Doctor: ";
        this.speciality = sp;
    }

    private String speciality;
    ArrayList<Patient> patients = new ArrayList<>();

    @Override public void GetEmployeeInfo()
    {
        String info = String.format(
            """

Doctor: %s
ID: %d
Speciality: %s

""", this.name, this.id, this.speciality);

        System.out.print(info);
    }
}

// nurse class whic inherit from Employee
class Nurse extends Employee
{
    Nurse(String n, double s, Account a)
    {
        super(n, s, a);
        this.jobtitle = "Nurse: ";
    }

    ArrayList<Patient> patients = new ArrayList<>();

    @Override public void GetEmployeeInfo()
    {
        String info = String.format(
            """

Nurse: %s
ID: %d

""", this.name, this.id);

        System.out.print(info);
    }
}
