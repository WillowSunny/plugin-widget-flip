package com.fr.plugin.widget.flip;

import com.fr.stable.fun.impl.AbstractJavaScriptFileHandler;

/**
 * Created by richie on 15/11/17.
 */
public class JavaScriptFileLoader extends AbstractJavaScriptFileHandler {
    @Override
    public String[] pathsForFiles() {
        return new String[]{
                "/com/fr/plugin/widget/flip/web/widget.filp.js"
        };
    }
}