// /rodi0231(Robert Dighem)_sisc7379(Simon Schwieler)_arho2993(Aaron Holmquist)
import java.util.*;


public class Event {

    private List<Result> resultArrayList = new ArrayList<>();
    private String eventName;
    private int attempts;

    public Event(String eventName, int attempts){
        this.eventName = eventName;
        this.attempts = attempts;
        this.resultArrayList = new ArrayList<>();
    }

    public void addResult(Result result){
        resultArrayList.add(result);
    }

    public void getResultForEvent() {
        boolean needsSorting = false;
        int placement = 1;
        Result previousPosition = null;
        Collections.sort(resultArrayList);
        ArrayList<Result> tempResult = getBestResults();
        System.out.println("Result for " + this + ":");



        for (int i = 0; i < tempResult.size(); i++) {
            Result currentPosition = tempResult.get(i);
            if (previousPosition != null) {
                if (currentPosition.getScore() != previousPosition.getScore()) {
                    placement = i;
                    placement++;
                }
            }
            previousPosition = currentPosition;

            if (currentPosition.getScore() >= 0) {
                System.out.println((placement) + " " + currentPosition.getScore() + " " + currentPosition.getParticipant().getFirstName()
                        + " " + currentPosition.getParticipant().getLastName() + " " + currentPosition.getParticipant().getTeamName());
            }
        }
    }

    public ArrayList<Result> sortOnLetter(ArrayList<Result> list){
        Collections.sort(list, new Comparator<Result>() {
            public int compare(Result one, Result other) {
                return one.getParticipant().getFirstName().compareTo(other.getParticipant().getFirstName());
            }
        });
        return list;
    }

    public void removeResult(Participant p) {
        ArrayList<Result> temporaryResultList = new ArrayList<>();

        for (Result r : resultArrayList) {
            if (r.getParticipant().equals(p))
                temporaryResultList.add(r);
        }
        resultArrayList.removeAll(temporaryResultList);
        temporaryResultList.clear();

    }

    private ArrayList<Result> getBestResults() {
        ArrayList<Participant> temporaryParticipantList = new ArrayList<>();
        ArrayList<Result> temporaryResultList = new ArrayList<>();
        for (Result r : resultArrayList) {
            if (!temporaryParticipantList.contains(r.getParticipant())) {
                temporaryParticipantList.add(r.getParticipant());
                temporaryResultList.add(r);

                Collections.sort(temporaryResultList, new Comparator<Result>() {
                    @Override
                    public int compare(Result item1, Result item2) {
                        if(item1.getScore() == item2.getScore()) {
                            String compareString1 = item1.getParticipant().getFirstName();
                            String compareString2 = item2.getParticipant().getFirstName();
                            return compareString1.compareToIgnoreCase(compareString2);
                        }
                        return 0;
                    }


                });
            }
        }

        return temporaryResultList;
    }

    public String getEventName() {
        return eventName;
    }

    public int getAttempts(){
        return attempts;
    }

    @Override
    public String toString(){
        return eventName;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Event)) {
            return false;
        }
        Event event = (Event) o;
        return attempts == event.attempts &&
                Objects.equals(eventName, event.eventName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventName, attempts);
    }

}
