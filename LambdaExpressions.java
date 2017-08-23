import java.util.LinkedList;

public class LambdaExpressions {
    
    /* 3 ways of doing the same */
    public void forEachLambdaExamples() {
        
        LinkedList<Object> listOne = new LinkedList<>();
        LinkedList<Object> listTwo = new LinkedList<>();

        /* OK, now we want to compare those 2 lists and remove objects which are the same ...: */
        
        /* TRADITIONAL IDIOM */
        for(Object one : listOne) {
            for(Object two : listTwo) {
                if (one == two)
                    listTwo.remove(two);
            }
        } // everyone knows this one
        
        /* LAMBDA IDIOM (explicit) */
        listOne.forEach(one -> {
            listTwo.forEach(two -> {
                if (one == two)
                    listTwo.remove(two);
            });
        }); // doesn't it look/read better as lambda?
        
        /* LAMBDA IDIOM (implicit) */
        listOne.forEach(one -> listTwo.forEach(two -> {
            if (one == two)
                listTwo.remove(two);
        })); // probably not the best way, since it is very implicit to read
        
    }
    
    public void filterLambdaExamples() {
        
    }
}
