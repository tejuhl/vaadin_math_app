package com.vaadin.vaadin_archetype_application;

import com.vaadin.annotations.Theme;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme")
public class BallTest extends HorizontalLayout {
    HorizontalLayout answerLine = new HorizontalLayout();   
    VerticalLayout numLayout = new VerticalLayout();
    Label numLabel = new Label();
    
    public BallTest(int number) {
        numLabel.setCaption(Integer.toString(number));
        numLabel.setStyleName("numLabel");
        this.setSpacing(true);
        this.setStyleName("challengeLine");
        this.setWidthUndefined();
        this.setHeightUndefined();
    
        numLayout.setStyleName("numLayout");
        numLayout.addComponents(new BallVisualization(number), numLabel);
        this.addComponent(numLayout);

    }
}
