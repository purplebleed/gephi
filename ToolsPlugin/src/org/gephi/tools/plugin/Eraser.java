/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gephi.tools.plugin;

import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.Node;
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
    private Color color;
    private float size;

    public Eraser() {
        //Default settings
        color = new Color(153, 153, 153);//Default gray of nodes
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
        listeners[0] = new MouseClickEventListener() {

            public void mouseClick(int[] positionViewport, float[] position3d) {
                color = new Color(153, 153, 153);
                size = eraserpanel.getEraserSize();
                GraphController gc = Lookup.getDefault().lookup(GraphController.class);
                Graph graph = gc.getModel().getGraph();
                Node node = gc.getModel().factory().newNode();
                node.getNodeData().setX(position3d[0]);
                node.getNodeData().setY(position3d[1]);
                node.getNodeData().setSize(size);
                node.getNodeData().setR(color.getRed() / 255f);
                node.getNodeData().setG(color.getGreen() / 255f);
                node.getNodeData().setB(color.getBlue() / 255f);
                graph.addNode(node);
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
                return NbBundle.getMessage(NodePencil.class, "Eraser.name");
            }

            public Icon getIcon() {
                return new ImageIcon(getClass().getResource("/org/gephi/tools/plugin/resources/eraser.png"));
            }

            public String getDescription() {
                return NbBundle.getMessage(NodePencil.class, "Eraser.description");
            }

            public int getPosition() {
                return 220;
            }
        };
    }

    public ToolSelectionType getSelectionType() {
        return ToolSelectionType.NONE;
    }
}
