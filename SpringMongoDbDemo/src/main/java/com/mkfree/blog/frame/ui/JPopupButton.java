package com.mkfree.blog.frame.ui;
import java.io.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

import com.sun.java.swing.plaf.windows.*;


public class JPopupButton
    extends JComboBox
    implements Serializable {
    public static final int TYPE_NORMAL = 0;

    public static final int TYPE_WITH_RIGHT_TOGGLE = 1;

    private int style = -1;

    private int actionIndex = -1;

    private JPopupMenu popup = null;

    private boolean mustRefresh = false;

    private JButton bttLeft;

    private JButton bttRight;

    private PopupButtonListener listener = new PopupButtonListener();

    public class UpBorder
        extends AbstractBorder {
        int thickness = 1;
        public void paintBorder(Component c, Graphics g, int x, int y,
                                int width, int height) {
            g.setColor(Color.white);
            g.drawLine(0, 0, width - 1, 0);
            g.drawLine(0, 0, 0, height - 1);
            g.setColor(Color.gray);
            g.drawLine(width - 1, 0, width - 1, height);
            g.drawLine(0, height - 1, width, height - 1);
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(thickness, thickness, thickness, thickness);
        }
    }

    public class DownBorder
        extends AbstractBorder {
        int thickness = 1;
        public void paintBorder(Component c, Graphics g, int x, int y,
                                int width, int height) {
            g.setColor(Color.gray);
            g.drawLine(0, 0, width - 1, 0);
            g.drawLine(0, 0, 0, height - 1);
            g.setColor(Color.white);
            g.drawLine(width - 1, 0, width - 1, height);
            g.drawLine(0, height - 1, width, height - 1);
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(thickness, thickness, thickness, thickness);
        }
    }

    private class PopupButtonListener
        implements MouseListener, PopupMenuListener {
        UpBorder upBorder = new UpBorder();
        DownBorder downBorder = new DownBorder();
        public void mouseClicked(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            if (!JPopupButton.this.isEnabled()) {
                return;
            }
            if (e.getSource() == bttLeft) {
                bttLeft.setBorder(downBorder);
                bttRight.setBorder(downBorder);
                if (getStyle() == TYPE_NORMAL) {
                    JPopupButton.this.showPopupMenu();
                }
                else if (actionIndex != -1
                         && actionIndex < popup.getSubElements().length) {
                    AbstractButton btt = (AbstractButton) (popup.getSubElements()
                        [actionIndex].getComponent());
                    btt.doClick();
                }
            }
            else {
                bttLeft.setBorder(upBorder);
                bttRight.setBorder(downBorder);
                bttRight.setSelected(true);
                JPopupButton.this.showPopupMenu();
            }
        }

        public void mouseReleased(MouseEvent e) {
            bttRight.setSelected(false);
        }

        public void mouseEntered(MouseEvent e) {
            if (!JPopupButton.this.isEnabled()) {
                return;
            }
            if (!JPopupButton.this.popup.isShowing()) {
                bttLeft.setBorder(upBorder);
                bttRight.setBorder(upBorder);
            }
        }

        public void mouseExited(MouseEvent e) {
            if (!JPopupButton.this.isEnabled()) {
                return;
            }

            if (!JPopupButton.this.popup.isShowing()) {
                bttLeft.setBorder(null);
                bttRight.setBorder(null);
            }
        }

        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        }

        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            if (!JPopupButton.this.isEnabled()) {
                return;
            }

            bttLeft.setBorder(null);
            bttRight.setBorder(null);
        }

        public void popupMenuCanceled(PopupMenuEvent e) {
        }
    }

    public JPopupButton() {
        this(TYPE_NORMAL);
    }

    public JPopupButton(int style) {
        this(style, null);
    }

    public JPopupButton(int style, String text) {
        this(style, text, null);
    }

    public JPopupButton(int style, String text, Icon icon) {
        this(style, text, icon,  new JPopupMenu());
    }

    public JPopupButton(int style, String text, Icon icon, JPopupMenu popup) {
        createButtons();
        setText(text);
        setIcon(icon);
        setPopup(popup);
        setStyle(style);
    }

    protected void showPopupMenu() {
        if (popup == null) {
            return;
        }
        popup.show(this, 0, this.getHeight());
    }

    protected void createButtons() {
        if (bttLeft == null) {
            bttLeft = new JButton(){
                public void setUI(ButtonUI ui) {
                    super.setUI(ui);
                }
            };
            bttLeft.setMargin(new Insets(0, 0, 0, 0));
        }
        if (bttRight == null) {
            bttRight = new JButton() {
                public void setUI(ButtonUI ui) {
                    super.setUI(ui);
                }
                public void paint(Graphics g) {
                    super.paint(g);
                    Polygon p = new Polygon();
                    int w = getWidth();
                    int y = (getHeight() - 4) / 2;
                    int x = (w - 6) / 2;
                    if (isSelected()) {
                        x += 1;
                    }
                    p.addPoint(x, y);
                    p.addPoint(x + 3, y + 3);
                    p.addPoint(x + 6, y);
                    g.fillPolygon(p);
                    g.drawPolygon(p);
                }
            };
            bttRight.setUI(new BasicButtonUI());
            bttLeft.setMargin(new Insets(0, 0, 0, 0));
        }
    }

    protected void refreshUI() {
        if (!mustRefresh) {
            return;
        }
        super.removeAll();
        this.setBorder(null);
        this.setLayout(new BorderLayout());
        this.add(bttLeft, BorderLayout.CENTER);
        if (style == TYPE_WITH_RIGHT_TOGGLE) {
            this.add(bttRight, BorderLayout.EAST);
        }
        bttLeft.setFocusable(false);
        bttLeft.setBorder(null);
        bttLeft.addMouseListener(listener);
        bttRight.setFocusable(false);
        bttRight.setPreferredSize(new Dimension(13, 1));
        bttRight.setBorder(null);
        bttRight.addMouseListener(listener);
        this.doLayout();
    }

    public void setStyle(int style) {
        if (this.style != style) {
            mustRefresh = true;
        }
        this.style = style;
        refreshUI();
    }

    public int getStyle() {
        return style;
    }

    public void setText(String text) {
        bttLeft.setText(text);
    }

    public String getText() {
        return bttLeft.getText();
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        bttLeft.setEnabled(enabled);
        bttRight.setEnabled(enabled);
    }

    public void setIcon(Icon icon) {
        bttLeft.setIcon(icon);
    }

    public Icon getIcon() {
        return bttLeft.getIcon();
    }

    public void setActionSameAsPopup(int index) {
        this.actionIndex = index;
    }

    public int getActionSameAsPopup() {
        return actionIndex;
    }

    public void setPopup(JPopupMenu pop) {
        if (this.popup != null) {
            popup.removePopupMenuListener(listener);
        }
        this.popup = pop;
        popup.removePopupMenuListener(listener);
        popup.addPopupMenuListener(listener);
    }

    public JPopupMenu getPopup() {
        return popup;
    }

}
