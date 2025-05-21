package matchers;

import javafx.scene.text.Text;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class TextMatcher {
    public static TypeSafeMatcher<Text> hasText(String expectedText) {
        return new TypeSafeMatcher<Text>() {

            @Override
            protected boolean matchesSafely(Text textArea) {
                String as = textArea.getText();
                return as.contains(expectedText);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("InlineCssTextArea has text ").appendValue(expectedText);
            }

            @Override
            protected void describeMismatchSafely(Text textArea, Description mismatchDescription) {
                mismatchDescription.appendText("was ").appendValue(textArea);
            }
        };
    }
}
