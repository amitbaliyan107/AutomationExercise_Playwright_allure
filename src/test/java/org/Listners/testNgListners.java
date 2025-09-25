package org.Listners;


import org.testng.ITestListener;
import org.testng.ITestResult;

public class testNgListners implements ITestListener {

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
    }
}
