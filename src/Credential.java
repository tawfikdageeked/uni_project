// this file contain the main logic behing loggin in the system and creating acounts
import java.util.ArrayList;

class Account
{
    private String username;
    private String password;

    Account(String u, String p)
    {
        username = u;
        password = p;
    }

    // verify login credentials and return the logged in employee
    public static Employee Login()
    {
        String u = MainSystem.GetInput("Username: ");
        String p = MainSystem.GetInput("Password: ");

        for (Employee emp : DataBase.employees)
        {
            if (emp.account.username.equals(u) && emp.account.password.equals(p))
            {
                return emp;
            }
        }
        return null;
    }

    // get new username from user
    public static String GetNewUsername()
    {
        String prompt = "Enter your new username (it can only contain letters and digits): ";
        System.out.println("Type \"back\" to get back to previous menu.");
        String username = MainSystem.GetInput(prompt);

        while(!username.matches("[a-zA-Z0-9]+") && !username.equalsIgnoreCase("back"))
        {
            System.out.println("Invalid Username.");
            username = MainSystem.GetInput(prompt);
        }

        if(username.equalsIgnoreCase("back"))
        {
            return "back";
        }

        while(true)
        {
            String copyOFusername = MainSystem.GetInput("Confirm your username: ");

            if(copyOFusername.equalsIgnoreCase("back"))
            {
                return "back";
            }
            if(copyOFusername.equals(username))
            {
                return username;
            }

            System.out.println("Username doesn't match.");
        }
    }

    // get new password from the user
    public static String GetNewPassword()
    {
        String prompt = "Enter your new password: ";
        System.out.println("Type \"back\" to get back previous menu");
        String password = MainSystem.GetInput(prompt);

        while(!password.matches("[a-zA-Z0-9]+") && !password.equalsIgnoreCase("back"))
        {
            System.out.println("Invalid Password.");
            password = MainSystem.GetInput(prompt);
        }

        if(password.equalsIgnoreCase("back"))
        {
            return "back";
        }

        while(true)
        {
            String copyOFpassword = MainSystem.GetInput("Confirm your password: ");

            if(copyOFpassword.equalsIgnoreCase("back"))
            {
                return "back";
            }
            if(copyOFpassword.equals(password))
            {
                return password;
            }

            System.out.println("Password doesn't match.");
        }
    }

    // method to create an employee and add him to the system
    public static void CreateEmployee(ArrayList<Employee> e)
    {
        while (true)
        {
            System.out.println("1-Doctor     2-Nurse     3-Back to previous menu");
            int choice = MainSystem.GetChoice("Choose: ", 1, 3);
            if (choice == 3) return;

            String name = "";
            String speciality = "";
            double salary = 5000.0;
            String username = "";
            String password = "";

            int step = 1;
            while (step != 0)
            {
                switch (step)
                {
                    case 1 -> {
                        System.out.println("Type \"back\" to go to the previous step.");
                        name = MainSystem.GetInput("Enter your name: ");
                        if (name.equalsIgnoreCase("back"))
                        {
                            step = -1;
                        }
                        else
                        {
                            System.out.println("Your name is " + name + ".");
                            step = (choice == 1) ? 2 : 3;
                        }
                    }
                    case 2 -> {
                        speciality = MainSystem.GetInput("Enter your speciality: ");
                        if (speciality.equalsIgnoreCase("back"))
                        {
                            step = 1;
                        }
                        else
                        {
                            System.out.println("Your Speciality is " + speciality + ".");
                            step = 3;
                        }
                    }
                    case 3 -> {
                        username = GetNewUsername();
                        if (username.equalsIgnoreCase("back"))
                        {
                            step = (choice == 1) ? 2 : 1;
                        }
                        else
                        {
                            step = 4;
                        }
                    }
                    case 4 -> {
                        password = GetNewPassword();
                        if (password.equalsIgnoreCase("back"))
                        {
                            step = 3;
                        }
                        else
                        {
                            step = 0;
                        }
                    }
                }
                if (step == -1) break;
            }

            if (step == 0)
            {
                if (choice == 1)
                {
                    e.add(new Doctor(name, salary, new Account(username, password), speciality));
                }
                else
                {
                    e.add(new Nurse(name, salary, new Account(username, password)));
                }
                return;
            }
        }
    }

    // add new patient to the system
    public static Patient AddPatient()
    {
        ArrayList<Doctor> doctors = new ArrayList<>();
        for (Employee emp : DataBase.employees)
        {
            if (emp instanceof Doctor)
            {
                doctors.add((Doctor) emp);
            }
        }

        if (doctors.isEmpty())
        {
            System.out.println("No doctors available in the system. Add a doctor first.");
            return null;
        }

        String name = MainSystem.GetInput("Enter Patient Name: ");
        String history = MainSystem.GetInput("Enter Medical History: ");

        System.out.println("\nAvailable Doctors:");
        for (int i = 0; i < doctors.size(); i++)
        {
            System.out.println((i + 1) + "- Dr. " + doctors.get(i).name + " (ID: " + doctors.get(i).id + ")");
        }
        int docChoice = MainSystem.GetChoice("Assign Doctor: ", 1, doctors.size());
        Doctor chosenDoc = doctors.get(docChoice - 1);

        Patient p = new Patient(name, history, chosenDoc);

        System.out.println("Reserve a room?  1-Yes   2-No");
        int roomChoice = MainSystem.GetChoice("Choose: ", 1, 2);
        if (roomChoice == 1)
        {
            Room.assignFirstFreeRoom(p);
        }

        chosenDoc.patients.add(p);
        DataBase.AddPatient(p);

        System.out.println("Patient added successfully. Patient ID: " + p.id);
        return p;
    }

    // modify existing patient info, room, doctor, or fees
    public static void ModifyPatient()
    {
        if (DataBase.all_patients.isEmpty())
        {
            System.out.println("No patients in the system.");
            return;
        }

        int id = MainSystem.GetChoice("Enter Patient ID: ");
        int index = Patient.searchPatientIndexById(DataBase.all_patients, id);

        if (index == -1)
        {
            System.out.println("Patient not found.");
            return;
        }

        Patient p = DataBase.all_patients.get(index);

        while (true)
        {
            System.out.println("\nModifying Patient: " + p.name + " (ID: " + p.id + ")");
            System.out.println("1- Edit Name");
            System.out.println("2- Append Medical History");
            System.out.println("3- Reserve / Release Room");
            System.out.println("4- Add Extra Fee");
            System.out.println("5- Pay Hospital Fees");
            System.out.println("6- Discharge Patient");
            System.out.println("7- Back");

            int choice = MainSystem.GetChoice("Choose: ", 1, 7);

            switch (choice)
            {
                case 1 -> {
                    p.name = MainSystem.GetInput("Enter New Name: ");
                    System.out.println("Name updated.");
                }
                case 2 -> {
                    String note = MainSystem.GetInput("Enter New Medical Note: ");
                    p.medical_history += " | " + note;
                    System.out.println("Medical history updated.");
                }
                case 3 -> {
                    if (p.reserved_room == null)
                    {
                        if (Room.assignFirstFreeRoom(p))
                        {
                            System.out.println("Assigned Room " + p.reserved_room.roomNumber);
                        }
                    }
                    else
                    {
                        System.out.println("Currently in Room " + p.reserved_room.roomNumber + ". Release room? 1-Yes  2-No");
                        if (MainSystem.GetChoice("Choose: ", 1, 2) == 1)
                        {
                            Room.releaseRoom(p);
                            System.out.println("Room released.");
                        }
                    }
                }
                case 4 -> {
                    String feeInput = MainSystem.GetInput("Enter Fee Amount: ");
                    try
                    {
                        double amount = Double.parseDouble(feeInput);
                        p.addFee(amount);
                        System.out.printf("Added %.2f. Total fees: %.2f%n", amount, p.hospital_fees);
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Invalid amount.");
                    }
                }
                case 5 -> {
                    System.out.printf("Current Balance: %.2f%n", p.hospital_fees);
                    String payInput = MainSystem.GetInput("Enter Payment Amount: ");
                    try
                    {
                        double amount = Double.parseDouble(payInput);
                        p.payFees(amount);
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Invalid amount.");
                    }
                }
                case 6 -> {
                    Room.releaseRoom(p);
                    if (p.assigned_doctor != null)
                    {
                        p.assigned_doctor.patients.remove(p);
                    }
                    DataBase.all_patients.remove(index);
                    System.out.println("Patient discharged and removed from active list.");
                    return;
                }
                case 7 -> {
                    return;
                }
            }
        }
    }

    // display full medical history and status of a patient
    public static void GetMedicalHistory()
    {
        if (DataBase.all_patients.isEmpty())
        {
            System.out.println("No patients in the system.");
            return;
        }

        int id = MainSystem.GetChoice("Enter Patient ID: ");
        int index = Patient.searchPatientIndexById(DataBase.all_patients, id);

        if (index == -1)
        {
            System.out.println("Patient not found.");
            return;
        }

        Patient p = DataBase.all_patients.get(index);
        String roomStatus = (p.reserved_room != null) ? ("Room #" + p.reserved_room.roomNumber) : "None";
        String docName = (p.assigned_doctor != null) ? p.assigned_doctor.name : "None";

        String info = String.format(
            """
            Patient ID: %d
            Name: %s
            Assigned Doctor: Dr. %s
            Reserved Room: %s
            Hospital Fees: $%.2f
            Medical History: %s

            """, p.id, p.name, docName, roomStatus, p.hospital_fees, p.medical_history);

        System.out.println(info);
    }

// reserve a surgery for a new or existing patient
    public static void SurgeryReservation()
    {
        System.out.println("1- New Patient     2- Existing Patient     3- Back");
        int choice = MainSystem.GetChoice("Choose: ", 1, 3);
        if (choice == 3) return;

        Patient p = null;

        if (choice == 1)
        {
            p = AddPatient();
            if (p == null) return;
        }
        else
        {
            if (DataBase.all_patients.isEmpty())
            {
                System.out.println("No existing patients found.");
                return;
            }
            int id = MainSystem.GetChoice("Enter Patient ID: ");
            int index = Patient.searchPatientIndexById(DataBase.all_patients, id);
            if (index == -1)
            {
                System.out.println("Patient not found.");
                return;
            }
            p = DataBase.all_patients.get(index);
        }

        System.out.println("\nSelect Surgery Type:");
        System.out.println("1- Minor Surgery ($1500)");
        System.out.println("2- Major Surgery ($4000)");
        System.out.println("3- Emergency Surgery ($6500)");
        int surgChoice = MainSystem.GetChoice("Choose: ", 1, 3);

        String surgeryName = "";
        double surgeryCost = 0;

        switch (surgChoice)
        {
            case 1 -> { surgeryName = "Minor Surgery"; surgeryCost = 1500.0; }
            case 2 -> { surgeryName = "Major Surgery"; surgeryCost = 4000.0; }
            case 3 -> { surgeryName = "Emergency Surgery"; surgeryCost = 6500.0; }
        }

        if (p.reserved_room == null)
        {
            if (!Room.assignFirstFreeRoom(p))
            {
                System.out.println("Cannot reserve surgery without an available room.");
                return;
            }
        }

        p.addFee(surgeryCost);
        p.medical_history += " | Scheduled: " + surgeryName;

        Surgery s = new Surgery(surgeryName, surgeryCost, p, p.assigned_doctor, p.reserved_room);
        DataBase.AddSurgery(s);

        System.out.printf("%s reserved for %s (Surgery ID: %d, Room #%d). Updated Fees: $%.2f%n",
            surgeryName, p.name, s.id, p.reserved_room.roomNumber, p.hospital_fees);
    }

}
