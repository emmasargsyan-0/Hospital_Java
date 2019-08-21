public class Human {
    private String name;
    private String lastName;
    private String passportNumber;

    Human(String name, String lastName, String passportNumber){
        this.lastName = lastName;
        this.name = name;
        this.passportNumber=passportNumber;
    }

    public void print(){
        System.out.println("Human");
    }

    public String getName(){
        return name;
    }

    public String getPassportNumber(){
        return passportNumber;
    }

    public String getLastName(){
        return lastName;
    }
}
