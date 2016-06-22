package com.fr.plugin.widget.flip.editor;

import com.fr.design.Exception.ValidationException;
import com.fr.design.editor.ValueEditorPane;
import com.fr.design.editor.editor.Editor;
import com.fr.design.editor.editor.FormulaEditor;
import com.fr.design.editor.editor.NoneEditor;
import com.fr.design.mainframe.widget.editors.AbstractPropertyEditor;
import com.fr.design.mainframe.widget.editors.DataBindingEditor;
import com.fr.design.mainframe.widget.editors.ServerDataBindingEditor;
import com.fr.form.ui.DataControl;
import com.fr.form.ui.WidgetValue;
import com.fr.general.Inter;
import com.fr.plugin.widget.flip.FlipWidget;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class FlipDataSourceEditor extends AbstractPropertyEditor	
							                 {
	 private DataControl widget;
	 private ValueEditorPane wep;
	public FlipDataSourceEditor(Object o)
	{
		this.widget=(DataControl)o;
		Editor[] editors=createWidgetValueEditor(widget, false);
		for(final Editor e:editors)
		{
		    e.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    firePropertyChanged();
                }

            });
		}
		this.wep = new ValueEditorPane(editors);
        wep.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                firePropertyChanged();
            }
        });

	}
    public static Editor[] createWidgetValueEditor(DataControl data, boolean onlyServer) {
        int types[] = data.getValueType();
        Editor[] editor = new Editor[types.length ];
        for (int i = 0; i < types.length; i++) {
            editor[i] = createWidgetValueEditorByType(types[i], onlyServer);
        }
        return editor;
    }
    public static Editor createWidgetValueEditorByType(int type, boolean onlyServer) {
        switch (type) {
            case FlipWidget.TYPE_FLIP_NONE:
                return new NoneEditor(null, Inter.getLocText("Plugin-Flip_None"));
           case DataControl.TYPE_FORMULA:
                return new FormulaEditor(Inter.getLocText("Parameter-Formula"));
            case DataControl.TYPE_DATABINDING:
                return onlyServer ? new ServerDataBindingEditor() : new DataBindingEditor();
            default:
                return null;
        }
    }
	@Override
	public void validateValue() throws ValidationException
	{
		// TODO Auto-generated method stub

	}
	@Override
	public Component getCustomEditor()
	{
		// TODO Auto-generated method stub
		return wep;
	}
	@Override
	public Object getValue()
	{
		 return new WidgetValue(wep.update());
	}
	@Override
	public void setValue(Object value)
	{
		 if(value != null) {
	            wep.populate(((WidgetValue)value).getValue());
	        }
	}
	
}

