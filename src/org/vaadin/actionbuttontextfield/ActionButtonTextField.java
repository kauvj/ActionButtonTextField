package org.vaadin.actionbuttontextfield;

import com.vaadin.server.AbstractClientConnector;
import com.vaadin.server.AbstractExtension;
import com.vaadin.ui.TextField;
import com.vaadin.util.ReflectTools;
import org.vaadin.actionbuttontextfield.widgetset.client.ActionButtonTextFieldRpc;

import java.lang.reflect.Method;
import java.util.EventObject;

public class ActionButtonTextField extends AbstractExtension {

    public static ActionButtonTextField extend(TextField field) {
        ActionButtonTextField me = new ActionButtonTextField();
                me.extend((AbstractClientConnector) field);
        return me;
    }

    public interface ClickedActionButtonEventListener {
        static Method METHOD = ReflectTools.findMethod(ClickedActionButtonEventListener.class,
                "go", ClickedActionButtonEvent.class);

        public void go(ClickedActionButtonEvent clickedActionButtonEvent);
    }

    public class ClickedActionButtonEvent extends EventObject {

        public ClickedActionButtonEvent(ActionButtonTextField actionButtonTextField) {
            super(actionButtonTextField);
        }

        public ActionButtonTextField getTextField() {
            return (ActionButtonTextField) getSource();
        }

    }

    public ActionButtonTextField() {
        registerRpc(new ActionButtonTextFieldRpc() {
            @Override
            public void go() {
                fireEvent(new ClickedActionButtonEvent(ActionButtonTextField.this));
            }
        });
    }

    public void addExtraButtonClickListener(ClickedActionButtonEventListener listener) {
        super.addListener(ClickedActionButtonEvent.class, listener, ClickedActionButtonEventListener.METHOD);
    }

    public void removeRefreshListener(ClickedActionButtonEventListener listener) {
        super.removeListener(ClickedActionButtonEvent.class, listener,
                ClickedActionButtonEventListener.METHOD);
    }

}
