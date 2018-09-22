import java.io.File;
import javax.swing.JTree;
import javax.swing.tree.*;
import javax.swing.*;
import sun.font.CreatedFontTracker;

public class treeUpdater {
    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
    private JTree tree;
    
    public JTree get()
    {
        run();
        return tree;
    }
    
    public void run(){
        File fileRoot=new File("/home/mojojojo/Adapter-IDEProjects");
        root=new DefaultMutableTreeNode(new FileNode(fileRoot));
        treeModel=new DefaultTreeModel(root);
        //System.out.println("reach");
        tree=new JTree(treeModel);
        tree.setShowsRootHandles(true);
        CreateChildNodes ccn=new CreateChildNodes(fileRoot,root);
        new Thread(ccn).start();
    }
    
    public class CreateChildNodes implements Runnable{
        private DefaultMutableTreeNode root;
        private File fileRoot;
        public CreateChildNodes(File fileRoot,DefaultMutableTreeNode root)
        {
            this.fileRoot=fileRoot;
            this.root=root;
        }
        @Override
        public void run()
        {
            createChildren(fileRoot,root);
        }
        private void createChildren(File fileRoot,DefaultMutableTreeNode node)
        {
            File[] files=fileRoot.listFiles();
            if(files==null)
                return;
            for(File file : files){
                DefaultMutableTreeNode childNode=new DefaultMutableTreeNode(new FileNode(file));
                node.add(childNode);
                if(file.isDirectory())
                {
                    createChildren(file, childNode);
                }
            }
        }
    }
    
    public class FileNode{
        private File file;
        public FileNode(File file)
        {
            this.file=file;
        }
        
        @Override 
        public String toString(){
            String name=file.getName();
            if(name.equals(""))
            {
                return file.getAbsolutePath();
            }
            else
                return name;
        }
    }
}
