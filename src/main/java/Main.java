import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    final static private String doctorsFileName = "doctors.json";
    final static  private String patientsFileName = "patients.json";
    static JSONParser parser = new JSONParser();
    static ObjectMapper mapper=new ObjectMapper();
    public static void main(String[] args) {
        JDBCManager manager=new JDBCManager("jdbc:sqlserver://localhost:1433;database=DB","lg","1234");
        Scanner scan = new Scanner(System.in);
        Hospital hospital=new Hospital();
        hospital.addPatients(new Patient("name","lastname","illness","passport"));
        hospital.addPatients(new Patient("name1","lastname1","illness","passport"));
        parser.addPatientToFile("pat.json",hospital.getPatients(),mapper);
        ArrayList<Patient> p=parser.readPatientsFromFile("pat.json",mapper);
        for(int i=0;i<2;i++)
            System.out.println(p.get(i));
        CommandParser commandParser = new CommandParser();
   /*     manager.getUserByUsernameAndPassword("aa","aa");
        while(true){
            hospital.readCommand(scan, manager, commandParser);
        }*/
    }

}
