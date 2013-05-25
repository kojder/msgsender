package eu.kojder.msgsender.display.main;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * User: Andrzej
 * Date: 25.05.13
 * Time: 13:43
 */
public class StartPage extends UI {
    @Override
    protected void init(VaadinRequest request) {
// Set the window or tab title
        getPage().setTitle("Messages Sender");
// Create the content root layout for the UI
        VerticalLayout content = new VerticalLayout();
        setContent(content);
// Display the greeting
        content.addComponent(new Label("Here will be Messages Sender App!"));
        final Button button = new Button("Click me");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                button.setCaption("Already clicked");
            }
        });
        content.addComponent(button);
    }
}
