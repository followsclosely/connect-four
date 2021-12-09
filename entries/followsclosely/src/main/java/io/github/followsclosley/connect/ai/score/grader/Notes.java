package io.github.followsclosley.connect.ai.score.grader;

public class Notes {

    private final static ThreadLocal<Notes> threadLocalValue = ThreadLocal.withInitial(() -> new Notes());

    private final StringBuilder builder = new StringBuilder();

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