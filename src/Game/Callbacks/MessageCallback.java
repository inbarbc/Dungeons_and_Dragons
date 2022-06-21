package Game.Callbacks;
//
//public class MessageCallback {
//    private String message;
//
//    public MessageCallback(String str){
//        message = str;
//    }
//
//    public void Print() {
//        System.out.println(message);
//    }
//
//    public void Send(String format) {
//        message = format;
//        Print();
//    }
//}

public interface MessageCallback{
    void Send(String message);
}