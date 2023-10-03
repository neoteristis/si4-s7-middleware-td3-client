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

            Scanner scanner = new Scanner(System.in);

            // ============== Authentication ============== //

            // Student ID
            boolean isStudentIDValid = false;
            String studentID = null;

            while (!isStudentIDValid) {
                System.out.println("Enter your student ID: ");
                studentID = scanner.nextLine();

                if (votingService.isStudentIDValid(studentID))
                    isStudentIDValid = true;
                else
                    System.out.println("Invalid student ID");
                client.setStudentID(studentID);
            }

            // One Time Password
            System.out.println("Do you have your One Time Password ? (y/n)");
            String hasOTPUserInput = scanner.nextLine();
            String password = null;
            if (!hasOTPUserInput.equals("y")) {
                try {
                    System.out.println("Your One Time Password is : " + votingService.getUserOTP(studentID));
                } catch (RemoteException e) {
                    if (e.getCause() instanceof HasAlreadyVotedException) {
                        System.out.println("You have already voted");
                        System.out.println("Do you want to vote again ? (y/n)");

                        String voteAgainUserInput = scanner.nextLine();
                        if (voteAgainUserInput.equals("y")) {
                            System.out.println("Enter your old One Time Password: ");
                            String oldPassword = scanner.nextLine();

                            String newPassword = votingService.updateUserOTP(studentID, oldPassword);
                            System.out.println("Your new One Time Password is : " + newPassword);
                        } else {
                            return;
                        }
                    }
                }
            }

            // Authenticate user
            System.out.println("You have to enter your One Time Password :");
            password = scanner.nextLine();
            if (!votingService.authenticate(studentID, password)) {
                System.out.println("Wrong password");
                return;
            }

            // ============== Vote ============== //

            List<String> candidates = votingService.getCandidates();
            System.out.println("Candidats : ");
            candidates.forEach(candidate -> System.out.println(candidate));
            System.out.println();

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
