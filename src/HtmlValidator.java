import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Add your own comments
 */
public class HtmlValidator {
    //IMPLEMENT YOUR CODE HERE
    private static final String INDENTATION_MARKER = "    ";

    private Queue<HtmlTag> tags;

    public HtmlValidator(Queue<HtmlTag> tags) {
        if(this.tags == null) {
            throw new IllegalArgumentException("Initial tags cannot be null.");
        }
        this.tags = new LinkedList<>(this.tags);
    }

    public void addTag(HtmlTag tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Cannot add a null tag");
        }
        tags.add(tag);
    }

    public Queue<HtmlTag> getTags() {
        return new LinkedList<>(tags);
    }

    public void removeAll(String element) {
        if (element == null) throw new IllegalArgumentException();
        tags.removeIf(tag -> tag.getElement().equalsIgnoreCase(element));
    }

    public void validate() {
        Stack<HtmlTag> openTags = new Stack<>();
        for(int i = 0; i < tags.size(); i++) {
            HtmlTag tag = tags.remove();
            tags.add(tag);

            if (tag.isSelfClosing()) {
                printWithIndentation(tag, openTags.size());
            } else if (tag.isOpenTag()) {
                printWithIndentation(tag, openTags.size());
                openTags.push(tag);
            } else if (!openTags.isEmpty() && tag.matches(openTags.peek())) {
                openTags.pop();
                printWithIndentation(tag, openTags.size());
            } else {
                System.out.println("ERROR unexpected tag: " + tag.toString());
            }
        }
        while (!openTags.isEmpty()) {
            HtmlTag tag = openTags.pop();
            System.out.println("ERROR unclosed tag: " + tag.toString());
        }
    }
    private static void printWithIndentation(HtmlTag tag, int indentationLevel) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indentationLevel; i++) {
            sb.append(INDENTATION_MARKER);
        }
        sb.append(tag.toString());
        System.out.println(sb.toString());
    }
}
