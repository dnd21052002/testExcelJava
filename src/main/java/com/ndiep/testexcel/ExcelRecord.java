/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndiep.testexcel;

/**
 *
 * @author ndiep
 */
public class ExcelRecord {
    private String code;
    private String name;
    private int content;
    private int pendingReview;
    private String creationDate;
    private String status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }


    public int getPendingReview() {
        return pendingReview;
    }

    public void setPendingReview(int pendingReview) {
        this.pendingReview = pendingReview;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
