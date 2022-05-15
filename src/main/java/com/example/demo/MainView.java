package com.example.demo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ErrorLevel;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.math.NumberUtils;

@Route("")
public class MainView extends VerticalLayout {
    Binder<snils> binder = new BeanValidationBinder<>(snils.class);
    private snilsRepository repository;
    private DataService service;
//    private TextField snils_num=new TextField("snils_num");
    ValidTextField snils_num = new ValidTextField();
    private Grid<snils> grid=new Grid<>(snils.class);
//    private Binder<snils> binder=new Binder<>(snils.class);

    public MainView(snilsRepository repository){
        binder.bindInstanceFields(this);
        this.repository = repository;
//    public MainView(DataService service){
//        this.service = service;


//        snils_num.addValidator(
//                s-> NumberUtils.isDigits(s), "String is not numeric!");
//        snils_num.addValidator(
//                s -> !repository.existsById(s), "Not unique!");

        grid.setColumns("snils_num");

        add(getForm(), grid);
        refreshGrid();

    }
    private Component getForm(){
        snils_num.setMinLength(11);
        snils_num.setMaxLength(11);
//        snils_num.setPattern("[0-9]");
//        snils_num.setPreventInvalidInput(true);
        snils_num.setLabel("SNILS number");
        snils_num.setClearButtonVisible(true);
//        valid.setValue("123-456-789 12");


        //
        binder.forField(snils_num)
                .withValidator(e -> {
                    snils_num.removeClassName("warn");
                    return e.length() > 10;
                }, "You must enter 11 numbers", ErrorLevel.ERROR)
                .withValidator(e -> {
                    snils_num.addClassName("warn");
                    return NumberUtils.isDigits(e);
                }, "Numbers!", ErrorLevel.ERROR)
                .withValidator(e -> {
                    snils_num.addClassName("warn");
                    return !repository.existsById(e);
                }, "Existing snils", ErrorLevel.ERROR)
                .withValidator(e -> {
                    snils_num.addClassName("warn");
                    return SNILSValidate(e);
                }, "Wrong checksum!", ErrorLevel.ERROR)
                .bind(snils::getSnils_num, snils::setSnils_num);
        //

        var layout = new HorizontalLayout();
        layout.setAlignItems(Alignment.BASELINE);

        var addButton=new Button("Add");
        addButton.addClickShortcut(Key.ENTER);
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layout.add(snils_num,addButton);

        binder.bindInstanceFields(this);

        addButton.addClickListener(click ->{
            try{
                var snils = new snils();
                binder.writeBean(snils);
                repository.save(snils);
                binder.readBean(new snils());
                refreshGrid();
            } catch (ValidationException z){
                //
            }
        });
        return layout;
    }
    private void refreshGrid() {
        grid.setItems(repository.findAll());
    }
//    private void refreshGrid() {
//        grid.setItems(service.findAllsnils(""));
//    }
    public int SNILSContolCalc (String snils){
        String workSnils=snils.substring(0,9);
//        System.out.println("workSnils="+workSnils);
        int totalSum = 0;

        for(int i = workSnils.length()-1, j=0; i>=0 ; i--, j++){
            int digit = workSnils.charAt(j) - '0';
//            System.out.println("digit-char="+workSnils.charAt(j));
//            System.out.println("digit="+digit);
//            System.out.println("i="+i);
//            System.out.println("digit*i="+digit*i);
            totalSum += digit*(i+1);
//            System.out.println("totalSum="+totalSum);
        }
//        System.out.println("totalSum="+totalSum);
//        return totalSum;
        return SNILSCheckControlSum(totalSum);
    }

    private int SNILSCheckControlSum(int _controlSum){
        int result=0;
        if(_controlSum < 100){
            result = _controlSum;
        }
        else if(_controlSum <= 101){
            result = 0;
        }
        else{
            int balance = _controlSum % 101;
            result = SNILSCheckControlSum(balance);
        }
        return result;
    }

    public Boolean SNILSValidate(String snils){
        Boolean result = false;
        int controlSum = SNILSContolCalc(snils);
//        System.out.println("controlSum="+controlSum);
        Integer strControlSum=Integer.parseInt(snils.substring(9,11));
//        System.out.println("controlSum="+controlSum);
//        System.out.println("strControlSum="+strControlSum);
        if(controlSum == strControlSum){
            result = true;
        }
        return result;
    }
}