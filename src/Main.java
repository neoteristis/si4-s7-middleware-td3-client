import client.Client;
import client.ClientInterface;
import client.Vote;
import remote.Distant;
import remote.Service;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Distant obj = (Distant) registry.lookup("objetDistant");

            Service votingService = obj.getNewService();
            ClientInterface client = new Client();

            List<String> candidates = votingService.getCandidates();
            System.out.println("Candidats : ");
            candidates.forEach( candidate -> System.out.println(candidate));
            System.out.println();

            Scanner scanner = new Scanner(System.in);
            List<Vote> votes = new ArrayList<>();
            for(int i=0; i<candidates.size(); i++){
                System.out.println("Vote pour candidat " + Integer.toString(i + 1) + ":");
                int voteValue = scanner.nextInt();
                if(voteValue < 0 || voteValue > 3){
                    System.out.println("Le vote est une valeur entre 0 et 3");
                    i--;
                    continue;
                }
                votes.add(new Vote(i+1, voteValue));
            }
            votingService.sendVotes(votes, client);

        } catch (RemoteException e) {
            System.out.println("Erreur avec le serveur");
        } catch (NotBoundException e) {
            System.out.println("Échec de récupération de l'objet distant 'objetDistant'");
        }
    }
}

