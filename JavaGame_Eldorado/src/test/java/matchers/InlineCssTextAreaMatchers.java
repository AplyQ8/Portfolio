package matchers;

import org.fxmisc.richtext.InlineCssTextArea;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class InlineCssTextAreaMatchers {
    public static TypeSafeMatcher<InlineCssTextArea> hasText(String expectedText) {
        return new TypeSafeMatcher<InlineCssTextArea>() {

            @Override
            protected boolean matchesSafely(InlineCssTextArea textArea) {
                String as = textArea.getText();
                return as.contains(expectedText);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("InlineCssTextArea has text ").appendValue(expectedText);
            }

            @Override
            protected void describeMismatchSafely(InlineCssTextArea textArea, Description mismatchDescription) {
                mismatchDescription.appendText("was ").appendValue(textArea);
            }
        };
    }
}
