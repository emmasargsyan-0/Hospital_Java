import java.util.Scanner;

public class CommandParser {

    public boolean insertCommand(Scanner scanner, JDBCManager manager, String human){
        System.out.println("Enter Name");
        String name = scanner.next();
        System.out.println("Enter Last Name");
        String lastName = scanner.next();
        System.out.println("Enter Passport Number");
        String passportNumber = scanner.next();
        int tmp = 0;
        if(human.toLowerCase().equals("doctor")) {
            if(manager.checkIfIsAnyDoctorByPassport(passportNumber)!=0) {
                System.out.println("We have doctor with same passport number");
                return false;
            }
            System.out.println("Enter Specialization");
            String specialization = scanner.next();
            tmp = manager.insertDoctorToDB(new Doctor(name, lastName, passportNumber, specialization));
        }else if(human.toLowerCase().equals("patient")) {
            if(manager.checkIfIsAnyPatientByPassport(passportNumber)!=0) {
                System.out.println("We have patient with same passport number");
                return false;
            }
            System.out.println("Enter Illness if any or null");
            String illness = scanner.next();
            if(illness.toLowerCase().equals("null"))
                illness="";
            System.out.println("Enter DoctorId if any or 0");
            int doctorId = scanner.nextInt();
            if(doctorId==0)
                doctorId=1;
            tmp = manager.insertPatientToDB(new Patient(name, lastName, passportNumber, illness, doctorId));
        }
        if(tmp == 0)
            return false;
        return true;
    }

    public boolean deleteCommand(Scanner scanner, JDBCManager manager, String human) {
        System.out.println("Enter Name");
        String name = scanner.next();
        System.out.println("Enter Last Name");
        String lastName = scanner.next();
        if(human.toLowerCase().equals("doctor")) {
            int tmp = manager.checkIfIsAnyDoctorByName(name, lastName);
            if (tmp == -1) {
                System.out.println("We do not have doctor with given name and lastname");
                return false;
            } else if (tmp == 0) {
                System.out.println("We have more than one doctor with given name and lastname, enter passport number");
                System.out.println("Enter Passport Number");
                String passportNumber = scanner.next();
                if (manager.deleteDoctorFromDBWithPassportNumber(passportNumber) == 0)
                    return false;
            }else{

                if (manager.deleteDoctorFromDBWithName(name,lastName) == 0)
                    return false;
            }
            return true;
        }else if(human.toLowerCase().equals("patient")){
            int tmp = manager.checkIfIsAnyPatientByName(name, lastName);
            if (tmp == 0) {
                System.out.println("We do not have patient with given name and lastname");
                return false;
            } else if (tmp > 1) {
                System.out.println("We have more than one patient with given name and lastname, enter passport number");
                System.out.println("Enter Passport Number");
                String passportNumber = scanner.next();
                if (manager.deletePatientFromDBByPassportNumber(passportNumber) == 0)
                    return false;
            }else{
                if (manager.deletePatientFromDBByName(name,lastName) == 0)
                    return false;
            }
            return true;
        }
        return false;
    }

    public boolean printCommand(Scanner scanner, JDBCManager manager, String human) {
        if(human.toLowerCase().equals("doctor")) {
            manager.getDoctorsFromDB();
        } else if(human.toLowerCase().equals("patient")) {
            manager.getPatientsFromDB();
        }else if(human.toLowerCase().equals("patients")) {
            String str1 = scanner.next();
            String str2 = scanner.next();
            if(!str1.toLowerCase().equals("by") || !str2.toLowerCase().equals("doctors"))
                return false;
            manager.getPatientsGroupByDoctorsFromDB();
        }
        return true;
    }

    public boolean giveDoctorToPatientCommand(Scanner scanner, JDBCManager manager, String human) {
        String command = scanner.next();
        human = scanner.next();
        if(!command.toLowerCase().equals("to") || !human.toLowerCase().equals("patient"))
            return  false;
        System.out.println("Enter Doctor Name");
        String doctorName = scanner.next();
        System.out.println("Enter Doctor Last Name");
        String doctorLastName = scanner.next();
        int tmp=manager.checkIfIsAnyDoctorByName(doctorName,doctorLastName);
        int doctorId=0;
        if(tmp>1){
            System.out.println("Enter Doctor Passport Number");
            String doctorPassportNumber = scanner.next();
            doctorId=manager.getIdOfDoctorByPassportNumber(doctorPassportNumber);
        }else if(tmp==1){
            doctorId=manager.getIdOfDoctorByName(doctorName, doctorLastName);
        }
        else {
            System.out.println("We do not have doctor with given name and lastname");
            return false;
        }
        System.out.println("Enter Patient Name");
        String patientName = scanner.next();
        System.out.println("Enter Patient Last Name");
        String patientLastName = scanner.next();
        tmp=manager.checkIfIsAnyPatientByName(patientName,patientLastName);
        if(tmp>1){
            System.out.println("Enter Patient Passport Number");
            String patientPassportNumber = scanner.next();
            tmp=manager.UpdatePatientsDoctorByPassportNumber(patientPassportNumber, doctorId);
        }else if(tmp==1){
            tmp=manager.UpdatePatientsDoctorByName(patientName, patientLastName, doctorId);
        }
        else {
            System.out.println("We do not have patient with given name and lastname");
            return false;
        }
        if(tmp==0)
            return false;
        return  true;
    }

    public boolean parseCommand(Scanner scanner, JDBCManager manager) {
        String command = scanner.next();
        if(!command.toLowerCase().equals("insert") && !command.toLowerCase().equals("delete") &&
                !command.toLowerCase().equals("print") && !command.toLowerCase().equals("give"))
            return false;
        String human = scanner.next();
        if(command.toLowerCase().equals("insert")) {
            return insertCommand(scanner, manager, human);
        }
        else if(command.toLowerCase().equals("delete")) {
           return deleteCommand(scanner,  manager,  human);
        }
        else if (command.toLowerCase().equals("print")) {
            return printCommand( scanner, manager, human);
        }
        else if (command.toLowerCase().equals("give") && human.toLowerCase().equals("doctor")) {
            return giveDoctorToPatientCommand( scanner,  manager,  human);
        }
        return false;
    }

}
