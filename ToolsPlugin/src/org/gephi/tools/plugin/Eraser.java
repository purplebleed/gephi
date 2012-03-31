/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gephi.tools.plugin;

import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.gephi.graph.api.*;
import org.gephi.tools.spi.*;
import org.gephi.ui.tools.plugin.EraserPanel;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author purplebleed
 */
@ServiceProvider(service = Tool.class)
public class Eraser implements Tool {
    //Architecture
    private ToolEventListener[] listeners;
    private EraserPanel eraserpanel;
    //Settings
    private float size;

    public Eraser() {
        //Default settings
        size = 10f;
    }

    public void select() {
    }

    public void unselect() {
        listeners = null;
        eraserpanel = null;
    }

    public ToolEventListener[] getListeners() {
        listeners = new ToolEventListener[1];
        listeners[0] = new NodeClickEventListener() {

            public void clickNodes(Node[] nodes) {
                Node n = nodes[0];
                GraphController gc = Lookup.getDefault().lookup(GraphController.class);
                Graph graph = gc.getModel().getGraph();
                graph.removeNode(n);
           }
        };
        return listeners;
    }

    public ToolUI getUI() {
        return new ToolUI() {

            public JPanel getPropertiesBar(Tool tool) {
                eraserpanel = new EraserPanel();
                eraserpanel.setEraserSize(size);
                return eraserpanel;
            }

            public String getName() {
                return NbBundle.getMessage(Eraser.class, "Eraser.name");
            }

            public Icon getIcon() {
                return new ImageIcon(getClass().getResource("/org/gephi/tools/plugin/resources/eraser.png"));
            }

            public String getDescription() {
                return NbBundle.getMessage(Eraser.class, "Eraser.description");
            }

            public int getPosition() {
                return 220;
            }
        };
    }

    public ToolSelectionType getSelectionType() {
        return ToolSelectionType.SELECTION;
    }
}
