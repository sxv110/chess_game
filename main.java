import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.*;

class myFrame {
    Font chessFont;
    boolean turn = false;
    int pressedA;
    int pressedB;
    Color offWhite = Color.decode("#d9d7ce");
    JTextField[][] boxes = {
            {
                    new JTextField("t"), new JTextField("j"), new JTextField("n"), new JTextField("w"),
                    new JTextField("l"), new JTextField("n"),
                    new JTextField("j"), new JTextField("t")
            },
            {
                    new JTextField("o"), new JTextField("o"), new JTextField("o"), new JTextField("o"),
                    new JTextField("o"),
                    new JTextField("o"), new JTextField("o"), new JTextField("o")
            },
            {
                    new JTextField(""), new JTextField(""), new JTextField(""), new JTextField(""), new JTextField(""),
                    new JTextField(""),
                    new JTextField(""), new JTextField("")
            },
            {
                    new JTextField(""), new JTextField(""), new JTextField(""), new JTextField(""), new JTextField(""),
                    new JTextField(""),
                    new JTextField(""), new JTextField("")
            },
            {
                    new JTextField(""), new JTextField(""), new JTextField(""), new JTextField(""), new JTextField(""),
                    new JTextField(""),
                    new JTextField(""), new JTextField("")
            },
            {
                    new JTextField(""), new JTextField(""), new JTextField(""), new JTextField(""), new JTextField(""),
                    new JTextField(""),
                    new JTextField(""), new JTextField("")
            },
            {
                    new JTextField("oa"), new JTextField("oa"), new JTextField("oa"), new JTextField("oa"),
                    new JTextField("oa"),
                    new JTextField("oa"), new JTextField("oa"), new JTextField("oa")
            },
            {
                    new JTextField("ta"), new JTextField("ja"), new JTextField("na"), new JTextField("wa"),
                    new JTextField("la"), new JTextField("na"),
                    new JTextField("ja"), new JTextField("ta")
            }
    };;

    myFrame() {
        // create font
        chessFont = createChessFont();
        int BOX_SIZE = 50;
        JFrame frame = new JFrame();
        frame.setSize(400, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("Chess Game");
        frame.setLayout(null);
        JPanel panel = createChessBoardPanel(BOX_SIZE);
        frame.add(panel);
        panel.setBounds(0, 0, 400, 400);
        panel.setLayout(null);
        initializeChessBoard(BOX_SIZE, panel);
    }

    private Font createChessFont() {
        try {
            InputStream is = getClass().getResourceAsStream("chess.ttf");
            return Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private JPanel createChessBoardPanel(int boxSize) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.gray);
        return panel;
    }

    private void initializeChessBoard(int boxSize, JPanel panel) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                final int a = x;
                final int b = y;
                boxes[x][y].setFont(chessFont);
                boxes[x][y].setEditable(false);
                boxes[x][y].setForeground(Color.black);
                boxes[x][y].setHorizontalAlignment(JTextField.CENTER);
                boxes[x][y].setFont(boxes[x][y].getFont().deriveFont(Font.PLAIN, 40f));
                boxes[x][y].setBackground(Color.white);
                boxes[x][y].setBounds(y * boxSize, x * boxSize, boxSize, boxSize);
                panel.add(boxes[x][y]);
                boxes[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                setBoxBackgroundColor(x, y);
                setInitialPiece(x, y);
                boxes[x][y].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        handleMousePress(a, b);
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (boxes[a][b].getBackground().equals(Color.decode("#94d6a6"))) {
                            boxes[a][b].setBackground(Color.decode("#94d6a6").darker());
                        } else if (boxes[a][b].getBackground().equals(Color.decode("#de5b3e"))) {
                            boxes[a][b].setBackground(Color.decode("#de5b3e").darker());
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (boxes[a][b].getBackground().equals(Color.decode("#94d6a6").darker())) {
                            boxes[a][b].setBackground(Color.decode("#94d6a6"));
                        } else if (boxes[a][b].getBackground().equals(Color.decode("#de5b3e").darker())) {
                            boxes[a][b].setBackground(Color.decode("#de5b3e"));
                        }
                    }
                });
            }
        }
    }

    private void setBoxBackgroundColor(int x, int y) {
        if ((x % 2 == 1 && y % 2 == 0) || (x % 2 == 0 && y % 2 == 1)) {
            boxes[x][y].setBackground(Color.decode("#785532"));
        } else {
            boxes[x][y].setBackground(Color.decode("#ccab8b"));
        }
    }

    private void setInitialPiece(int x, int y) {
        if (boxes[x][y].getText().length() == 2) {
            boxes[x][y].setForeground(offWhite);
            boxes[x][y].setText(boxes[x][y].getText().charAt(0) + "");
        }
    }

    private void handleMousePress(int a, int b) {
        if (boxes[a][b].getBackground().equals(Color.decode("#94d6a6").darker())) {
            movePiece(a, b);
            turn = !turn;
        } else if (boxes[a][b].getBackground().equals(Color.decode("#de5b3e").darker())) {
            movePiece(a, b);
            turn = !turn;
        } else {
            resetBoardHighlights();
            if (turn && boxes[a][b].getForeground().equals(Color.black)) {
                highlightPossibleMoves(a, b, true);
            } else if (!turn && boxes[a][b].getForeground().equals(offWhite)) {
                highlightPossibleMoves(a, b, false);
            }
        }
    }

    private void movePiece(int a, int b) {
        boxes[a][b].setText(boxes[pressedA][pressedB].getText());
        boxes[pressedA][pressedB].setText("");
        setPieceColor(a, b);
        resetBoardHighlights();
    }

    private void setPieceColor(int a, int b) {
        if (!turn) {
            boxes[a][b].setForeground(offWhite);
        } else {
            boxes[a][b].setForeground(Color.black);
        }
    }

    private void resetBoardHighlights() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                setBoxBackgroundColor(x, y);
                if (boxes[x][y].getForeground() != Color.black) {
                    boxes[x][y].setForeground(offWhite);
                }
            }
        }
    }

    private void highlightPossibleMoves(int a, int b, boolean isWhiteTurn) {
        pressedA = a;
        pressedB = b;
        if (boxes[a][b].getText().equals("o")) {
            highlightPawnMoves(a, b, isWhiteTurn);
        }
        if (boxes[a][b].getText().equals("t") || boxes[a][b].getText().equals("w")) {
            highlightRookMoves(a, b, isWhiteTurn);
        }
        if (boxes[a][b].getText().equals("n") || boxes[a][b].getText().equals("w")) {
            highlightDiagonalMoves(a, b, isWhiteTurn);
        }
        if (boxes[a][b].getText().equals("j")) {
            highlightKnightMoves(a, b, isWhiteTurn);
        }
        if (boxes[a][b].getText().equals("l")) {
            highlightKingMoves(a, b, isWhiteTurn);
        }
    }

    private void highlightPawnMoves(int a, int b, boolean isWhiteTurn) {
        int a1 = a + (isWhiteTurn ? 1 : -1);
        if (isWhiteTurn) {
            boxes[a1][b].setBackground(Color.black);
            if(boxes[a1][b].getText().equals("")){
                boxes[a1][b].setBackground(Color.decode("#94d6a6"));
            }
            if (a == 1) {
                int a2 = a + 2;
                highlightMove(a2, b, isWhiteTurn);
            }
            highlightDiagonalPawnMoves(a, b, isWhiteTurn);
        } else {
            boxes[a1][b].setBackground(Color.decode("#94d6a6"));
            if (a == 6) {
                int a2 = a - 2;
                highlightMove(a2, b, isWhiteTurn);
            }
            highlightDiagonalPawnMoves(a, b, isWhiteTurn);
        }
    }

    private void highlightDiagonalPawnMoves(int a, int b, boolean isWhiteTurn) {
        int a3 = a + (isWhiteTurn ? 1 : -1);
        if (b != 0) {
            int b3 = b - 1;
            if (!boxes[a3][b3].getText().equals("")) {
                if (isWhiteTurn && boxes[a3][b3].getForeground().equals(offWhite)) {
                    boxes[a3][b3].setBackground(Color.decode("#de5b3e"));
                } else if (!isWhiteTurn && boxes[a3][b3].getForeground().equals(Color.black)) {
                    boxes[a3][b3].setBackground(Color.decode("#de5b3e"));
                }
            }
        }
        int b4 = b + 1;
        if (!boxes[a3][b4].getText().equals("")) {
            if (isWhiteTurn && boxes[a3][b4].getForeground().equals(offWhite)) {
                boxes[a3][b4].setBackground(Color.decode("#de5b3e"));
            } else if (!isWhiteTurn && boxes[a3][b4].getForeground().equals(Color.black)) {
                boxes[a3][b4].setBackground(Color.decode("#de5b3e"));
            }
        }
    }

    private void highlightRookMoves(int a, int b, boolean isWhiteTurn) {
        highlightHorizontalMoves(a, b, isWhiteTurn);
        highlightVerticalMoves(a, b, isWhiteTurn);
    }

    private void highlightBishopMoves(int a, int b, boolean isWhiteTurn) {
        highlightDiagonalMoves(a, b, isWhiteTurn);
    }

    private void highlightHorizontalMoves(int a, int b, boolean isWhiteTurn) {

        for (int i = b - 1; i >= 0; i--) {
            if (boxes[a][i].getText().equals("")) {
                highlightMove(a, i, isWhiteTurn);
            } else if (isWhiteTurn && boxes[a][i].getForeground().equals(offWhite)) {
                highlightMove(a, i, isWhiteTurn);
                break;
            } else if (!isWhiteTurn && boxes[a][i].getForeground().equals(Color.black)) {
                highlightMove(a, i, isWhiteTurn);
                break;
            } else {
                break;
            }
        }

        for (int i = b + 1; i <= 7; i++) {
            if (boxes[a][i].getText().equals("")) {
                highlightMove(a, i, isWhiteTurn);
            } else if (isWhiteTurn && boxes[a][i].getForeground().equals(offWhite)) {
                highlightMove(a, i, isWhiteTurn);
                break;
            } else if (!isWhiteTurn && boxes[a][i].getForeground().equals(Color.black)) {
                highlightMove(a, i, isWhiteTurn);
                break;
            } else {
                break;
            }
        }
    }

    private void highlightVerticalMoves(int a, int b, boolean isWhiteTurn) {
        for (int i = a - 1; i >= 0; i--) {
            if (boxes[i][b].getText().equals("")) {
                highlightMove(i, b, isWhiteTurn);
            } else if (isWhiteTurn && boxes[i][b].getForeground().equals(offWhite)) {
                highlightMove(i, b, isWhiteTurn);
                break;
            } else if (!isWhiteTurn && boxes[i][b].getForeground().equals(Color.black)) {
                highlightMove(i, b, isWhiteTurn);
                break;
            } else {
                break;
            }
        }
        for (int i = a + 1; i <= 7; i++) {
            if (boxes[i][b].getText().equals("")) {
                highlightMove(i, b, isWhiteTurn);
            } else if (isWhiteTurn && boxes[i][b].getForeground().equals(offWhite)) {
                highlightMove(i, b, isWhiteTurn);
                break;
            } else if (!isWhiteTurn && boxes[i][b].getForeground().equals(Color.black)) {
                highlightMove(i, b, isWhiteTurn);
                break;
            } else {
                break;
            }
        }
    }

    private void highlightDiagonalMoves(int a, int b, boolean isWhiteTurn) {
        for (int i = 1; i < 8; i++) {
            if (a - i >= 0 && b - i >= 0) {
                if (boxes[a - i][b - i].getText().equals("")) {
                    highlightMove(a - i, b - i, isWhiteTurn);
                } else if (isWhiteTurn && boxes[a - i][b - i].getForeground().equals(offWhite)) {
                    highlightMove(a - i, b - i, isWhiteTurn);
                    break;
                } else if (!isWhiteTurn && boxes[a - i][b - i].getForeground().equals(Color.black)) {
                    highlightMove(a - i, b - i, isWhiteTurn);
                    break;
                } else {
                    break;
                }
            }
        }
        for (int i = 1; i < 8; i++) {
            if (a + i <= 7 && b + i <= 7) {
                if (boxes[a + i][b + i].getText().equals("")) {
                    highlightMove(a + i, b + i, isWhiteTurn);
                } else if (isWhiteTurn && boxes[a + i][b + i].getForeground().equals(offWhite)) {
                    highlightMove(a + i, b + i, isWhiteTurn);
                    break;
                } else if (!isWhiteTurn && boxes[a + i][b + i].getForeground().equals(Color.black)) {
                    highlightMove(a + i, b + i, isWhiteTurn);
                    break;
                } else {
                    break;
                }
            }
        }
        for (int i = 1; i < 8; i++) {
            if (a + i <= 7 && b - i >= 0) {
                if (boxes[a + i][b - i].getText().equals("")) {
                    highlightMove(a + i, b - i, isWhiteTurn);
                } else if (isWhiteTurn && boxes[a + i][b - i].getForeground().equals(offWhite)) {
                    highlightMove(a + i, b - i, isWhiteTurn);
                    break;
                } else if (!isWhiteTurn && boxes[a + i][b - i].getForeground().equals(Color.black)) {
                    highlightMove(a + i, b - i, isWhiteTurn);
                    break;
                } else {
                    break;
                }
            }
        }
        for (int i = 1; i < 8; i++) {
            if (a - i >= 0 && b + i <= 7) {
                if (boxes[a - i][b + i].getText().equals("")) {
                    highlightMove(a - i, b + i, isWhiteTurn);
                } else if (isWhiteTurn && boxes[a - i][b + i].getForeground().equals(offWhite)) {
                    highlightMove(a - i, b + i, isWhiteTurn);
                    break;
                } else if (!isWhiteTurn && boxes[a - i][b + i].getForeground().equals(Color.black)) {
                    highlightMove(a - i, b + i, isWhiteTurn);
                    break;
                } else {
                    break;
                }
            }
        }
    }

    private void highlightKnightMoves(int a, int b, boolean isWhiteTurn) {

        if (a - 2 >= 0 && b - 1 >= 0) {
            if (boxes[a - 2][b - 1].getText().equals("")) {
                highlightMove(a - 2, b - 1, isWhiteTurn);
            } else if (isWhiteTurn && boxes[a - 2][b - 1].getForeground().equals(offWhite)) {
                highlightMove(a - 2, b - 1, isWhiteTurn);
            } else if (!isWhiteTurn && boxes[a - 2][b - 1].getForeground().equals(Color.black)) {
                highlightMove(a - 2, b - 1, isWhiteTurn);
            }
        }
        if (a - 2 >= 0 && b + 1 <= 7) {
            if (boxes[a - 2][b - 1].getText().equals("")) {
                highlightMove(a - 2, b + 1, isWhiteTurn);
            } else if (isWhiteTurn && boxes[a - 2][b + 1].getForeground().equals(offWhite)) {
                highlightMove(a - 2, b + 1, isWhiteTurn);
            } else if (!isWhiteTurn && boxes[a - 2][b + 1].getForeground().equals(Color.black)) {
                highlightMove(a - 2, b + 1, isWhiteTurn);
            }
        }
        if (a + 2 <= 7 && b - 1 >= 0) {
            if (boxes[a + 2][b - 1].getText().equals("")) {
                highlightMove(a + 2, b - 1, isWhiteTurn);
            } else if (isWhiteTurn && boxes[a + 2][b - 1].getForeground().equals(offWhite)) {
                highlightMove(a + 2, b - 1, isWhiteTurn);
            } else if (!isWhiteTurn && boxes[a + 2][b - 1].getForeground().equals(Color.black)) {
                highlightMove(a + 2, b - 1, isWhiteTurn);
            }
        }
        if (a + 2 <= 7 && b + 1 <= 7) {
            if (boxes[a + 2][b + 1].getText().equals("")) {
                highlightMove(a + 2, b + 1, isWhiteTurn);
            } else if (isWhiteTurn && boxes[a + 2][b + 1].getForeground().equals(offWhite)) {
                highlightMove(a + 2, b + 1, isWhiteTurn);
            } else if (!isWhiteTurn && boxes[a + 2][b + 1].getForeground().equals(Color.black)) {
                highlightMove(a + 2, b + 1, isWhiteTurn);
            }
        }
        if (a - 1 >= 0 && b - 2 >= 0) {
            if (boxes[a - 1][b - 2].getText().equals("")) {
                highlightMove(a - 1, b - 2, isWhiteTurn);
            } else if (isWhiteTurn && boxes[a - 1][b - 2].getForeground().equals(offWhite)) {
                highlightMove(a - 1, b - 2, isWhiteTurn);
            } else if (!isWhiteTurn && boxes[a - 1][b - 2].getForeground().equals(Color.black)) {
                highlightMove(a - 1, b - 2, isWhiteTurn);
            }
        }
        if (a - 1 >= 0 && b + 2 <= 7) {
            if (boxes[a - 1][b - 2].getText().equals("")) {
                highlightMove(a - 1, b + 2, isWhiteTurn);
            } else if (isWhiteTurn && boxes[a - 1][b + 2].getForeground().equals(offWhite)) {
                highlightMove(a - 1, b + 2, isWhiteTurn);
            } else if (!isWhiteTurn && boxes[a - 1][b + 2].getForeground().equals(Color.black)) {
                highlightMove(a - 1, b + 2, isWhiteTurn);
            }
        }
        if (a + 1 <= 7 && b - 2 >= 0) {
            if (boxes[a + 1][b - 2].getText().equals("")) {
                highlightMove(a + 1, b - 2, isWhiteTurn);
            } else if (isWhiteTurn && boxes[a + 1][b - 2].getForeground().equals(offWhite)) {
                highlightMove(a + 1, b - 2, isWhiteTurn);
            } else if (!isWhiteTurn && boxes[a + 1][b - 2].getForeground().equals(Color.black)) {
                highlightMove(a + 1, b - 2, isWhiteTurn);
            }
        }
        if (a + 1 <= 7 && b + 2 <= 7) {
            if (boxes[a + 1][b + 2].getText().equals("")) {
                highlightMove(a + 1, b + 2, isWhiteTurn);
            } else if (isWhiteTurn && boxes[a + 1][b + 2].getForeground().equals(offWhite)) {
                highlightMove(a + 1, b + 2, isWhiteTurn);
            } else if (!isWhiteTurn && boxes[a + 1][b + 2].getForeground().equals(Color.black)) {
                highlightMove(a + 1, b + 2, isWhiteTurn);
            }
        }

    }

    private void highlightKingMoves(int a, int b, boolean isWhiteTurn) {
        int a1 = a - 1;
        int b1 = b;
        if (a1 >= 0 && b1 >= 0) {
            if (boxes[a1][b1].getText().equals("")) {
                highlightMove(a1, b1, isWhiteTurn);
            } else if (isWhiteTurn && boxes[a1][b1].getForeground().equals(offWhite)) {
                highlightMove(a1, b1, isWhiteTurn);
            } else if (!isWhiteTurn && boxes[a1][b1].getForeground().equals(Color.black)) {
                highlightMove(a1, b1, isWhiteTurn);
            }
        }
        a1 = a + 1;
        b1 = b;
        if (a1 <= 7 && b1 >= 0) {
            if (boxes[a1][b1].getText().equals("")) {
                highlightMove(a1, b1, isWhiteTurn);
            } else if (isWhiteTurn && boxes[a1][b1].getForeground().equals(offWhite)) {
                highlightMove(a1, b1, isWhiteTurn);
            } else if (!isWhiteTurn && boxes[a1][b1].getForeground().equals(Color.black)) {
                highlightMove(a1, b1, isWhiteTurn);
            }
        }
        a1 = a;
        b1 = b - 1;
        if (a1 >= 0 && b1 >= 0) {
            if (boxes[a1][b1].getText().equals("")) {
                highlightMove(a1, b1, isWhiteTurn);
            } else if (isWhiteTurn && boxes[a1][b1].getForeground().equals(offWhite)) {
                highlightMove(a1, b1, isWhiteTurn);
            } else if (!isWhiteTurn && boxes[a1][b1].getForeground().equals(Color.black)) {
                highlightMove(a1, b1, isWhiteTurn);
            }
        }
        a1 = a;
        b1 = b + 1;
        if (a1 >= 0 && b1 <= 7) {
            if (boxes[a1][b1].getText().equals("")) {
                highlightMove(a1, b1, isWhiteTurn);
            } else if (isWhiteTurn && boxes[a1][b1].getForeground().equals(offWhite)) {
                highlightMove(a1, b1, isWhiteTurn);
            } else if (!isWhiteTurn && boxes[a1][b1].getForeground().equals(Color.black)) {
                highlightMove(a1, b1, isWhiteTurn);
            }
        }
        a1 = a - 1;
        b1 = b - 1;
        if (a1 >= 0 && b1 >= 0) {
            if (boxes[a1][b1].getText().equals("")) {
                highlightMove(a1, b1, isWhiteTurn);
            } else if (isWhiteTurn && boxes[a1][b1].getForeground().equals(offWhite)) {
                highlightMove(a1, b1, isWhiteTurn);
            } else if (!isWhiteTurn && boxes[a1][b1].getForeground().equals(Color.black)) {
                highlightMove(a1, b1, isWhiteTurn);
            }
        }
        a1 = a - 1;
        b1 = b;
        if (a1 >= 0 && b1 >= 0) {
            if (boxes[a1][b1].getText().equals("")) {
                highlightMove(a1, b1, isWhiteTurn);
            } else if (isWhiteTurn && boxes[a1][b1].getForeground().equals(offWhite)) {
                highlightMove(a1, b1, isWhiteTurn);
            } else if (!isWhiteTurn && boxes[a1][b1].getForeground().equals(Color.black)) {
                highlightMove(a1, b1, isWhiteTurn);
            }
        }
        a1 = a - 1;
        b1 = b + 1;
        if (a1 >= 0 && b1 <= 7) {
            if (boxes[a1][b1].getText().equals("")) {
                highlightMove(a1, b1, isWhiteTurn);
            } else if (isWhiteTurn && boxes[a1][b1].getForeground().equals(offWhite)) {
                highlightMove(a1, b1, isWhiteTurn);
            } else if (!isWhiteTurn && boxes[a1][b1].getForeground().equals(Color.black)) {
                highlightMove(a1, b1, isWhiteTurn);
            }
        }
        a1 = a + 1;
        b1 = b - 1;
        if (a1 <= 7 && b1 >= 0) {
            if (boxes[a1][b1].getText().equals("")) {
                highlightMove(a1, b1, isWhiteTurn);
            } else if (isWhiteTurn && boxes[a1][b1].getForeground().equals(offWhite)) {
                highlightMove(a1, b1, isWhiteTurn);
            } else if (!isWhiteTurn && boxes[a1][b1].getForeground().equals(Color.black)) {
                highlightMove(a1, b1, isWhiteTurn);
            }
        }
        a1 = a + 1;
        b1 = b;
        if (a1 <= 7 && b1 >= 0) {
            if (boxes[a1][b1].getText().equals("")) {
                highlightMove(a1, b1, isWhiteTurn);
            } else if (isWhiteTurn && boxes[a1][b1].getForeground().equals(offWhite)) {
                highlightMove(a1, b1, isWhiteTurn);
            } else if (!isWhiteTurn && boxes[a1][b1].getForeground().equals(Color.black)) {
                highlightMove(a1, b1, isWhiteTurn);
            }
        }
        a1 = a + 1;
        b1 = b + 1;
        if (a1 <= 7 && b1 <= 7) {
            if (boxes[a1][b1].getText().equals("")) {
                highlightMove(a1, b1, isWhiteTurn);
            } else if (isWhiteTurn && boxes[a1][b1].getForeground().equals(offWhite)) {
                highlightMove(a1, b1, isWhiteTurn);
            } else if (!isWhiteTurn && boxes[a1][b1].getForeground().equals(Color.black)) {
                highlightMove(a1, b1, isWhiteTurn);
            }
        }
    }

    private void highlightMove(int a, int b, boolean isWhiteTurn) {
        if (boxes[a][b].getText().equals("")) {
            boxes[a][b].setBackground(Color.decode("#94d6a6"));
        } else if (isWhiteTurn && boxes[a][b].getForeground().equals(offWhite)) {
            boxes[a][b].setBackground(Color.decode("#de5b3e"));
        } else if (!isWhiteTurn && boxes[a][b].getForeground().equals(Color.black)) {
            boxes[a][b].setBackground(Color.decode("#de5b3e"));
        }
    }
}

public class Main {
    public static void main(String[] args) {
        new myFrame();
    }
}
