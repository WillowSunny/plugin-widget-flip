package com.fr.plugin.widget.flip;

import com.fr.design.designer.creator.CRPropertyDescriptor;
import com.fr.design.designer.creator.XWidgetCreator;
import com.fr.design.form.util.XCreatorConstants;
import com.fr.design.gui.ilable.UILabel;
import com.fr.design.gui.itextfield.UITextField;
import com.fr.design.layout.FRGUIPaneFactory;
import com.fr.form.ui.Widget;
import com.fr.general.IOUtils;
import com.fr.general.Inter;
import com.fr.plugin.widget.flip.editor.FlipAlign;
import com.fr.plugin.widget.flip.editor.FlipDataSourceEditor;
import com.fr.stable.ArrayUtils;

import javax.swing.*;
import java.awt.*;
import java.beans.IntrospectionException;
public class FlipWidgetCreate extends XWidgetCreator{
	 private UITextField textField;
	public FlipWidgetCreate(Widget widget, Dimension dimension) {
        super(widget, dimension);
    }
      @Override
    protected JComponent initEditor() {
          if(editor==null)
        {
                editor = FRGUIPaneFactory.createBorderLayout_S_Pane();
                UILabel label=new UILabel();
                label.setIcon(IOUtils.readIcon("/com/fr/plugin/widget/flip/images/flip_display.png"));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.CENTER);
                editor.add(label, BorderLayout.CENTER);
                textField = new UITextField(5);
                textField.setOpaque(false);
                editor.add(textField, BorderLayout.SOUTH);
                editor.setBackground(Color.WHITE);
        }
        return editor;
    }
    @Override
    public CRPropertyDescriptor[] supportedDescriptor() throws IntrospectionException {
        return (CRPropertyDescriptor[]) ArrayUtils.addAll(super.supportedDescriptor(), new CRPropertyDescriptor[]{
            new CRPropertyDescriptor("widgetValue", this.data.getClass())
            .setI18NName(Inter.getLocText("Plugin-Flip_DataSource"))
            .setEditorClass(FlipDataSourceEditor.class)
            .putKeyValue(XCreatorConstants.PROPERTY_CATEGORY,"Advanced"),
            new CRPropertyDescriptor("align",this.data.getClass())
            .setI18NName(Inter.getLocText("Plugin-Flip_AlignType"))
            .setEditorClass(FlipAlign.class)
            .putKeyValue(XCreatorConstants.PROPERTY_CATEGORY,"Advanced"),
            new CRPropertyDescriptor("refresh",this.data.getClass())
                    .setI18NName(Inter.getLocText("Plugin-Flip_Refresh"))
                    .putKeyValue(XCreatorConstants.PROPERTY_CATEGORY,"Advanced")
            }
    );
    }
    @Override
    public String getIconPath() {
        return "com/fr/plugin/widget/flip/images/flip_icon.png";
    }
}
