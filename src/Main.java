// main entry point and program loop for the hospital system
public class Main
{
    public static void main(String[] args)
    {
        LoadDefaultData();

        while (true)
        {
            MainSystem.Flush();
            MainSystem.Print();
            System.out.println("1- Login");
            System.out.println("2- Exit");

            int startChoice = MainSystem.GetChoice("Choose: ", 1, 2);

            if (startChoice == 1)
            {
                Employee currentUser = Account.Login();
                if (currentUser == null)
                {
                    System.out.println("Invalid username or password.");
                    MainSystem.Pause("Press Enter to continue...");
                }
                else
                {
                    Dashboard(currentUser);
                }
            }
            else
            {
                System.out.println("Exiting system...");
                break;
            }
        }
    }

    // hardcoded admin, doctors, nurses, patients, and surgeries
    public static void LoadDefaultData()
    {
        Admin admin = new Admin("Admin", 10000.0, new Account("admin", "1234"));

        Doctor d1 = new Doctor("Mahmoud Ahmed", 9000.0, new Account("mahmoud1", "1234"), "Cardiology");
        Doctor d2 = new Doctor("Youssef Farouk", 8500.0, new Account("youssef", "1234"), "Neurology");
        Doctor d3 = new Doctor("Ahmed Darwish", 8000.0, new Account("ahmed", "1234"), "General Surgery");
        Doctor d4 = new Doctor("Mahmoud Shaaban", 8500.0, new Account("mahmoud2", "1234"), "Orthopedics");

        Nurse n1 = new Nurse("Hadayat Ashraf", 4500.0, new Account("hadayat", "1234"));
        Nurse n2 = new Nurse("Rahma Wael", 4500.0, new Account("rahma", "1234"));
        Nurse n3 = new Nurse("Marwa Hussien", 4500.0, new Account("marwa", "1234"));

        DataBase.AddEmployee(admin);
        DataBase.AddEmployee(d1);
        DataBase.AddEmployee(d2);
        DataBase.AddEmployee(d3);
        DataBase.AddEmployee(d4);
        DataBase.AddEmployee(n1);
        DataBase.AddEmployee(n2);
        DataBase.AddEmployee(n3);

        Patient p1 = new Patient("Karim Mahmoud", "High blood pressure", d1, DataBase.rooms[0]);
        Patient p2 = new Patient("Nour Ibrahim", "Migraine and dizziness", d2);
        Patient p3 = new Patient("Tarek Mostafa", "Acute appendicitis", d3, DataBase.rooms[0]);

        d1.patients.add(p1);
        d2.patients.add(p2);
        d3.patients.add(p3);

        DataBase.AddPatient(p1);
        DataBase.AddPatient(p2);
        DataBase.AddPatient(p3);

        p3.addFee(4000.0);
        p3.medical_history += " | Scheduled: Major Surgery";
        DataBase.AddSurgery(new Surgery("Major Surgery", 4000.0, p3, d3, p3.reserved_room));
    }

    // dashboard menu after employee logs in
    public static void Dashboard(Employee user)
    {
        while (true)
        {
            MainSystem.Flush();
            MainSystem.Print();
            System.out.println("Logged in as: " + user.jobtitle + user.name + " (ID: " + user.id + ")\n");
            System.out.println("1- Add Patient");
            System.out.println("2- Modify Patient");
            System.out.println("3- Get Medical History");
            System.out.println("4- Surgery Reservation");
            System.out.println("5- Show All Surgeries");
            System.out.println("6- Show All Employees");
            System.out.println("7- Show Rooms Status");
            System.out.println("8- Create Employee Account");
            System.out.println("9- Logout");

            int choice = MainSystem.GetChoice("Choose: ", 1, 9);

            switch (choice)
            {
                case 1 -> {
                    Account.AddPatient();
                    MainSystem.Pause("Press Enter to continue...");
                }
                case 2 -> {
                    Account.ModifyPatient();
                    MainSystem.Pause("Press Enter to continue...");
                }
                case 3 -> {
                    Account.GetMedicalHistory();
                    MainSystem.Pause("Press Enter to continue...");
                }
                case 4 -> {
                    Account.SurgeryReservation();
                    MainSystem.Pause("Press Enter to continue...");
                }
                case 5 -> {
                    if (DataBase.surgeries.isEmpty())
                    {
                        System.out.println("No surgeries scheduled.");
                    }
                    else
                    {
                        for (Surgery s : DataBase.surgeries)
                        {
                            s.GetSurgeryInfo();
                        }
                    }
                    MainSystem.Pause("Press Enter to continue...");
                }
                case 6 -> {
                    for (Employee emp : DataBase.employees)
                    {
                        emp.GetEmployeeInfo();
                    }
                    MainSystem.Pause("Press Enter to continue...");
                }
                case 7 -> {
                    System.out.println("\nRooms Status (Sorted Free First):");
                    for (Room r : DataBase.rooms)
                    {
                        System.out.println("Room #" + r.roomNumber + " -> " + (r.isFree ? "FREE" : "OCCUPIED"));
                    }
                    MainSystem.Pause("Press Enter to continue...");
                }
                case 8 -> {
                    Account.CreateEmployee(DataBase.employees);
                    MainSystem.Pause("Press Enter to continue...");
                }
                case 9 -> {
                    return;
                }
            }
        }
    }
}
