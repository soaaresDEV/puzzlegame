import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Felipe
 */
public class QuebraCabecaJogo extends javax.swing.JFrame {


    private final JButton[] botoes;
    private List<Integer> posicoes;
    private int posicaoVazia;
    private final Timer cronometroTimer;
    private int segundosPassados;
    private int numeroCliques;
    
    /**
     * Creates new form QuebraCabecaJogo
     */
  
    public QuebraCabecaJogo() {
        initComponents(); //inicializa os métodos
        
        //dá inicio ao array dos botões
        botoes = new JButton[] {
            b1, b2, b3, b4,
            b5, b6, b7, b8,
            b9, b10, b11, b12,
            b13, b14, b15, jButton16 
        };

        setTitle("Quebra-Cabeça");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        segundosPassados = 0;
        cronometroTimer = new Timer(1000, (ActionEvent e) -> {
            segundosPassados++;
            int minutos = segundosPassados / 60;
            int segundos = segundosPassados % 60;
            jLabel2.setText(String.format("%02d:%02d", minutos, segundos));
        });

        numeroCliques = 0;
        configurarJogo();
        configurarListeners();
        cronometroTimer.start();
    }

    private void incrementarCliques() {
        numeroCliques++;
        jLabel4.setText("" + numeroCliques);
    }

    private void configurarJogo() {
        posicoes = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            posicoes.add(i);
        }
        posicoes.add(0);
        posicaoVazia = 15;

        embaralharPecas();
    }

    private void configurarListeners() {
    for (int i = 0; i < botoes.length; i++) {
        final int pos = i; //copia o índice para uso na lambda expression
        botoes[i].addActionListener(e -> moverPeca(pos));
    }
}

    private void moverPeca(int posicaoClicada) {
        //verifica se a peça clicada é adjacente ao espaço vazio
        if (verificarPosicaoVizinha(posicaoClicada, posicaoVazia)) {
            Collections.swap(posicoes, posicaoClicada, posicaoVazia);

            //atualiza a nova posição do espaço vazio
            posicaoVazia = posicaoClicada;

            atualizarBotoes();

            //adiciona o contador de cliques
            incrementarCliques();

            //verifica se o jogador venceu
            if (verificarVitoria()) {
                cronometroTimer.stop(); // Para o cronômetro
                    JOptionPane.showMessageDialog(this,
                        "Parabéns! Você completou o nosso quebra-cabeça!\n" +
                        "Tempo: " + jLabel2.getText().trim() + "\n" +
                        "Jogadas: " + jLabel4.getText().trim(),
                        "Vitória!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


    private boolean verificarPosicaoVizinha(int pos1, int pos2) {
        int linha1 = pos1 / 4; 
        int col1 = pos1 % 4;  
        int linha2 = pos2 / 4;
        int col2 = pos2 % 4;

        return (linha1 == linha2 && Math.abs(col1 - col2) == 1) ||
               (col1 == col2 && Math.abs(linha1 - linha2) == 1);
    }

    private void atualizarBotoes() {
        for (int i = 0; i < botoes.length; i++) {
            int valor = posicoes.get(i);
            if (valor == 0) { 
                botoes[i].setText("");
                
                botoes[i].setBackground(new Color(239, 239, 239));
            } else {
                botoes[i].setText(String.valueOf(valor));
                
                botoes[i].setBackground(new Color(239, 239, 239));
            }
        }
    }

    private void embaralharPecas() {
        List<Integer> numeros = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            numeros.add(i);
        }

        Collections.shuffle(numeros);

        if (posicoes.size() != 16) {
            posicoes.clear();
            for(int i = 0; i < 16; i++) posicoes.add(0); 
        }

        int index = 0;
        for (int i = 0; i < 16; i++) {
            if (i == 15) { 
                posicoes.set(i, 0);
                posicaoVazia = i;
            } else {
                posicoes.set(i, numeros.get(index++)); 
            }
        }

        for (int k = 0; k < 200; k++) { 
            List<Integer> movimentosValidos = new ArrayList<>();
            //verifica movimentos possiveis no espaço que está vazio (cima, baixo, esquerda, direita)
            if (posicaoVazia - 4 >= 0) movimentosValidos.add(posicaoVazia - 4); // Cima
            if (posicaoVazia + 4 < 16) movimentosValidos.add(posicaoVazia + 4); // Baixo
            if (posicaoVazia % 4 != 0) movimentosValidos.add(posicaoVazia - 1); // Esquerda 
            if (posicaoVazia % 4 != 3) movimentosValidos.add(posicaoVazia + 1); // Direita 

            if (!movimentosValidos.isEmpty()) {
                int movimentoAleatorio = movimentosValidos.get((int)(Math.random() * movimentosValidos.size()));
                Collections.swap(posicoes, posicaoVazia, movimentoAleatorio);
                posicaoVazia = movimentoAleatorio;
            }
        }

        atualizarBotoes();
    }

    private boolean verificarVitoria() {
        for (int i = 0; i < 15; i++) {
            if (posicoes.get(i) != i + 1) {
                return false;
            }
        }
        return posicoes.get(15) == 0;
    }

    private void reiniciarContadores() {
        segundosPassados = 0;
        numeroCliques = 0;
        jLabel2.setText("00:00"); //reinicia o texto do cronômetro
        jLabel4.setText("0"); //reinicia o texto do contador de cliques
        cronometroTimer.restart(); //reinicia o timer
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        b1 = new javax.swing.JButton();
        b2 = new javax.swing.JButton();
        b3 = new javax.swing.JButton();
        b4 = new javax.swing.JButton();
        b5 = new javax.swing.JButton();
        b6 = new javax.swing.JButton();
        b7 = new javax.swing.JButton();
        b8 = new javax.swing.JButton();
        b9 = new javax.swing.JButton();
        b10 = new javax.swing.JButton();
        b11 = new javax.swing.JButton();
        b12 = new javax.swing.JButton();
        b13 = new javax.swing.JButton();
        b14 = new javax.swing.JButton();
        b15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        b1.setBackground(new java.awt.Color(239, 239, 239));
        b1.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        b1.setText("1");
        b1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b1ActionPerformed(evt);
            }
        });
        getContentPane().add(b1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 80, 80));

        b2.setBackground(new java.awt.Color(239, 239, 239));
        b2.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        b2.setText("2");
        b2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b2ActionPerformed(evt);
            }
        });
        getContentPane().add(b2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 80, 80));

        b3.setBackground(new java.awt.Color(239, 239, 239));
        b3.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        b3.setText("3");
        b3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b3ActionPerformed(evt);
            }
        });
        getContentPane().add(b3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 80, 80));

        b4.setBackground(new java.awt.Color(239, 239, 239));
        b4.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        b4.setText("4");
        getContentPane().add(b4, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 50, 80, 80));

        b5.setBackground(new java.awt.Color(239, 239, 239));
        b5.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        b5.setText("5");
        getContentPane().add(b5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 80, 80));

        b6.setBackground(new java.awt.Color(239, 239, 239));
        b6.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        b6.setText("6");
        b6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b6ActionPerformed(evt);
            }
        });
        getContentPane().add(b6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 80, 80));

        b7.setBackground(new java.awt.Color(239, 239, 239));
        b7.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        b7.setText("7");
        getContentPane().add(b7, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 80, 80));

        b8.setBackground(new java.awt.Color(239, 239, 239));
        b8.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        b8.setText("8");
        getContentPane().add(b8, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 130, 80, 80));

        b9.setBackground(new java.awt.Color(239, 239, 239));
        b9.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        b9.setText("9");
        getContentPane().add(b9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 80, 80));

        b10.setBackground(new java.awt.Color(239, 239, 239));
        b10.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        b10.setText("10");
        getContentPane().add(b10, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 80, 80));

        b11.setBackground(new java.awt.Color(239, 239, 239));
        b11.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        b11.setText("11");
        getContentPane().add(b11, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 210, 80, 80));

        b12.setBackground(new java.awt.Color(239, 239, 239));
        b12.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        b12.setText("12");
        getContentPane().add(b12, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 210, 80, 80));

        b13.setBackground(new java.awt.Color(239, 239, 239));
        b13.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        b13.setText("13");
        getContentPane().add(b13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 80, 80));

        b14.setBackground(new java.awt.Color(239, 239, 239));
        b14.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        b14.setText("14");
        getContentPane().add(b14, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 290, 80, 80));

        b15.setBackground(new java.awt.Color(239, 239, 239));
        b15.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        b15.setText("15");
        getContentPane().add(b15, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 290, 80, 80));

        jButton16.setBackground(new java.awt.Color(239, 239, 239));
        jButton16.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        getContentPane().add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 290, 80, 80));

        jLabel1.setBackground(new java.awt.Color(153, 153, 153));
        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("     Cronômetro");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 200, 100, 30));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("0:00");
        jLabel2.setAlignmentY(0.0F);
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 230, 100, 30));

        jLabel3.setBackground(new java.awt.Color(153, 153, 153));
        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("    nº de jogadas");
        jLabel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, 100, 30));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("0");
        jLabel4.setAlignmentY(0.0F);
        jLabel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 170, 100, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel5.setText("BOA SORTE!!!");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, -1));

        setBounds(0, 0, 516, 508);
    }// </editor-fold>//GEN-END:initComponents

    private void b6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b6ActionPerformed

    private void b3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b3ActionPerformed

    private void b2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b2ActionPerformed

    private void b1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b1;
    private javax.swing.JButton b10;
    private javax.swing.JButton b11;
    private javax.swing.JButton b12;
    private javax.swing.JButton b13;
    private javax.swing.JButton b14;
    private javax.swing.JButton b15;
    private javax.swing.JButton b2;
    private javax.swing.JButton b3;
    private javax.swing.JButton b4;
    private javax.swing.JButton b5;
    private javax.swing.JButton b6;
    private javax.swing.JButton b7;
    private javax.swing.JButton b8;
    private javax.swing.JButton b9;
    private javax.swing.JButton jButton16;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables

}
