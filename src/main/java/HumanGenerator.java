import java.util.*;

public class HumanGenerator {
    private ArrayList<Patient> patients = new ArrayList();
    private ArrayList<Doctor> doctors = new ArrayList();
    JDBCManager manager=new JDBCManager("jdbc:sqlserver://localhost:1433;database=DB","lg","1234");
    String[] names =new String[]{"Emma", "Gohar", "Lusine", "Ani", "Sara", "Elen", "Vahan", "Gor", "Arsen","Vahag", "Narek", "Edgar","Alyona","Khazhak",
            "Hakob", "Sargis"};
    String[] lastnames =new String[]{"Sargsyan", "Muradyan", "Arakelyan", "Ghazartyan", "Grigoryan", "Manukyan", "Harutyunyan", "Manukyan"
            ,"Ayvazyan", "Ter-Antonyan"};
    String[] specialization=new String[]{"Addiction psychiatrist",
            "Adolescent medicine specialist",
            "Allergist",
            "Anesthesiologist",
            "Cardiac electrophysiologist",
            "Cardiologist",
            "Cardiovascular surgeon",
            "Colon and rectal surgeon"};
    String [] illness=new String[]{"Alcohol abuse and alcoholism",
            "Allergies",
            "Alopecia areata",
            "Amputation",
            "Anxiety disorders",
            "Arthritis",
            "Asperger syndrome",
            "Asthma",
            "Deafness and being hard of hearing",
            "Depression",
            "Diabetes",
            "Down syndrome",
            "Drug abuse and addiction",
            "Dwarfism" };
    public void generateDoctors(){
        Random rand = new Random();
        for(int i=0; i<20; i++) {
            int nameIndex = rand.nextInt(12); //0-11
            int lastNameIndex = rand.nextInt(10); //0-11
            int specializationIndex = rand.nextInt(8); // 0-7
            manager.insertDoctorToDB(new Doctor(names[nameIndex], lastnames[lastNameIndex], specialization[specializationIndex],generatePassportNumber()));
        }
    }
    public void generatePatients(){
        Random rand = new Random();
        for(int i=0; i<100; i++) {
            int nameIndex = rand.nextInt(12); //0-11
            int lastNameIndex = rand.nextInt(10); //0-11
            int illnessIndex = rand.nextInt(14); //0-14
            manager.insertPatientToDB(new Patient(names[nameIndex], lastnames[lastNameIndex], illness[illnessIndex]
                    ,generatePassportNumber(), rand.nextInt(20)));
        }
    }
    public String generatePassportNumber(){
        Random rand = new Random();
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        String passportNumber="";
        passportNumber+=""+alphabet[rand.nextInt(26)]+alphabet[rand.nextInt(26)]+(rand.nextInt(8999999)+1000000);
        return passportNumber;
    }
}
