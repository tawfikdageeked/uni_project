// this file include the methods which will print the headline banner and flush the screen to print the new frame

import java.util.Scanner;



public class MainSystem
{


    public static Scanner in = new Scanner(System.in);


// the banner of the system
    public static final String banner ="""
  _    _                 _ _        _                 _                 
 | |  | |               (_| |      | |               | |                
 | |__| | ___  ___ _ __  _| |_ __ _| |  ___ _   _ ___| |_ ___ _ __ ___  
 |  __  |/ _ \\/ __| '_ \\| | __/ _` | | / __| | | / __| __/ _ | '_ ` _ \\ 
 | |  | | (_) \\__ | |_) | | || (_| | | \\__ | |_| \\__ | ||  __| | | | | |
 |_|  |_|\\___/|___| .__/|_|\\__\\__,_|_| |___/\\__, |___/\\__\\___|_| |_| |_|
                  | |                        __/ |                      
                  |_|                       |___/                       
""";
// print the banner
    public static void Print()
    {
        System.out.println(banner);
    }




// flush the current frame
    public static void Flush()
    {
        System.out.print("\033[H\033[2J");
    }



    // to get and parse whatever input from the user (RECURSION IS USED HERE)
    public static int GetChoice(String prompt)
    {
        System.out.print(prompt);
        String userinput;
        userinput = in.nextLine();

        boolean input = userinput.matches(".*\\d.*");
        if(input)
        {
            try
            {
                String inputASnumber = userinput.replaceAll("[^0-9]", "");
                int actualinput = Integer.parseInt(inputASnumber);
                return actualinput;
            }
            catch(NumberFormatException e)
            {
                System.out.println("Invalid Input.");
                return GetChoice(prompt);
            }
        }

        else
        {
            System.out.println("Invalid Input.");
            
            return GetChoice(prompt);
        }
    }






    // OVERLOADING IS HERE
    public static int GetChoice(String prompt, int min, int max)
    {

        System.out.print(prompt);
        String userinput;
        userinput = in.nextLine();
        int actualinput = 0;

        boolean input = userinput.matches(".*\\d.*");
        if(input)
        {
            try
            {
                String inputASnumber = userinput.replaceAll("[^0-9]", "");
                actualinput = Integer.parseInt(inputASnumber);
            }
            catch(NumberFormatException e)
            {
                System.out.println("Invalid Input.");
            }

            if(min <= actualinput && actualinput <= max)
            {
                return actualinput;
            }
            else
            {
                return GetChoice(prompt, min, max);
            }
        }

        else
        {
            System.out.println("Invalid Input.");
            
            return GetChoice(prompt, min, max);
        }
    }




    public static String GetInput(String prompt)
    {
        System.out.print(prompt);
        String userinput;
        userinput = in.nextLine();
        return userinput;
    }









// pauses the system until the user input
    public static void Pause(String prompt)
    {
        System.out.print(prompt);
        String userinput = in.nextLine();
    }
}
