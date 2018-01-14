// /rodi0231(Robert Dighem)_sisc7379(Simon Schwieler)_arho2993(Aaron Holmquist)


public class Result implements Comparable<Result> {

    private double score;
    private Participant participant;
    private Event event;

    public Result(double score, Participant participant, Event event){
        this.score = score;
        this.participant = participant;
        this.event = event;
    }

    public double getScore(){
        return score;
    }

    public Participant getParticipant(){
        return participant;
    }

    public Event getEvent(){
        return event;
    }

    public String toString(){
        return "Results: " + participant.getFirstName() + participant.getLastName() + event + score;
    }
    @Override
    public int compareTo(Result r) {
        if(r.getScore() > this.getScore()){
            return 1;
        }
        else if(r.getScore() == this.getScore()){
            return 0;
        }
        else
            return -1;

    }

    public int compareName(Result other) {
        return event.getEventName().compareTo(other.getEvent().getEventName());
    }


}
