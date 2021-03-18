package io.github.followsclosley.connect.ai.grader;

public class Notes {

    private static ThreadLocal<Notes> threadLocalValue = new ThreadLocal<>() {
        protected Notes initialValue() {
            return new Notes();
        }
    };

    private StringBuilder builder = new StringBuilder();

    public static Notes getInstance(){
        return threadLocalValue.get();
    }
    public static void destroyInstance(){
        threadLocalValue.remove();
    }

    public void append(String message, Object... args){
        builder.append(String.format(message, args));
    }

    public String getDetails(){
        return builder.toString();
    }
}