package com.garbuzov.diary.jstl;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class Footer extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().print("<footer class=\"footer\">\n" +
                                         "<div class=\"block-1\">© 2019 Doskort Inc.</div>\n" +
                                         "<div class=\"block-2\">By using the Doskort website, you agree to the Doskort Agreement.</div>\n" +
                                       "</footer>");
        } catch(IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}
