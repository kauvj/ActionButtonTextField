package org.vaadin.actionbuttontextfield.widgetset.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.DOM;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.client.ui.VTextField;
import com.vaadin.shared.ui.Connect;
import org.vaadin.actionbuttontextfield.ActionButtonTextField;

/**
 * This class adds a div to the right side of a text field. The div is styled based upon
 * the state (getState()) set for the field. This connector also sends back an event when
 * the div (styled to look like a button via styles.css) is clicked.
 */
@Connect(ActionButtonTextField.class)
public class ActionButtonTextFieldConnector extends
        AbstractExtensionConnector implements KeyUpHandler,
        AttachEvent.Handler, StateChangeEvent.StateChangeHandler {
    private static final long serialVersionUID = -737765038361894693L;
    public static final String CLASSNAME = "actionbuttontextfield";
    private VTextField textField;
    private Element actionButton;

    @Override
    protected void extend(ServerConnector target) {
        target.addStateChangeHandler(new StateChangeEvent.StateChangeHandler() {
            private static final long serialVersionUID = -8439729365677484553L;

            @Override
            public void onStateChanged(StateChangeEvent stateChangeEvent) {
                Scheduler.get().scheduleDeferred(new ScheduledCommand() {
                    @Override
                    public void execute() {
                        updateActionButtonVisibility();
                    }
                });
            }
        });
        textField = (VTextField) ((ComponentConnector) target).getWidget();
        textField.addStyleName(CLASSNAME + "-textfield");

        // create the div for the action button
        actionButton = DOM.createDiv();

        textField.addAttachHandler(this);
        textField.addKeyUpHandler(this);
    }

    public native void addActionButtonClickListener(Element el)
    /*-{
        var self = this;
        el.onclick = $entry(function () {
            self.@org.vaadin.actionbuttontextfield.widgetset.client.ActionButtonTextFieldConnector::clickActionButton()();
        });
    }-*/;

    public native void removeActionButtonClickListener(Element el)
    /*-{
        el.onclick = null;
    }-*/;

    @Override
    public void onAttachOrDetach(AttachEvent event) {
        if (event.isAttached()) {
            String actionButtonType = getState().type;
            if (ActionButtonType.ACTION_CLEAR.equals(actionButtonType)) {
                actionButton.addClassName(CLASSNAME + "-resetbutton");
            } else if (ActionButtonType.ACTION_UPLOAD.equals(actionButtonType)) {
                actionButton.addClassName(CLASSNAME + "-upload");
            } else if (ActionButtonType.ACTION_SEARCH.equals(actionButtonType)) {
                actionButton.addClassName(CLASSNAME + "-search");
            }
            textField.getElement().getParentElement()
                    .insertAfter(actionButton, textField.getElement());
            updateActionButtonVisibility();
            addActionButtonClickListener(actionButton);
        } else {
            Element parentElement = actionButton.getParentElement();
            if (parentElement != null) {
                parentElement.removeChild(actionButton);
            }
            removeActionButtonClickListener(actionButton);
        }
    }

    @Override
    public void onKeyUp(KeyUpEvent event) {
        updateActionButtonVisibility();
    }

    @Override
    public ActionButtonTextFieldState getState() {
        return (ActionButtonTextFieldState) super.getState();
    }

    private void updateActionButtonVisibility() {
        if (ActionButtonType.ACTION_CLEAR.equals(getState().type)) {
            // remove the button when the text field is empty
            if (textField.getValue().isEmpty()) {
                actionButton.getStyle().setDisplay(Display.NONE);
            } else {
                actionButton.getStyle().clearDisplay();
            }
        }
    }

    private void clickActionButton() {
        if (ActionButtonType.ACTION_CLEAR.equals(getState().type)) {
            textField.setValue("");
            textField.valueChange(true);
        }
        updateActionButtonVisibility();
        textField.getElement().focus();
        getRpcProxy(ActionButtonTextFieldRpc.class).go();
    }
}
