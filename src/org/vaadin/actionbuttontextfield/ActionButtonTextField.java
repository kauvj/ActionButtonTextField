package org.vaadin.actionbuttontextfield;

import com.vaadin.server.AbstractClientConnector;
import com.vaadin.server.AbstractExtension;
import com.vaadin.server.ClientConnector;
import com.vaadin.ui.TextField;
import com.vaadin.util.ReflectTools;
import org.vaadin.actionbuttontextfield.widgetset.client.ActionButtonTextFieldRpc;
import org.vaadin.actionbuttontextfield.widgetset.client.ActionButtonTextFieldState;

import java.lang.reflect.Method;
import java.util.EventObject;

public class ActionButtonTextField extends AbstractExtension {

    public static ActionButtonTextField extend(TextField field) {
        ActionButtonTextField me = new ActionButtonTextField();
                me.extend((AbstractClientConnector) field);
        return me;
    }

    public void setActionButtonType(String type) {
        getState().type = type;
    }

    @Override
    public ActionButtonTextFieldState getState() {
        return (ActionButtonTextFieldState) super.getState();
    }

    protected Class<? extends ClientConnector> getSupportedParentType() {
        return TextField.class;
    }

    public interface ClickListener {
        static Method METHOD = ReflectTools.findMethod(ClickListener.class,
                "buttonClick", ClickEvent.class);

        public void buttonClick(ClickEvent clickEvent);
    }

    public class ClickEvent extends EventObject {

        public ClickEvent(ActionButtonTextField actionButtonTextField) {
            super(actionButtonTextField);
        }

        public TextField getTextField() {
            return (TextField) getParent();
        }

    }

    public ActionButtonTextField() {
        registerRpc(new ActionButtonTextFieldRpc() {
            @Override
            public void go() {
                fireEvent(new ClickEvent(ActionButtonTextField.this));
            }
        });
    }

    public void addClickListener(ClickListener listener) {
        super.addListener(ClickEvent.class, listener, ClickListener.METHOD);
    }

    public void removeClickListener(ClickListener listener) {
        super.removeListener(ClickEvent.class, listener,
                ClickListener.METHOD);
    }

}
