package com.mgdesign.pages;

import com.mgdesign.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderPage {
    public OrderPage(){
        PageFactory.initElements(Driver.get(), this);
    }

    @FindBy(className="dropdown-toggler-content-XHHDfD3")
    public WebElement onboard;


    @FindBy(linkText="Orders")
    public WebElement orders;
    @FindBy(linkText="Awaiting Shipment")
    public WebElement awaitingShipment;
    public void onboardClick() {
        onboard.click();
    }
    public void ordersClick() {
        orders.click();
    }

    public void awaitingShipmentClick() {
        awaitingShipment.click();
    }
}
