package net.teslaworks.visualizer.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import org.dom4j.Element;

public abstract class Shape {

    // DMX
    public final String name;
    public final int channel;

    // Position
    public final int x;
    public final int y;
    public final double rotation;

    // Color
    public final int red;
    public final int blue;
    public final int green;

    // Make a new shape from a <shape> tag in configuration
    public static Shape makeShape(Element e) {
        // Switch based on "type" attribute in tag.
        String type = e.attributeValue("type");
        switch (type) {
        case "text":
            return new Text(e);
        case "rect":
            return new Rectangle(e);
        case "oval":
            return new Oval(e);
        case "line":
            return new Line(e);
        case "arc":
            return new Arc(e);
        case "fan": // Old name for Sunburst, here just in case.
        case "sunburst":
            return new Sunburst(e);
        case "megatree":
            return new Megatree(e);
        }
        throw new IllegalArgumentException("Unknown shape type: " + e.asXML());
    }

    // Set values common to all shapes
    protected Shape(Element e) {
        name = e.attributeValue("name");
        channel = Integer.parseInt(e.attributeValue("channel"));

        int _x, _y;
        double _rotation;

        try { _x = Integer.parseInt(e.attributeValue("x")); }
        catch (Exception r) { _x = 0; }

        try { _y = Integer.parseInt(e.attributeValue("y")); }
        catch (Exception r) { _y = 0; }

        try { _rotation = Double.parseDouble(e.attributeValue("rotation")); }
        catch (Exception r) { _rotation = 0; }

        x = _x;
        y = _y;
        rotation = _rotation;

        red = Integer.parseInt(e.attributeValue("red", "255"));
        blue = Integer.parseInt(e.attributeValue("blue", "255"));
        green = Integer.parseInt(e.attributeValue("green", "255"));
    }

    public String toString() {
        return name + " @channel " + channel;
    }

    // Draw this shape to the graphics2d instance
    public void paint(Graphics2D g2d, int[] channelValues) {
        g2d.setStroke(new BasicStroke(3));
        g2d.setPaint(new Color(red, green, blue, channelValues[channel]));
    }
}
