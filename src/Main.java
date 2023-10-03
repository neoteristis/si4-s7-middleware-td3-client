import client.Client;
import client.ClientInterface;
import client.Vote;
import exceptions.HasAlreadyVotedException;
import remote.Distant;
import remote.Service;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            // ============== Setup ============== //

            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Distant obj = (Distant) registry.lookup("objetDistant");

            Service votingService = obj.getNewService();
            ClientInterface client = new Client();

            // ============== Authentication ============== //

            // Student ID
            System.out.println("Enter your student ID: ");
            Scanner scannerID = new Scanner(System.in);
            String studentID = scannerID.nextLine();
            client.setStudentID(studentID);

            // One Time Password
            System.out.println("Dou you have your One Time Password ? (y/n)");
            Scanner scannerOTP = new Scanner(System.in);
            String hasOTPUserInput = scannerOTP.nextLine();
            String password = null;
            if (hasOTPUserInput.equals("y")) {
                System.out.println("Enter your One Time Password :");
                Scanner scanner = new Scanner(System.in);
                password = scanner.nextLine();
            } else {
                try {
                    password = votingService.getUserOTP(studentID);
                    System.out.println("Your One Time Password is : " + password);
                } catch (HasAlreadyVotedException e) {
                    System.out.println("You have already voted");
                    return;
                } catch (RemoteException e) {
                    System.out.println("Error : " + e.getMessage());
                }
            }

            // Authenticate user
            if (!votingService.authenticate(studentID, password)) {
                System.out.println("Wrong password");
                return;
            }

            // ============== Vote ============== //

            List<String> candidates = votingService.getCandidates();
            System.out.println("Candidats : ");
            candidates.forEach(candidate -> System.out.println(candidate));
            System.out.println();

            Scanner scanner = new Scanner(System.in);
            List<Vote> votes = new ArrayList<>();
            for(int i=0; i<candidates.size(); i++){
                String candidateLabel = candidates.get(i).split("\"")[0];
                System.out.println("Vote pour candidat " + candidateLabel);
                int voteValue = scanner.nextInt();
                if (voteValue < 0 || voteValue > 3) {
                    System.out.println("Le vote est une valeur entre 0 et 3");
                    i--;
                    continue;
                }
                votes.add(new Vote(i + 1, voteValue));
            }
            votingService.sendVotes(votes, client);

            // ============== Résultats ============== //
            System.out.println("Terminé ! Veux-tu consulter les résultats ? (y/n)");


        } catch (RemoteException e) {
            System.out.println("Erreur avec le serveur");
        } catch (NotBoundException e) {
            System.out.println("Échec de récupération de l'objet distant 'objetDistant'");
        }
    }
}
