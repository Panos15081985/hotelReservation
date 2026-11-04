package gr.damianakis.hotelreservation.exception;

public class RoomAlreadyExistException extends RuntimeException{
    public RoomAlreadyExistException(String message){super(message);}
}
