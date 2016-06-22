package com.fr.plugin.widget.flip.editor;

import com.fr.design.designer.properties.Encoder;

/**
 * Created by Administrator on 2016-05-31.
 */
public class FlipWrapper implements Encoder {
    @Override
    public String encode(Object o) {
        if(o!=null)
        {
            return o.toString();
        }
        return null;
    }
}
