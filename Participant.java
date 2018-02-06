// /rodi0231(Robert Dighem)_sisc7379(Simon Schwieler)_arho2993(Aaron Holmquist) Grupp 320.
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Participant {

    //Static nedan tillhör inte något specifikt objekt av klassen utan tillhör klassen. Används för att räkna hur många
    //objekt som skapats av klassen, alltså ID'n.
    private static  int next_id = 99;
    private String firstName;
    private String lastName;
    private String teamName;
    private int id;
    private List<Result> resultArrayList;

    public Participant(String firstName, String lastName, String teamName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamName = teamName;
        this.id = ++next_id;
        this.resultArrayList = new ArrayList<>();
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

    public ArrayList<Event> getEventsWithAResult(){
        ArrayList<Event> tempEvent = new ArrayList<>();
        for (Result r : resultArrayList){
            if(!tempEvent.contains(r.getEvent())){
                tempEvent.add(r.getEvent());
            }

        }
        return tempEvent;
    }

    public void getResultByParticipant(){

        for (Event event : getEventsWithAResult()) {

            System.out.print("Results for " + getFirstName() + " " + getLastName() + " in "
                    + event.getEventName() + ": ");
            sortArray(getResultArrayListForEvent(event));
            for (Result tempResult : getResultArrayListForEvent(event)) {

                if (tempResult.getEvent() == event && resultArrayList.size() <= 1) {
                    System.out.print(tempResult.getScore() + ".");
                }
                else if(tempResult.getEvent() == event){
                    System.out.print(tempResult.getScore() + ", ");
                }
            }
            System.out.println("");
        }
    }

    public ArrayList<Result> getResultArrayListForEvent(Event e) {
        ArrayList<Result> temporaryResultList = new ArrayList<>();
        for (Result r : resultArrayList){
            if(r.getEvent() == e) {
                temporaryResultList.add(r);
            }
        }

        return temporaryResultList;
    }

    public int getId() {
        return id;
    }

    public void sortArray(ArrayList<Result> unsortedResultList) {
        for(int indexToSort = 1; indexToSort < unsortedResultList.size(); indexToSort++){
            moveLeftUntilSorted(indexToSort, unsortedResultList);
        }
    }

    private void moveLeftUntilSorted(int index, ArrayList<Result> unsortedResultList){
        while(index>0 && unsortedResultList.get(index).getScore() > unsortedResultList.get(index-1).getScore()){
            swap(unsortedResultList, index, index-1);
        }
    }

    private void swap(ArrayList<Result> unsortedResultList, int first, int second){
        double temp = unsortedResultList.get(first).getScore();
        unsortedResultList.get(first).setScore(unsortedResultList.get(second).getScore());
        unsortedResultList.get(second).setScore(temp);
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
