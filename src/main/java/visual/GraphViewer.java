package visual;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import model.SensorNode;
import model.SinkNode;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

public class GraphViewer {

    public static BufferedImage viewGraph(Graph g) {
        //init GraphViz Object
        HashMap<Vertex, MutableNode> vHash = new HashMap<>();
        MutableGraph mg = mutGraph("example1");
        for(Vertex v: g.getVertices()) {
            MutableNode vgv = mutNode(v.getAssignedNode().getName());
            if(v.getAssignedNode() instanceof SensorNode){
                if(((SensorNode) v.getAssignedNode()).isCritical()) vgv.attrs().add("shape","doublecircle");
                else vgv.attrs().add("shape","circle");
            }
            if(v.getAssignedNode() instanceof SinkNode) {
                vgv.attrs().add("shape","box");
            }

            mg.add(vgv);
            vHash.put(v,vgv);
        }
        for(Edge e: g.getEdges()) {
            vHash.get(e.getSource()).addLink(vHash.get(e.getDestination()));
        }

        return Graphviz.fromGraph(mg).render(Format.PNG).toImage();
    }

}
