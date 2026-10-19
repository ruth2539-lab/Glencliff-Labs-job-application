
import java.awt.Desktop;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OutdoorFinder {

    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("==================================");
        System.out.println("     OUTDOOR ACTIVITY FINDER");
        System.out.println("==================================");
        System.out.println("Let's find something fun to do!");

        String location = ask("What city or ZIP code are you in?");
        String interest = ask(
            "What do you enjoy? (nature, sports, adventure, "
            + "relaxing, photography, or anything)"
        );
        String company = ask(
            "Who are you going with? (alone, friends, family, date)"
        );
        String budget = ask(
            "What's your budget? (free, under $20, or any budget)"
        );
        String time = ask(
            "How much time do you have? (30 minutes, 1 hour, "
            + "a few hours, or all day)"
        );
        String travel = ask(
            "How far can you travel? (walking distance, 10 miles, "
            + "or farther)"
        );

        List<String> activities = recommend(interest, company, budget);

        System.out.println("\n==================================");
        System.out.println("       YOUR OUTDOOR IDEAS");
        System.out.println("==================================");

        for (int i = 0; i < activities.size(); i++) {
            System.out.println((i + 1) + ". " + activities.get(i));
        }

        System.out.println("\nLet's find places that match your answers!");

        String query = interest + " outdoor activities near " + location
            + " for " + company
            + ", budget " + budget
            + ", time available " + time
            + ", travel distance " + travel;

        System.out.println("\nGoogle search:");
        System.out.println(query);

        openGoogle(query);

        System.out.println("\nWant more ideas? Run the program again!");
        input.close();
    }

    private static String ask(String question) {
        System.out.print("\n" + question + "\n> ");
        return input.nextLine().trim();
    }

    private static List<String> recommend(
            String interest, String company, String budget) {

        String interests = interest.toLowerCase();
        String group = company.toLowerCase();
        String money = budget.toLowerCase();

        List<String> ideas = new ArrayList<>();

        if (interests.contains("nature")
                || interests.contains("anything")) {
            ideas.add("Explore a local park or nature trail");
            ideas.add("Go birdwatching or watch the sunset");
            ideas.add("Take a nature photography walk");
        }

        if (interests.contains("sport")) {
            ideas.add("Play basketball, soccer, or tennis");
            ideas.add("Go biking or try a local skate park");
        }

        if (interests.contains("adventure")) {
            ideas.add("Try hiking or a new walking trail");
            ideas.add("Explore a new outdoor area safely");
            ideas.add("Look for kayaking or climbing opportunities");
        }

        if (interests.contains("relax")) {
            ideas.add("Have a picnic or read outside");
            ideas.add("Visit a garden or sit by a lake");
        }

        if (interests.contains("photo")) {
            ideas.add("Go on a photo scavenger hunt");
            ideas.add("Photograph wildlife or interesting buildings");
        }

        if (interests.contains("anything")) {
            ideas.add("Try geocaching");
            ideas.add("Visit a farmers market or outdoor event");
        }

        if (ideas.isEmpty()) {
            ideas.add("Explore a nearby park");
            ideas.add("Try a walking or biking trail");
            ideas.add("Discover a local outdoor event");
            ideas.add("Search for " + interest + " activities");
        }

        if (group.contains("friend")) {
            ideas.add("Organize an outdoor scavenger hunt");
            ideas.add("Play a group game at a park");
        } else if (group.contains("family")) {
            ideas.add("Plan a family nature walk");
        } else if (group.contains("date")) {
            ideas.add("Plan a sunset walk or outdoor picnic");
        }

        if (money.contains("free")) {
            ideas.add("Look for free parks and community events");
        }

        return ideas;
    }

    private static void openGoogle(String query) {
        try {
            String encoded = URLEncoder.encode(
                query, StandardCharsets.UTF_8
            );
            URI google = URI.create(
                "https://www.google.com/search?q=" + encoded
            );

            if (Desktop.isDesktopSupported()
                    && Desktop.getDesktop().isSupported(
                        Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(google);
                System.out.println("Google opened in your browser.");
            } else {
                System.out.println(
                    "Your system cannot open a browser automatically."
                );
                System.out.println(google);
            }
        } catch (Exception e) {
            System.out.println("Could not open Google: " + e.getMessage());
        }
    }
}
