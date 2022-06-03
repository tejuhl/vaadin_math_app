package com.vaadin.vaadin_archetype_application;

import java.util.ArrayList;

import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;


@Theme("mytheme")
public class OwnChallenge extends FormLayout {
    
    ArrayList<String> ownChallengeList = new ArrayList<String>();
    VerticalLayout challengeListLayout = new VerticalLayout();

    public OwnChallenge () {
        HorizontalLayout addChallengeLayout = new HorizontalLayout();
        TextField newChallengeText = new TextField("Add a new challenge");
        Button addButton = new Button("Add");
        addButton.setStyleName("check");
        addButton.setClickShortcut(KeyCode.ENTER);
        addButton.addClickListener(event -> {
            String ch = newChallengeText.getValue();
            addNewChallenge(ch);
<<<<<<< HEAD
            newChallengeText.setValue("");
=======
>>>>>>> e93f3a05429f3cb6d72cceab1946da1e611fa077
        });

        addChallengeLayout.addComponents(newChallengeText, addButton);
        addChallengeLayout.setSpacing(true);
        addChallengeLayout.setComponentAlignment(addButton, Alignment.BOTTOM_LEFT);

        HorizontalLayout formButtonBox = new HorizontalLayout();
	        
	    Button submitButton = new Button("Submit");
	    submitButton.addStyleName("submitButton");
        formButtonBox.addComponent(submitButton);
	    formButtonBox.addStyleName("formButtonBox");

        formButtonBox.setWidth("100%");
	    formButtonBox.setComponentAlignment(submitButton, Alignment.MIDDLE_CENTER);

        challengeListLayout.setStyleName("challengeListLayout");

        addComponents(addChallengeLayout, challengeListLayout, formButtonBox);
        setComponentAlignment(challengeListLayout, Alignment.MIDDLE_CENTER);
        setSpacing(true);
    }


    private HorizontalLayout ownChallengeLine (String challenge, int index) {
        HorizontalLayout challengeLine = new HorizontalLayout();
        Label challengeLabel = new Label(challenge);
        Button deleteButton = new Button();
        deleteButton.setIcon(FontAwesome.TRASH);
<<<<<<< HEAD
        deleteButton.setStyleName("previousButton");
=======
>>>>>>> e93f3a05429f3cb6d72cceab1946da1e611fa077

        challengeLine.setSpacing(true);
        challengeLine.addComponents(challengeLabel, deleteButton);
        deleteButton.addClickListener(e -> {
            ownChallengeList.remove(index);
            updateChallengeList();
        });
<<<<<<< HEAD
        challengeLine.setSpacing(true);
        challengeLine.setComponentAlignment(deleteButton, Alignment.MIDDLE_LEFT);
        challengeLine.setComponentAlignment(challengeLabel, Alignment.MIDDLE_LEFT);
        challengeLine.setHeight("50px");
        challengeLine.setWidth("100px");
=======
>>>>>>> e93f3a05429f3cb6d72cceab1946da1e611fa077


        return challengeLine;
    }

    private void addNewChallenge(String challengeText) {
        ownChallengeList.add(challengeText);
        updateChallengeList();
    }


    private void updateChallengeList() {
        challengeListLayout.removeAllComponents();
        for (int i = 0; i < ownChallengeList.size(); i++) {
            HorizontalLayout challengeLine = ownChallengeLine(ownChallengeList.get(i), i);

            challengeListLayout.addComponent(challengeLine);
            challengeListLayout.setComponentAlignment(challengeLine, Alignment.TOP_CENTER);

        }
    }
}
