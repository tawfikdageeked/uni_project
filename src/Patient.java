// This file is responsible for patient and room logic
import java.util.ArrayList;

public class Patient
{
    public static int id_counter = 0;
    public static final double CONSULTATION_FEE = 200.0;
    public static final double ROOM_FEE = 500.0;

    String name;
    String medical_history;
    int id;
    Doctor assigned_doctor;
    Room reserved_room;
    double hospital_fees;

    Patient(String n, String m, Doctor d, Room r)
    {
        name = n;
        medical_history = m;
        assigned_doctor = d;
        id_counter++;
        id = id_counter;
        reserved_room = r;
        hospital_fees = CONSULTATION_FEE;

        if (r != null) {
            r.isFree = false;
            hospital_fees += ROOM_FEE;
            Room.sortRooms(DataBase.rooms);
        }
    }


    Patient(String n, String m, Doctor d)
    {
        this(n, m, d, null);
    }


    public void addFee(double amount) {
        if (amount > 0) {
            hospital_fees += amount;
        }
    }


    public boolean payFees(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid payment amount.");
            return false;
        }
        if (amount > hospital_fees) {
            System.out.printf("Amount exceeds current fees (%.2f).%n", hospital_fees);
            return false;
        }
        hospital_fees -= amount;
        System.out.printf("Paid %.2f. Remaining balance: %.2f%n", amount, hospital_fees);
        return true;
    }


    public static int searchPatientIndexById(ArrayList<Patient> patients, int targetId) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).id == targetId) {
                return i;
            }
        }
        return -1;
    }
}

class Room {
    public static final int MAX_ROOMS = 30;

    public int roomNumber;
    public boolean isFree;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.isFree = true;
    }

    public static Room[] initRooms() {
        Room[] rooms = new Room[MAX_ROOMS];
        for (int i = 0; i < MAX_ROOMS; i++) {
            rooms[i] = new Room(i + 1);
        }
        return rooms;
    }

    public static boolean assignFirstFreeRoom(Patient p) {
        if (!DataBase.rooms[0].isFree) {
            System.out.println("No free rooms available.");
            return false;
        }

        if (p.reserved_room != null) {
            releaseRoom(p);
        }

        Room chosen = DataBase.rooms[0];
        chosen.isFree = false;
        p.reserved_room = chosen;
        p.addFee(Patient.ROOM_FEE);

        sortRooms(DataBase.rooms);
        return true;
    }

    public static void releaseRoom(Patient p) {
        if (p.reserved_room != null) {
            p.reserved_room.isFree = true;
            p.reserved_room = null;
            sortRooms(DataBase.rooms);
        }
    }

    // Rooms are sorted by selection sort
    public static void sortRooms(Room[] rooms) {
        for (int i = 0; i < rooms.length - 1; i++) {
            int bestIdx = i;

            for (int j = i + 1; j < rooms.length; j++) {
                if ((rooms[j].isFree && !rooms[bestIdx].isFree) ||
                    (rooms[j].isFree == rooms[bestIdx].isFree && rooms[j].roomNumber < rooms[bestIdx].roomNumber)) {
                    bestIdx = j;
                }
            }

            Room temp = rooms[i];
            rooms[i] = rooms[bestIdx];
            rooms[bestIdx] = temp;
        }
    }
}
