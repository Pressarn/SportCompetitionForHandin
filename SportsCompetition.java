// /rodi0231(Robert Dighem)_sisc7379(Simon Schwieler)_arho2993(Aaron Holmquist)
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SportsCompetition {

    private Scanner keyboard = new Scanner(System.in);

    private List<Participant> participantList = new ArrayList<>();
    private List<Event> eventList = new ArrayList<>();

    private void writeMenu(){
        System.out.println("Following commands are possible:");
        System.out.println(" Add event");
        System.out.println(" Add participant");
        System.out.println(" Remove participant");
        System.out.println(" Add result");
        System.out.println(" Participant");
        System.out.println(" Event result (write the name of the event");
        System.out.println(" Teams");
        System.out.println(" Message (followed by your message)");
        System.out.println(" Exit");
    }

    private void runCommandLoop() {
        writeMenu();

        boolean running = true;
        while (running) {
            System.out.println("Command> ");
            String cmd = keyboard.nextLine().toLowerCase();

            if (cmd.matches("message.+")) {
                message(cmd);
            }
            else if (getEvent(cmd) != null){
                eventResult(cmd);
            }else {
                switch (cmd) {
                    case "add event":
                        addEvent();
                        break;
                    case "add participant":
                        addParticipant();
                        break;
                    case "remove participant":
                        removeParticipant();
                        break;
                    case "add result":
                        addResult();
                        break;
                    case "participant":
                        getParticipantResult();
                        break;
                    case "message":
                        message(cmd);
                        break;
                    case "exit":
                        exit();
                        break;
                    default:
                        System.out.println("Error: Unknown command: " + cmd);
                }
            }
        }
    }

    private void eventResult(String eventName) {
        Event event = null;
        for (Event tempEvent : eventList) {
            if (eventName.equalsIgnoreCase(tempEvent.getEventName())) {
                event = tempEvent;
                break;
            }
        }
        event.getResultForEvent();
    }

    private void getParticipantResult() {
        Participant p = participantControl();
        if(p != null){
            p.getResultByParticipant();
        }

    }

    private void addResult() {

        Participant p = participantControl();
        if(p == null){
            return;
        }
        Event e = getEvent(stringRead("Enter event: "));
        if(e == null){
            System.out.print("Error: no event with that name exists.");
            return;
        }

        if(p.hasRemainingAttempts(e)){
            double score;
            while ((score = doubleRead("Results for " + p.getFirstName() + " " + p.getLastName() + " from " +
                    p.getTeamName() + " in " + e.getEventName() +": ")) < 0) {
                System.out.println("Error: Attempts can't be smaller than 0!");
            }
            Result r = new Result(score, p, e);
            e.addResult(r);
            p.addResult(r);
            System.out.println("Result for " + p.getFirstName() + " in " + e + " added!");

        }
    }

    private Event getEvent (String eventName){
        for (Event event : eventList){
            if (event.getEventName().equalsIgnoreCase(eventName)){
                return event;
            }
        }
        return null;
    }

    private int intRead(String prompt) {
        System.out.println(prompt);
        int readInt = keyboard.nextInt();
        keyboard.nextLine();
        return readInt;
    }

    private double doubleRead(String prompt) {
        System.out.println(prompt);
        double readDouble = keyboard.nextDouble();
        keyboard.nextLine();
        return readDouble;
    }

    private String stringRead(String prompt) {
        System.out.println(prompt);
        String s = keyboard.nextLine();
        if(s.isEmpty()){
            System.out.println("Error: string is empty.");
        }
        return normalizer(s);
    }

    private void addEvent(){

        int attempts;
        String eventName;

        do{
            eventName = stringRead("Event name: ");
        }while(eventName == null);

        if (getEvent(eventName) != null){
            System.out.println("Error:" + eventName + " has already been added.");
            return;
        }
        while ((attempts = intRead("Attempts allowed: ")) < 1) {
            System.out.println("Error: Attempts can't be smaller than 1!");
        }
        Event event = new Event(eventName, attempts);
        eventList.add(event);
        System.out.println(event.getEventName() + " has been added.");
    }

    private void addParticipant(){

        String firstName;
        String lastName;
        String teamName;

        do{
            firstName = stringRead("First name: ");
        }
        while(firstName == null);
        do{
            lastName = stringRead("Last name: ");
        }
        while(lastName == null);
        do{
            teamName = stringRead("Team name: ");
        }
        while(teamName == null);

        Participant participant = new Participant(firstName, lastName, teamName);
        participantList.add(participant);
        System.out.println(participant.getFirstName() + " " + participant.getLastName() + " from " + participant.getTeamName()
                + " with ID: " + participant.getId()   + " has been added!");

    }

    private void removeParticipant(){

        Participant p = participantControl();

        if (p == null){
            return;
        }

        participantList.remove(p);
        for(Event e : eventList){
            e.removeResult(p);

        }
        System.out.println(p.getFirstName() + " " + p.getLastName() + " from " + p.getTeamName() + " with number " + p.getId() + " removed.");
    }

    private Participant participantControl() {

        int idCheck = intRead("ID: ");

        Participant p = null;
        for (Participant temp : participantList) {
            if (temp.getId() == idCheck)
                p = temp;
        }
        if (p == null) {
            System.out.println("Error: No participant with ID: " + idCheck + " exists.");
        }
        return p;
    }

    private String normalizer(String s) {

        s = s.toLowerCase().trim();
        if (s.isEmpty()){
//            throw new IllegalArgumentException("");
            System.out.println("Error: String is empty.");

        }
        else {
            char[] name = s.toCharArray();
            name[0] = ("" + (name[0])).toUpperCase().charAt(0);
            s = new String(name);
            return s;
        }
        return null;
    }

    private void message(String message){

        message = message.replaceFirst("message", "");

        if (message.length() > 57) {
            message = message.substring(0, 57);
        }

        char fill = ' ';

        String toPad = "#";
        String specialInLine = new String(new char[toPad.length() + 57 - message.length()]).replace('\0', fill) + toPad;
        System.out.println("");
        printSign(60, '#');
        System.out.println("");
        System.out.println("#                                                          #");
        System.out.print("#" + message.toUpperCase());
        System.out.println(specialInLine);
        System.out.println("#                                                          #");
        printSign(60, '#');
        System.out.println("");

    }

    private void printSign(int ammount, char type){
        for (int x = 0; x < ammount; x++){
            System.out.print(type);
        }
    }

    private void exit(){
        System.out.println("Good bye!");
        System.exit(0);
    }

    private void setUp(){
        System.out.println("Welcome");
    }

    private void run() {
        setUp();
        runCommandLoop();
//        closeDown();
    }

    public static void main(String[] args) {
        SportsCompetition sportsCompetition = new SportsCompetition();
        sportsCompetition.run();

    }

}
