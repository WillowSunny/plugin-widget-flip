package com.fr.plugin.widget.flip.render;

import com.fr.design.mainframe.widget.renderer.EncoderCellRenderer;
import com.fr.plugin.widget.flip.editor.FlipWrapper;

/**
 * Created by Administrator on 2016-05-31.
 */
public class FlipDataSourceRenderer extends EncoderCellRenderer{
    public FlipDataSourceRenderer() {
        super(new FlipWrapper());
    }
}
