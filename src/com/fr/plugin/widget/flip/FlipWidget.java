package com.fr.plugin.widget.flip;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import com.fr.base.FRContext;
import com.fr.base.Formula;
import com.fr.base.ParameterMapNameSpace;
import com.fr.form.data.DataBinding;
import com.fr.form.ui.DataControl;
import com.fr.form.ui.DirectWriteEditor;
import com.fr.form.ui.WidgetValue;
import com.fr.general.Inter;
import com.fr.general.ModuleContext;
import com.fr.json.JSONArray;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.plugin.PluginLicense;
import com.fr.plugin.PluginLicenseManager;
import com.fr.script.Calculator;
import com.fr.stable.ArrayUtils;
import com.fr.stable.CodeUtils;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.script.CalculatorProvider;
import com.fr.stable.web.Repository;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;
import com.fr.web.core.SessionIDInfor;
import com.fr.web.utils.WebUtils;

public class FlipWidget extends DirectWriteEditor implements DataControl {
    private WidgetValue widgetValue = new WidgetValue();
    private String align=new String();
    private int refresh=0;

    public String getAlign() {
        return align;
    }
    public void setAlign(String align) {
        this.align = align;
    }

    public int getRefresh() {
        return refresh;
    }
    public void setRefresh(int refresh) {
        this.refresh = refresh;
    }

    @Override
    public WidgetValue getWidgetValue() {
        return this.widgetValue;
    }
    @Override
    public void setWidgetValue(WidgetValue widgetValue) {
        this.widgetValue = widgetValue;
    }
    @Override
    public String getXType() {//控件类型，参考其他的案例，返回的是文本框
        return "flip";
    }
    public static final int TYPE_FLIP_NONE=8;
    @Override
    public int[] getValueType() {
        return new int[]{TYPE_FLIP_NONE,TYPE_FORMULA,TYPE_DATABINDING};
    }
    @Override
    public String[] dependence(CalculatorProvider calculatorProvider) {
        return super.dependence(calculatorProvider);
    }
    public int getWidgetValueFM(Calculator var1) throws Exception {
        int value =0;
        if(widgetValue.getValue()==null){
            value = 0;
        }else{
            if(widgetValue.getValue() instanceof Formula)
            {
                value = (Integer)var1.eval((Formula)widgetValue.getValue());
            } else
            if(widgetValue.getValue() instanceof DataBinding)
            {
                value =  getWidgetValueByBinding(var1);
            }
        }
        return value;
    }
    private int getWidgetValueByBinding(Calculator var1){
        int value =0;
        WidgetValue.WidgetValueInfo var5 = new WidgetValue.WidgetValueInfo(this.getWidgetName());
        Object var6 = this.getWidgetValue().createAttrResult(var5,var1, new JSONObject());
        if(var6 instanceof Integer){
            value = ((Integer) var6).intValue();
        }
        else if(var6 instanceof BigDecimal){
            value = ((BigDecimal) var6).intValue();
        }
        else if(var6 instanceof String){
            try {
                value = (Integer.parseInt(var6.toString()));
            }
            catch (Exception e)
            {
                FRContext.getLogger().error(e.getMessage(), e);
            }
        }
        return value;
    }
    @Override
    public void createValueResult(DataControl var1, Calculator var2, JSONObject var3, JSONObject var4) {
        if(this.getWidgetValue() != null) {
            WidgetValue.WidgetValueInfo var5 = new WidgetValue.WidgetValueInfo(this.getWidgetName());
            Object var6 = this.getWidgetValue().createAttrResult(var5, var2, var4);

            try {
                var3.put(this.widgetName.toUpperCase(), var6 == null?"":var6);
            } catch (JSONException var8) {
                FRContext.getLogger().error(var8.getMessage(), var8);
            }

        }
    }
    @Override
    public JSONObject createJSONConfig(Repository var1, Calculator var2,NodeVisitor var3) throws JSONException {
        JSONObject var4 = super.createJSONConfig(var1, var2, var3);

        try {
            Object var5 = var4.get("value");
            if (var5 == null) {
                var4.remove("value");
            } else if (var5 instanceof String) {
                var4.put("value", (new JSONObject()).put("flip", var5));
            }
        } catch (Exception var8) {
            ;
        }
        if (this.align != null) {
            var4.put("align", this.value2Config(this.align, var2));
        }

        var4.put("refresh", this.value2Config(this.refresh, var2));

        String[] var9 = this.dependence(var2);
        if (!ArrayUtils.isEmpty(var9)) {
            var4.put("dependence", var9);
        }

        return var4;
    }
    @Override
    public JSONArray createJSONData(SessionIDInfor var1, Calculator var2, HttpServletRequest var3) throws Exception {
    	PluginLicense pluginLicense = PluginLicenseManager.getInstance().getPluginLicenseByID(FlipConstant.PLUGIN_ID);
        if ((pluginLicense != null && pluginLicense.isAvailable()) || isDesign()) {
        	
        	JSONArray var4 = super.createJSONData(var1, var2, var3);
        	ParameterMapNameSpace var5 = ParameterMapNameSpace.create(var1.getParameterMap4Execute());
        	DependenceNameSpace var6 = new DependenceNameSpace(CodeUtils.cjkDecode(WebUtils.getHTTPRequestParameter(var3, "dependence")));
        	var2.pushNameSpace(var5);
        	var2.pushNameSpace(var6);
        	JSONObject var7 = new JSONObject();
        	var7.put("value",this.getWidgetValueFM(var2));
        	var4.put(var7);
        	var2.removeNameSpace(var5);
        	var2.removeNameSpace(var6);
        	return var4;
        }else {
            JSONArray errorMsg = new JSONArray();
            errorMsg.put(new JSONObject().put("text", Inter.getLocText("Plugin-Flip_License_Expired")).put("value", Inter.getLocText("Plugin-Flip_License_Expired")));
            return errorMsg;
        }
    }
    
    private boolean isDesign() {
        return ModuleContext.isModuleStarted("com.fr.design.module.DesignerModule");
    }
    
    @Override
    public String[] supportedEvents() {
        return new String[]{};
    }







    public void readXML(XMLableReader var1) {
        super.readXML(var1);
        String var2;
        if(var1.isChildNode()) {
            var2 = var1.getTagName();//wdigetName,widgetAttr,FlipAttr,widgetValue
            if(var2.equals("FlipAttr")) {//读取XML的开头，在写入的时候，已经写了
                String var3 = null;
                if((var3 = var1.getAttrAsString("align", (String)null)) != null) {
                    setAlign(var3);
                }
                if((var3 = var1.getAttrAsString("refresh", (String)null)) != null) {
                    setRefresh(Integer.parseInt(var3));
                }
            } else if("widgetValue".equals(var2)) {
                this.widgetValue = new WidgetValue();
                var1.readXMLObject(this.widgetValue);
            }
        }

    }

    public void writeXML(XMLPrintWriter var1) {//表单预览的时候，会加载此方法
        super.writeXML(var1);
        var1.startTAG("FlipAttr");

        if(this.align!=null) {
            var1.attr("align", this.getAlign().toString());
        }
        if(this.refresh!=0) {
            var1.attr("refresh", getRefresh());
        }
        var1.end();
        if(this.widgetValue != null) {
            this.widgetValue.writeXML(var1);
        }

    }

}
