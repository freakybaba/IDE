
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.tree.TreePath;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import org.fife.ui.rtextarea.*;
import org.fife.ui.rsyntaxtextarea.*;

public class home extends javax.swing.JFrame {

    private String fpath=null;
    private String fname=null;
    private LinkedList<String> plist=new LinkedList<String>();
    private LinkedList<String> nlist=new LinkedList<String>();
    private JTree tree=new JTree();
    private String face="Abyssinica SIL";
    private int fsize=18;
    private int flagProj=1,flagStat=1,projSizeX,projSizeY,statSizeX,statSizeY,fullFlag=1,geeksFlag=0;
    private int count,op=(int)'{',cl=(int)'}',prev=0,indflag=0;
    private JPopupMenu jp=new JPopupMenu();
    private BrowserView view;
    
    
    public home() {
        initComponents();
        this.setBounds(0, 0, 1864,1040);
        
        ttree();
        plist.add(null);
        nlist.add(null);
        projSizeX=projectPane.getWidth();
        projSizeY=projectPane.getHeight();
        statSizeX=jScrollPane2.getWidth();
        statSizeY=jScrollPane2.getHeight();
        JMenuItem m1=new JMenuItem();
        m1.setText("Cut");
        jp.add(m1);
        m1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutMenuActionPerformed(evt);
            }
        });
        JMenuItem m2=new JMenuItem();
        m2.setText("Copy");
        jp.add(m2);
        m2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyMenuActionPerformed(evt);
            }
        });
        JMenuItem m3=new JMenuItem();
        m3.setText("Paste");
        jp.add(m3);
        m3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteMenuActionPerformed(evt);
            }
        });
        JMenuItem m4=new JMenuItem();
        m4.setText("Save");
        jp.add(m4);
        m4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuActionPerformed(evt);
            }
        });
        JMenuItem m5=new JMenuItem();
        m5.setText("Run");
        jp.add(m5);
        m5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runMenuActionPerformed(evt);
            }
        });
        JMenuItem m6=new JMenuItem();
        m6.setText("Full Screen");
        jp.add(m6);
        m6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fullMenuActionPerformed(evt);
            }
        });
        
    }
    
    
    
    void unDoreDo(JTextArea jt)
    {
        final UndoManager undo = new UndoManager();
   Document doc = jt.getDocument();
    
   // Listen for undo and redo events
   doc.addUndoableEditListener(new UndoableEditListener() {
       public void undoableEditHappened(UndoableEditEvent evt) {
           undo.addEdit(evt.getEdit());
       }
   });
    
   // Create an undo action and add it to the text component
   jt.getActionMap().put("Undo",
       new AbstractAction("Undo") {
           public void actionPerformed(ActionEvent evt) {
               try {
                   if (undo.canUndo()) {
                       undo.undo();
                   }
               } catch (CannotUndoException e) {
               }
           }
      });
    
   // Bind the undo action to ctl-Z
   jt.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");
    
   // Create a redo action and add it to the text component
   jt.getActionMap().put("Redo",
       new AbstractAction("Redo") {
           public void actionPerformed(ActionEvent evt) {
               try {
                   if (undo.canRedo()) {
                       undo.redo();
                   }
               } catch (CannotRedoException e) {
               }
           }
       });
    
   // Bind the redo action to ctl-Y
   jt.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "Redo");
    
    }
    
    
    private void ttree()
    {
        treeUpdater l=new treeUpdater();
        tree=l.get();
        tree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                treeValueChanged(evt);
            }
        });
        projectPane.getViewport().add(tree);
    }
    
    private void treeValueChanged(javax.swing.event.TreeSelectionEvent evt)
    {
       try{
        TreePath p=tree.getSelectionPath();
        Object[] pa=p.getPath();
        int l=p.getPathCount();
        String tfpath="";
        for(int i=0;i<l-1;i++)
            tfpath="/"+pa[i];
        String fnme=""+pa[l-1];
        if(fnme.endsWith(".c")||fnme.endsWith(".cpp")||fnme.endsWith(".java"))
        {
            fnme="/home/mojojojo/Adapter-IDEProjects"+tfpath;
            String filename=""+pa[l-1];
                plist.add(fnme);
                nlist.add(filename);
                open op=new open();
                String s=op.get(fnme+"/"+filename);
                RSyntaxTextArea syn=new RSyntaxTextArea();
                if(filename.endsWith(".c"))
                syn.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
                else if(filename.endsWith(".cpp"))
                 syn.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
                else
                    syn.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
                JTextArea jt=syn;
                jt.setText(s);
                jt.addCaretListener(new javax.swing.event.CaretListener() {
                public void caretUpdate(javax.swing.event.CaretEvent evt) {
                textAreaCaretUpdate(evt);
                }
                });
                jt.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                textAreaKeyTyped(evt);
                 }
                });
                JScrollPane js=new JScrollPane();
                js.getViewport().add(jt);
                pane.add(""+filename,js);
                pane.setSelectedIndex(plist.size()-1);
                               
        }
        
       }catch(Exception e)
       {
           System.out.println(e);
       }
    }
    
    void textAreaCaretUpdate(javax.swing.event.CaretEvent evt)
    {
        JScrollPane js=(JScrollPane)pane.getSelectedComponent();
            JTextArea jt=(JTextArea)js.getViewport().getView();
       
            int n=jt.getCaretPosition();
        String s=jt.getText();
        int x=0, y=0;
        for(int i=0;i<n;i++)
        { 
            char c=s.charAt(i);
            x=x+1;
            if(c=='\n')
            {
                x=0;
                y++;
            }
        }
        posLabel.setText(x+" , "+y);
    }
    
    void textAreaKeyTyped(java.awt.event.KeyEvent evt)
    {
       /* JScrollPane js=(JScrollPane)pane.getSelectedComponent();
        JTextArea jt=(JTextArea)js.getViewport().getView();
        if(jt.getText().equals(""));
            indflag=0;
        char c=evt.getKeyChar();
        int asc=(int)c;
        System.out.print(asc+" ");
        String add="           ";
        String s="     ",s2="  ";
        if(asc==op)
          ++count;
        if(asc==cl)
          --count;
        if(asc==10 && prev!=62)
        {
             String toAdd=add;
             for(int i=0;i<count;i++)
                 toAdd=toAdd+s;
             if(prev==41)
                 toAdd+=s2;
             if(prev==41&&indflag==0)
             {
                 toAdd=s2;
                 indflag=1;
             }
             
             jt.setT;
        }
        prev=asc;
        System.out.println("Here "+count);*/
            
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        cond = new javax.swing.JTextPane();
        pane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        bgBut = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        fontStyleBut = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jcloseTab = new javax.swing.JButton();
        projectPane = new javax.swing.JScrollPane();
        deleteBut = new javax.swing.JButton();
        copyIcon = new javax.swing.JButton();
        newFileIcon = new javax.swing.JButton();
        newFileInOIcon = new javax.swing.JButton();
        newProjectIcon = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        cutIcon = new javax.swing.JButton();
        pasteIcon = new javax.swing.JButton();
        saveIcon = new javax.swing.JButton();
        runIcon = new javax.swing.JButton();
        pathlbl = new javax.swing.JLabel();
        coProjects = new javax.swing.JButton();
        coStatus = new javax.swing.JButton();
        posLabel = new javax.swing.JLabel();
        goGeeks = new javax.swing.JButton();
        geeksPane = new javax.swing.JScrollPane();
        jMenu = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        newProjectMenu = new javax.swing.JMenuItem();
        newFileMenu = new javax.swing.JMenuItem();
        saveMenu = new javax.swing.JMenuItem();
        newFileInOMenu = new javax.swing.JMenuItem();
        openFileMenu = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        cutMenu = new javax.swing.JMenuItem();
        copyMenu = new javax.swing.JMenuItem();
        pasteMenu = new javax.swing.JMenuItem();
        fullMenu = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        runMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Adapter-IDE");
        setBounds(new java.awt.Rectangle(0, 0, 1875, 964));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        setSize(new java.awt.Dimension(1875, 964));
        getContentPane().setLayout(null);

        cond.setEditable(false);
        cond.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "STATUS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 14))); // NOI18N
        cond.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        jScrollPane2.setViewportView(cond);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(250, 740, 1590, 195);

        pane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                paneStateChanged(evt);
            }
        });

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        bgBut.setText("Click Here");
        bgBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bgButActionPerformed(evt);
            }
        });

        jLabel1.setText("Change Background");

        jLabel2.setText("Change Font and Size");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Abyssinica SIL", "Ani", "DejaVu Sans Light", "Laksaman", "Mathjax_Fraktur", "Mathjax_Math", "Pursia", "URW ChanceryL" }));

        jTextField1.setText("18");

        jLabel3.setText("= Font Size");

        fontStyleBut.setText("Set");
        fontStyleBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fontStyleButActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Nimbus Mono L", 0, 36)); // NOI18N
        jLabel4.setText("Smarter Way To Code");

        jLabel5.setFont(new java.awt.Font("Nimbus Mono L", 0, 24)); // NOI18N
        jLabel5.setText("Shortcut");

        jLabel6.setText("New Project                     Ctrl+Shift+N");

        jLabel7.setFont(new java.awt.Font("Nimbus Mono L", 0, 36)); // NOI18N
        jLabel7.setText("     For");

        jLabel8.setFont(new java.awt.Font("Nimbus Mono L", 0, 36)); // NOI18N
        jLabel8.setText("Alt+B");

        jLabel9.setFont(new java.awt.Font("Nimbus Mono L", 0, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(43, 200, 43));
        jLabel9.setText("GeeksForGeeks");

        jLabel10.setFont(new java.awt.Font("Nimbus Mono L", 0, 36)); // NOI18N
        jLabel10.setText("   Geeks!");

        jLabel11.setText("New File                             Ctrl+N");

        jLabel12.setText("Save                                   Ctrl+S");

        jLabel13.setText("Open File                           Ctrl+O");

        jLabel14.setText("Cut                                      Ctrl+X");

        jLabel15.setText("Copy                                    Ctrl+C");

        jLabel16.setText("Paste                                  Ctrl+V    ");

        jLabel17.setText("Full Screen                        Ctrl+Shift+Enter");

        jLabel18.setText("Run                                     Shift+F6");

        jLabel19.setText("Close Tab                          Alt+X");

        jLabel20.setText("Delete                               Alt+D");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(435, 435, 435)
                                .addComponent(jLabel10))
                            .addComponent(jLabel13)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 533, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bgBut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(fontStyleBut, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel3))))
                        .addGap(1, 1, 1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15))
                                .addGap(496, 496, 496)
                                .addComponent(jLabel8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel11))
                                .addGap(382, 382, 382)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel7))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17)
                            .addComponent(jLabel16))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(fontStyleBut, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(bgBut, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel20))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(108, Short.MAX_VALUE))))
        );

        pane.addTab("WELCOME", jPanel1);

        getContentPane().add(pane);
        pane.setBounds(248, 55, 1590, 680);

        jcloseTab.setMnemonic('X');
        jcloseTab.setText("CLOSE TAB");
        jcloseTab.setToolTipText("");
        jcloseTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcloseTabActionPerformed(evt);
            }
        });
        getContentPane().add(jcloseTab);
        jcloseTab.setBounds(1324, 12, 117, 31);

        projectPane.setViewportBorder(javax.swing.BorderFactory.createTitledBorder("Projects"));
        projectPane.setMinimumSize(new java.awt.Dimension(2, 2));
        projectPane.setPreferredSize(new java.awt.Dimension(0, 0));
        getContentPane().add(projectPane);
        projectPane.setBounds(0, 55, 242, 880);

        deleteBut.setMnemonic('D');
        deleteBut.setText("DELETE FILE");
        deleteBut.setToolTipText("");
        deleteBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButActionPerformed(evt);
            }
        });
        getContentPane().add(deleteBut);
        deleteBut.setBounds(1459, 12, 117, 31);

        copyIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/fcopy.png"))); // NOI18N
        copyIcon.setToolTipText("Copy (Ctrl+c)");
        copyIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyIconActionPerformed(evt);
            }
        });
        getContentPane().add(copyIcon);
        copyIcon.setBounds(307, 0, 47, 49);

        newFileIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/nfile.png"))); // NOI18N
        newFileIcon.setToolTipText("New File (Ctrl+N)");
        newFileIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newFileIconActionPerformed(evt);
            }
        });
        getContentPane().add(newFileIcon);
        newFileIcon.setBounds(12, 0, 47, 49);

        newFileInOIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/nproject.png"))); // NOI18N
        newFileInOIcon.setToolTipText("New File in Other Project(Shit+N)");
        newFileInOIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newFileInOIconActionPerformed(evt);
            }
        });
        getContentPane().add(newFileInOIcon);
        newFileInOIcon.setBounds(71, 0, 47, 49);

        newProjectIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/finother.png"))); // NOI18N
        newProjectIcon.setToolTipText("New Project(Ctrl+Shit+N)");
        newProjectIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newProjectIconActionPerformed(evt);
            }
        });
        getContentPane().add(newProjectIcon);
        newProjectIcon.setBounds(130, 0, 47, 49);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/finsame.jpg"))); // NOI18N
        jButton5.setToolTipText("Open File(Ctrl+O)");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5);
        jButton5.setBounds(189, 0, 47, 49);

        cutIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/fcut.png"))); // NOI18N
        cutIcon.setToolTipText("Cut(Ctrl+X)");
        cutIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutIconActionPerformed(evt);
            }
        });
        getContentPane().add(cutIcon);
        cutIcon.setBounds(248, 0, 47, 49);

        pasteIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/fpaste.png"))); // NOI18N
        pasteIcon.setToolTipText("Paste(Ctrl+V)");
        pasteIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteIconActionPerformed(evt);
            }
        });
        getContentPane().add(pasteIcon);
        pasteIcon.setBounds(366, 0, 47, 49);

        saveIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/fsave.jpg"))); // NOI18N
        saveIcon.setToolTipText("Save(Ctrl+S)");
        saveIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveIconActionPerformed(evt);
            }
        });
        getContentPane().add(saveIcon);
        saveIcon.setBounds(425, 0, 47, 49);

        runIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/frun.png"))); // NOI18N
        runIcon.setToolTipText("Run(Ctrl+F6)");
        runIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runIconActionPerformed(evt);
            }
        });
        getContentPane().add(runIcon);
        runIcon.setBounds(484, 0, 47, 49);

        pathlbl.setBackground(new java.awt.Color(199, 195, 195));
        getContentPane().add(pathlbl);
        pathlbl.setBounds(90, 950, 486, 24);

        coProjects.setText(".");
        coProjects.setToolTipText("Close/Open Project Window");
        coProjects.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coProjectsActionPerformed(evt);
            }
        });
        getContentPane().add(coProjects);
        coProjects.setBounds(40, 950, 31, 31);

        coStatus.setText(".");
        coStatus.setToolTipText("Close/Open Status");
        coStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coStatusActionPerformed(evt);
            }
        });
        getContentPane().add(coStatus);
        coStatus.setBounds(1550, 950, 31, 31);

        posLabel.setFont(new java.awt.Font("Noto Sans", 0, 18)); // NOI18N
        posLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(posLabel);
        posLabel.setBounds(1640, 950, 127, 24);

        goGeeks.setMnemonic('B');
        goGeeks.setText("Go Geeks");
        goGeeks.setToolTipText("");
        goGeeks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goGeeksActionPerformed(evt);
            }
        });
        getContentPane().add(goGeeks);
        goGeeks.setBounds(1594, 12, 117, 31);

        geeksPane.setBackground(new java.awt.Color(252, 113, 113));
        getContentPane().add(geeksPane);
        geeksPane.setBounds(1850, 60, 0, 922);

        jMenu.setMinimumSize(new java.awt.Dimension(2, 5));
        jMenu.setPreferredSize(new java.awt.Dimension(87, 30));

        jMenu1.setMnemonic('f');
        jMenu1.setText("File");

        newProjectMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        newProjectMenu.setText("New Project...");
        newProjectMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newProjectMenuMouseClicked(evt);
            }
        });
        newProjectMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newProjectMenuActionPerformed(evt);
            }
        });
        jMenu1.add(newProjectMenu);

        newFileMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newFileMenu.setText("New File...");
        newFileMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newFileMenuActionPerformed(evt);
            }
        });
        jMenu1.add(newFileMenu);

        saveMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenu.setText("Save...");
        saveMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuActionPerformed(evt);
            }
        });
        jMenu1.add(saveMenu);

        newFileInOMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.SHIFT_MASK));
        newFileInOMenu.setText("New File in Other Project..");
        newFileInOMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newFileInOMenuActionPerformed(evt);
            }
        });
        jMenu1.add(newFileInOMenu);

        openFileMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openFileMenu.setText("Open File");
        openFileMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileMenuActionPerformed(evt);
            }
        });
        jMenu1.add(openFileMenu);

        jMenu.add(jMenu1);

        jMenu2.setText("Edit");

        cutMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        cutMenu.setText("Cut");
        cutMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutMenuActionPerformed(evt);
            }
        });
        jMenu2.add(cutMenu);

        copyMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        copyMenu.setText("Copy");
        copyMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyMenuActionPerformed(evt);
            }
        });
        jMenu2.add(copyMenu);

        pasteMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        pasteMenu.setText("Paste");
        pasteMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteMenuActionPerformed(evt);
            }
        });
        jMenu2.add(pasteMenu);

        fullMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        fullMenu.setText("Full Screen");
        fullMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fullMenuActionPerformed(evt);
            }
        });
        jMenu2.add(fullMenu);

        jMenu.add(jMenu2);

        jMenu3.setText("Run");

        runMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, java.awt.event.InputEvent.SHIFT_MASK));
        runMenu.setText("Run");
        runMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runMenuActionPerformed(evt);
            }
        });
        jMenu3.add(runMenu);

        jMenu.add(jMenu3);

        setJMenuBar(jMenu);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newProjectMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newProjectMenuMouseClicked

        
    }//GEN-LAST:event_newProjectMenuMouseClicked

    private void newFileMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newFileMenuActionPerformed
        JScrollPane js;
        JTextArea jt=new JTextArea();
        RSyntaxTextArea syn=new RSyntaxTextArea();
        if(fpath==null)
        {
            JFileChooser jf=new JFileChooser();
            jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jf.showDialog(this,"Select Folder");
            File file=jf.getSelectedFile();
            if(file!=null)
            {
                fpath=file.getAbsolutePath();
            }
        }
        String fname=JOptionPane.showInputDialog("Enter file name with extension (.c OR .cpp OR .java)");
      String[] comm=null;
        if(fpath!=null&& fname!=null)
        {
            boolean b=false;
            int t=0;
            if(fname.endsWith(".c"))
                t=1;
            else if(fname.endsWith(".cpp"))
                t=2;
            else if(fname.endsWith(".java"))
                t=3;
            
            if(t==1)
            {
                comm=new String[]{"/bin/sh","-c","printf \'#include<stdio.h>\n\nint main()\n{\n    printf(\"Hello World!\");\n    return 0;\n}\'> "+fname};
                syn.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
            }
            if(t==2)
            {
                comm=new String[]{"/bin/sh","-c","printf \'#include<iostream>\n#include<bits/stdc++.h>\n\nusing namespace std;\n\nint main()\n{\n    printf(\"Hello World!\");\n    return 0;\n}\'> "+fname};
                syn.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
            }
            if(t==3)
            {
                int l=fname.length();
                String g=fname.substring(0,l-5);
                comm=new String[]{"/bin/sh","-c","printf \'public class "+g+"\n{\n "
                        + "     public static void main(String args[])\n    {\n       System.out.print(\"Hello java\");"
                        + "\n    }\n}  \'> "+fname};
                syn.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
                Script s=new Script();
                    s.scr(g,fpath);
                
            }
            if(t!=0)
            {
            ProcessBuilder pb=new ProcessBuilder(comm);
              pb.directory(new File(fpath));
              try{
              Process p=pb.start();
              while(p.isAlive());
              }
              catch(Exception e)
              {
                   System.out.println(e);
              }
            }
            if(t!=0)
            {
                plist.add(fpath);
                nlist.add(fname);
                open op=new open();
                String s=op.get(fpath+"/"+fname);
                jt=syn;
                jt.setText(s);
                jt.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                textAreaCaretUpdate(evt);
            }
        });     
                jt.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                textAreaKeyTyped(evt);
                 }
                });
                js=new JScrollPane(jt);
                pane.add(""+fname,js);
                pane.setSelectedIndex(plist.size()-1);
            }
        }
        ttree();
    }//GEN-LAST:event_newFileMenuActionPerformed

    @SuppressWarnings("empty-statement")
    private void newProjectMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newProjectMenuActionPerformed
        JFileChooser jf=new JFileChooser();
        jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jf.showDialog(this,"Select Folder");
        File file=jf.getSelectedFile();
        if(file!=null)
        {
        String fullp=file.getAbsolutePath();
        String name=JOptionPane.showInputDialog(null,"Project Name");
        fpath=fullp+"/"+name;
        if(name!=null)
           {
              ProcessBuilder pb1=new ProcessBuilder("/bin/sh","-c","mkdir "+name);
              ProcessBuilder pb2=new ProcessBuilder("/bin/sh","-c","mkdir Resource");
              ProcessBuilder pb5=new ProcessBuilder("/bin/sh","-c","touch state");
              
              pb1.directory(new File(fullp));
              pb2.directory(new File(fullp+"/"+name));
              pb5.directory(new File(fullp+"/"+name+"/Resource"));
              
              Process p1,p2,p3,p4,p5;
              try{
                    p1=pb1.start();
                    while(p1.isAlive());
                    p2=pb2.start();
                    while(p2.isAlive());
                    p5=pb5.start();
                    Script s=new Script();
                    s.scr("ZEH",fullp+"/"+name);
              
              }
              catch(Exception e)
                      {
                          System.out.println(e);
                      }
           }
        }
        ttree();
    }//GEN-LAST:event_newProjectMenuActionPerformed

    private void jcloseTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcloseTabActionPerformed
        
        int index=pane.getSelectedIndex();
        if(index!=0)
        {
            pane.remove(index);
            plist.remove(index);
            nlist.remove(index);
        }
        else
            JOptionPane.showMessageDialog(rootPane, "Can not close this");
    }//GEN-LAST:event_jcloseTabActionPerformed

    private void runMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runMenuActionPerformed
       

        if(flagStat==0)
        {
            this.coStatusActionPerformed(evt);
        }
        long startTime=(long) 0.0,stopTime=(long) 0.0;
        if(pane.getSelectedIndex()==0)
            return;
       
           // home g=new home();
           int flag=1;
           String cm1[]=null;
           String cm2[]=null;
           
            this.saveMenuActionPerformed(evt);
           
            String path=plist.get(pane.getSelectedIndex());
            String name=nlist.get(pane.getSelectedIndex());
            int t=0;
            if(name.endsWith(".c"))
                t=1;
            else if(name.endsWith(".cpp"))
                t=2;
            else if(name.endsWith(".java"))
                t=3;
            
            if(t==1)
            {
                cm1=new String[]{"/bin/sh","-c","gcc "+name+" 2> "+path+"/Resource/state"};
                cm2=new String[]{"/bin/sh","-c","./ZEH.sh"};
            }
            if(t==2)
            {
                cm1=new String[]{"/bin/sh","-c","g++ "+name+" 2> "+path+"/Resource/state"};
                cm2=new String[]{"/bin/sh","-c","./ZEH.sh"};
            }
            if(t==3)
            {
                int l=name.length();
                String g=name.substring(0,l-5);
                cm1=new String[]{"/bin/sh","-c","javac "+name+" 2> "+path+"/Resource/state"};
                cm2=new String[]{"/bin/sh","-c","./"+g+".sh"};
            }
            ProcessBuilder pb1=new ProcessBuilder(cm1);
            ProcessBuilder pb2=new ProcessBuilder(cm2);
            
            pb1.directory(new File(path));
            pb2.directory(new File(path+"/Resource"));
            
            try{
                 startTime=System.nanoTime();
            Process p=pb1.start();
                 stopTime=System.nanoTime();
            while(p.isAlive());
            }
            catch(Exception e)
            {
                
            }
            
            try{
                String curr=null,s=null;
            FileReader fr=new FileReader(path+"/Resource/state");
            BufferedReader br=new BufferedReader(fr);
            if(br.readLine()==null)
                cond.setText("Successfull Execution :-)\nCompilation Time : " +(((double)(stopTime-startTime))/1000000000.0));
            else
            {  
                flag=0;
                s="";
                while((curr=br.readLine())!=null)
               {
                s=s+curr;
                s=s+"\n";
               }
              cond.setText(s);
            }
            }
            catch(Exception e)
            {
            }
            if(flag==1)
            {
            try {
                Process pb=pb2.start();
            } catch (IOException ex) {
                Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
       
    }//GEN-LAST:event_runMenuActionPerformed

    private void saveMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuActionPerformed
        Save sv=new Save();
        int ind=pane.getSelectedIndex();
        if(ind>0)
        {
            JScrollPane js=(JScrollPane)pane.getSelectedComponent();
            int n=sv.save(js,plist.get(ind),nlist.get(ind));
        }
    }//GEN-LAST:event_saveMenuActionPerformed

    private void cutMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutMenuActionPerformed
        //cut
        int ind=pane.getSelectedIndex();
        if(ind>0)
        {
            JScrollPane js=(JScrollPane)pane.getSelectedComponent();
            JTextArea jt=(JTextArea)js.getViewport().getView();
            jt.cut();
        }
        
    }//GEN-LAST:event_cutMenuActionPerformed

    private void copyMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyMenuActionPerformed
        // copy
        int ind=pane.getSelectedIndex();
        if(ind>0)
        {
            JScrollPane js=(JScrollPane)pane.getSelectedComponent();
            JTextArea jt=(JTextArea)js.getViewport().getView();
            jt.copy();
        }
    }//GEN-LAST:event_copyMenuActionPerformed

    private void pasteMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteMenuActionPerformed
        // paste
        int ind=pane.getSelectedIndex();
        if(ind>0)
        {
            JScrollPane js=(JScrollPane)pane.getSelectedComponent();
            JTextArea jt=(JTextArea)js.getViewport().getView();
            jt.paste();
        }
    }//GEN-LAST:event_pasteMenuActionPerformed

    private void newFileInOMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newFileInOMenuActionPerformed
        // TODO add your handling code here:
        fpath=null;
        this.newFileMenuActionPerformed(evt);
    }//GEN-LAST:event_newFileInOMenuActionPerformed

    private void deleteButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButActionPerformed
        int index=pane.getSelectedIndex();
        if(index!=0)
        {
            
            pane.remove(index);
            String p=plist.get(index);
 
            plist.remove(index);
            String n=nlist.get(index);
            String g=n;
            nlist.remove(index);
            if(n.endsWith(".java"))
            {
                int l=n.length();
                g=n.substring(0,l-5);
            }
            ProcessBuilder pb1=new ProcessBuilder("/bin/sh","-c","rm "+n);
            ProcessBuilder pb2=new ProcessBuilder("/bin/sh","-c","rm "+g+".sh");
            pb1.directory(new File(p));
            pb2.directory(new File(p+"/"+"Resource"));
            try{
                Process p1=pb1.start();
                while(p1.isAlive());
                Process p2;
                if(n.endsWith(".java"))
                {
                    p2=pb2.start();
                    while(p2.isAlive());
                }
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            ttree();
        }
        else
            JOptionPane.showMessageDialog(rootPane, "Can not delete this");
        
    }//GEN-LAST:event_deleteButActionPerformed

    private void openFileMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileMenuActionPerformed
        // TODO add your handling code here:
        String filename=null,fpath=null;
        JFileChooser jf=new JFileChooser();
            jf.showDialog(this,"Select Desired File");
            File file=jf.getSelectedFile();
            if(file!=null)
            {
                filename=file.getName();
                fpath=file.getParent();
                plist.add(fpath);
                nlist.add(filename);
                open op=new open();
                String s=op.get(fpath+"/"+filename);
                RSyntaxTextArea syn=new RSyntaxTextArea();
                if(filename.endsWith(".c"))
                syn.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
                else if(filename.endsWith(".cpp"))
                 syn.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS);
                else
                    syn.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
                JTextArea jt=syn;
                jt.setText(s);
                jt.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                textAreaCaretUpdate(evt);
            }
        });
                jt.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                textAreaKeyTyped(evt);
                 }
                });
                JScrollPane js=new JScrollPane(jt);
                pane.add(""+filename,js);
                pane.setSelectedIndex(plist.size()-1);
            }
    }//GEN-LAST:event_openFileMenuActionPerformed

    private void paneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_paneStateChanged
        // TODO add your handling code here:
        posLabel.setText("");
        int i=pane.getSelectedIndex();
        if(i>0)
        {  
            
            posLabel.setText("0 , 0");
            pathlbl.setText("Path of file= "+plist.get(i));
            JScrollPane jt=(JScrollPane) pane.getSelectedComponent();
            JTextArea j=(JTextArea) jt.getViewport().getView();
            Font f=new Font(face,Font.PLAIN,fsize);
            j.setFont(f);
            j.setComponentPopupMenu(jp);
            unDoreDo(j);
        }
        if(i==0)
        pathlbl.setText("");  
    }//GEN-LAST:event_paneStateChanged

    private void newFileIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newFileIconActionPerformed
        // TODO add your handling code here:
        this.newFileMenuActionPerformed(evt);
    }//GEN-LAST:event_newFileIconActionPerformed

    private void newFileInOIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newFileInOIconActionPerformed
        // TODO add your handling code here:
        this.newFileInOMenuActionPerformed(evt);
    }//GEN-LAST:event_newFileInOIconActionPerformed

    private void newProjectIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newProjectIconActionPerformed
        // TODO add your handling code here:
        this.newProjectMenuActionPerformed(evt);
    }//GEN-LAST:event_newProjectIconActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        this.openFileMenuActionPerformed(evt);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void cutIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutIconActionPerformed
        // TODO add your handling code here:
        this.cutMenuActionPerformed(evt);
    }//GEN-LAST:event_cutIconActionPerformed

    private void copyIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyIconActionPerformed
        // TODO add your handling code here:\
        this.copyMenuActionPerformed(evt);
    }//GEN-LAST:event_copyIconActionPerformed

    private void pasteIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteIconActionPerformed
        // TODO add your handling code here:
        this.pasteMenuActionPerformed(evt);
    }//GEN-LAST:event_pasteIconActionPerformed

    private void saveIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveIconActionPerformed
        // TODO add your handling code here:
        this.saveMenuActionPerformed(evt);
    }//GEN-LAST:event_saveIconActionPerformed

    private void runIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runIconActionPerformed
        // TODO add your handling code here:
        this.runMenuActionPerformed(evt);
    }//GEN-LAST:event_runIconActionPerformed

    private void bgButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bgButActionPerformed
        // TODO add your handling code here:
        Color initialcolor=Color.GRAY;
        Color colo=JColorChooser.showDialog(this,"Select Color",initialcolor);
        this.getContentPane().setBackground(colo);
        pathlbl.setOpaque(true);
        pathlbl.setBackground(Color.white);
    }//GEN-LAST:event_bgButActionPerformed

    private void fontStyleButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fontStyleButActionPerformed
        // TODO add your handling code here:
        int i=jComboBox1.getSelectedIndex();
        face=jComboBox1.getItemAt(i);
        fsize=Integer.parseInt(jTextField1.getText());
       // JFontChooser jf=new JFontChooser(new Font("Arial",Font.BOLD,30));
       //Font f=JFont
    }//GEN-LAST:event_fontStyleButActionPerformed

    private void coProjectsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coProjectsActionPerformed
        // TODO add your handling code here:
        if(flagProj==1)
        {
            projSizeX=projectPane.getWidth();
            projSizeY=projectPane.getHeight();
            projectPane.setBounds(projectPane.getX(),projectPane.getY(),1,projSizeY);
            pane.setBounds(projectPane.getX()+4, pane.getY(), (pane.getWidth()+projSizeX),pane.getHeight());
            jScrollPane2.setBounds(projectPane.getX()+4,jScrollPane2.getY(),(jScrollPane2.getWidth()+projSizeX),jScrollPane2.getHeight());
            cond.setBounds(projectPane.getX()+4,jScrollPane2.getY(),(jScrollPane2.getWidth()+projSizeX),jScrollPane2.getHeight());
            flagProj=0;
        }
        else
        {
            projectPane.setBounds(projectPane.getX(),projectPane.getY(),projSizeX,projSizeY);
            pane.setBounds(projSizeX, pane.getY(), (pane.getWidth()-projSizeX),pane.getHeight());
            jScrollPane2.setBounds(projSizeX,jScrollPane2.getY(),(jScrollPane2.getWidth()-projSizeX),jScrollPane2.getHeight());
            cond.setBounds(projSizeX,jScrollPane2.getY(),(jScrollPane2.getWidth()-projSizeX),jScrollPane2.getHeight());
            flagProj=1;
        }
    }//GEN-LAST:event_coProjectsActionPerformed

    private void coStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coStatusActionPerformed
        // TODO add your handling code here:
        System.out.println("hello");
        if(flagStat==1)
        {
            statSizeX=jScrollPane2.getWidth();
            statSizeY=jScrollPane2.getHeight();
            jScrollPane2.setBounds(jScrollPane2.getX(),jScrollPane2.getY()+statSizeY,jScrollPane2.getWidth(),0);
            cond.setBounds(jScrollPane2.getX(),jScrollPane2.getY()+statSizeY,jScrollPane2.getWidth(),0);
            pane.setBounds(pane.getX(),pane.getY(),pane.getWidth(),pane.getHeight()+statSizeY);
            flagStat=0;
        }
        else
        {
                
                pane.setBounds(pane.getX(),pane.getY(),pane.getWidth(),pane.getHeight()-statSizeY);
                jScrollPane2.setBounds(jScrollPane2.getX(),jScrollPane2.getY()-statSizeY,pane.getWidth(),statSizeY);
                cond.setBounds(jScrollPane2.getX(),jScrollPane2.getY()-statSizeY,pane.getWidth(),statSizeY);
                flagStat=1;     
        }
    }//GEN-LAST:event_coStatusActionPerformed

    private void fullMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullMenuActionPerformed
        // TODO add your handling code here:
        if(fullFlag==1)
        {
            flagStat=1;
            flagProj=1;
            this.coProjectsActionPerformed(evt);
            this.coStatusActionPerformed(evt);
            fullFlag=0;
        }
        else
        {
            flagStat=0;
            flagProj=0;
            this.coProjectsActionPerformed(evt);
            this.coStatusActionPerformed(evt);
            fullFlag=1;
        }
    }//GEN-LAST:event_fullMenuActionPerformed

    private void goGeeksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goGeeksActionPerformed
        
        if(geeksFlag==0)
        {
            
            pane.setBounds(pane.getX(), pane.getY(),pane.getWidth()-550,pane.getHeight());
            jScrollPane2.setBounds(jScrollPane2.getX(),jScrollPane2.getY(),jScrollPane2.getWidth()-550,jScrollPane2.getHeight());
            cond.setBounds(jScrollPane2.getX(),jScrollPane2.getY(),jScrollPane2.getWidth()-550,jScrollPane2.getHeight());
            geeksPane.setBounds(pane.getX()+pane.getWidth()+10,pane.getY(),530,pane.getHeight()+jScrollPane2.getHeight()); 
            geeksPane.setBorder(pane.getBorder());
            Browser browser = new Browser();
            view = new BrowserView(browser);
            browser.isZoomEnabled();
            geeksPane.getViewport().add(view);
            
        (new Thread(){
             public void run()
                      {
                          browser.loadURL("http://www.geeksforgeeks.org/");
                      }
        }).start();
        geeksFlag=1;
        }
        else
        {          
            pane.setBounds(pane.getX(), pane.getY(),pane.getWidth()+550,pane.getHeight());
            jScrollPane2.setBounds(pane.getX(),jScrollPane2.getY(),jScrollPane2.getWidth()+550,jScrollPane2.getHeight());
            cond.setBounds(pane.getX(),jScrollPane2.getY(),jScrollPane2.getWidth()+jScrollPane2.getWidth()+550,jScrollPane2.getHeight());
            geeksPane.setBounds(pane.getWidth()+pane.getX()+8,pane.getY(),0,pane.getHeight()+jScrollPane2.getHeight());
            geeksPane.setBorder(null);
            view.setSize(0,0);
            geeksFlag=0;
        }
        
          
    }//GEN-LAST:event_goGeeksActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bgBut;
    private javax.swing.JButton coProjects;
    private javax.swing.JButton coStatus;
    private javax.swing.JTextPane cond;
    private javax.swing.JButton copyIcon;
    private javax.swing.JMenuItem copyMenu;
    private javax.swing.JButton cutIcon;
    private javax.swing.JMenuItem cutMenu;
    private javax.swing.JButton deleteBut;
    private javax.swing.JButton fontStyleBut;
    private javax.swing.JMenuItem fullMenu;
    private javax.swing.JScrollPane geeksPane;
    private javax.swing.JButton goGeeks;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton jcloseTab;
    private javax.swing.JButton newFileIcon;
    private javax.swing.JButton newFileInOIcon;
    private javax.swing.JMenuItem newFileInOMenu;
    private javax.swing.JMenuItem newFileMenu;
    private javax.swing.JButton newProjectIcon;
    private javax.swing.JMenuItem newProjectMenu;
    private javax.swing.JMenuItem openFileMenu;
    private javax.swing.JTabbedPane pane;
    private javax.swing.JButton pasteIcon;
    private javax.swing.JMenuItem pasteMenu;
    private javax.swing.JLabel pathlbl;
    private javax.swing.JLabel posLabel;
    private javax.swing.JScrollPane projectPane;
    private javax.swing.JButton runIcon;
    private javax.swing.JMenuItem runMenu;
    private javax.swing.JButton saveIcon;
    private javax.swing.JMenuItem saveMenu;
    // End of variables declaration//GEN-END:variables
}
