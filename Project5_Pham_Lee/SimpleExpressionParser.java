import javax.swing.text.html.parser.Parser;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Starter code to implement an ExpressionParser. Your parser methods should use the following grammar:
 *    E := A | X
 *  * A := A+M | M
 *  * M := M*M | X
 *  * X := (E) | L
 *  * L := [0-9]+ | [a-z]
 */
public class SimpleExpressionParser implements ExpressionParser {

    Expression expression;

    /*
     * Attempts to create an expression tree -- flattened as much as possible -- from the specified String.
     * Throws a ExpressionParseException if the specified string cannot be parsed.
     * @param str the string to parse into an expression tree
     * @param withJavaFXControls you can just ignore this variable for R1
     * @return the Expression object representing the parsed expression tree
     */
    public Expression parse(String str, boolean withJavaFXControls) throws ExpressionParseException {
        // Remove spaces -- this simplifies the parsing logic
        str = str.replaceAll(" ", "");
        Expression expression = parseExpression(str);
        if (expression == null) {
            // If we couldn't parse the string, then raise an error
            throw new ExpressionParseException("Cannot parse expression: " + str);
        }

        // Flatten the expression before returning
        expression.flatten();
        return expression;
    }

    /**
     * Parses the expression depending on the grammar rule
     * @param str is the string version of the expression
     * @return a parsed expression
     */
    protected Expression parseExpression(String str) {

        if (parseE(str)) {
            return expression;
        } else if (parseA(str)) {
            return expression;
        } else if (parseM(str)) {
            return expression;
        } else if (parseX(str)) {
            return expression;
        } else if (parseL(str)) {
            return expression;
        }

        return null;
    }

    /**
     * returns true if the expression can be parsed using the grammar rule E
     * @param str is the string version of the expression
     * @return true if expression can be parsed using rule A and rule X
     */
    private boolean parseE(String str) {
        return (parseA(str) || parseX(str));
    }

    /**
     * returns true if the expression can be parsed using the grammar rule X, which is parenthesis
     * @param str is the string version of the expression
     * @return true if the expression can be parsed using rule X
     */
    private boolean parseX(String str) {
        if (str.contains("(") && str.contains(")")) {
            if (str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')' && parseE(str.substring(1, str.length() - 1))) {
                ExpressionTree ex = new ExpressionTree("()");
                if (str.length() > 2) {
                    Expression a = parseExpression(str.substring(1, str.length() - 1));
                    ex.addSubexpression(a);
                    expression = ex;
                    return true;
                }
            }

        }
        return parseL(str);
    }

    /**
     * returns true if the expression can be parsed using rule L, which is alphabets and numbers
     * @param str is the string version of the expression
     * @return true if the expression can be parsed using rule L
     */
    private boolean parseL(String str) {
        if (str.length() > 0) {

            char c = str.charAt(0);
            if (Character.isAlphabetic(c) || isNumber(str)) {
                ExpressionTree b = new ExpressionTree(str);
                expression = b;

                return true;
            }


        }
        return false;
    }

    /**
     * returns true if the string is a number
     * @param str is the string version of the Number
     * @return a boolean value
     */
    private boolean isNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * returns true if the expression can be parsed using rule A, which is +
     * can prove by calling a helper function
     * @param str is the string version of the expression
     * @return true if the expression can be parsed
     */
    private boolean parseA(String str) {

            return (parseHelper(str, '+', this::parseA, this::parseM, this::parseM));
    }


    int[] findparenthesis(String str) {
        int[] pos = new int[2];
        int a = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                if (a == 0) {
                    pos[0] = i;
                }
                a++;
            }
            else if (str.charAt(i) == ')') {
                a--;
                if (a == 0) {
                    pos[1] = i;
                }
            }
        }
        return pos;
    }


    /**
     * returns a boolean value rather if the expression can be parsed using rule M
     * can prove by calling a helper function
     * @param str is the string version of the expression
     * @return returns true if the expression can be parsed
     */
    private boolean parseM(String str) {
        return (parseHelper(str, '*', this::parseM,this::parseM, this::parseX));

    }


    /**
     * an helper function that reduces the redundancy for rule A and M. Since both rules have similar codes,
     * it will be better to use the function
     * @param str is the string version of the expression
     * @param op is the operation, either + or *
     * @param a is the first possible grammar
     * @param b is the second possible grammar
     * @return true if the expression can be parsed using rule A or M.
     */
    private boolean parseHelper(String str, char op, Function<String, Boolean> a, Function<String, Boolean> b, Function<String,Boolean> c)
    {

            for (int i = 1; i < str.length() - 1; i++) {
                    String dummy = str.substring(0, i);
                    String dummy2 = str.substring(i + 1);

                if (str.charAt(i) == op && a.apply(dummy) && b.apply(dummy2) && helper(i, str)) {
                    String s = Character.toString(op);
                    ExpressionTree ex = new ExpressionTree(s);
                    //parse the expression before the operator
                    Expression one = parseExpression(dummy);
                    //parse the expression after the operator
                    Expression two = parseExpression(dummy2);
                    //add the parsed expressions as the children for the operator
                    if (one != null && two != null) {
                        ex.addSubexpression(one);
                        ex.addSubexpression(two);
                        expression = ex;
                        return true;
                    }
                }
            }
            return c.apply(str);

    }

    /**
     *
     * @param i position of the opperator
     * @param str string that needed to parse
     * @return true if the operator is in the right place--> if the is parenthesis, it should be outside of the parenthesis pair
     */
    boolean helper(int i, String str)
    {
        if (str.contains("(")&&str.contains(")")) {
            int[] pos = findparenthesis(str);

            return i < pos[0] || i > pos[1];
        }
        else
            return true;
    }


}
