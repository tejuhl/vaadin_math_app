package com.vaadin.vaadin_archetype_application;

import com.vaadin.annotations.Theme;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme")
public class BallContainer extends HorizontalLayout {
    HorizontalLayout answerLine = new HorizontalLayout();   
    VerticalLayout numLayout = new VerticalLayout();
    Label numLabel = new Label();
    
    public BallContainer(int number, String type, boolean visualizeBool) {
        numLabel.setCaption(Integer.toString(number));
        numLabel.setStyleName("numLabel");
        this.setSpacing(true);
        this.setStyleName("challengeLine");
        this.setWidthUndefined();
        this.setHeightUndefined();
        
        BallVisualization ballVis = new BallVisualization(number, type);
        ballVis.setHeightUndefined();
        ballVis.setWidth("60px");
        numLayout.setStyleName("numLayout");
        numLayout.setHeightUndefined();
        numLayout.setSpacing(true);
        if (!visualizeBool) {
            numLayout.addComponents(new VerticalLayout() ,numLabel);
        } else {
            numLayout.addComponents(ballVis, numLabel);
        }
        
        this.addComponent(numLayout);

    }
}
