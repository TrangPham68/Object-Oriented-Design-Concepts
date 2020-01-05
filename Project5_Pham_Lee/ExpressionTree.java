import java.util.ArrayList;
import java.util.List;

public class ExpressionTree implements CompoundExpression{

    String name;
    ExpressionTree parent;
    List <ExpressionTree> children = new ArrayList<>();
    public ExpressionTree(String root)
    {
        this.name = root;
        this.parent = null;
    }
    /**
     * Returns the expression's parent.
     * @return the expression's parent
     */
    public CompoundExpression getParent (){
        return parent;
    }

    /**
     * Sets the parent be the specified expression.
     * @param parent the CompoundExpression that should be the parent of the target object
     */
    public void setParent (CompoundExpression parent){
        this.parent = (ExpressionTree) parent;
    }

    /**
     * Creates and returns a deep copy of the expression.
     * The entire tree rooted at the target node is copied, i.e.,
     * the copied Expression is as deep as possible.
     * @return the deep copy
     */
    public ExpressionTree deepCopy (){
        ExpressionTree copy = new ExpressionTree(name);

        for(ExpressionTree x: children)
        {
            x = x.deepCopy();
            copy.children.add(x);
        }
        return copy;
    }

    /**
     * Recursively flattens the expression as much as possible
     * throughout the entire tree. Specifically, in every multiplicative
     * or additive expression x whose first or last
     * child c is of the same type as x, the children of c will be added to x, and
     * c itself will be removed. This method modifies the expression itself.
     */
    public void flatten (){
        List flatten = new ArrayList();
        //make a new list of children that get flattened
        for (int i = 0; i< children.size();i++)
        {
            ExpressionTree child = children.get(i);
            child.flatten();    //check flatten in the children node

            if (child.name.equals(name)) {
                for (int j = 0; j<child.children.size(); j++) {

                    ExpressionTree child2 =child.children.get(j);
                    child2.parent = this;
                    flatten.add(child2);
                }
                children.remove(child);
                i--;
            }
            else
            {
                flatten.add(child);
                //if child is not equal to its parent, just add it to flatten
            }


                }
        children = flatten;

    }

    /**
     * Creates a String representation by recursively printing out (using indentation) the
     * tree represented by this expression, starting at the specified indentation level.
     * @param stringBuilder the StringBuilder to use for building the String representation
     * @param indentLevel the indentation level (number of tabs from the left margin) at which to start
     */
    public void convertToString (StringBuilder stringBuilder, int indentLevel){

            for (int i = 0; i < indentLevel; i++) {
                stringBuilder.append("\t");
            }
            stringBuilder.append(name + "\n");

            for (ExpressionTree child : children) {
                child.convertToString(stringBuilder, indentLevel+1);
            }

    }

    /**
     * Adds the specified expression as a child.
     * @param subexpression the child expression to add
     */
    public void addSubexpression (Expression subexpression)
    {
        if(subexpression != null)
        {
            subexpression.setParent(this);
            children.add((ExpressionTree)subexpression);

        }
    }

}
