package com.mgdesign.pages;

import com.mgdesign.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public LoginPage(){
        PageFactory.initElements(Driver.get(), this);
    }

    @FindBy(id="username")
    public WebElement userName;



    @FindBy(id="password")
    public WebElement password;

    @FindBy(id = "btn-login")
    public WebElement login;


    public void login(String userNameStr, String passwordStr) {
        userName.sendKeys(userNameStr);
        password.sendKeys(passwordStr);
        login.click();

    }


}
