import java.util.ArrayList;

public class TicketBooking {
    int id = 0;
    char[][] avlSeats = new char[4][6];
    char[][] wAvlSeats = new char[4][6];
    ArrayList<Passenger> passengerList = new ArrayList<>();
    ArrayList<Passenger> waitingList=new ArrayList<>();

    public TicketBooking() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                avlSeats[i][j] = '_';
                wAvlSeats[i][j] = '_';
            }
        }
    }
    private int getIndex(char ind) {
        return switch (ind) {
            case 'A' -> 0;
            case 'B' -> 1;
            case 'C' -> 2;
            case 'D' -> 3;
            case 'E' -> 4;
            default -> -1;
        };
    }
    public void booking(String pName, char startPosition, char endPosition, int numberOfTickets) {
        int stInd = getIndex(startPosition);
        int endInd = getIndex(endPosition);
        int count = 0;
        ArrayList<Integer> seatNo = new ArrayList<>();

        if (isAvailable(stInd, endInd, numberOfTickets,'t')) {
            for (int j = 0; j < 6; j++)
                if (count < numberOfTickets)
                    if (avlSeats[stInd][j] == '_') {
                        avlSeats[stInd][j] = 'x';
                        count++;
                        seatNo.add(j + 1);
                    }

            for (int i = stInd+1; i < endInd; i++) {
                count = 0;
                for (Integer integer : seatNo)
                    if (count < numberOfTickets)
                        if (avlSeats[i][integer - 1] == '_') {
                            avlSeats[i][integer - 1] = 'x';
                            count++;
                        }
            }
            System.out.println("Ticket booked successfully\nPNR number :" + ++id + "\n" +
                    "Seat no:" + seatNo + "\n\n");
            passengerList.add(new Passenger(id, pName, startPosition, endPosition, numberOfTickets, seatNo));
        }
        else if(numberOfTickets<=2 && isAvailable(stInd,endInd,numberOfTickets,'w')){
            for (int j = 0; j < 6; j++)
                if (count < numberOfTickets)
                    if (wAvlSeats[stInd][j] == '_') {
                        wAvlSeats[stInd][j] = 'x';
                        count++;
                        seatNo.add(j + 1);
                    }

            for (int i = stInd+1; i < endInd; i++) {
                count = 0;
                for (Integer integer : seatNo)
                    if (count < numberOfTickets)
                        if (wAvlSeats[i][integer - 1] == '_') {
                            wAvlSeats[i][integer - 1] = 'x';
                            count++;
                        }
            }
            System.out.println("Under Waiting\nPNR number :" + ++id + "\n\n");
            waitingList.add(new Passenger(id, pName, startPosition, endPosition, numberOfTickets, seatNo));
        }
        else
            System.out.println("No tickets available");
    }
    private boolean isAvailable(int stInd, int endInd, int numberOfTickets ,char type) {
        int aSeat;
        int aWSeat;
        for (int i = stInd; i < endInd; i++) {
            aSeat = 0;
            aWSeat=0;
            for (int j = 0; j < 6; j++) {
                if (avlSeats[i][j] == '_' && type=='t')
                    aSeat++;
                if(wAvlSeats[i][j] == '_' && type=='w')
                    aWSeat++;
            }
            if (aSeat < numberOfTickets && type=='t')
                return false;
            if(aWSeat < numberOfTickets && type=='w')
                return false;
        }
        return true;
    }
    public void chart () {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.print(avlSeats[i][j] + " ");
            }
            System.out.println();
        }
    }
    public void cancel(int pnr){
        int startPosition=0;
        int numberOfTicket;
        int endPosition=0;
        int pnrNo=0;
        String pName;
        int f=0;
        ArrayList<Integer> seatNo=new ArrayList<>();
        for(Passenger i:passengerList){
            if(i.PNR_NO()==pnr){
                f=1;
                startPosition=getIndex(i.startPositions());
                endPosition=getIndex(i.endPositions());
                seatNo=i.seatNo();
                break;
            }
        }
        if(f==0)
            System.out.println("Not found");
        else {
            passengerList.removeIf(passenger -> passenger.PNR_NO() == pnr);
            for (int i = startPosition; i < endPosition; i++)
                for (int j : seatNo)
                    avlSeats[i][j - 1] = '_';
        }
        f=0;
        for(Passenger i:waitingList){
            startPosition=getIndex(i.startPositions());
            endPosition=getIndex(i.endPositions());
            numberOfTicket=i.numberOfTickets();
            seatNo=i.seatNo();
            pName=i.name();
            pnrNo=i.PNR_NO();
            if(isAvailable(startPosition,endPosition,numberOfTicket,'t')){
                f=1;
                booking(pName,i.startPositions(),i.endPositions(),numberOfTicket);
                break;
            }
        }
        final int finalPnr=pnrNo;
        if(f==1){
            waitingList.removeIf(passenger -> passenger.PNR_NO()==finalPnr);
            for (int i = startPosition; i < endPosition; i++)
                for (int j : seatNo)
                    wAvlSeats[i][j - 1] = '_';
        }
    }
}
