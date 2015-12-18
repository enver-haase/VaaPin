package com.infraleap.vaapin.util;

import com.vaadin.event.FieldEvents;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.AbstractTextField;

// http://ramontalaverasuarez.blogspot.de/2014/06/vaadin-7-detect-enter-key-in-textfield.html
public abstract class OnEnterKeyHandler {

    @SuppressWarnings("serial")
    final ShortcutListener enterShortCut = new ShortcutListener("EnterOnTextAreaShorcut", ShortcutAction.KeyCode.ENTER, null) {
        @Override
        public void handleAction(Object sender, Object target) {
            onEnterKeyPressed();
        }
    };

    @SuppressWarnings("serial")
    public void installOn(final AbstractTextField component) {
        component.addFocusListener(new FieldEvents.FocusListener() {

            @Override
            public void focus(FieldEvents.FocusEvent event) {
                component.addShortcutListener(enterShortCut);
            }

        });

        component.addBlurListener(new FieldEvents.BlurListener() {

            @Override
            public void blur(FieldEvents.BlurEvent event) {
                component.removeShortcutListener(enterShortCut);
            }

        });
    }

    public abstract void onEnterKeyPressed();

}
