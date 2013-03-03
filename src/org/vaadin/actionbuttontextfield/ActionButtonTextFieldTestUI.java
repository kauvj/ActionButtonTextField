package org.vaadin.actionbuttontextfield;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import org.vaadin.actionbuttontextfield.widgetset.client.ActionButtonType;

import java.util.Date;

@SuppressWarnings("serial")
public class ActionButtonTextFieldTestUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        final FormLayout layout = new FormLayout();
        layout.setMargin(true);
        setContent(layout);

        final TextField tf = new TextField("Clear Action");
        ActionButtonTextField clearButtonTextField = ActionButtonTextField.extend(tf);
        clearButtonTextField.getState().type = ActionButtonType.ACTION_CLEAR;
        clearButtonTextField.addClickListener(new ActionButtonTextField.ClickListener() {
            @Override
            public void buttonClick(ActionButtonTextField.ClickEvent clickEvent) {
                Notification.show("Clear Action Button Clicked...");
            }
        });
        layout.addComponent(tf);
        tf.setImmediate(true);
        tf.setWidth("300px");

        final TextField searchField = new TextField("Search Action");
        ActionButtonTextField searchButtonTextField = ActionButtonTextField.extend(searchField);
        searchButtonTextField.getState().type = ActionButtonType.ACTION_SEARCH;
        searchButtonTextField.addClickListener(new ActionButtonTextField.ClickListener() {
            @Override
            public void buttonClick(ActionButtonTextField.ClickEvent clickEvent) {
                Notification.show("Search Action Button Clicked...");
            }
        });
        layout.addComponent(searchField);
        searchField.setImmediate(true);
        searchField.setWidth("300px");

        final TextField uploadField = new TextField("Upload Action");
        ActionButtonTextField uploadButtonTextField = ActionButtonTextField.extend(uploadField);
        uploadButtonTextField.getState().type = ActionButtonType.ACTION_UPLOAD;
        uploadButtonTextField.addClickListener(new ActionButtonTextField.ClickListener() {
            @Override
            public void buttonClick(ActionButtonTextField.ClickEvent clickEvent) {
                Notification.show("Upload Action Button Clicked...");
            }
        });
        layout.addComponent(uploadField);
        uploadField.setImmediate(true);
        uploadField.setWidth("300px");

        final Label textChangeEventListenerLabel = new Label();
        textChangeEventListenerLabel.setCaption("I show textChangeEvents!");
        layout.addComponent(textChangeEventListenerLabel);

        final Label valueChangeListenerLabel = new Label();
        valueChangeListenerLabel.setCaption("I show valueChangeEvents!");
        layout.addComponent(valueChangeListenerLabel);

        tf.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                valueChangeListenerLabel.setValue(event.getProperty()
                        .getValue().toString());
            }
        });

        tf.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(TextChangeEvent event) {
                textChangeEventListenerLabel.setValue(event.getText());
            }
        });

        Button b1 = new Button("Clear the value of the textfield",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        tf.setValue("");
                    }
                });
        Button b2 = new Button(
                "Set the value of the textfield to current date",
                new Button.ClickListener() {

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        tf.setValue(new Date().toString());
                    }
                });

        layout.addComponent(b1);
        layout.addComponent(b2);
    }

}