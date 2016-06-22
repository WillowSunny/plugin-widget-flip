package com.fr.plugin.widget.flip.editor;

import com.fr.design.mainframe.widget.editors.ComboEditor;
import com.fr.general.Inter;

import javax.swing.*;
import java.util.Vector;

/**
 * Created by Administrator on 2016-06-03.
 */
public class FlipAlign extends ComboEditor {

    @Override
    public ComboBoxModel model() {
        Vector<String> s=new Vector<String>();
        s.add(Inter.getLocText("Plugin-Flip_AlignLeft"));
        s.add(Inter.getLocText("Plugin-Flip_AlignCenter"));
        s.add(Inter.getLocText("Plugin-Flip_AlignRight"));
        return new DefaultComboBoxModel(s);
    }
    @Override
    public void setValue(Object o) {
         comboBox.setSelectedItem(o);
        }
    @Override
    public Object getValue() {
        return comboBox.getSelectedItem();
    }




}
