// this file manages surgery records and details
public class Surgery
{
    public static int id_counter = 0;

    int id;
    String surgery_type;
    double cost;
    Patient patient;
    Doctor doctor;
    Room room;

    public Surgery(String type, double c, Patient p, Doctor d, Room r)
    {
        id_counter++;
        id = id_counter;
        surgery_type = type;
        cost = c;
        patient = p;
        doctor = d;
        room = r;
    }

    public void GetSurgeryInfo()
    {
        String info = String.format(
            """
            Surgery ID: %d
            Type: %s
            Patient: %s (ID: %d)
            Doctor: Dr. %s
            Room: #%d
            Cost: $%.2f
            """, id, surgery_type, patient.name, patient.id, doctor.name, room.roomNumber, cost);

        System.out.println(info);
    }
}
