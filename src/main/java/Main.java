import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.*;
import java.util.Scanner;

public class Main {

    final static private String doctorsFileName = "doctors.json";
    final static  private String patientsFileName = "patients.json";

    public static void main(String[] args) {
        JDBCManager manager=new JDBCManager("jdbc:sqlserver://localhost:1433;database=DB","lg","1234");
        Scanner scan = new Scanner(System.in);
        Hospital hospital=new Hospital();
        CommandParser commandParser = new CommandParser();
        while(true){
            hospital.readCommand(scan, manager, commandParser);
        }
    }

}