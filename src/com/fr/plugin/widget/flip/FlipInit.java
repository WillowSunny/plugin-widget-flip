package com.fr.plugin.widget.flip;
import com.fr.design.fun.impl.AbstractFormWidgetOptionProvider;
import com.fr.form.ui.Widget;
import com.fr.general.Inter;
import com.fr.plugin.ExtraClassManager;
import com.fr.stable.fun.FunctionHelper;
import com.fr.stable.fun.FunctionProcessor;
import com.fr.stable.fun.impl.AbstractFunctionProcessor;


/**
 * Created by Administrator on 2016-05-28.
 */
public class FlipInit extends AbstractFormWidgetOptionProvider {
	
    private static FunctionProcessor ONEFUNCTION = new AbstractFunctionProcessor() {
        @Override
        public int getId() {
            return FunctionHelper.generateFunctionID("com.fr.plugin.widget.flip");
        }

        @Override
        public String toString() {
        	return Inter.getLocText("Plugin-Flip_Trigger");
        }
    };

    @Override
    public Class<? extends Widget> classForWidget() {//插件类
        FunctionProcessor processor= ExtraClassManager.getInstance().getFunctionProcessor();
        if(processor!=null){
            processor.recordFunction(ONEFUNCTION);
        }
        return FlipWidget.class;
    }

    @Override
    public Class<?> appearanceForWidget() {//界面类
        return FlipWidgetCreate.class;
    }

    @Override
    public String iconPathForWidget() {//图标
        return "com/fr/plugin/widget/flip/images/flip_icon.png";
    }

    @Override
    public String nameForWidget() {//插件名称
        return Inter.getLocText("Plugin-Flip_Widget");
    }

    @Override
    public boolean isContainer() {//是否容器
        return false;
    }
}
