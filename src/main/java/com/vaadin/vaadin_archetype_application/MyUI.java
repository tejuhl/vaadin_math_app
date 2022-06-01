package com.vaadin.vaadin_archetype_application;

import java.util.ArrayList;
import java.util.Random;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
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
	
	private String questions;
	private String plusValue;
	private String minusValue;
    private String plusRange;
    private String minusRange;
    private ArrayList<String> challengeList = new ArrayList<>();
    private ArrayList<String> answerList = new ArrayList<>();
    private PropertysetItem answers = new PropertysetItem();
    private int points = 0;
    private Label pointLabel = new Label();

    private Random random = new Random();
    
	
	

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	VerticalLayout mainLayout = new VerticalLayout();
		   mainLayout.setSizeFull();
		   mainLayout.addStyleName(".mainLayout");
           
		    FormLayout form = new FormLayout();
	        PropertysetItem formData = new PropertysetItem();
	        formData.addItemProperty("questions", new ObjectProperty<Integer>(1));
	        formData.addItemProperty("plus", new ObjectProperty<Boolean>(false));
	        formData.addItemProperty("minus", new ObjectProperty<Boolean>(false));
            formData.addItemProperty("plusRange", new ObjectProperty<String>("0-50"));
            formData.addItemProperty("minusRange", new ObjectProperty<String>("0-50"));
	        

	        final TextField numberOfQuestions = new TextField("Number of questions");
	        numberOfQuestions.setRequired(true);
	        numberOfQuestions.addValidator(new IntegerRangeValidator("Input should be an Integer between 1 - 50.",1,50));
	        
	        Label plusMinusLabel = new Label("Which type of calculations you want?");
            HorizontalLayout plusLayout = new HorizontalLayout();

	        final CheckBox plus = new CheckBox();
	        plus.setIcon(FontAwesome.PLUS);
            Label plusRangeLabel = new Label("Range of numbers: ");
           
            TextField plusRangeOfNumbers = new TextField();

            plusRangeOfNumbers.setWidth("100px");
            plusLayout.setSpacing(true);
            plusLayout.setMargin(true);

            plusLayout.addComponents(plus, plusRangeLabel, plusRangeOfNumbers);

            HorizontalLayout minusLayout = new HorizontalLayout();
	        final CheckBox minus = new CheckBox();
	        minus.setIcon(FontAwesome.MINUS);
            Label minusRangeLabel = new Label("Range of numbers: ");
           
            TextField minusRangeOfNumbers = new TextField();

            minusRangeOfNumbers.setWidth("100px");
            minusLayout.setSpacing(true);
            minusLayout.setMargin(true);
            minusLayout.addComponents(minus, minusRangeLabel, minusRangeOfNumbers);
	        
	        
	        FieldGroup binder = new FieldGroup(formData);
	        binder.bind(numberOfQuestions, "questions");
	        binder.bind(plus, "plus");
	        binder.bind(minus, "minus");
            binder.bind(plusRangeOfNumbers, "plusRange");
            binder.bind(minusRangeOfNumbers, "minusRange");


            
	        Panel panel = new Panel("Challenge Panel");
            panel.setWidthUndefined();
            VerticalLayout challengeLayout = new VerticalLayout();
            panel.setVisible(false);
            panel.setContent(challengeLayout);
            

            
	        
	        Button submitButton = new Button("Submit");
	        submitButton.addClickListener( event -> {
	        	try {
	        		binder.commit();
	        		questions = binder.getField("questions").getValue().toString();
	        		plusValue = binder.getField("plus").getValue().toString();
	        		minusValue = binder.getField("minus").getValue().toString();
	        		plusRange = binder.getField("plusRange").getValue().toString();
                    minusRange = binder.getField("minusRange").getValue().toString();

                    
	        		if (!Boolean.parseBoolean(plusValue) && !Boolean.parseBoolean(minusValue)) {
                        Notification.show("At least one of checkboxes must be selected.");
                    } else {
                        panel.setVisible(true);
                        generateChallenges(challengeLayout);
                    }
	        		
	        		
	        		
				} catch (CommitException e) {
					System.out.println(e);
					Notification.show("Something went wrong");
				}
	            
	        });
	        
	        form.addComponents(numberOfQuestions, plusMinusLabel, plusLayout, minusLayout, submitButton);
	        form.setMargin(true);
	        form.setSpacing(true);
	        form.setWidthUndefined();
	        mainLayout.addComponent(form);    
            mainLayout.addComponent(panel);
            mainLayout.setComponentAlignment(panel, Alignment.TOP_CENTER);
	        
	        setContent(mainLayout);
        
    }
  

    private void generateChallenges(VerticalLayout challengeLayout) {
        ArrayList<Label> challengeLinesLabels = new ArrayList<Label>();
        for (int i=0;i<Integer.parseInt(questions);i++) {
            HorizontalLayout challengeLine = new HorizontalLayout();
            Label challengeLabel = new Label();
            String challenge = createChallenge(); 
            TextField answerBox = new TextField();
            Label answerCorrect = new Label();

            answerCorrect.setVisible(false);
            
            answerBox.setWidth("100px");
            challengeLabel.setCaption(challenge);
            challengeLine.addComponents(challengeLabel, answerBox, answerCorrect);
            challengeLine.setSpacing(true);
            challengeLayout.addComponent(challengeLine);
            answers.addItemProperty("answer" + i, answerBox);
            challengeLinesLabels.add(answerCorrect);

            challengeList.add(challenge);
        }
        Button checkButton = new Button("Check Answers");
        pointLabel.setVisible(false);
        challengeLayout.addComponent(pointLabel);
        challengeLayout.addComponent(checkButton);
        challengeLayout.setComponentAlignment(checkButton, Alignment.BOTTOM_RIGHT);

        challengeLayout.setMargin(true);

        checkButton.addClickListener(event -> {
            try {
                saveAnswers();
                checkAnswers(challengeLinesLabels);
            } catch (Exception e) {
                System.out.println(e);
                Notification.show("Something went wrong");
            }
        });

    }

    private void checkAnswers(ArrayList<Label> challengeLinesLabels) {
        
        for (int i = 0; i < Integer.parseInt(questions); i++) {
            
            String[] challengeParts = challengeList.get(i).split(" ");
            int rightAnswer = 0;
            
            switch (challengeParts[1]) {
                case "+":
                    rightAnswer = Integer.parseInt(challengeParts[0]) + Integer.parseInt(challengeParts[2]);
                    break;
                case "-":
                    rightAnswer = Integer.parseInt(challengeParts[0]) - Integer.parseInt(challengeParts[2]);
                    break;     
            }
            challengeLinesLabels.get(i).setVisible(true);
            if (answerList.get(i).equals(Integer.toString(rightAnswer))) {
                
                challengeLinesLabels.get(i).setIcon(FontAwesome.CHECK);
                points += 1;
            } else {
                challengeLinesLabels.get(i).setIcon(FontAwesome.AMBULANCE);
                challengeLinesLabels.get(i).setCaption("RightAnswer: " + Integer.toString(rightAnswer));
            }
            
        }
        pointLabel.setVisible(true);
        pointLabel.setCaption(Integer.toString(points) + " / " + questions);
    }


    private void saveAnswers() {
        for (int i = 0; i < Integer.parseInt(questions); i++) {
            answerList.add(answers.getItemProperty("answer" + i).getValue().toString());
        }
    }


    private String createChallenge() {
        
        
        
        
        String[] options = {"+", "-"};
        String operator;
        Integer[] numbers;
        if (Boolean.parseBoolean(plusValue) && Boolean.parseBoolean(minusValue)) {
            operator = options[random.nextInt(options.length)];
            numbers = generateNumbers(operator);
            return numbers[0] + " " + operator + " " + numbers[1];
        } else if (Boolean.parseBoolean(minusValue)) {
            operator = "-";
            numbers = generateNumbers(operator);
            return numbers[0] + " - " + numbers[1];
        } else if (Boolean.parseBoolean(plusValue)) {
            operator = "+";
            numbers = generateNumbers(operator);
            return numbers[0] + " + " + numbers[1];
        }
        return "";

    }

    private Integer[] generateNumbers(String operator) {
        String[] rangeValues;
        int firstNum;
        int secondNum;
        Integer[] numbers;
        switch (operator) {
            case "+":
                rangeValues = plusRange.split("-");
                firstNum = random.nextInt(Integer.parseInt(rangeValues[1]) - Integer.parseInt(rangeValues[0]) + 1) + Integer.parseInt(rangeValues[0]);
                secondNum = random.nextInt(Integer.parseInt(rangeValues[1]) - Integer.parseInt(rangeValues[0]) + 1) + Integer.parseInt(rangeValues[0]);
                numbers = new Integer[]{firstNum, secondNum};
                return numbers;
                
            case "-":
                rangeValues = minusRange.split("-");
                firstNum = random.nextInt(Integer.parseInt(rangeValues[1]) - Integer.parseInt(rangeValues[0]) + 1) + Integer.parseInt(rangeValues[0]);
                secondNum = random.nextInt(Integer.parseInt(rangeValues[1]) - Integer.parseInt(rangeValues[0]) + 1) + Integer.parseInt(rangeValues[0]);
                numbers = new Integer[]{firstNum, secondNum};
                return numbers;
                
        }
        numbers = new Integer[]{0,0};
        return numbers;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}

	
