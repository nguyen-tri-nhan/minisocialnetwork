/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dtos;

/**
 *
 * @author nguyentrinhan2000
 */
public class ErrorUserDTO {
    private String errormail, errorfirstname, errorlastname, errorpassword, errorrepassword;

    public ErrorUserDTO() {
    }

    public ErrorUserDTO(String errormail, String errorfirstname, String errorlastname, String errorpassword, String errorrepassword) {
        this.errormail = errormail;
        this.errorfirstname = errorfirstname;
        this.errorlastname = errorlastname;
        this.errorpassword = errorpassword;
        this.errorrepassword = errorrepassword;
    }

    public String getErrormail() {
        return errormail;
    }

    public void setErrormail(String errormail) {
        this.errormail = errormail;
    }

    public String getErrorfirstname() {
        return errorfirstname;
    }

    public void setErrorfirstname(String errorfirstname) {
        this.errorfirstname = errorfirstname;
    }

    public String getErrorlastname() {
        return errorlastname;
    }

    public void setErrorlastname(String errorlastname) {
        this.errorlastname = errorlastname;
    }

    public String getErrorpassword() {
        return errorpassword;
    }

    public void setErrorpassword(String errorpassword) {
        this.errorpassword = errorpassword;
    }

    public String getErrorrepassword() {
        return errorrepassword;
    }

    public void setErrorrepassword(String errorrepassword) {
        this.errorrepassword = errorrepassword;
    }

    
    
    
}
