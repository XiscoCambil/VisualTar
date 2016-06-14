import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by fjcambilr on 08/06/16.
 */
public class form {
    private JButton button1;
    private JTable table;
    private JPanel panel;
    private JScrollPane Scroll;
    private JFrame frame = new JFrame("Extraer archivo");
    private Programa p;
    private Tar t;

    public static void main(String[] args) {

        form form = new form();
        form.frame.setContentPane(form.panel);
        form.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.frame.pack();
        form.frame.setVisible(true);
    }

    public form(){
        JMenuBar jmb = new JMenuBar();
        JMenu m1 = new JMenu("File");
        JMenuItem load = new JMenuItem("Load");
        final JMenuItem exit = new JMenuItem("Exit");
        jmb.add(m1);
        m1.add(load);
        m1.add(new JSeparator());
        m1.add(exit);
        frame.setJMenuBar(jmb);
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                filltable();
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int row  = table.getSelectedRow();
                int cel = table.getSelectedColumn();
                JFileChooser jfc = new JFileChooser();
                Object o = table.getValueAt(row,0);
                String nom = (String) o;
                int code = jfc.showSaveDialog(frame);
                if(code == JFileChooser.APPROVE_OPTION){
                    try {
                        FileOutputStream f = new FileOutputStream(jfc.getSelectedFile());
                        f.write(t.lista.get(row).getContenido());

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private void filltable(){
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("File Name");
        dtm.addColumn("Size");
        JFileChooser jfc = new JFileChooser();
        int code = jfc.showOpenDialog(frame);
        if(code == JFileChooser.APPROVE_OPTION){
            File f = jfc.getSelectedFile();
            t = new Tar(f.getPath());
            t.expand();
            for (int i = 0; i < t.lista.size(); i++) {
                dtm.addRow(new String[]{t.lista.get(i).getNom(), String.valueOf(t.lista.get(i).getSize())});
            }
        }

        table.setModel(dtm);
    }

}
