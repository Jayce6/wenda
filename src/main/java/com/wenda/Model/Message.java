package com.wenda.Model;

import java.util.Date;

/**
 * @auther 张伟豪
 * @create 2019/6/23-15:21
 */
public class Message {
    private int id;
    private int formid;
    private int toid;
    private String content;
     private int hasRead;

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    private String conversionId;
    private Date createDate;

    public Message() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFormId() {
        return formid;
    }

    public void setFormId(int formId) {
        this.formid = formId;
    }


    public int getToId() {
        return toid;
    }

    public void setToId(int toid) {
        this.toid = toid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConversionId() {
        if(formid<toid)
        {
         return String.format("%d_%d",formid,toid);
        }else {
            return String.format("%d_%d",toid,formid);
        }
    }

    public void setConversionId(String conversionId) {
        this.conversionId = conversionId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }




}
