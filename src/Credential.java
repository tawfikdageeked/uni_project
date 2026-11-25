// this file contain the main logic behing loggin in the system and creating acounts
import java.util.ArrayList;
import java.util.Scanner;

class Account
{
    public ArrayList<Acount> accounts = new ArrayList<>()
    private String username;
    private String password;

    Account(String u, String p)
    {
        username = u;
        password = p;
    }


    private String GetNewUsername()
    {
        String prompt = "Enter your username (it can only contain letters and digits): ";
        System.out.println("Type \"back\" to get back to main menu.");
        String username = MainSystem.GetInput(prompt);

        while (!username.matches("[a-zA-Z0-9]+") && !username.equalsIgnoreCase("back"))
        {
            System.out.println("Invalid Username.");
            username = MainSystem.GetInput(prompt);
        }

        if (username.equalsIgnoreCase("back"))
        {
            return "back";
        }

        while (true)
        {
            String copyOFusername = MainSystem.GetInput("Confirm your username: ");

            if (copyOFusername.equalsIgnoreCase("back"))
            {
                return "back";
            }
            if (copyOFusername.equals(username))
            {
                return username;
            }

            System.out.println("Username doesn't match.");
        }
    }

    
}
