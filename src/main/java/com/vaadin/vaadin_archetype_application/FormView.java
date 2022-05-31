package com.vaadin.vaadin_archetype_application;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

public class FormView extends MyUI{
	public FormView() {
		   VerticalLayout mainLayout = new VerticalLayout();
		   mainLayout.setSizeFull();
		   
		    FormLayout form = new FormLayout();
	        PropertysetItem formData = new PropertysetItem();
	        formData.addItemProperty("questions", new ObjectProperty<Integer>(1));
	        formData.addItemProperty("plus", new ObjectProperty<Boolean>(false));
	        formData.addItemProperty("minus", new ObjectProperty<Boolean>(false));
	        

	        final TextField numberOfQuestions = new TextField("Number of questions");
	        numberOfQuestions.setRequired(true);
	        numberOfQuestions.addValidator(new IntegerRangeValidator("Input should be an Integer between 1 - 50.",1,50));
	        
	        Label plusMinusLabel = new Label("Which type of calculations you want?");
	        final CheckBox plus = new CheckBox();
	        plus.setIcon(FontAwesome.PLUS);
	        final CheckBox minus = new CheckBox();
	        minus.setIcon(FontAwesome.MINUS);
	        
	        
	        FieldGroup binder = new FieldGroup(formData);
	        binder.bind(numberOfQuestions, "questions");
	        binder.bind(plus, "plus");
	        binder.bind(minus, "minus");

	        
	        Button submitButton = new Button("Submit");
	        submitButton.addClickListener( event -> {
	        	try {
	        		binder.commit();
	        		//questions = binder.getField("questions").getValue().toString();
	        		//plusValue = binder.getField("plus").getValue().toString();
	        		//minusValue = binder.getField("minus").getValue().toString();
	        		
	        		
	        		
	        		
				} catch (CommitException e) {
					System.out.println(e);
					Notification.show("Something went wrong");
				}
	            
	        });
	        
	        form.addComponents(numberOfQuestions, plusMinusLabel, plus, minus, submitButton);
	        form.setMargin(true);
	        form.setSpacing(true);
	        form.setWidth("50%");
	        mainLayout.addComponent(form);
	        mainLayout.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
	       
	   }
}
