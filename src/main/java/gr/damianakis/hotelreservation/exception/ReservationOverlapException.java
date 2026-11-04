package gr.damianakis.hotelreservation.exception;

public class ReservationOverlapException extends RuntimeException{
    public ReservationOverlapException(String message){
        super(message);
    }
}
