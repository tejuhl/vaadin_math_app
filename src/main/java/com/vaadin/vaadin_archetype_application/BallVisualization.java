package com.vaadin.vaadin_archetype_application;

import com.vaadin.annotations.Theme;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

@Theme("mytheme")
public class BallVisualization extends HorizontalLayout {

	public BallVisualization(int number) {
		this.setHeightUndefined();
		this.setWidthUndefined();
		for (int i = 0; i < number; i++) {
			Label ball = new Label();
			ball.setStyleName("ball");
			this.addComponent(ball);
		}
	}
}
