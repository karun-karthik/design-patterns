package com.dsahub.patterns.proxy.virtual;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

interface Icon {
    int getIconWidth();
    int getIconHeight();
    void paintIcon(final Component c, Graphics g, int x, int y);
}

// (Heavy object)
class ImageIconReal implements Icon {

    private ImageIcon imageIcon;

    public ImageIconReal(URL imageURL) {
        imageIcon = new ImageIcon(imageURL); // EXPENSIVE
    }

    @Override
    public int getIconWidth() {
        return imageIcon.getIconWidth();
    }

    @Override
    public int getIconHeight() {
        return imageIcon.getIconHeight();
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        imageIcon.paintIcon(c, g, x, y);
    }
}

// VIRTUAL PROXY
class ImageProxy implements Icon {

    private ImageIconReal imageIcon;  // Real subject (lazy)
    private URL imageURL;
    private boolean retrieving = false;

    public ImageProxy(URL url) {
        this.imageURL = url;
    }

    @Override
    public int getIconWidth() {
        if (imageIcon != null) {
            return imageIcon.getIconWidth();
        }
        return 800; // placeholder size
    }

    @Override
    public int getIconHeight() {
        if (imageIcon != null) {
            return imageIcon.getIconHeight();
        }
        return 600; // placeholder size
    }

    @Override
    public void paintIcon(final Component c, Graphics g, int x, int y) {
        if (imageIcon != null) {
            // Real image available
            imageIcon.paintIcon(c, g, x, y);
        } else {
            // Proxy behavior
            g.drawString("Loading image, please wait...", x + 200, y + 300);

            if (!retrieving) {
                retrieving = true;
                // Background thread loads image lazily
                new Thread(() -> {
                    try {
                        imageIcon = new ImageIconReal(imageURL);
                        c.repaint();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }
}


// CLIENT
class ImageProxyTestDrive {

    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame("HFDP Virtual Proxy - Image Loader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ✅ Any large image URL
        URL imageURL = new URL(
            "https://picsum.photos/536/354"
        );

        // PROXY used instead of real image
        Icon icon = new ImageProxy(imageURL);

        JLabel label = new JLabel();
        label.setIcon(new javax.swing.ImageIcon() {
            @Override
            public int getIconWidth() {
                return icon.getIconWidth();
            }

            @Override
            public int getIconHeight() {
                return icon.getIconHeight();
            }

            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                icon.paintIcon(c, g, x, y);
            }
        });

        frame.getContentPane().add(label);
        frame.setSize(600, 1200);
        frame.setVisible(true);
    }
}