import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PermainanManyBreaks extends JPanel implements KeyListener, ActionListener {
    private boolean mulai = false;
    private int skor = 0;
    private Timer timer;
    private int delay = 8;

    private int pemainX = 310;
    private int bolaPosX = 120;
    private int bolaPosY = 350;
    private int bolaDirX = -1;
    private int bolaDirY = -2;

    private PembuatBlok pembuatBlok;

    public PermainanManyBreaks() {
        pembuatBlok = new PembuatBlok(5, 10);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        super.paint(g);
       
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        pembuatBlok.gambar((Graphics2D) g);


        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

  
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Skor: " + skor, 560, 30);

    
        g.setColor(Color.green);
        g.fillRect(pemainX, 550, 100, 8);


        g.setColor(Color.yellow);
        g.fillOval(bolaPosX, bolaPosY, 20, 20);


        if (bolaPosY > 570) {
            mulai = false;
            bolaDirX = 0;
            bolaDirY = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Skor: " + skor, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Tekan Enter untuk Mulai Ulang", 230, 350);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (mulai) {
            if (new Rectangle(bolaPosX, bolaPosY, 20, 20).intersects(new Rectangle(pemainX, 550, 100, 8))) {
                bolaDirY = -bolaDirY;
            }

            pembuatBlok.tabrakan(bolaPosX, bolaPosY, this);

            bolaPosX += bolaDirX;
            bolaPosY += bolaDirY;
            if (bolaPosX < 0) {
                bolaDirX = -bolaDirX;
            }
            if (bolaPosY < 0) {
                bolaDirY = -bolaDirY;
            }
            if (bolaPosX > 670) {
                bolaDirX = -bolaDirX;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (pemainX >= 600) {
                pemainX = 600;
            } else {
                pindahKanan();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (pemainX < 10) {
                pemainX = 10;
            } else {
                pindahKiri();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!mulai) {
                mulai = true;
                bolaPosX = 120;
                bolaPosY = 350;
                bolaDirX = -1;
                bolaDirY = -2;
                pemainX = 310;
                skor = 0;
                pembuatBlok = new PembuatBlok(5, 10);
                repaint();
            }
        }
    }

    public void pindahKanan() {
        mulai = true;
        pemainX += 20;
    }

    public void pindahKiri() {
        mulai = true;
        pemainX -= 20;
    }

    public void tambahSkor(int nilai) {
        skor += nilai;
    }

    public int getBolaDirX() {
        return bolaDirX;
    }

    public void setBolaDirX(int bolaDirX) {
        this.bolaDirX = bolaDirX;
    }

    public int getBolaDirY() {
        return bolaDirY;
    }

    public void setBolaDirY(int bolaDirY) {
        this.bolaDirY = bolaDirY;
    }

    public static void main(String[] args) {
        JFrame obj = new JFrame();
        PermainanManyBreaks permainan = new PermainanManyBreaks();
        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("Permainan Many Breaks");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(permainan);
    }
}

class PembuatBlok {
    public int[][] blok;
    public int lebarBlok;
    public int tinggiBlok;

    public PembuatBlok(int baris, int kolom) {
        blok = new int[baris][kolom];
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                blok[i][j] = 1;
            }
        }
        lebarBlok = 540 / kolom;
        tinggiBlok = 150 / baris;
    }

    public void gambar(Graphics2D g) {
        for (int i = 0; i < blok.length; i++) {
            for (int j = 0; j < blok[0].length; j++) {
                if (blok[i][j] > 0) {
                    g.setColor(Color.white);
                    g.fillRect(j * lebarBlok + 80, i * tinggiBlok + 50, lebarBlok, tinggiBlok);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * lebarBlok + 80, i * tinggiBlok + 50, lebarBlok, tinggiBlok);
                }
            }
        }
    }

    public void tabrakan(int bolaX, int bolaY, PermainanManyBreaks permainan) {
        for (int i = 0; i < blok.length; i++) {
            for (int j = 0; j < blok[0].length; j++) {
                if (blok[i][j] > 0) {
                    int blokX = j * lebarBlok + 80;
                    int blokY = i * tinggiBlok + 50;
                    int lebar = lebarBlok;
                    int tinggi = tinggiBlok;

                    Rectangle rect = new Rectangle(blokX, blokY, lebar, tinggi);
                    Rectangle bolaRect = new Rectangle(bolaX, bolaY, 20, 20);

                    if (bolaRect.intersects(rect)) {
                        blok[i][j] = 0;
                        permainan.tambahSkor(5);

                        if (bolaX + 19 <= rect.x || bolaX + 1 >= rect.x + rect.width) {
                            permainan.setBolaDirX(-permainan.getBolaDirX());
                        } else {
                            permainan.setBolaDirY(-permainan.getBolaDirY());
                        }
                    }
                }
            }
        }
    }
}
