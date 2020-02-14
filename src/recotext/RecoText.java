
package recotext;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
public class RecoText implements Runnable {
static int i=0;
static int j=0;
static File ff;
static String path;
static String textt;
static JLabel forDate = new JLabel();
    public static void main(String[] args) {
        
        
        JFrame win = new JFrame("RecoText");
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("file");
        JMenu help = new JMenu("help");
        JMenuItem exit = new JMenuItem("EXIT",new ImageIcon(RecoText.class.getResource("../images/exit-icon.png")));
        JMenuItem item = new JMenuItem("open",new ImageIcon(RecoText.class.getResource("../images/open-icon.png")));
        JMenuItem save = new JMenuItem("save",new ImageIcon(RecoText.class.getResource("../images/save-icon.png")));
        JMenuItem rename = new JMenuItem("rename",new ImageIcon(RecoText.class.getResource("../images/new-file-icon.png")));
        JMenuItem color = new JMenuItem("pick color");
        
        
        
        
        JLabel test = new JLabel();
        JTextArea text = new JTextArea();
       
        JScrollPane pane = new JScrollPane(text);
        JPopupMenu popupMenu  = new JPopupMenu();
        
        JMenuItem copy = new JMenuItem("copy");
        
        popupMenu.add(copy);
        text.add(popupMenu);
        

        
        win.setLayout(null);
        JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        win.setVisible(true);
        win.setSize(1360, 720);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setJMenuBar(menubar);
        win.add(pane);
        menubar.add(menu);
        menubar.add(help);
        menu.add(item);
        menu.add(save);
        menu.add(rename);
        menu.add(color);
        menu.addSeparator();
        menu.add(exit);
        JFileChooser ch = new JFileChooser();
        Font f = new Font("Serif",Font.BOLD,20);
        forDate.setBounds(0, 665, 300,20);
        win.add(forDate);
        Thread th = new Thread(new RecoText());
        th.start();
        text.setFont(f);
        win.add(tab);
        tab.add(pane);
        tab.setBounds(0, 0, 1366, 667);
        tab.setTitleAt(0, "tab");
        save.setEnabled(false);
        rename.setEnabled(false);

        
        exit.addActionListener(new ActionListener(){
          

            @Override
            public void actionPerformed(ActionEvent e) {
              int select =  JOptionPane.showConfirmDialog(null, "are you want to save :","EXIT", JOptionPane.YES_NO_CANCEL_OPTION);
              switch(select)
              {
                  case JOptionPane.YES_OPTION : save.addActionListener(this);break;
                  case JOptionPane.NO_OPTION : System.exit(0);break;
                  case JOptionPane.CANCEL_OPTION: break;
              }
               
            }
        });
        
        item.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ch.showOpenDialog(win);
                 ff = ch.getSelectedFile();
                 save.setEnabled(true);
        rename.setEnabled(true);
               path = ff.getPath();
               System.out.println(path);
                try {
                    BufferedReader read = new BufferedReader(new FileReader(path));
                    String line = "";
                    String textt = "";
                    while((line = read.readLine()) != null)
                    {
                        textt += line + "\n";
                        
                    }
                    text.setText(textt);
                    tab.setTitleAt(0, path);
                    path = "";
                    read.close();
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "erreur", JOptionPane.ERROR_MESSAGE);
                    
                } catch (IOException ex) {
                     JOptionPane.showMessageDialog(null, ex.getMessage(), "erreur", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        });
        color.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(null, "pick please",null);
                if(c != null)
                {
                
                    text.setFont(f);
                    text.setForeground(Color.WHITE);
                    text.setBackground(c);
                    if(Color.WHITE == text.getBackground())
                    {
                        text.setForeground(Color.BLACK);
                    }
                    
                }
                
            }
        });
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                                
           String tx = text.getText();
                try {
                    FileWriter write = new FileWriter(ff);
                    BufferedWriter wr = new BufferedWriter(write);
                    wr.write(tx +"\n");
                    wr.flush();
                    JOptionPane.showMessageDialog(null, "thank you", "save", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    Logger.getLogger(RecoText.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        rename.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String n = JOptionPane.showInputDialog(test, "rename", "rename", JOptionPane.OK_CANCEL_OPTION);
               if(n != null)
               {
               if(JOptionPane.OK_OPTION == 0)
               {
                   ch.setName(n);
               }
               } 
            }
        });
    }

@Override
    public void run() {
        
        try{
            
        while(!Thread.currentThread().isInterrupted())
        {
            Date d = new Date();
            SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a",Locale.FRANCE);
        forDate.setText(form.format(d));
          Thread.sleep(1000);
        }
        }catch(Exception r){
          forDate.setText(r.getMessage());
        }
    }
    
}
