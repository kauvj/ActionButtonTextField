package org.vaadin.actionbuttontextfield.widgetset.client;

import com.vaadin.shared.communication.SharedState;

/**
 * This state is used to pass the type of action button across to the connector
 */
public class ActionButtonTextFieldState extends SharedState {
    public String type = ActionButtonType.ACTION_CLEAR;
}

