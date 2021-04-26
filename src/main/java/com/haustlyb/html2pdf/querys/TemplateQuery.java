package com.haustlyb.html2pdf.querys;


import com.haustlyb.html2pdf.util.HSPageQuery;

/**
 * @author haust_lyb
 * email : 1570194845@qq.com
 * @date 2020/9/18 下午1:49
 */
public class TemplateQuery extends HSPageQuery {
    @Override
    protected String listHQL() {
        return "from LYB_Template as e "+buildWhere() + " order by e.auditDate desc ";
    }

    @Override
    protected String countHQL() {
        return "select count(*) from LYB_Template as e "+buildWhere();
    }

    private String buildWhere() {
        String rs = " where 1=1 ";

        return rs;
    }
}
