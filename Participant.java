// /rodi0231(Robert Dighem)_sisc7379(Simon Schwieler)_arho2993(Aaron Holmquist)
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Participant {

    private static int next_id = 99;



    private String firstName;
    private String lastName;
    private String teamName;
    private int id;
    private List<Result> resultArrayList = new ArrayList<>();



    public Participant(String firstName, String lastName, String teamName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamName = teamName;
        this.id = ++next_id;
        this.resultArrayList = new ArrayList<>();
    }

    //For testing early.
    public Participant(String firstName, String lastName, String teamName, int id){
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamName = teamName;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void addResult(Result result){
        resultArrayList.add(result);
    }

    public boolean hasRemainingAttempts(Event event){
        int attemptCounter = 0;
        for (Result r : resultArrayList){
            if(r.getParticipant() == this && r.getEvent() == event){
                ++attemptCounter;
            }
            if(attemptCounter >= event.getAttempts()){
                System.out.println("Error: Attempt limit reached.");
                return false;
            }

        }   return true;


    }

    public void getResultByParticipant(){

        ArrayList<Event> tempEvent = new ArrayList<>();
        for (Result r : resultArrayList){
            if(!tempEvent.contains(r.getEvent())){
                tempEvent.add(r.getEvent());
            }

        }
        for (Event event : tempEvent) {
            System.out.print("Results for " + getFirstName() + " " + getLastName() + " in "
                    + event.getEventName() + ": ");
            for (Result tempResult : resultArrayList) {
                if (tempResult.getEvent() == event && resultArrayList.size() <= 1) {
                    System.out.print(tempResult.getScore() + ".");
                }
                else if(tempResult.getEvent() == event){
                    System.out.print(tempResult.getScore() + ", ");
                }
            }
            //Hoppar raden.
            System.out.println("");
        }
    }

    public boolean hasResultsInEvent(Event e){
        for (Result r : resultArrayList){
            if(r.getEvent() == e){
                return true;
            }
        }
        return false;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString(){
        return firstName + " " + lastName + " " + teamName + " " + id;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Participant)) {
            return false;
        }
        Participant participant = (Participant) o;
        return id == participant.id &&
                Objects.equals(firstName, participant.firstName) &&
                Objects.equals(lastName, participant.lastName) &&
                Objects.equals(teamName, participant.teamName);
    }

}
