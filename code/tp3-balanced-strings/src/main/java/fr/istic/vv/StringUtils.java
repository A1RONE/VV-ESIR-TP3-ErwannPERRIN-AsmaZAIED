package fr.istic.vv;

import java.util.Stack;

public class StringUtils {

    private StringUtils() {}

    public static boolean isBalanced(String str) {
        Stack<Character> stack = new Stack<>();

        for (int i=0; i<str.length() ; i++)
        {
            char checked = str.charAt(i);
            if (checked == '(' || checked == '[' || checked == '{') {
                stack.push(checked);
            }
            else if (!stack.isEmpty() && (checked == ')' || checked == ']' || checked == '}'))
            {
                char begin = stack.pop();
                if ( !(
                    (checked == ')' && begin == '(')
                    ||
                    (checked == ']' && begin == '[')
                    ||
                    (checked == '}' && begin == '{')
                    )
                    )
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        
        return stack.isEmpty();
    }

}
