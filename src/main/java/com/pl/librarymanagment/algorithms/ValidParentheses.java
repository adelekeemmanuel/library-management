package com.pl.librarymanagment.algorithms;

import java.util.*;

public class ValidParentheses {

    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        Map<Character, Character> brackets = new HashMap<>();
        brackets.put(')', '(');
        brackets.put('}', '{');
        brackets.put(']', '[');

        for (char c : s.toCharArray()) {
            if (brackets.containsValue(c)) {
                stack.push(c);
            } else if (brackets.containsKey(c)) {
                if (stack.isEmpty() || stack.pop() != brackets.get(c)) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    // Test
    public static void main(String[] args) {
        System.out.println(isValid("({[]})")); // true
        System.out.println(isValid("([)]"));   // false
        System.out.println(isValid("{[}"));    // false
        System.out.println(isValid(""));       // true
    }

}
