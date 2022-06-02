package com.vaadin.vaadin_archetype_application;

import java.util.ArrayList;
import java.util.Random;

import javax.servlet.annotation.WebServlet;
import javax.swing.Icon;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
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
    private ArrayList<Label> challengeLinesLabels = new ArrayList<Label>();
    private PropertysetItem answers = new PropertysetItem();
    private int points = 0;
    private Label pointLabel = new Label();
    private Panel panel = new Panel("Challenge Panel");
    private int questionsAnswered = 0;
    private VerticalLayout challengeLayout = new VerticalLayout();
    private FormLayout form = new FormLayout();
    private FontAwesome correctIcon = FontAwesome.CHECK;
    private FontAwesome wrongIcon = FontAwesome.CLOSE;

    private Random random = new Random();
    
	
	

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	VerticalLayout mainLayout = new VerticalLayout();
   
		   mainLayout.setSizeFull();
           
           form.setStyleName("form");
		   
		    
	        PropertysetItem formData = new PropertysetItem();
	        formData.addItemProperty("questions", new ObjectProperty<Integer>(1));
	        formData.addItemProperty("plus", new ObjectProperty<Boolean>(false));
	        formData.addItemProperty("minus", new ObjectProperty<Boolean>(false));
            formData.addItemProperty("plusRangeLowest", new ObjectProperty<String>("1"));
            formData.addItemProperty("plusRangeLargest", new ObjectProperty<String>("10"));
            formData.addItemProperty("minusRangeLowest", new ObjectProperty<String>("1"));
            formData.addItemProperty("minusRangeLargest", new ObjectProperty<String>("10"));


	        HorizontalLayout questionsLayout = new HorizontalLayout();
            Label questionsLabel = new Label("Number of questions");
	        final TextField numberOfQuestions = new TextField();
	        numberOfQuestions.setRequired(true);
	        numberOfQuestions.addValidator(new IntegerRangeValidator("Input should be an Integer between 1 - 10.",1,10));
            numberOfQuestions.setWidth("60px");
            questionsLayout.addComponents(questionsLabel, numberOfQuestions);
            questionsLayout.setSpacing(true);
            questionsLayout.setStyleName("questionsLayout");
	        
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
           

            plusRangeLayout.addComponents(plusRangeLowest, new Label("-"), plusRangeLargest);
            plusRangeLayout.setSpacing(true);
            

            plusLayout.addComponents(plus, plusRangeLabel, plusRangeLayout);
            plusLayout.addStyleName("plusLayout");
            plus.addStyleName("plusIcon");
            
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
            
            minusLayout.setSpacing(true);
            
            minusLayout.addComponents(minus, minusRangeLabel, minusRangeLayout);
            minusLayout.addStyleName("minusLayout");
            minus.addStyleName("minusIcon");
	        
	        
	        FieldGroup binder = new FieldGroup(formData);
	        binder.bind(numberOfQuestions, "questions");
	        binder.bind(plus, "plus");
	        binder.bind(minus, "minus");
            binder.bind(plusRangeLowest, "plusRangeLowest");
            binder.bind(plusRangeLargest, "plusRangeLargest");
            binder.bind(minusRangeLowest, "minusRangeLowest");
            binder.bind(minusRangeLargest, "minusRangeLargest");
      
            panel.setWidthUndefined();
            
            panel.setVisible(false);
            panel.setWidth("500px");

            
            HorizontalLayout formButtonBox = new HorizontalLayout();
	        
	        Button submitButton = new Button("Submit");
	        submitButton.addStyleName("submitButton");
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
                        Notification.show("Please select calulation type");
                    } else {
                        panel.setVisible(true);
                        form.setVisible(false);
                        generateChallenges();
                        generateOneChallengeLayouts();
                    }
	        		
	        		
	        		
				} catch (CommitException | NumberFormatException e) {
					System.out.println(e);
					Notification.show("Please input Integers", Type.WARNING_MESSAGE);
				}
	            
	        });
	        formButtonBox.addComponent(submitButton);
	        formButtonBox.addStyleName("formButtonBox");
	        form.addComponents(questionsLayout, plusMinusLabel, plusLayout, minusLayout, formButtonBox);
	        
	        form.setSpacing(true);
	        form.setWidth("600px");
	        formButtonBox.setWidth("100%");
	        formButtonBox.setComponentAlignment(submitButton, Alignment.MIDDLE_CENTER);
	        
	        mainLayout.addComponent(form);    
            mainLayout.addComponent(panel);
            BallTest ballTest = new BallTest(1);
            mainLayout.addComponent(ballTest);
            mainLayout.setComponentAlignment(ballTest, Alignment.TOP_CENTER);
            mainLayout.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
            mainLayout.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
	        
	        setContent(mainLayout);
        
    }
    private void initializeQuestions() {
        panel.setVisible(false);
        form.setVisible(true);
        challengeList.clear();
        challengeLayouts.clear();
        challengeLines.clear();
        answerList.clear();
        challengeLinesLabels.clear();
        questionsAnswered = 0;
        points = 0;
        challengeLayout = new VerticalLayout();
    }

    private void generateOneChallengeLayouts() {
        for (int i = 0; i < questions; i++) {
            VerticalLayout oneChallengeLayout = new VerticalLayout();
            oneChallengeLayout.addComponent(challengeLines.get(i));
            oneChallengeLayout.addStyleName("oneChallengeLayout");
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
                
                
                if (questionsAnswered == questions) {
                    pointLabel.setCaption(points + " / " + questions);
                    createResultLayout();
                    panel.setContent(challengeLayout);
                    panel.setCaption("Results");
                    
                }
            });

            previousButton.addStyleName("previousButton");
            nextButton.addStyleName("nextButton");
            check.addStyleName("check");

            buttonsLayout.setComponentAlignment(check, Alignment.BOTTOM_CENTER);
            buttonsLayout.setSpacing(true);

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
        String answerStr = answers.getItemProperty("answer" + page).getValue().toString().trim();
        try {
            int answer = Integer.parseInt(answerStr);
            String[] challengeParts = challengeList.get(page).split(" ");
            int rightAnswer = 0;
            
            switch (challengeParts[1]) {
                case "+":
                    rightAnswer = Integer.parseInt(challengeParts[0]) + Integer.parseInt(challengeParts[2]);
                    break;
                case "-":
                    rightAnswer = Integer.parseInt(challengeParts[0]) - Integer.parseInt(challengeParts[2]);
                    break;     
            }   
            
            challengeLinesLabels.get(page).setVisible(true);
            if (answer == rightAnswer) {
                
                challengeLinesLabels.get(page).setIcon(correctIcon);
                challengeLinesLabels.get(page).setStyleName("correctIconLabel");
                points += 1;
                challengeLinesLabels.get(page).setCaption("");
            } else {
                challengeLinesLabels.get(page).setStyleName("wrongIconLabel");
                challengeLinesLabels.get(page).setIcon(wrongIcon);
                challengeLinesLabels.get(page).setCaption("RightAnswer: " + rightAnswer);
            }
            questionsAnswered ++;
            
        } catch (NumberFormatException e) {
            Notification.show("Please enter an Integer value.", Type.WARNING_MESSAGE);
        }
    }

    private void generateChallenges() {
        answers = new PropertysetItem();
        for (int i=0;i<questions;i++) {
            HorizontalLayout challengeLine = new HorizontalLayout();
            HorizontalLayout answerLine = new HorizontalLayout();
            String challenge = createChallenge();
            Label challengeLabel = new Label(challenge);
            TextField answerBox = new TextField();
            Label answerCorrect = new Label();     
            
            answerLine.setSpacing(true);
            answerLine.addStyleName("questionsLayout");
            
            answerBox.setWidth("60px");
            answerLine.addComponents(new BallTest(2), new Label("+"), new BallTest(6), answerBox);
            challengeLine.addComponents(answerLine, answerCorrect);
            challengeLine.setSpacing(true);
            challengeLine.setStyleName("challengeLine");
            answerCorrect.setStyleName("answerCorrect");

            answers.addItemProperty("answer" + i, answerBox);
            challengeLinesLabels.add(answerCorrect);
            challengeLines.add(challengeLine);
            challengeList.add(challenge);     
        }
    }

    private void checkAnswers() {
        
        for (int i = 0; i < questions; i++) {          
            checkAnswer(i);   
        } 
    }


    private void saveAnswers() {
        for (int i = 0; i < questions; i++) {
            answerList.add(answers.getItemProperty("answer" + i).getValue().toString());
        }
    }

    private void createResultLayout() {
        for (HorizontalLayout challengeLine : challengeLines) {
            challengeLayout.addComponent(challengeLine);
        }
        Button checkButton = new Button("Check Answers");
        Button newGameButton = new Button("New Game");
        HorizontalLayout resultLayoutButtons = new HorizontalLayout();
        HorizontalLayout pointTextLayout = new HorizontalLayout();
        
        pointTextLayout.addComponent(pointLabel);
        pointTextLayout.addStyleName("pointTextLayout");
        
        resultLayoutButtons.addComponents(newGameButton, checkButton);
        challengeLayout.addComponent(pointTextLayout);
        challengeLayout.addComponent(resultLayoutButtons);
        resultLayoutButtons.setWidth("100%");
        resultLayoutButtons.setComponentAlignment(checkButton, Alignment.MIDDLE_RIGHT);
        resultLayoutButtons.setComponentAlignment(newGameButton, Alignment.MIDDLE_LEFT);
        checkButton.addStyleName("check");
        newGameButton.addStyleName("previousButton");

        challengeLayout.setMargin(true);
        
        checkButton.addClickListener(event -> {
            try {
                
                saveAnswers();
                checkAnswers();
            } catch (Exception e) {
                System.out.println(e);
                Notification.show("Something went wrong!" + e.getMessage(), Type.WARNING_MESSAGE);
            }
        });
        newGameButton.addClickListener(event -> {
            initializeQuestions();
        });
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

	
