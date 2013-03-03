Vaadin 7 add-on **ActionButtonTextField** version 1.0.0. It adds a reset button, a search button or an upload
button to the TextField component allowing user to perform actions within the text field.

The buttons are drawn using SVG images in supported browsers. With IE8 PNG versions of those are used.

Usage: add the JAR to your project, compile widgetset and then extend any TextField:

    TextField tf = new TextField();
    ActionButtonTextField.extend(tf);

Events also come back to the server side so that you can hook up your own actions after the button
has been clicked. Note, the reset button does clear the text for you without doing so yourself on the server
side. The search and upload buttons do nothing until you add a click listener on the server side.

    final TextField searchField = new TextField("Search Action");
    ActionButtonTextField searchButtonTextField = ActionButtonTextField.extend(searchField);
    searchButtonTextField.getState().type = ActionButtonType.ACTION_SEARCH;
    searchButtonTextField.addClickListener(new ActionButtonTextField.ClickListener() {
        @Override
        public void buttonClick(ActionButtonTextField.ClickEvent clickEvent) {
            Notification.show("Search Action Button Clicked...");
        }
    });

