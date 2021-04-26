package com.haustlyb.html2pdf.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import ognl.Ognl;
import ognl.OgnlException;
import org.apache.commons.lang3.Validate;

import java.util.Map;

/**
 * Created by 李一博 on 10:57 2020/7/5.
 */
@SuppressWarnings("unchecked")
public class OgnlWrapper {


    private static ObjectMapper om = new ObjectMapper();
    private Map<String, Object> payload;

    public OgnlWrapper(Map<String, Object> playload) {
        Validate.notEmpty(playload, "can not construct with none playload!");
        this.payload = playload;
    }


    public OgnlWrapper(Object playload) {
        this.payload = om.convertValue(playload, Map.class);
    }

    public <T> T get(String expression) {
        try {
            return (T) Ognl.getValue(expression, this.payload);
        } catch (OgnlException e) {
//            e.printStackTrace();
            return null;
        }
    }

    public Long getLong(String expression) {
        try {
            Object obj = Ognl.getValue(expression, this.payload);
            if (null == obj)
                return null;
            try {
                return Long.parseLong(obj.toString());
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                return null;
            }
        } catch (OgnlException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer getInt(String expression) {
        try {
            Object obj = Ognl.getValue(expression, this.payload);
            if (null == obj)
                return null;
            try {
                return Integer.parseInt(obj.toString());
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                return null;
            }
        } catch (OgnlException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("OgnlWrapper[%s]", this.payload.toString());
    }

}
