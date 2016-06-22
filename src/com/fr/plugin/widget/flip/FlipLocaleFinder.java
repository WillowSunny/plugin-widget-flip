package com.fr.plugin.widget.flip;

import com.fr.stable.fun.impl.AbstractLocaleFinder;

/**
 * Created by Administrator on 2016-05-29.
 */
public class FlipLocaleFinder extends AbstractLocaleFinder {
    @Override
    public String find() {//查找字符串，类似于android的value
        return "com/fr/plugin/widget/flip/locale/flip";
    }
}
