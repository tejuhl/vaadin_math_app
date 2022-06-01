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
import com.vaadin.ui.Notification.Type;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
	
	private int questions;
	private String plusValue;
	private String minusValue;
    private int plusRangeLowestValue;
    private int plusRangeLargestValue;
    private int minusRangeLowestValue;
    private int minusRangeLargestValue;
    private ArrayList<String> challengeList = new ArrayList<>();
    private ArrayList<VerticalLayout> challengeLayouts = new ArrayList<>();
    private ArrayList<HorizontalLayout> challengeLines = new ArrayList<>();
    private ArrayList<String> answerList = new ArrayList<>();
    private PropertysetItem answers = new PropertysetItem();
    private int points = 0;
    private Label pointLabel = new Label();
    Panel panel = new Panel("Challenge Panel");

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
            formData.addItemProperty("plusRangeLowest", new ObjectProperty<String>("0"));
            formData.addItemProperty("plusRangeLargest", new ObjectProperty<String>("10"));
            formData.addItemProperty("minusRangeLowest", new ObjectProperty<String>("0"));
            formData.addItemProperty("minusRangeLargest", new ObjectProperty<String>("10"));
	        

	        final TextField numberOfQuestions = new TextField("Number of questions");
	        numberOfQuestions.setRequired(true);
	        numberOfQuestions.addValidator(new IntegerRangeValidator("Input should be an Integer between 1 - 50.",1,50));
	        
	        Label plusMinusLabel = new Label("Which type of calculations you want?");
            HorizontalLayout plusLayout = new HorizontalLayout();

	        final CheckBox plus = new CheckBox();
	        plus.setIcon(FontAwesome.PLUS);
            Label plusRangeLabel = new Label("Range of numbers: ");
            HorizontalLayout plusRangeLayout = new HorizontalLayout();
           
            TextField plusRangeLowest = new TextField();
            TextField plusRangeLargest = new TextField();

            plusRangeLowest.setWidth("60px");
            plusRangeLargest.setWidth("60px");
            plusLayout.setSpacing(true);
            plusLayout.setMargin(true);

            plusRangeLayout.addComponents(plusRangeLowest, new Label("-"), plusRangeLargest);
            plusRangeLayout.setSpacing(true);
            plusRangeLayout.setMargin(true);

            plusLayout.addComponents(plus, plusRangeLabel, plusRangeLayout);

            HorizontalLayout minusLayout = new HorizontalLayout();
	        final CheckBox minus = new CheckBox();
	        minus.setIcon(FontAwesome.MINUS);
            Label minusRangeLabel = new Label("Range of numbers: ");

            HorizontalLayout minusRangeLayout = new HorizontalLayout();

            TextField minusRangeLowest = new TextField();
            TextField minusRangeLargest = new TextField();

            minusRangeLowest.setWidth("60px");
            minusRangeLargest.setWidth("60px");
           
            minusRangeLayout.addComponents(minusRangeLowest, new Label("-"), minusRangeLargest);

            minusRangeLayout.setSpacing(true);
            minusRangeLayout.setMargin(true);

            minusLayout.setSpacing(true);
            minusLayout.setMargin(true);
            minusLayout.addComponents(minus, minusRangeLabel, minusRangeLayout);
	        
	        
	        FieldGroup binder = new FieldGroup(formData);
	        binder.bind(numberOfQuestions, "questions");
	        binder.bind(plus, "plus");
	        binder.bind(minus, "minus");
            binder.bind(plusRangeLowest, "plusRangeLowest");
            binder.bind(plusRangeLargest, "plusRangeLargest");
            binder.bind(minusRangeLowest, "minusRangeLowest");
            binder.bind(minusRangeLargest, "minusRangeLargest");


            
	        
            panel.setWidthUndefined();
            VerticalLayout challengeLayout = new VerticalLayout();
            panel.setVisible(false);
            panel.setContent(challengeLayout);
            panel.setWidth("500px");
            
            

            
	        
	        Button submitButton = new Button("Submit");
	        submitButton.addClickListener( event -> {
	        	try {
	        		binder.commit();
                    plusValue = binder.getField("plus").getValue().toString();
	        		minusValue = binder.getField("minus").getValue().toString();

	        		questions = Integer.parseInt(binder.getField("questions").getValue().toString());
	        		plusRangeLowestValue = Integer.parseInt(binder.getField("plusRangeLowest").getValue().toString());
                    plusRangeLargestValue = Integer.parseInt(binder.getField("plusRangeLargest").getValue().toString());
                    minusRangeLowestValue = Integer.parseInt(binder.getField("minusRangeLowest").getValue().toString());
                    minusRangeLargestValue = Integer.parseInt(binder.getField("minusRangeLargest").getValue().toString());

                    
	        		if (!Boolean.parseBoolean(plusValue) && !Boolean.parseBoolean(minusValue)) {
                        Notification.show("At least one of checkboxes must be selected.");
                    } else {
                        panel.setVisible(true);
                        generateChallenges(challengeLayout);
                        generateOneChallengeLayouts();
                    }
	        		
	        		
	        		
				} catch (CommitException | NumberFormatException e) {
					System.out.println(e);
					Notification.show("Something went wrong! " + e.getMessage(), Type.WARNING_MESSAGE);
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

    private void generateOneChallengeLayouts() {
        for (int i = 0; i < questions; i++) {
            VerticalLayout oneChallengeLayout = new VerticalLayout();
            oneChallengeLayout.addComponent(challengeLines.get(i));
            HorizontalLayout buttonsLayout = new HorizontalLayout();
            Button previousButton = new Button("Previous");
            Button nextButton = new Button("Next");
            nextButton.setWidth("100px");
            previousButton.setWidth("100px");

            Button check = new Button("Check Answer");
            

            int page = i;
        
            if (i != 0) {
                previousButton.addClickListener(event -> {
                    panel.setContent(challengeLayouts.get(page - 1));
                    panel.setCaption("Question " + (page));
                });
                buttonsLayout.addComponent(previousButton);
                buttonsLayout.setComponentAlignment(previousButton, Alignment.BOTTOM_LEFT);
            }
            buttonsLayout.addComponent(check);
            if (i != questions -1) {
             
                nextButton.addClickListener(event -> {
                    panel.setContent(challengeLayouts.get(page + 1));
                    panel.setCaption("Question " + (page + 2));
                });
                buttonsLayout.addComponent(nextButton);
                buttonsLayout.setComponentAlignment(nextButton, Alignment.BOTTOM_RIGHT);
            }

            check.addClickListener(event -> {
                checkAnswer(page);
            });
            
            
            

            buttonsLayout.setComponentAlignment(check, Alignment.BOTTOM_CENTER);
            buttonsLayout.setSpacing(true);
            buttonsLayout.setMargin(true);

            oneChallengeLayout.addComponent(buttonsLayout);
            oneChallengeLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_CENTER);
            oneChallengeLayout.setSpacing(true);
            oneChallengeLayout.setMargin(true);
            challengeLayouts.add(oneChallengeLayout);
            
        }   
        
        panel.setContent(challengeLayouts.get(0));
        panel.setCaption("Question 1");
    }
  

    private void checkAnswer(int page) {
        
    }

    private void generateChallenges(VerticalLayout challengeLayout) {
        ArrayList<Label> challengeLinesLabels = new ArrayList<Label>();
        for (int i=0;i<questions;i++) {
            HorizontalLayout challengeLine = new HorizontalLayout();
            Label challengeLabel = new Label();
            String challenge = createChallenge(); 
            TextField answerBox = new TextField();
            Label answerCorrect = new Label();
            
            
            answerBox.setWidth("100px");
            challengeLabel.setCaption(challenge);
            challengeLine.addComponents(challengeLabel, answerBox, answerCorrect);
            challengeLine.setSpacing(true);
            challengeLayout.addComponent(challengeLine);
            answers.addItemProperty("answer" + i, answerBox);
            challengeLinesLabels.add(answerCorrect);
            challengeLines.add(challengeLine);
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
                Notification.show("Something went wrong!" + e.getMessage(), Type.WARNING_MESSAGE);
            }
        });

    }

    private void checkAnswers(ArrayList<Label> challengeLinesLabels) {
        
        for (int i = 0; i < questions; i++) {
            
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
        for (int i = 0; i < questions; i++) {
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
        int firstNum;
        int secondNum;
        Integer[] numbers;
        switch (operator) {
            case "+":
                firstNum = random.nextInt(plusRangeLargestValue - plusRangeLowestValue + 1) + plusRangeLowestValue;
                secondNum = random.nextInt(plusRangeLargestValue - plusRangeLowestValue + 1) + plusRangeLowestValue;
                numbers = new Integer[]{firstNum, secondNum};
                return numbers;
                
            case "-":
                firstNum = random.nextInt(minusRangeLargestValue - minusRangeLowestValue + 1) + minusRangeLowestValue;
                secondNum = random.nextInt(minusRangeLargestValue - minusRangeLowestValue + 1) + minusRangeLowestValue;
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

	
