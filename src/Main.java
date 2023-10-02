import client.Client;
import client.ClientInterface;
import client.Vote;
import remote.Distant;
import remote.Service;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Distant obj = (Distant) registry.lookup("objetDistant");

            Service refObjRmi = obj.getNewService();
            ClientInterface client = new Client();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Veuillez saisir votre candidat : ");
            int rankValue = scanner.nextInt();
            System.out.print("Veuillez saisir votre vote : ");
            int voteValue = scanner.nextInt();

            refObjRmi.sendVote(new Vote(rankValue, voteValue), client);

        } catch (RemoteException e) {
            System.out.println("Erreur avec le serveur");
        } catch (NotBoundException e) {
            System.out.println("Échec de récupération de l'objet distant 'objetDistant'");
        }
    }
}

