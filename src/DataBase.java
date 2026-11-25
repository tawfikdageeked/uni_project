import java.util.ArrayList;

public class DataBase
{
    public static ArrayList<Employee> employees = new ArrayList<>();
    public static ArrayList<Patient> all_patients = new ArrayList<>();
    public static ArrayList<Surgery> surgeries = new ArrayList<>();
    public static Room[] rooms = Room.initRooms();

    static public void AddEmployee(Employee e)
    {
        employees.add(e);
    }

    static public void AddPatient(Patient p)
    {
        all_patients.add(p);
    }

    static public void AddSurgery(Surgery s)
    {
        surgeries.add(s);
    }
}
