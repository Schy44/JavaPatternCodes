package observerpattern;

import java.util.ArrayList;
import java.util.List;

// Subject interface
interface Subject {
    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers(String post);
}

// Concrete Subject
class FacebookUser implements Subject {
    private String username;
    private List<Observer> followers;
    private String latestPost;

    public FacebookUser(String username) {
        this.username = username;
        this.followers = new ArrayList<>();
    }

    public void postUpdate(String post) {
        latestPost = post;
        notifyObservers(post);
    }

    @Override
    public void registerObserver(Observer observer) {
        followers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        followers.remove(observer);
    }

    @Override
    public void notifyObservers(String post) {
        for (Observer follower : followers) {
            follower.update(username, post);
        }
    }
}

// Observer interface
interface Observer {
    void update(String username, String post);
}

// Concrete Observer
class FacebookFollower implements Observer {
    private String followerName;

    public FacebookFollower(String followerName) {
        this.followerName = followerName;
    }

    @Override
    public void update(String username, String post) {
        System.out.println(followerName + " received an update from " + username + ":");
        System.out.println(post);
        System.out.println();
    }
}

public class ObserverPatternDemo {
    public static void main(String[] args) {
        // Create Facebook users
        FacebookUser alice = new FacebookUser("Alice");
        FacebookUser bob = new FacebookUser("Bob");

        // Create followers
        FacebookFollower follower1 = new FacebookFollower("Follower 1");
        FacebookFollower follower2 = new FacebookFollower("Follower 2");

        // Register followers to users
        alice.registerObserver(follower1);
        alice.registerObserver(follower2);
        bob.registerObserver(follower2);

        // Users post updates
        alice.postUpdate("Hello, everyone! This is my first post.");
        bob.postUpdate("Feeling excited about the weekend!");

        // Follower 1 decides to unfollow Alice
        alice.removeObserver(follower1);

        // Alice posts another update
        alice.postUpdate("A beautiful sunset today!");

        // Follower 1 doesn't receive the new update
    }
}