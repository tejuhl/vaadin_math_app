package com.vaadin.vaadin_archetype_application;

import com.vaadin.annotations.Theme;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

@Theme("mytheme")
public class BallVisualization extends VerticalLayout {

	int layers = 1;
	public BallVisualization(int number, String type) {
		if (number > 5) {
			layers += Math.floor(number / 5);
			if (number % 5 == 0) layers--;
		}
		int index  = 0;
		switch(type) {
			case "firstNum":
				for (int j = 0; j < layers; j++) {
					HorizontalLayout ballLayout = new HorizontalLayout();
					for (int i = 0; i < 5; i++) {
						Label ball = new Label();
						ball.setStyleName("ball");
						ballLayout.addComponent(ball);
						index++;
						if (index >= number) i = 8;
					}
					this.addComponent(ballLayout);
					ballLayout.setHeightUndefined();
					ballLayout.setStyleName("ballLayout");
					this.setHeightUndefined();
				}
				
				break;
			case "secondNum":
				for (int j = 0; j < layers; j++) {
					HorizontalLayout ballLayout = new HorizontalLayout();
					for (int i = 0; i < 5; i++) {
						Label square = new Label();
						square.setStyleName("square");
						ballLayout.addComponent(square);
						index++;
						if (index >= number) i = 8;
					}
					this.addComponent(ballLayout);
					ballLayout.setHeightUndefined();
					ballLayout.setStyleName("ballLayout");
					this.setHeightUndefined();
				}
				
				break;
		}
		;
		
		
	}
}
