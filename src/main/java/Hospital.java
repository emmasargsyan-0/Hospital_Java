import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Scanner;
import java.lang.String;

public class Hospital
{

    private ArrayList<Patient> patients = new ArrayList();
    private ArrayList<Doctor> doctors = new ArrayList();
    private String doctorsFileName;
    private String patientsFileName;

    public Hospital(String doctorsFileName, String patientsFileName){
        this.doctorsFileName = doctorsFileName;
        this.patientsFileName = patientsFileName;
    }

    public Hospital(){}

    public ArrayList<Patient> getPatients(){
        return patients;
    }

    public void addPatients(Patient p){
        patients.add(p);
    }

    public ArrayList<Doctor> getDoctors(){
        return doctors;
    }

    public boolean readCommand(Scanner scanner, JDBCManager manager, CommandParser commandParser) {
        System.out.println('\n' +
                "Print command(Insert, Delete, Print) and Doctor/Patient or " + '\n' +
                "Give Doctor to Patient or " + '\n' +
                "Print Patients by Doctors" + '\n');
        return commandParser.parseCommand(scanner,manager);
    }

    public void deleteHumanFromJSON(Scanner scanner, JSONParser jsonparser, ObjectMapper mapper){
        String human = scanner.next();
        System.out.println("Print name");
        String name = scanner.next();
        System.out.println("Print last name");
        String lastName = scanner.next();
        if (human.equals("Doctor")) {
            System.out.println("Print specialization");
            String specialization = scanner.next();
            for (int i = 0; i < doctors.size(); i++){
                if (doctors.get(i).getLastName().equals(lastName) && doctors.get(i).getName().equals(name) &&
                        doctors.get(i).getSpecialization().equals(specialization)){
                    doctors.remove(i);
                    i--;
                }
            }
            jsonparser.addDoctorToFile(doctorsFileName, doctors,  mapper);
        } else if (human.equals("Patient")) {
            System.out.println("Print illness");
            String illness = scanner.next();
            for (int i = 0; i < patients.size(); i++){
                if (patients.get(i).getLastName().equals(lastName) && patients.get(i).getName().equals(name)&&
                        patients.get(i).getIllness().equals(illness)){
                    patients.remove(i);
                    i--;
                }
            }
            jsonparser.addPatientToFile(patientsFileName, patients , mapper);
         }
    }

    public void print() {
        for(Patient p: patients)
            p.print();
        for(Doctor d: doctors)
            d.print();
    }

    public void readFromJSON(JSONParser jsonparser, ObjectMapper mapper){
        doctors = jsonparser.readDoctorsFromFile(doctorsFileName, mapper);
        patients = jsonparser.readPatientsFromFile(patientsFileName, mapper);
        print();
    }

    public void insertHumanIntoJSON(Scanner scanner, JSONParser jsonparser, ObjectMapper mapper){
        String human = scanner.next();
        System.out.println("Print name");
        String name = scanner.next();
        System.out.println("Print last name");
        String lastName = scanner.next();
        if (human.equals("Doctor")) {
            System.out.println("Print specialization");
            String specialization = scanner.next();
            System.out.println("Print passport number");
            String passportNumber = scanner.next();
        //    doctors.add(new Doctor(name, lastName, specialization, passportNumber));
            jsonparser.addDoctorToFile(doctorsFileName, doctors,  mapper);
        } else if (human.equals("Patient")) {
            System.out.println("Print illness");
            String illness = scanner.next();
            System.out.println("Print passport number");
            String passportNumber = scanner.next();
        //    patients.add(new Patient(name, lastName, illness, passportNumber));
            jsonparser.addPatientToFile(patientsFileName, patients , mapper);
        }
    }

    public boolean readCommandForJSON(Scanner scanner, JSONParser jsonparser, ObjectMapper mapper) {
        System.out.println("Print command(Insert, Delete, Print) Doctor/Patient");
        String command = scanner.next();
        if(command.equals("Insert")) {
            insertHumanIntoJSON(scanner, jsonparser, mapper);
            return true;
        }
        if(command.equals("Delete")) {
            deleteHumanFromJSON(scanner, jsonparser, mapper);
            return true;
        }
        if (command.equals("Print")) {
            readFromJSON(jsonparser, mapper);
            return true;
        }
        return false;
    }

}
