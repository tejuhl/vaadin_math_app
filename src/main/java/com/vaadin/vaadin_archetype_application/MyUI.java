package com.vaadin.vaadin_archetype_application;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    
    protected void init(VaadinRequest vaadinRequest) {
    	final VerticalLayout mainLayout = new VerticalLayout();
    	mainLayout.setSizeFull();
    	setContent(mainLayout);
        final FormLayout form = new FormLayout();
        

        final TextField numberOfQuestions = new TextField("Number of questions");
        numberOfQuestions.setRequired(true);
        numberOfQuestions.addValidator(new IntegerRangeValidator("Input should be an Integer between 1 - 50.",1,50));
        
        Label plusMinusLabel = new Label("Which type of calculations you want?");
        final CheckBox plus = new CheckBox();
        plus.setIcon(FontAwesome.PLUS);
        final CheckBox minus = new CheckBox();
        minus.setIcon(FontAwesome.MINUS);
        

        
        Button submitButton = new Button("Submit");
        submitButton.addClickListener( e -> {
            
        });
        
        form.addComponents(numberOfQuestions, plusMinusLabel, plus, minus, submitButton);
        form.setMargin(true);
        form.setSpacing(true);
        form.setWidth("50%");
        
        
        mainLayout.addComponent(form);
        mainLayout.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
        
       
    }
    

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
