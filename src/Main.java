
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        TicketBooking b=new TicketBooking();

        while (true) {
            System.out.println("1.Booking\n2.Cancel\n3.Print");
            int c = sc.nextInt();
            switch (c) {
                case 1:
                    System.out.println("Passenger Name: ");
                    String pName = sc.next();
                    System.out.println("Start Position: ");
                    char startPosition = sc.next().charAt(0);
                    System.out.println("End Position: ");
                    char endPosition = sc.next().charAt(0);
                    System.out.println("Total no of Tickets: ");
                    int numberOfTickets = sc.nextInt();
                    b.booking(pName, startPosition, endPosition, numberOfTickets);
                    break;

                case 2:
                    System.out.println("Enter pnr :");
                    int pnr=sc.nextInt();
                    b.cancel(pnr);
                    break;
                case 3:
                    b.chart();
                    break;
            }
            if (c == 0)
                break;
        }


    }
}