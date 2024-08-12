import java.util.ArrayList;

public record Passenger(int PNR_NO, String name, char startPositions, char endPositions, int numberOfTickets,
                        ArrayList<Integer> seatNo) {
}
