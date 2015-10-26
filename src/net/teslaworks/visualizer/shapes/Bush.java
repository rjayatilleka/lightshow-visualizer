package net.teslaworks.visualizer.shapes;

import org.dom4j.Element;

import java.awt.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Bush extends Shape {

    private static Color TRANSPARENT = new Color(0, 0, 0, 0);

    // Shape size
    public final int width;
    public final int height;

    // Secondary colors
    public final int red2, green2, blue2;
    public final boolean dualColor;

    // Set values unique to rectangles
    protected Bush(Element e) {
        super(e);
        width = Integer.parseInt(e.attributeValue("width"));
        height = Integer.parseInt(e.attributeValue("height"));
        dualColor = Boolean.parseBoolean(e.attributeValue("dual"));

        red2 = Integer.parseInt(e.attributeValue("red2"));
        green2 = Integer.parseInt(e.attributeValue("green2"));
        blue2 = Integer.parseInt(e.attributeValue("blue2"));
    }

    private TexturePaint makeTexturePaint(int[] channelValues) {
        BufferedImage image = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setPaint(new Color(red, green, blue, channelValues[channel]));
        g2d.fill(new Rectangle(0, 0, 4, 4));
        g2d.fill(new Rectangle(4, 4, 4, 4));

        g2d.setPaint(dualColor
                ? new Color(red2, green2, blue2, channelValues[channel + 1])
                : TRANSPARENT);
        g2d.fill(new Rectangle(4, 0, 4, 4));
        g2d.fill(new Rectangle(0, 4, 4, 4));

        TexturePaint paint = new TexturePaint(image, new Rectangle(0, 0, 8, 8));
        g2d.dispose();

        return paint;
    }

    // Draw this rectangle
    public void paintWork(Graphics2D g2d, int[] channelValues) {
        g2d.setPaint(makeTexturePaint(channelValues));
        g2d.fill(new java.awt.Rectangle(x, y, width, height));
    }
}